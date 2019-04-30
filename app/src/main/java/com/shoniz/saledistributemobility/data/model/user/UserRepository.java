package com.shoniz.saledistributemobility.data.model.user;

import com.shoniz.saledistributemobility.data.model.user.db.IUserDataDao;
import com.shoniz.saledistributemobility.data.model.user.api.IUserApi;
import com.shoniz.saledistributemobility.data.model.user.db.IRoleDao;
import com.shoniz.saledistributemobility.data.model.user.db.IUserDao;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.List;

public class UserRepository implements IUserRepository {


    IUserDataDao userDataDao;
    IUserDao userDao;
    IRoleDao roleDao;
    IUserApi userApi;


    public UserRepository(IUserDataDao userDataDao,
                          IUserDao userDao,
                          IRoleDao roleDao,
                          IUserApi userApi) {
        this.userDataDao = userDataDao;
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.userApi = userApi;
    }

    @Override
    public List<UserData> getUsers() {
        return userDataDao.getUsersData();
    }

    @Override
    public void sync() throws BaseException {
        userDao.deleteAll();
        roleDao.deleteAll();
        userDao.insert(userApi.getUsers());
        roleDao.insert(userApi.getRoles());
    }

    @Override
    public UserData getUser(int userId) {
        return null;
    }

    @Override
    public List<UserEntity> getUsersExpect(int userId) {
        return userDao.getUsersExpect(userId);
    }

    @Override
    public List<RoleEntity> getRoles() {
        return roleDao.getRoles();
    }

    @Override
    public int getUserMainRoleId(int userId) {
        return userDao.getUserMainRole(userId);
    }
}
