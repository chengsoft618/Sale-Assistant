package com.shoniz.saledistributemobility.data.sharedpref.api;

import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.sharedpref.SettingEntity;
import com.shoniz.saledistributemobility.infrastructure.version.VersionData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ISettingRetrofitService {

    @POST("GetUserSettings")
    Call<List<SettingEntity>> getUserSettings(@Body ApiParameter parameter);

}

