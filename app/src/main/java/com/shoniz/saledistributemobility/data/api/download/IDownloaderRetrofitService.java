package com.shoniz.saledistributemobility.data.api.download;

import com.shoniz.saledistributemobility.data.api.retrofit.IRetroService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface IDownloaderRetrofitService extends IRetroService{
    @Streaming
    @GET
    Call<ResponseBody> download(@Url String fileUrl);

    @Streaming
    @POST("GetSaleDatabase")
    Call<ResponseBody> downloadSaleDatabase(@Url String fileUrl);

    @Streaming
    @POST("Get")
    Call<ResponseBody> downloadImages(@Url String fileUrl);
}
