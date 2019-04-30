package com.shoniz.saledistributemobility.data.model.update.api;

import com.shoniz.saledistributemobility.base.FileContentModel;
import com.shoniz.saledistributemobility.catalog.ProductImageModel;
import com.shoniz.saledistributemobility.catalog.ResourceModel;
import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.api.retrofit.IRetroCommand;
import com.shoniz.saledistributemobility.data.api.retrofit.RetroManager;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.view.entity.CategoryEntity;
import com.shoniz.saledistributemobility.view.entity.ProfileCategoryEntity;
import com.shoniz.saledistributemobility.view.entity.SubCategoryDetailEntity;
import com.shoniz.saledistributemobility.view.entity.SubCategoryEntity;

import java.util.List;

public class DatabaseUpdateRetrofit implements IDatabaseUpdateApi {
    ApiParameter apiParameter;
    CommonPackage commonPackage;


    public DatabaseUpdateRetrofit(ApiParameter apiParameter, CommonPackage commonPackage) {
        this.apiParameter = apiParameter;

        this.commonPackage = commonPackage;
    }

    @Override
    public FileContentModel getSaleDb() throws BaseException {
        IRetroCommand<FileContentModel, IUpdateRetrofitService> command =
                service -> {
                    apiParameter.RoleId = commonPackage.getSettingPref().getCurrentRoleId();
                    return service.getSaleDb(apiParameter).execute().body();
                };

        return new RetroManager<FileContentModel, IUpdateRetrofitService>(commonPackage).callOfficeApi(command
                , IUpdateRetrofitService.class);
    }


}
