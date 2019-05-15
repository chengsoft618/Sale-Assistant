package com.shoniz.saledistributemobility.data.model.update.api;

import com.shoniz.saledistributemobility.base.FileContentModel;
import com.shoniz.saledistributemobility.catalog.CategoryModel;
import com.shoniz.saledistributemobility.catalog.ProductImageModel;
import com.shoniz.saledistributemobility.catalog.ProfileCategoryModel;
import com.shoniz.saledistributemobility.catalog.ResourceModel;
import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.api.retrofit.IRetroCommand;
import com.shoniz.saledistributemobility.data.api.retrofit.RetroManager;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.view.catalog.subcategory.SubCategoryDetailModel;
import com.shoniz.saledistributemobility.view.catalog.subcategory.SubCategoryModel;
import com.shoniz.saledistributemobility.view.entity.CategoryEntity;
import com.shoniz.saledistributemobility.view.entity.ProfileCategoryEntity;
import com.shoniz.saledistributemobility.view.entity.SubCategoryDetailEntity;
import com.shoniz.saledistributemobility.view.entity.SubCategoryEntity;

import java.util.List;

public class CategoryUpdateRetrofit implements ICategoryUpdateApi {
    ApiParameter apiParameter;
    CommonPackage commonPackage;


    public CategoryUpdateRetrofit(ApiParameter apiParameter, CommonPackage commonPackage) {
        this.apiParameter = apiParameter;
        this.commonPackage = commonPackage;
    }


    @Override
    public List<ProductImageModel> getShortcutChanges(List<ResourceModel> models) throws BaseException {
        apiParameter = new ApiParameter(commonPackage);
        apiParameter.ResourceFiles = models;
        IRetroCommand<List<ProductImageModel>, IUpdateRetrofitService> command =
                service -> {
                    apiParameter.RoleId = commonPackage.getSettingPref().getCurrentRoleId();
                    return service.getShortcutChanges(apiParameter).execute().body();
                };

        return new RetroManager<List<ProductImageModel>, IUpdateRetrofitService>(commonPackage).callOfficeApi(command
                , IUpdateRetrofitService.class);
    }

    @Override
    public List<ResourceModel> getResourceChanges(List<ResourceModel> models) throws BaseException {
        apiParameter = new ApiParameter(commonPackage);
        apiParameter.ResourceFiles = models;
        IRetroCommand<List<ResourceModel>, IUpdateRetrofitService> command =
                service -> {
                    apiParameter.RoleId = commonPackage.getSettingPref().getCurrentRoleId();
                    return service.getResourceChanges(apiParameter).execute().body();
                };

        return new RetroManager<List<ResourceModel>, IUpdateRetrofitService>(commonPackage).callOfficeApi(command
                , IUpdateRetrofitService.class);
    }

    @Override
    public List<ProfileCategoryEntity> getProfileCategoryAll() throws BaseException {
        apiParameter = new ApiParameter(commonPackage);
        IRetroCommand<List<ProfileCategoryEntity>, IUpdateRetrofitService> command =
                service -> {
                    apiParameter.RoleId = commonPackage.getSettingPref().getCurrentRoleId();
                    return service.getProfileCategoryAll(apiParameter).execute().body();
                };

        return new RetroManager<List<ProfileCategoryEntity>, IUpdateRetrofitService>(commonPackage).callOfficeApi(command
                , IUpdateRetrofitService.class);
    }

    @Override
    public List<CategoryEntity> getCategoryAll() throws BaseException {
        apiParameter = new ApiParameter(commonPackage);
        IRetroCommand<List<CategoryEntity>, IUpdateRetrofitService> command =
                service -> {
                    apiParameter.RoleId = commonPackage.getSettingPref().getCurrentRoleId();
                    return service.getCategoryAll(apiParameter).execute().body();
                };

        return new RetroManager<List<CategoryEntity>, IUpdateRetrofitService>(commonPackage).callOfficeApi(command
                , IUpdateRetrofitService.class);
    }

    @Override
    public List<SubCategoryEntity> getSubCategoryAll() throws BaseException {
        apiParameter = new ApiParameter(commonPackage);
        IRetroCommand<List<SubCategoryEntity>, IUpdateRetrofitService> command =
                service -> {
                    apiParameter.RoleId = commonPackage.getSettingPref().getCurrentRoleId();
                    return service.getSubCategoryAll(apiParameter).execute().body();
                };

        return new RetroManager<List<SubCategoryEntity>, IUpdateRetrofitService>(commonPackage).callOfficeApi(command
                , IUpdateRetrofitService.class);
    }

    @Override
    public List<SubCategoryDetailEntity> getSubCategoryDetailAll() throws BaseException {
        apiParameter = new ApiParameter(commonPackage);
        IRetroCommand<List<SubCategoryDetailEntity>, IUpdateRetrofitService> command =
                service -> {
                    apiParameter.RoleId = commonPackage.getSettingPref().getCurrentRoleId();
                    return service.getSubCategoryDetailAll(apiParameter).execute().body();
                };

        return new RetroManager<List<SubCategoryDetailEntity>, IUpdateRetrofitService>(commonPackage).callOfficeApi(command
                , IUpdateRetrofitService.class);
    }

    @Override
    public ProductImageModel getShortcutImage(ProductImageModel shortcutModel) throws BaseException {
        apiParameter = new ApiParameter(commonPackage);
        IRetroCommand<ProductImageModel, IUpdateRetrofitService> command =
                service -> {
                    apiParameter.Shortcut = shortcutModel.Shortcut;
                    apiParameter.VersionNo = shortcutModel.VersionNo;
                    return service.getShortcutImage(apiParameter).execute().body();
                };

        return new RetroManager<ProductImageModel, IUpdateRetrofitService>(commonPackage).callOfficeApi(command
                , IUpdateRetrofitService.class);
    }

    @Override
    public ResourceModel getResourceFile(ResourceModel resourceModel) throws BaseException {
        apiParameter = new ApiParameter(commonPackage);
        IRetroCommand<ResourceModel, IUpdateRetrofitService> command =
                service -> {
                    apiParameter.ResourceFileId = resourceModel.ResourceFileId;
                    apiParameter.VersionNo = resourceModel.VersionNo;
                    return service.getResourceFile(apiParameter).execute().body();
                };

        return new RetroManager<ResourceModel, IUpdateRetrofitService>(commonPackage).callOfficeApi(command
                , IUpdateRetrofitService.class);
    }


}
