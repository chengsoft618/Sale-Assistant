package com.shoniz.saledistributemobility.data.model.user.api;

import com.shoniz.saledistributemobility.data.model.user.RoleEntity;
import com.shoniz.saledistributemobility.data.model.user.UserEntity;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.io.IOException;
import java.util.List;

public interface IUserApi {
    public List<UserEntity> getUsers() throws BaseException;
    public List<RoleEntity> getRoles() throws BaseException;
}
