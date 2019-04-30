package com.shoniz.saledistributemobility.data.model.app.api;


import android.os.Build;

import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.data.api.retrofit.IRetroCommand;
import com.shoniz.saledistributemobility.data.api.retrofit.RetroManager;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.infrastructure.version.VersionData;
import com.shoniz.saledistributemobility.view.entity.EmployeeInfoEntity;

public class AppApi implements IAppApi {

    ApiParameter apiParameter;
    CommonPackage commonPackage;

    public AppApi(ApiParameter apiParameter, CommonPackage commonPackage) {
        this.apiParameter = apiParameter;
        this.commonPackage = commonPackage;
    }

    @Override
    public VersionData getAppVersionLink() throws BaseException {
        IRetroCommand<VersionData, IAppRetrofitService> command = service -> RetroManager.execCaller(service.getVersionLink(apiParameter)).body() ;
        return new RetroManager<VersionData, IAppRetrofitService>(commonPackage).callOfficeApi(command
                , IAppRetrofitService.class);
    }

    @Override
    public VersionData getGooglePlayServicesVersionLink() throws BaseException {

        IRetroCommand<VersionData, IAppRetrofitService> command = service -> {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                apiParameter.CPU = Build.CPU_ABI;
            } else {
                apiParameter.CPU = Build.SUPPORTED_ABIS[0];
            }
            apiParameter.Release = Build.VERSION.RELEASE;

            return  RetroManager.execCaller(service.getGooglePlayServicesVersionLink(apiParameter)).body();
        };
        return new RetroManager<VersionData, IAppRetrofitService>(commonPackage).callOfficeApi(command
                , IAppRetrofitService.class);
    }

    @Override
    public String getChromeLink() throws BaseException {
        IRetroCommand<String, IAppRetrofitService> command = service -> RetroManager.execCaller(service.getChromeLink(apiParameter)).body();
        return new RetroManager<String, IAppRetrofitService>(commonPackage).callOfficeApi(command
                , IAppRetrofitService.class);
    }

    @Override
    public EmployeeInfoEntity getEmployeeInfoEntity() throws BaseException {

        IRetroCommand<EmployeeInfoEntity, IAppRetrofitService> command = service -> {
            return RetroManager.execCaller(service.getEmployeeInfo(apiParameter)).body();
        };
        return new RetroManager<EmployeeInfoEntity, IAppRetrofitService>(commonPackage).callOfficeApi(command
                , IAppRetrofitService.class);
    }
}
