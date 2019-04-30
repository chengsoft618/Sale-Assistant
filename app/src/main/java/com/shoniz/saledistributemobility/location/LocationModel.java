package com.shoniz.saledistributemobility.location;

import android.content.Context;
import com.shoniz.saledistributemobility.location.data.LocationData;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by 921235 on 3/29/2018.
 */
public class LocationModel {
    public  Double latitude = 0.0;
    public  Double longitude = 0.0;
    public  Long locationDate = 0L;
    public  Float accuracy = 0.0f;

    public LocationModel(Double latitude, Double longitude, Long locationDate, Float accuracy) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationDate = locationDate;
        this.accuracy = accuracy;
    }

    public LocationModel() {
    }

    void insertLocation(Context context , LocationModel locationModel ) throws IOException, SQLException {
        new LocationData().insertLocation(context, locationModel);
    }

    public List<LocationModel> getAllLocations(Context context) throws IOException {
        return new LocationData().getLocations(context);
    }

}