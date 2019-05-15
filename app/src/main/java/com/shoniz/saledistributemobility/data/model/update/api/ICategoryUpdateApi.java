package com.shoniz.saledistributemobility.data.model.update.api;

import com.shoniz.saledistributemobility.catalog.ProductImageModel;
import com.shoniz.saledistributemobility.catalog.ResourceModel;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.view.entity.CategoryEntity;
import com.shoniz.saledistributemobility.view.entity.ProfileCategoryEntity;
import com.shoniz.saledistributemobility.view.entity.SubCategoryDetailEntity;
import com.shoniz.saledistributemobility.view.entity.SubCategoryEntity;

import java.util.List;

public interface ICategoryUpdateApi {
    List<ProductImageModel> getShortcutChanges(List<ResourceModel> models) throws BaseException;
    List<ResourceModel> getResourceChanges(List<ResourceModel> models) throws BaseException;
    List<ProfileCategoryEntity> getProfileCategoryAll() throws BaseException;
    List<CategoryEntity> getCategoryAll() throws BaseException;
    List<SubCategoryEntity> getSubCategoryAll() throws BaseException;
    List<SubCategoryDetailEntity> getSubCategoryDetailAll() throws BaseException;
    ProductImageModel getShortcutImage(ProductImageModel shortcutModel) throws BaseException;
    ResourceModel getResourceFile(ResourceModel resourceModel) throws BaseException;

}
