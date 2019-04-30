package com.shoniz.saledistributemobility.data.model.user.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.shoniz.saledistributemobility.data.model.user.UserEntity;

import java.util.List;

@Dao
public interface IUserDao {

    @Insert
    public void insert(UserEntity... entity);

    @Insert
    public void insert(List<UserEntity> entity);

    @Query("DELETE FROM user")
    public void deleteAll();

    @Query("SELECT DISTINCT u.userId,u.fullName,0 officerUserId,0 roleId FROM user u WHERE u.userId<>:userId ORDER BY u.userId  ")
    public List<UserEntity> getUsersExpect(int userId);

    @Query("SELECT MAX(u.roleId) FROM user u WHERE u.userId = :userId ")
    public int getUserMainRole(int userId);
}
