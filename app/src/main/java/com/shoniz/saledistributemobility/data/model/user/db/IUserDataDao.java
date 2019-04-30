package com.shoniz.saledistributemobility.data.model.user.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.shoniz.saledistributemobility.data.model.user.UserData;

import java.util.List;

@Dao
public interface IUserDataDao {

    @Query("SELECT " +
            "u.*, r.canTerminate r_canTerminate, r.roleName r_roleName, r.directingPriority " +
            "r_directingPriority FROM user u " +
            "INNER JOIN role r ON r.roleId = u.roleId WHERE u.userId = :userId")
    public List<UserData> getUserData(int userId);

    @Query("SELECT " +
            "u.*, r.canTerminate r_canTerminate, r.roleName r_roleName, r.roleId r_roleId, r.directingPriority " +
            "r_directingPriority FROM user u " +
            "INNER JOIN role r ON r.roleId = u.roleId")
    public List<UserData> getUsersData();



}
