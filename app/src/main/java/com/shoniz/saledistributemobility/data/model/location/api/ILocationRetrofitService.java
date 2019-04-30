package com.shoniz.saledistributemobility.data.model.location.api;

import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.model.order.OrderDetailEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by 921235 on 5/12/2018.
 */

public interface ILocationRetrofitService {

    @POST("startExactTracking")
    Call<String> startExactTracking(@Body ApiParameter parameter);

    @POST("stopExactTracking")
    Call<String> stopExactTracking(@Body ApiParameter parameter);
}
