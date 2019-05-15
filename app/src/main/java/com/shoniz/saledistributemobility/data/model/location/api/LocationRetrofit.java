package com.shoniz.saledistributemobility.data.model.location.api;

import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.api.retrofit.IRetroCommand;
import com.shoniz.saledistributemobility.data.api.retrofit.RetroManager;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

public class LocationRetrofit implements ILocationApi {

    private ApiParameter apiParameter;
    private CommonPackage commonPackage;



    public LocationRetrofit(ApiParameter apiParameter, CommonPackage commonPackage) {
        this.apiParameter = apiParameter;
        this.commonPackage = commonPackage;

    }


    @Override
    public String startExactTracking(int userId) throws BaseException {
        IRetroCommand<String, ILocationRetrofitService> command = service -> {
            apiParameter.UserID = userId;
            return   RetroManager.execCaller(service.startExactTracking(apiParameter)).body() ;
        };

       return new RetroManager<String, ILocationRetrofitService>(commonPackage).callOfficeApi(command
                , ILocationRetrofitService.class);
    }

    @Override
    public String stopExactTracking(int userId) throws BaseException {
        IRetroCommand<String, ILocationRetrofitService> command = service -> {
            apiParameter.UserID = userId;
            return  RetroManager.execCaller(service.stopExactTracking(apiParameter)).body();
        };

        return new RetroManager<String, ILocationRetrofitService>(commonPackage).callOfficeApi(command
                , ILocationRetrofitService.class);
    }
}
