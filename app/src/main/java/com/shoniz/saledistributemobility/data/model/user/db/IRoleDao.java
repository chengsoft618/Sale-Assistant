package com.shoniz.saledistributemobility.data.model.user.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.shoniz.saledistributemobility.data.model.user.RoleEntity;

import java.util.List;

@Dao
public interface IRoleDao {

    @Insert
    public void insert(RoleEntity... entity);

    @Insert
    public void insert(List<RoleEntity> entity);

    @Query("DELETE FROM role")
    public void deleteAll();


    @Query("SELECT * FROM role")
    public List<RoleEntity> getRoles();

    @Query("SELECT r.* FROM user u JOIN role r ON r.roleId = r.roleId WHERE userId = :userId" )
    public List<RoleEntity> getRoles(int userId);
}
