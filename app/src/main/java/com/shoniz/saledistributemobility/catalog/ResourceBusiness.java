package com.shoniz.saledistributemobility.catalog;

import android.content.Context;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.framework.exception.OldApiException;
import com.shoniz.saledistributemobility.framework.exception.ConnectionException;
import com.shoniz.saledistributemobility.utility.data.api.OfficeApi;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.StringHelper;
import com.shoniz.saledistributemobility.view.catalog.subcategory.SubCategoryDetailModel;
import com.shoniz.saledistributemobility.view.catalog.subcategory.SubCategoryModel;

import java.io.IOException;
import java.util.List;

/**
 * Created by ferdos.s on 5/17/2017.
 */

public class ResourceBusiness {

    public static List<ProductImageModel> GetShortcutChanges(Context context) throws IOException, OldApiException, ConnectionException {
        OfficeApi officeApi=new OfficeApi(context);
        List<ResourceModel>  allCoding= ResourceData.getShortcutStored(context);
        return officeApi.getShortcutChanges(allCoding);
    }
    public static ProductImageModel GetShortcutImage(Context context, ProductImageModel shortcutModel) throws IOException, OldApiException, ConnectionException {
        OfficeApi officeApi=new OfficeApi(context);
        return officeApi.getShortcutImage(shortcutModel);
    }
    public static ProfileCategoryModel GetProfileCategory(Context context,List<Integer> ids) throws IOException, OldApiException, ConnectionException {
        OfficeApi officeApi=new OfficeApi(context);
        return officeApi.getProfileCategory(ids);
    }

    public static List<ProfileCategoryModel> GetProfileCategoryAllAPI(Context context) throws IOException, OldApiException, ConnectionException {
        OfficeApi officeApi=new OfficeApi(context);
        return officeApi.getProfileCategoryAll();
    }

    public static List<ProfileCategoryModel> GetProfileCategoryAll(Context context) throws IOException {
        return ResourceData.getProfileCategory(context);
    }

    public static List<CategoryModel> GetCategoryAll(Context context) throws IOException, OldApiException, ConnectionException {
        OfficeApi officeApi=new OfficeApi(context);
        return officeApi.getCategoryAll();
    }

    public static List<SubCategoryModel> GetSubCategoryAll(Context context) throws IOException, OldApiException, ConnectionException {
        OfficeApi officeApi=new OfficeApi(context);
        return officeApi.getSubCategoryAll();
    }

    public static List<SubCategoryDetailModel> GetSubCategoryDetailAll(Context context) throws IOException, OldApiException, ConnectionException {
        OfficeApi officeApi=new OfficeApi(context);
        return officeApi.getSubCategoryDetailAll();
    }

    public static ResourceModel GetResourceFile(Context context,ResourceModel resourceModel) throws IOException, OldApiException, ConnectionException {
        OfficeApi officeApi=new OfficeApi(context);
        return officeApi.getResource(resourceModel);
    }

    public static void StoreImage(Context context, ProductImageModel tShortcutModel, String imagePath) throws IOException {
        String fileNamePath = StringHelper.GenerateMessage(context.getString(R.string.path_resource),
                imagePath,
                "",tShortcutModel.Shortcut);

        Common.storeBase64ImageToFile(tShortcutModel.Image,fileNamePath);
        fileNamePath = StringHelper.GenerateMessage(context.getString(R.string.path_resource),
                imagePath,
                "s",tShortcutModel.Shortcut);

        Common.storeBase64ImageToFile(tShortcutModel.SmallImage,fileNamePath);
    }

    public static void StoreResource(Context context,ResourceModel resourceModel, String imagePath) throws IOException {
        String fileNamePath = StringHelper.GenerateMessage(context.getString(R.string.path_resource),
                imagePath,
                "r",resourceModel.ResourceFileId);

        Common.storeBase64ImageToFile(resourceModel.Resource,fileNamePath);
    }


    public static void InsertShortcut(Context context, ProductImageModel tShortcutModel) throws IOException {
        ResourceData.insertShortcutStored(context,tShortcutModel);
    }
    public static void InsertResource(Context context,ResourceModel resourceModel) throws IOException {
        ResourceData.insertResourceStored(context,resourceModel);
    }

    public static List<ResourceModel> GetResourceChanges(Context context) throws IOException, OldApiException, ConnectionException {
        OfficeApi officeApi=new OfficeApi(context);
        List<ResourceModel>  resourceModels= ResourceData.getResourceStored(context);
        return officeApi.getResourceChanges(resourceModels);
    }

    public static void InsertProfileCategoryAll(Context context,List<ProfileCategoryModel> profileCategories) throws IOException {
        ResourceData.insertProfileCategoryAll(context,profileCategories);
    }

    public static void InsertCategoryAll(Context context,List<CategoryModel> categoryModels) throws IOException {
        ResourceData.insertCategoryAll(context,categoryModels);
    }

    public static void InsertSubCategoryAll(Context context,List<SubCategoryModel> subCategoryModels) throws IOException {
        ResourceData.insertSubCategoryAll(context,subCategoryModels);
    }

    public static void InsertSubCategoryDetailAll(Context context,List<SubCategoryDetailModel> subCategoryDetailModels) throws IOException {
        ResourceData.insertSubCategoryDetailAll(context,subCategoryDetailModels);
    }

    public static List<CategoryModel> getCategory(Context context, int profileCategoryId) throws IOException {
        return ResourceData.getCategory(context,profileCategoryId);
    }
    public static List<SubCategoryModel> getSubCategory(Context context, int profileCategoryId) throws IOException {
        return ResourceData.getSubCategory(context,profileCategoryId);
    }
    public static SubCategoryDetailModel getShortcut(Context context, String shortcut) throws IOException {
        return ResourceData.getShortcut(context,shortcut);

    }  public static SubCategoryDetailModel getShortcutInfo(Context context, int shortcut) throws IOException {
        return ResourceData.getShortcutInfo(context,shortcut);
    }
}
