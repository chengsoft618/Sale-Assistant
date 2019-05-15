package com.shoniz.saledistributemobility.view.path.customerlist;

import android.content.Context;
import android.database.Cursor;

import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.data.sqlite.DBHelper;
import com.shoniz.saledistributemobility.view.path.PathModel__;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ferdos.s on 6/8/2017.
 */

public class CustomerData__ {

    public static List<PathModel__> getAllCustomers(Context context) throws IOException {
        DBHelper db = null;
        List<PathModel__> pathModels = new LinkedList<>();
        try {
            db = Common.getSaleDataBase(context);
            Cursor cursor = db.select("SELECT * FROM Path Order BY IsActive DESC, PathName ASC ");
            if (cursor.getCount()>0) {
                cursor.moveToFirst();
                cursor.moveToFirst();
                do {
                    PathModel__ pathModel = new PathModel__();
                    pathModel.PathName = cursor.getString(cursor.getColumnIndex(PathModel__.Column.PATH_NAME));
                    pathModel.PathEndPoint = cursor.getString(cursor.getColumnIndex(PathModel__.Column.PATH_END_POINT));
                    pathModel.CustomerCount = cursor.getInt(cursor.getColumnIndex(PathModel__.Column.CUSTOMER_COUNT));
                    pathModel.PathDescription = cursor.getString(cursor.getColumnIndex(PathModel__.Column.PATH_DESCRIPTION));
                    pathModel.PathCode = cursor.getInt(cursor.getColumnIndex(PathModel__.Column.PATH_CODE));
                    pathModel.PathStartPoint = cursor.getString(cursor.getColumnIndex(PathModel__.Column.PATH_START_POINT));
                    pathModel.RetailerCount = cursor.getInt(cursor.getColumnIndex(PathModel__.Column.RETAILER_COUNT));
                    pathModel.WholesalerCount = cursor.getInt(cursor.getColumnIndex(PathModel__.Column.WHOLESALER_COUNT));
                    pathModel.TourPeriod = cursor.getInt(cursor.getColumnIndex(PathModel__.Column.TOUR_PERIOD));
                    pathModel.ZoneCode = cursor.getInt(cursor.getColumnIndex(PathModel__.Column.ZONE_CODE));
                    pathModel.IsActive = cursor.getInt(cursor.getColumnIndex(PathModel__.Column.IS_ACTIVE))!=0;
                    pathModel.PersianDate = cursor.getString(cursor.getColumnIndex(PathModel__.Column.Persian_Date));
                    pathModels.add(pathModel);

                } while (cursor.moveToNext());
                cursor.close();
            }
        } finally {
            if (db != null)
                db.close();
        }
        return pathModels;
    }

}
