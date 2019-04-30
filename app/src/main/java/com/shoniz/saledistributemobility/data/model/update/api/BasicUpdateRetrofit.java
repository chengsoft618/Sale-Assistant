package com.shoniz.saledistributemobility.data.model.update.api;

import com.shoniz.saledistributemobility.base.FileContentModel;
import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.api.retrofit.IRetroCommand;
import com.shoniz.saledistributemobility.data.api.retrofit.RetroManager;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

public class BasicUpdateRetrofit implements IBasicUpdateApi {
    ApiParameter apiParameter;
    CommonPackage commonPackage;


    public BasicUpdateRetrofit(ApiParameter apiParameter, CommonPackage commonPackage) {
        this.apiParameter = apiParameter;

        this.commonPackage = commonPackage;
    }

    @Override
    public void updateUsers() throws BaseException {
//        IRetroCommand<FileContentModel, IUpdateRetrofitService> command =
//                service -> {
//                    apiParameter.RoleId = commonPackage.getSettingPref().getCurrentRoleId();
//                    return service.getSaleDb(apiParameter).execute().body();
//                };
//
//        return new RetroManager<FileContentModel, IUpdateRetrofitService>(commonPackage).callOfficeApi(command
//                , IUpdateRetrofitService.class);
    }
}
