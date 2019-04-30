package com.shoniz.saledistributemobility.location.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.shoniz.saledistributemobility.utility.data.sqlite.DBHelper;
import com.shoniz.saledistributemobility.location.LocationModel;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.Enums;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 921235 on 3/4/2018.
 */

public class LocationData {

    public void insertLocation(Context context,LocationModel model) throws IOException, SQLException {
         DBHelper db = Common.getUserDataBase(context);

        try {
            ContentValues values = new ContentValues();

            values.put(Column.Latitude, model.latitude);
            values.put(Column.Longitude, model.longitude);
            values.put(Column.Accuracy, model.accuracy);
            values.put(Column.LocationDate, model.locationDate);

            db.getSQLiteDatabase().insertOrThrow(TableName.Location, null, values);
        } finally {
            db.close();
        }
    }


    public List<LocationModel> getLocations(Context context) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        List<LocationModel> locationModels = new ArrayList();
        try {
            db = Common.getUserDataBase(context);
            cursor = db.select(LocationQuery.AllLocations);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    LocationModel locationModel = new LocationModel();

                    locationModel.latitude = cursor.getDouble(cursor.getColumnIndex(Column.Latitude));
                    locationModel.longitude = cursor.getDouble((cursor.getColumnIndex(Column.Longitude)));
                    locationModel.accuracy = cursor.getFloat((cursor.getColumnIndex(Column.Accuracy)));
                    locationModel.locationDate = cursor.getLong(cursor.getColumnIndex(Column.LocationDate));
                    locationModels.add(locationModel);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return locationModels;
    }


    LocationModel getLastLocation(Context context) throws IOException, SQLException {
        DBHelper db = null;
        Cursor cursor = null;
        try {
            db = Common.getUserDataBase(context);
            cursor = db.select(LocationQuery.LastLocation);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                LocationModel locationModel = new LocationModel();
                    locationModel.latitude = cursor.getDouble(cursor.getColumnIndex(Column.Latitude));
                    locationModel.longitude = cursor.getDouble((cursor.getColumnIndex(Column.Longitude)));
                    locationModel.accuracy = cursor.getFloat((cursor.getColumnIndex(Column.Accuracy)));
                    locationModel.locationDate = cursor.getLong(cursor.getColumnIndex(Column.LocationDate));
                return locationModel;
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        throw new SQLException("Can not find last location");
    }

}
