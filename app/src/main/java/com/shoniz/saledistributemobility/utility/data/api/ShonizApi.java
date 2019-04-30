package com.shoniz.saledistributemobility.utility.data.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.shoniz.saledistributemobility.data.model.app.BranchEntity;
import com.shoniz.saledistributemobility.framework.exception.OldApiException;
import com.shoniz.saledistributemobility.framework.exception.ConnectionException;
import com.shoniz.saledistributemobility.utility.device.Connection;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class ShonizApi {
    private final OkHttpClient client = new OkHttpClient();

    public List<BranchEntity> GetBranchList(Context context) throws OldApiException, IOException, ConnectionException {

        Response response = new CallApi<List<BranchEntity>>(Connection.getShonizApiUrl(context))
               .Post(ApiConsts.Api.GET_BRANCH, new PostModel());
        if(response.code() != 200){
            throw
                    new OldApiException(context,response);

        }
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        List<BranchEntity> obj =   gson.fromJson(response.body().charStream(),
                new TypeToken<List<BranchEntity>>(){}.getType());
        return obj;
    }
}