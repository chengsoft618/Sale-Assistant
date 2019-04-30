package com.shoniz.saledistributemobility.data.model.user.api;


import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.api.retrofit.IRetroCommand;
import com.shoniz.saledistributemobility.data.api.retrofit.RetroManager;
import com.shoniz.saledistributemobility.data.model.user.RoleEntity;
import com.shoniz.saledistributemobility.data.model.user.UserEntity;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.io.IOException;
import java.util.List;

public class UserRetrofit implements IUserApi {

    ApiParameter apiParameter;
    CommonPackage commonPackage;

    public UserRetrofit(ApiParameter apiParameter, CommonPackage commonPackage) {
        this.apiParameter = apiParameter;
        this.commonPackage = commonPackage;
    }


    @Override
    public List<UserEntity> getUsers() throws BaseException {

        IRetroCommand<List<UserEntity>, IUserRetrofitService> command = new IRetroCommand<List<UserEntity>, IUserRetrofitService>() {
            @Override
            public List<UserEntity> exec(IUserRetrofitService service) throws IOException {
                return service.getUsers(apiParameter).execute().body();
            }
        };
        return new RetroManager<List<UserEntity>, IUserRetrofitService>(commonPackage).callOfficeApi(command
                , IUserRetrofitService.class);
    }


    @Override
    public List<RoleEntity> getRoles() throws BaseException {
        IRetroCommand<List<RoleEntity>, IUserRetrofitService> command = new IRetroCommand<List<RoleEntity>, IUserRetrofitService>() {
            @Override
            public List<RoleEntity> exec(IUserRetrofitService service) throws IOException {
                return service.getRoles(apiParameter).execute().body();
            }
        };
        return new RetroManager<List<RoleEntity>, IUserRetrofitService>(commonPackage).callOfficeApi(command, IUserRetrofitService.class);
    }



}
