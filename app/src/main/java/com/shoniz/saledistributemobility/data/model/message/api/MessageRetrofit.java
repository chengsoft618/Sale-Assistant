package com.shoniz.saledistributemobility.data.model.message.api;

import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.api.retrofit.IRetroCommand;
import com.shoniz.saledistributemobility.data.api.retrofit.RetroManager;
import com.shoniz.saledistributemobility.data.model.message.MessageEntity;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.List;

public class MessageRetrofit implements IMessageApi {

    private ApiParameter apiParameter;
    private CommonPackage commonPackage;


    public MessageRetrofit(ApiParameter apiParameter, CommonPackage commonPackage) {
        this.apiParameter = apiParameter;
        this.commonPackage = commonPackage;

    }


    @Override
    public List<MessageEntity> catchMessage() throws BaseException {
        IRetroCommand<List<MessageEntity>, IMessageRetrofitService> command = service ->  RetroManager.execCaller(service.catchMessage(apiParameter)).body();


        return new RetroManager<List<MessageEntity>, IMessageRetrofitService>(commonPackage).callOfficeApi(command
                , IMessageRetrofitService.class);
    }

    @Override
    public List<MessageEntity> catchLatestMessage(int sendId) throws BaseException {
        IRetroCommand<List<MessageEntity>, IMessageRetrofitService> command = service -> {
            apiParameter.SendId = sendId;
            return  RetroManager.execCaller(service.catchLatestMessage(apiParameter)).body();
        };

        return new RetroManager<List<MessageEntity>, IMessageRetrofitService>(commonPackage).callOfficeApi(command
                , IMessageRetrofitService.class);
    }

    @Override
    public void SetMessageDelivered(List<Long> sendId) throws BaseException {
        IRetroCommand<Void, IMessageRetrofitService> command = service -> {
            apiParameter.Ids = sendId;

            RetroManager.execCaller(service.setMessageDelivered(apiParameter));
            return null;
        };

        new RetroManager<Void, IMessageRetrofitService>(commonPackage).callOfficeApi(command
                , IMessageRetrofitService.class);
    }

    @Override
    public void sendEndOfDailyVisitMessage(String message) throws BaseException {
        IRetroCommand<Void, IMessageRetrofitService> command = service -> {
            apiParameter.Message = message;
            apiParameter.MessageTypeId = 5;
            service.sendCompletePathVisit(apiParameter).execute();
            RetroManager.execCaller(service.sendCompletePathVisit(apiParameter));
            return null;
        };

        new RetroManager<Void, IMessageRetrofitService>(commonPackage).callOfficeApi(command
                , IMessageRetrofitService.class);
    }
}
