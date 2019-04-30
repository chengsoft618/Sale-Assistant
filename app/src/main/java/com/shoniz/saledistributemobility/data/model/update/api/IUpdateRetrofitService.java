package com.shoniz.saledistributemobility.data.model.update.api;

import com.shoniz.saledistributemobility.base.FileContentModel;
import com.shoniz.saledistributemobility.catalog.CategoryModel;
import com.shoniz.saledistributemobility.catalog.ProductImageModel;
import com.shoniz.saledistributemobility.catalog.ResourceModel;
import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.view.entity.CategoryEntity;
import com.shoniz.saledistributemobility.view.entity.FileResourceEntity;
import com.shoniz.saledistributemobility.view.entity.ProfileCategoryEntity;
import com.shoniz.saledistributemobility.view.entity.SubCategoryDetailEntity;
import com.shoniz.saledistributemobility.view.entity.SubCategoryEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IUpdateRetrofitService {
    @POST("GetShortcutChangesNew")
    Call<List<ProductImageModel>> getShortcutChanges(@Body ApiParameter parameter);

    @POST("getResourceChanges")
    Call<List<ResourceModel>> getResourceChanges(@Body ApiParameter parameter);

    @POST("GetProfileCategoryAll")
    Call<List<ProfileCategoryEntity>> getProfileCategoryAll(@Body ApiParameter parameter);

    @POST("getCategoryAll")
    Call<List<CategoryEntity>> getCategoryAll(@Body ApiParameter parameter);

    @POST("getSubCategoryAll")
    Call<List<SubCategoryEntity>> getSubCategoryAll(@Body ApiParameter parameter);

    @POST("getSubCategoryDetailAll")
    Call<List<SubCategoryDetailEntity>> getSubCategoryDetailAll(@Body ApiParameter parameter);

    @POST("GetShortcutImage")
    Call<ProductImageModel> getShortcutImage(@Body ApiParameter parameter);

    @POST("GetResourceFile")
    Call<ResourceModel> getResourceFile(@Body ApiParameter parameter);

    @POST("GetSaleDatabase")
    Call<FileContentModel> getSaleDb(@Body ApiParameter parameter);
}
