package com.shoniz.saledistributemobility.data.model.update;

import com.shoniz.saledistributemobility.R;


public class AppBusiness {
//    public static void setAppInit(Context context) {
//        AppPref.setAppInit(context);
//    }
//
//    public static Boolean isAppInitialized(Context context) {
//        return AppPref.isAppInit(context);
//    }
//
//    public static void saveDatabasesFiles(Context context, StageListener onStageListener) throws ConnectionException, OldApiException, IOException {
//        int allProgress = 1, currentTaskIndex = 1;
//        onStageListener.OnStageGoing(ProgressStage.GetBaseDb, currentTaskIndex, allProgress);
//        CustomerData__.createSaleDb(context, CustomerBusiness.getSaleDb(context).FileContents);
//    }
//
//    public static void updateCoding(Context context, StageListener onStageListener) throws ConnectionException, OldApiException, IOException {
//
//        List<ProductImageModel> shortcutModels = ResourceBusiness.GetShortcutChanges(context);
//        List<ResourceModel> resourceModels = ResourceBusiness.GetResourceChanges(context);
//        int allProgress = resourceModels.size() + shortcutModels.size() + 5, currentTaskIndex = 1;
//
//        currentTaskIndex++;
//        onStageListener.OnStageGoing(ProgressStage.GetProfileCategory, currentTaskIndex, allProgress);
//
//        List<ProfileCategoryModel> profileCategoryModel = ResourceBusiness.GetProfileCategoryAllAPI(context);
//        ResourceBusiness.InsertProfileCategoryAll(context, profileCategoryModel);
//
//        currentTaskIndex++;
//        onStageListener.OnStageGoing(ProgressStage.GetCategory, currentTaskIndex, allProgress);
//        List<CategoryModel> categoryModels = ResourceBusiness.GetCategoryAll(context);
////        ResourceBusiness.InsertCategoryAll(context, categoryModels);
//
//        currentTaskIndex++;
//        onStageListener.OnStageGoing(ProgressStage.GetSubCategory, currentTaskIndex, allProgress);
////        List<SubCategoryModel> subCategoryModels = ResourceBusiness.GetSubCategoryAll(context);
////        ResourceBusiness.InsertSubCategoryAll(context, subCategoryModels);
//
//        currentTaskIndex++;
//        onStageListener.OnStageGoing(ProgressStage.GetSubCategoryDetail, currentTaskIndex, allProgress);
////        List<SubCategoryDetailModel> subCategoryDetailModels = ResourceBusiness.GetSubCategoryDetailAll(context);
////        ResourceBusiness.InsertSubCategoryDetailAll(context, subCategoryDetailModels);
//
//
//        String imagePath = Common.getImagePath(context);
//        for (ProductImageModel shortcutModel : shortcutModels) {
//            if (shortcutModel.Action != 1) {
//                ProductImageModel tShortcutModel = ResourceBusiness.GetShortcutImage(context, shortcutModel);
//                String preMessage = context.getString(R.string.get_shortcut_image, shortcutModel.Shortcut, allProgress, currentTaskIndex);
//                onStageListener.OnStageGoing(preMessage);
//                currentTaskIndex++;
//                ResourceBusiness.StoreImage(context, tShortcutModel, imagePath);
//                ResourceBusiness.InsertShortcut(context, tShortcutModel);
//            }
//        }
//
//        for (ResourceModel resourceModel : resourceModels) {
//            ResourceModel tResourceModel = ResourceBusiness.GetResourceFile(context, resourceModel);
//            String preMessage = context.getString(R.string.get_resource, tResourceModel.ResourceFileId, allProgress, currentTaskIndex);
//
//            onStageListener.OnStageGoing(preMessage);
//            currentTaskIndex++;
//            ResourceBusiness.StoreResource(context, tResourceModel, imagePath);
//            ResourceBusiness.InsertResource(context, tResourceModel);
//        }
//    }


}
