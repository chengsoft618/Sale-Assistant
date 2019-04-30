package com.shoniz.saledistributemobility.framework.exception;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.framework.exception.model.ApiMessage;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;



public class OldApiException extends OldBaseException {
    private  Request request;

    public OldApiException(Context context, Response response) throws IOException {
        super();
        this.request = response.networkResponse().request();
        errorModel.systemMessage = response.networkResponse().message();
        generateMessage(context,response);
    }

    private  void generateMessage(Context context, Response response) throws IOException {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        ApiMessage apiMessage= gson.fromJson(response.body().string(),
                new TypeToken<ApiMessage>() {
                }.getType());


        switch (response.code()){
            case 400:
                errorModel.userMessage =  apiMessage.Message;
                errorModel.userTitle= context.getString(R.string.error_message_api_not_found);
                errorModel.systemMessage= "Error code : 400";
                // StringHelper.GenerateMessage(ErrorCodes.API_NOT_FOUND, args);
                break;
            case 404:
                errorModel.userMessage =  apiMessage.Message;
                errorModel.userTitle= context.getString(R.string.error_message_api_not_found);
                errorModel.systemMessage= "Request: " + request.toString();
                // StringHelper.GenerateMessage(ErrorCodes.API_NOT_FOUND, args);
                break;
            case 500:
                errorModel.userMessage = apiMessage.Message;
                errorModel.userTitle= context.getString(R.string.error_message_api_server);
                errorModel.systemMessage= "Request: " + request.toString();
                break;

            default:
                errorModel.userMessage = apiMessage.Message;
                errorModel.userTitle= String.valueOf(response.code());
                errorModel.systemMessage= "Request: " + request.toString();
                break;
        }
    }
}

