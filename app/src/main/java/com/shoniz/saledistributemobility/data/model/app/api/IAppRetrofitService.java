package com.shoniz.saledistributemobility.data.model.app.api;

import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.api.retrofit.IRetroService;
import com.shoniz.saledistributemobility.data.model.user.RoleEntity;
import com.shoniz.saledistributemobility.data.model.user.UserEntity;
import com.shoniz.saledistributemobility.infrastructure.version.VersionData;
import com.shoniz.saledistributemobility.view.entity.EmployeeInfoEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IAppRetrofitService {

    @POST("GetVersionLink")
    Call<VersionData> getVersionLink(@Body ApiParameter parameter);

    @POST("GetEmployeeInfo")
    Call<EmployeeInfoEntity> getEmployeeInfo(@Body ApiParameter parameter);

    @POST("GetPlayStoreLink")
    Call<VersionData> getGooglePlayServicesVersionLink(@Body ApiParameter apiParameter);

    @POST("GetChromeLink")
    Call<String> getChromeLink(@Body ApiParameter apiParameter);
}

