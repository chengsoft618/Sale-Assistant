package com.shoniz.saledistributemobility.framework.repository.update;

import com.shoniz.saledistributemobility.catalog.ProductImageModel;
import com.shoniz.saledistributemobility.catalog.ResourceModel;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.List;

public interface ICategoryUpdateRepository extends IUpdateRepository {
    List<ProductImageModel> getChangedProductImages() throws BaseException;

    List<ResourceModel> getResourceChanges() throws BaseException;

    void syncProfileCategoryAll() throws BaseException;

    void syncCategoryAll() throws BaseException;

    void syncSubCategoryAll() throws BaseException;

    void syncSubCategoryDetailAll() throws BaseException;

    void syncShortcutImage() throws BaseException;

    void syncResourceFile() throws BaseException;
}
