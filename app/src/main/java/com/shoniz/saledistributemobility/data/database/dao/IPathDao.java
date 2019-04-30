package com.shoniz.saledistributemobility.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.shoniz.saledistributemobility.view.entity.PathEntity;

import java.util.List;

@Dao
public interface IPathDao {
    @Query("SELECT * FROM Path")
    List<PathEntity> getPaths();

    @Query("SELECT * FROM Path where IsActive=1")
    PathEntity getActivePath();
}
