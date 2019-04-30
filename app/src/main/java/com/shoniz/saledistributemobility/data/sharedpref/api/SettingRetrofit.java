package com.shoniz.saledistributemobility.data.sharedpref.api;

import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.data.api.retrofit.IRetroCommand;
import com.shoniz.saledistributemobility.data.api.retrofit.RetroManager;
import com.shoniz.saledistributemobility.data.sharedpref.SettingEntity;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.io.IOException;
import java.util.List;

public class SettingRetrofit implements ISettingApi {

    ApiParameter apiParameter;
    CommonPackage commonPackage;


    public SettingRetrofit(ApiParameter apiParameter, CommonPackage commonPackage) {
        this.apiParameter = apiParameter;
        this.commonPackage = commonPackage;
    }


    @Override
    public List<SettingEntity> getUserSettings() throws BaseException {
        IRetroCommand<List<SettingEntity>, ISettingRetrofitService> command = new IRetroCommand<List<SettingEntity>, ISettingRetrofitService>() {
            @Override
            public List<SettingEntity> exec(ISettingRetrofitService service) throws IOException, ApiException {
                return  RetroManager.execCaller(service.getUserSettings(apiParameter)).body();
            }
        };
        return new RetroManager<List<SettingEntity>, ISettingRetrofitService>(commonPackage).callOfficeApi(command
                , ISettingRetrofitService.class);
    }
}
