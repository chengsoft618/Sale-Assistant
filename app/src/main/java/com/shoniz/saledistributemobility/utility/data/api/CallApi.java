package com.shoniz.saledistributemobility.utility.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public final class CallApi<T> {

    private String baseUrl;

    public CallApi(String url){
        this.baseUrl = url;
    }

    private OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build();

    public Response Post(String route, Object object) throws IOException  {

        Gson gson = new Gson();
        String MIME_JSON = "application/json";
        RequestBody body = RequestBody.create(MediaType.parse(MIME_JSON), gson.toJson(object));

        Request request = new Request.Builder()
                .url(baseUrl + route)
                .post(body)
                .build();

        return okHttpClient.newCall(request).execute();

    }

}
