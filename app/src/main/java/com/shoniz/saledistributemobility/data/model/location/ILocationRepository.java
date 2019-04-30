package com.shoniz.saledistributemobility.data.model.location;

import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.List;

public interface ILocationRepository {
    void insert(LocationEntity locationEntity);
    void insert(List<LocationEntity> locationEntities);
    void deleteAll();
    List<LocationEntity> getLocations();

    String startExactTracking(int userId) throws BaseException, ApiException;
    String stopExactTracking(int userId) throws BaseException, ApiException;
}
