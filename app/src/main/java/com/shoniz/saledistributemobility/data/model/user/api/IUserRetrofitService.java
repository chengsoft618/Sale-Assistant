package com.shoniz.saledistributemobility.data.model.user.api;

import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.api.retrofit.IRetroService;
import com.shoniz.saledistributemobility.data.model.user.RoleEntity;
import com.shoniz.saledistributemobility.data.model.user.UserEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IUserRetrofitService {

    @POST("GetUsers")
    Call<List<UserEntity>> getUsers(@Body ApiParameter parameter);

    @POST("GetRoles")
    Call<List<RoleEntity>> getRoles(@Body ApiParameter parameter);
}

