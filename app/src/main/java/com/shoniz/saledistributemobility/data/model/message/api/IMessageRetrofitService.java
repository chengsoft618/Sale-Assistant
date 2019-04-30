package com.shoniz.saledistributemobility.data.model.message.api;

import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.model.message.MessageEntity;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.log.ILoggerCommand;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by 921235 on 5/12/2018.
 */

public interface IMessageRetrofitService {

    @POST("CatchMessage")
    Call<List<MessageEntity>> catchMessage(@Body ApiParameter parameter) ;
    @POST("CatchLatestMessage")
    Call<List<MessageEntity>> catchLatestMessage(@Body ApiParameter parameter);
    @POST("SetMessageDelivered")
    Call<Void> setMessageDelivered(@Body ApiParameter parameter) ;

    @POST("InsertMessage")
    Call<Void> sendCompletePathVisit(@Body ApiParameter apiParameter);
}
