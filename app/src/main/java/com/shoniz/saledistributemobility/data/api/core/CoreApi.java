package com.shoniz.saledistributemobility.data.api.core;

import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.data.api.retrofit.IRetroCommand;
import com.shoniz.saledistributemobility.data.api.retrofit.RetroManager;
import com.shoniz.saledistributemobility.data.model.app.BranchEntity;
import com.shoniz.saledistributemobility.data.model.app.api.IShonizRetrofitService;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.io.IOException;
import java.util.List;

public class CoreApi implements ICoreApi {
    private ApiParameter apiParameter;
    private CommonPackage commonPackage;
    private ISettingRepository settingRepository;

    public CoreApi(ApiParameter apiParameter, CommonPackage commonPackage, ISettingRepository settingRepository) {
        this.apiParameter = apiParameter;
        this.commonPackage = commonPackage;
        this.settingRepository = settingRepository;
    }


    @Override
    public String getActiveUrl() throws BaseException {
        IRetroCommand<Boolean, ICoreRetrofitService> command = new IRetroCommand<Boolean, ICoreRetrofitService>() {
            @Override
            public Boolean exec(ICoreRetrofitService service) throws IOException, ApiException {
                return RetroManager.execCaller(service.IsOnline()).body();
            }
        };
        return new RetroManager<Boolean, ICoreRetrofitService>(commonPackage).getOfficeActiveUrl(command
                , ICoreRetrofitService.class);

    }



}
