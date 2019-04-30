package com.shoniz.saledistributemobility.data.model.update;

import android.content.Context;
import android.support.annotation.NonNull;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.app.repository.update.ICategoryUpdateRepository;
import com.shoniz.saledistributemobility.catalog.ProductImageModel;
import com.shoniz.saledistributemobility.catalog.ResourceModel;
import com.shoniz.saledistributemobility.data.model.update.api.ICategoryUpdateApi;
import com.shoniz.saledistributemobility.data.model.update.db.IUpdateDao;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.StringHelper;
import com.shoniz.saledistributemobility.utility.data.fileSystem.FileManager;
import com.shoniz.saledistributemobility.view.entity.CategoryEntity;
import com.shoniz.saledistributemobility.view.entity.FileResourceEntity;
import com.shoniz.saledistributemobility.view.entity.ImageVersionEntity;
import com.shoniz.saledistributemobility.view.entity.ProfileCategoryEntity;
import com.shoniz.saledistributemobility.view.entity.SubCategoryDetailEntity;
import com.shoniz.saledistributemobility.view.entity.SubCategoryEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryUpdateRepository extends UpdateBase implements ICategoryUpdateRepository {

    public CategoryUpdateRepository(CommonPackage commonPackage,
                                    ICategoryUpdateApi updateApi,
                                    IUpdateDao updateDao) {
        this.commonPackage = commonPackage;
        this.context = commonPackage.getContext();
        this.updateApi = updateApi;
        this.updateDao = updateDao;
    }


    @Override
    public List<ProductImageModel> getChangedProductImages() throws BaseException {
        setUpdateMessage("در حال دریافت اطلاعات محصولات جدید");
        List<ImageVersionEntity> updatedImages = updateDao.getUpdatedShortcutsImages();
        List<ResourceModel> resourceModels = mapImageEntitiesToResourceModels(updatedImages);
        return updateApi.getShortcutChanges(resourceModels);
    }

    @NonNull
    private List<ResourceModel> mapImageEntitiesToResourceModels(List<ImageVersionEntity> updatedImages) {
        List<ResourceModel> resourceModels = new ArrayList<>(updatedImages.size());
        for (ImageVersionEntity updatedImage: updatedImages) {
            ResourceModel resourceModel = new ResourceModel();
            resourceModel.ResourceFileId = Integer.parseInt(updatedImage.Shortcut);
            resourceModel.VersionNo = updatedImage.VersionNo;
            resourceModels.add(resourceModel);
        }
        return resourceModels;
    }

    @Override
    public List<ResourceModel> getResourceChanges() throws BaseException {
        return updateApi.getResourceChanges();
    }

    @Override
    public void syncProfileCategoryAll() throws BaseException {
        List<ProfileCategoryEntity> list = updateApi.getProfileCategoryAll();
        for (ProfileCategoryEntity cat : list) {
            updateDao.deleteProfileCategory(cat.ProfileCategoryId);
            updateDao.insertProfileCategory(cat);
        }
    }

    @Override
    public void syncCategoryAll() throws BaseException {
        setUpdateMessage("در حال بروزرسانی کاتالوگ ها");
        List<CategoryEntity> list = updateApi.getCategoryAll();
        for (CategoryEntity cat : list) {
            updateDao.deleteCategory(cat.ProfileCategoryId);
            updateDao.insertCategory(cat);
        }
    }

    @Override
    public void syncSubCategoryAll() throws BaseException {
        List<SubCategoryEntity> list = updateApi.getSubCategoryAll();
        for (SubCategoryEntity cat : list) {
            updateDao.deleteSubCategory(cat.SubCategoryId);
            updateDao.insertSubCategory(cat);
        }
    }

    @Override
    public void syncSubCategoryDetailAll() throws BaseException {
        List<SubCategoryDetailEntity> list = updateApi.getSubCategoryDetailAll();
        for (SubCategoryDetailEntity cat : list) {
            updateDao.deleteSubCategoryDetail(cat.SubCategoryDetailId);
            updateDao.insertSubCategoryDetail(cat);
        }
    }

    @Override
    public void syncShortcutImage() throws BaseException {
        List<ProductImageModel> list = getChangedProductImages();
        String imagePath = FileManager.getImagePath(context);
        int index = 0;
        for (ProductImageModel productImageModel : list) {
            setUpdateMessage("در حال بروزرسانی تصویر " + ++index + " از " + list.size());
            if (productImageModel.Action != 1) {
                productImageModel = fillShortcutImage(productImageModel);

//                String fileNamePath = StringHelper.GenerateMessage(context.getString(R.string.path_resource),
//                        imagePath,
//                        "", "");
                storeProductImages(productImageModel, imagePath);
                updateDao.deleteImageVersion(productImageModel.Shortcut);
                updateDao.insertImageVersion(mapToImageVersion(productImageModel));
            }
        }
    }

    @Override
    public void syncResourceFile() throws BaseException {
        List<ResourceModel> resources =  getResourceChanges();
        int index = 0;
        for (ResourceModel resource : resources) {
            resource = fillResourceModelByFile(resource);
            setUpdateMessage("در حال بروزرسانی منابع " + ++index + " از " + resources.size());

            storeResource(resource, FileManager.getImagePath(context));
            updateDao.deleteFileResource(resource.ResourceFileId);
            updateDao.insertFileResource(mapToFileResourceEntity(resource));
        }
    }

    private ProductImageModel fillShortcutImage(ProductImageModel shortcutModel) throws BaseException {
        return updateApi.getShortcutImage(shortcutModel);
    }

    private ImageVersionEntity mapToImageVersion(ProductImageModel productImage){
        ImageVersionEntity imageVersion = new ImageVersionEntity();
        imageVersion.Shortcut = productImage.Shortcut + "";
        imageVersion.VersionNo = productImage.VersionNo;
        return imageVersion;
    }

    private void storeProductImages(ProductImageModel productImageModel, String imagePath) throws InOutError {
        try {
            String fileNamePath = StringHelper.GenerateMessage(context.getString(R.string.path_resource),
                    imagePath,
                    "", productImageModel.Shortcut);
            FileManager.saveBase64File(productImageModel.Image, fileNamePath);
            fileNamePath = StringHelper.GenerateMessage(context.getString(R.string.path_resource),
                    imagePath,
                    "s", productImageModel.Shortcut);

            Common.storeBase64ImageToFile(productImageModel.SmallImage, fileNamePath);
        } catch (IOException e) {
            throw new InOutError(commonPackage, e, "خطا در درج تصویر محصول " + productImageModel.ProductName);
        }
    }

    private FileResourceEntity mapToFileResourceEntity(ResourceModel resource) {
        FileResourceEntity resourceEntity = new FileResourceEntity();
        resourceEntity.ResourceFileId = resource.ResourceFileId;
        resourceEntity.VersionNo = resource.VersionNo;
        return resourceEntity;
    }

    private ResourceModel fillResourceModelByFile(ResourceModel resourceModel) throws BaseException {
        return updateApi.getResourceFile(resourceModel);
    }

    private void storeResource(ResourceModel resourceModel, String filePath) throws BaseException {
        String fileNamePath = StringHelper.GenerateMessage(context.getString(R.string.path_resource)
                , filePath,
                "r",resourceModel.ResourceFileId);

        try {
            FileManager.saveBase64File(resourceModel.Resource, fileNamePath);
        } catch (IOException e) {
            throw new InOutError(commonPackage, e, "خطا در درج فایل منبع " );
        }
    }


//    @Override
//    public void updateWholeData() throws BaseException{
//        updateData(UpdateType.WHOLE);
//    }

//    @Override
//    public void updateCatalogData() throws ConnectionException, OldApiException, BaseException, IOException {
//        updateData(UpdateType.CATALOG);
//    }
//
//    @Override
//    public void updatePrePathData() throws ConnectionException, OldApiException, BaseException, IOException {
//        updateData(UpdateType.PRE_PATH);
//    }

//    @Override
//    public void updateOrder() throws BaseException {
//        setUpdateMessage(context.getString(R.string.get_order_list));
//        orderRepository.sync();
//    }




//    @Override
//    public void updatePathData(int pathId) throws ConnectionException, OldApiException, IOException {
//        if (appUpdateListener != null) {
//            appUpdateListener.onStageUpdate("در حال بروزرسانی اطلاعات مسیر...");
//        }
//        AppBusiness.updatePathData(context, pathId);
//    }

//    private void updateData(UpdateType updateType) throws ConnectionException, OldApiException, IOException, BaseException {
//        switch (updateType) {
//            case WHOLE:
//                updatePrePath();
//                updateCatalog();
//                updateUsers();
//                settingPref.setUnchangedOrdersNoInCardindeForEdit(0L);
//                break;
//            case CATALOG:
//                updateCatalog();
//                break;
//            case PRE_PATH:
//                updatePrePath();
//                break;
//        }
//
//    }


//    private void updatePrePath() throws ConnectionException, OldApiException, IOException {
//        AppBusiness.saveDatabasesFiles(context, new StageListener() {
//            @Override
//            public void OnStageGoing(ProgressStage stage, int currentProgress, int allProgress) {
//                if (appUpdateListener != null) {
//                    appUpdateListener.onStageUpdate(UpdateMessageHelper.createMessage(context, stage, currentProgress, allProgress));
//                }
//            }
//
//            @Override
//            public void OnStageGoing(String message) {
//                if (appUpdateListener != null) {
//                    appUpdateListener.onStageUpdate(message);
//                }
//            }
//        });
//    }


//    private void updateUsers() throws BaseException {
//        setUpdateMessage("در حال بروزرسانی کاربران...");
//        userRepository.sync();
//        settingPref.setCurrentRoleId(userRepository.getUserMainRoleId(
//                settingPref.getEmployeeInfoEntity().EmployeeId
//        ));
//    }

//    private void updateCatalog() throws ConnectionException, OldApiException, IOException {
//        AppBusiness.updateCoding(context, new StageListener() {
//            @Override
//            public void OnStageGoing(ProgressStage stage, int currentProgress, int allProgress) {
//                if (appUpdateListener != null) {
//                    appUpdateListener.onStageUpdate(UpdateMessageHelper.createMessage(context, stage, currentProgress, allProgress));
//                }
//            }
//
//            @Override
//            public void OnStageGoing(String message) {
//                if (appUpdateListener != null) {
//                    appUpdateListener.onStageUpdate(message);
//                }
//            }
//        });
//    }

//    private enum UpdateType {
//        WHOLE,
//        PRE_PATH,
//        CATALOG
//    }

//    public interface IAppUpdateListener {
//        void onStageUpdate(String message);
//    }
//
//    private void setUpdateMessage(String message){
//        if (appUpdateListener != null) {
//            appUpdateListener.onStageUpdate(message);
//        }
//    }

    private CommonPackage commonPackage;
    private Context context;
    private ICategoryUpdateApi updateApi;
    private IUpdateDao updateDao;
}
