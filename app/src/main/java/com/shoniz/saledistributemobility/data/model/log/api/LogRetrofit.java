package com.shoniz.saledistributemobility.data.model.log.api;

import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.data.api.retrofit.IRetroCommand;
import com.shoniz.saledistributemobility.data.api.retrofit.RetroManager;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.io.IOException;
import java.util.List;

public class LogRetrofit implements ILogApi {


    ApiParameter apiParameter;
    CommonPackage commonPackage;

    public LogRetrofit(CommonPackage commonPackage) {
        this.commonPackage = commonPackage;
    }


    @Override
    public void sendLog(com.shoniz.saledistributemobility.data.model.log.LogEntity log) throws BaseException {
        IRetroCommand<Void, ILogRetrofitService> command = new IRetroCommand<Void, ILogRetrofitService>() {
            @Override
            public Void exec(ILogRetrofitService service) throws IOException, ApiException {
                RetroManager.execCaller(service.SendLog(log)).body();
                return null;
            }
        };
        new RetroManager<Void, ILogRetrofitService>(commonPackage).callShonizApi(command
                , ILogRetrofitService.class);
    }

    @Override
    public void sendLogs(List<com.shoniz.saledistributemobility.data.model.log.LogEntity> logs) throws BaseException {
        IRetroCommand<Void, ILogRetrofitService> command = new IRetroCommand<Void, ILogRetrofitService>() {
            @Override
            public Void exec(ILogRetrofitService service) throws IOException, ApiException {
                RetroManager.execCaller(service.SendLogs(logs)).body();
                return null;
            }
        };
        new RetroManager<Void, ILogRetrofitService>(commonPackage).callShonizApi(command
                , ILogRetrofitService.class);
    }
}
