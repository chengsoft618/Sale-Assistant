package com.shoniz.saledistributemobility.data.model.location.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.shoniz.saledistributemobility.data.model.location.LocationEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ILocationDao {
    @Insert(onConflict = REPLACE)
    void insert(LocationEntity location);

    @Insert(onConflict = REPLACE)
    void insert(List<LocationEntity> locations);

    @Query("DELETE FROM location")
    void deleteAll();

    @Query("SELECT * FROM location")
    List<LocationEntity> getLocations();
}
