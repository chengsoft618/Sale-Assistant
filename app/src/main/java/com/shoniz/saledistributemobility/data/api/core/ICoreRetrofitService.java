package com.shoniz.saledistributemobility.data.api.core;

import com.shoniz.saledistributemobility.data.api.retrofit.IRetroService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ICoreRetrofitService {

    @GET("isOnline")
    Call<Boolean> IsOnline();
}
