package com.shoniz.saledistributemobility.data.api.retrofit;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.shoniz.saledistributemobility.framework.exception.model.ApiMessage;

import java.io.IOException;

import retrofit2.Response;

public class ApiException extends Exception {
    public ApiException(String response)   {
         super(response);

    }


}
