package com.shoniz.saledistributemobility.data.model.app.api;


import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.data.api.retrofit.IRetroCommand;
import com.shoniz.saledistributemobility.data.api.retrofit.RetroManager;
import com.shoniz.saledistributemobility.data.model.app.BranchEntity;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.List;

public class ShonizApi implements IShonizApi {

    ApiParameter apiParameter;
    CommonPackage commonPackage;

    public ShonizApi(ApiParameter apiParameter, CommonPackage commonPackage) {
        this.apiParameter = apiParameter;
        this.commonPackage = commonPackage;
    }


    @Override
    public List<BranchEntity> getBranchEntities() throws BaseException {
        IRetroCommand<List<BranchEntity>, IShonizRetrofitService> command = service -> RetroManager.execCaller(service.getBranches(apiParameter)).body() ;
        return new RetroManager<List<BranchEntity>, IShonizRetrofitService>(commonPackage).callShonizApi(command
                , IShonizRetrofitService.class);
    }
}
