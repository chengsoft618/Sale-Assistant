package com.shoniz.saledistributemobility.data.model.log.api;

import com.shoniz.saledistributemobility.data.model.log.LogEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by 921235 on 5/12/2018.
 */

public interface ILogRetrofitService {
    @POST("InsertErrorLog")
    Call<Void> SendLog(@Body LogEntity logEntity);

    @POST("InsertErrorLogs")
    Call<Void> SendLogs(@Body List<LogEntity> logsEntities);
}
