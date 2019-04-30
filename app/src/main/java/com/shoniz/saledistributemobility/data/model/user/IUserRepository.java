package com.shoniz.saledistributemobility.data.model.user;

import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.List;

import retrofit2.http.GET;

public interface IUserRepository {

    public List<UserData> getUsers();

    public void sync() throws BaseException;

    public UserData getUser(int userId);

    List<UserEntity> getUsersExpect(int userId);

    List<RoleEntity> getRoles();

    int getUserMainRoleId(int userId);
}
