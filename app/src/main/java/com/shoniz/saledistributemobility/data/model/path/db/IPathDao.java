package com.shoniz.saledistributemobility.data.model.path.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.shoniz.saledistributemobility.data.model.path.PathListData;

import java.util.List;

@Dao
public interface IPathDao {
    @Query("SELECT * FROM Path")
    List<PathEntity> getPaths();

    @Query("SELECT * FROM Path where IsActive=1")
    PathEntity getActivePath();

    @Query("SELECT *, 1 RowNumber FROM Path Order BY IsActive DESC, PathName ASC")
    List<PathListData> getAllPaths();

    @Query("SELECT *, 1 RowNumber FROM Path WHERE PathCode = :pathId Order BY IsActive DESC, PathName ASC")
    PathEntity getPath(int pathId);
}
