package com.shoniz.saledistributemobility.data.model.location;

import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.data.model.location.api.ILocationApi;
import com.shoniz.saledistributemobility.data.model.location.db.ILocationDao;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.List;

public class LocationRepository implements ILocationRepository {
    private ILocationDao locationDao;
    private ILocationApi locationApi;

    public LocationRepository(ILocationDao locationDao,ILocationApi locationApi){
        this.locationDao = locationDao;
        this.locationApi = locationApi;
    }

    @Override
    public void insert(LocationEntity locationEntity) {
        locationDao.insert(locationEntity);
    }

    @Override
    public void insert(List<LocationEntity> locationEntities) {
        locationDao.insert(locationEntities);
    }

    @Override
    public void deleteAll() {
        locationDao.deleteAll();
    }

    @Override
    public List<LocationEntity> getLocations() {
        return locationDao.getLocations();
    }

    @Override
    public String startExactTracking(int userId) throws BaseException {
       return locationApi.startExactTracking(userId);
    }

    @Override
    public String stopExactTracking(int userId) throws BaseException {
        return locationApi.stopExactTracking(userId);
    }
}
