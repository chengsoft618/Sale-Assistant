package com.shoniz.saledistributemobility.view.path;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.shoniz.saledistributemobility.utility.data.sqlite.DBHelper;
import com.shoniz.saledistributemobility.utility.data.sqlite.SqliteConsts;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.Enums;
import com.shoniz.saledistributemobility.utility.StringHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ferdos.s on 6/8/2017.
 */

public class PathData {

    public static void updatePaths(Context context, List<PathModel> models) throws IOException, SQLException {
        //TODO:CHECK this
        DBHelper db = Common.getSaleDataBase(context);
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (PathModel model : models) {
                values.put(PathModel.Column.CUSTOMER_COUNT, model.CustomerCount);
                values.put(PathModel.Column.PATH_CODE, model.PathCode);
                values.put(PathModel.Column.PATH_DESCRIPTION, model.PathDescription);
                values.put(PathModel.Column.PATH_NAME, model.PathName);
                values.put(PathModel.Column.PATH_START_POINT, model.PathStartPoint);
                values.put(PathModel.Column.PATH_END_POINT, model.PathEndPoint);
                values.put(PathModel.Column.RETAILER_COUNT, model.RetailerCount);
                values.put(PathModel.Column.VISIT_PATH_ROW, model.VisitPathRow);
                values.put(PathModel.Column.WHOLESALER_COUNT, model.WholesalerCount);
                values.put(PathModel.Column.ZONE_CODE, model.ZoneCode);
                db.getSQLiteDatabase().update(SqliteConsts.TableName.PATH, values,PathModel.Column.PATH_CODE +" = "+ model.PathCode,null);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static List<PathModel> getAllPaths(Context context) throws IOException {
        DBHelper db = null;
        List<PathModel> pathModels = new LinkedList<>();
        try {
            db = Common.getSaleDataBase(context);
            Cursor cursor = db.select("SELECT * FROM Path Order BY IsActive DESC, PathName ASC ");
            if (cursor.getCount()>0) {
                cursor.moveToFirst();
                do {
                    PathModel pathModel = new PathModel();
                    pathModel.PathName = cursor.getString(cursor.getColumnIndex(PathModel.Column.PATH_NAME));
                    pathModel.PathEndPoint = cursor.getString(cursor.getColumnIndex(PathModel.Column.PATH_END_POINT));
                    pathModel.CustomerCount = cursor.getInt(cursor.getColumnIndex(PathModel.Column.CUSTOMER_COUNT));
                    pathModel.PathDescription = cursor.getString(cursor.getColumnIndex(PathModel.Column.PATH_DESCRIPTION));
                    pathModel.PathCode = cursor.getInt(cursor.getColumnIndex(PathModel.Column.PATH_CODE));
                    pathModel.PathStartPoint = cursor.getString(cursor.getColumnIndex(PathModel.Column.PATH_START_POINT));
                    pathModel.RetailerCount = cursor.getInt(cursor.getColumnIndex(PathModel.Column.RETAILER_COUNT));
                    pathModel.WholesalerCount = cursor.getInt(cursor.getColumnIndex(PathModel.Column.WHOLESALER_COUNT));
                    pathModel.TourPeriod = cursor.getInt(cursor.getColumnIndex(PathModel.Column.TOUR_PERIOD));
                    pathModel.ZoneCode = cursor.getInt(cursor.getColumnIndex(PathModel.Column.ZONE_CODE));
                    pathModel.IsActive = cursor.getInt(cursor.getColumnIndex(PathModel.Column.IS_ACTIVE))!=0;
                    pathModel.PersianDate = cursor.getString(cursor.getColumnIndex(PathModel.Column.Persian_Date));
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

    public static void createPath(Context context,String dbFileBase64) throws IOException {
        byte[] buffer = StringHelper.getByteFromBase64(dbFileBase64);
        File f = new File(DBHelper.getDatabasePath(context)
                + "/" + Enums.DBName.path + ".db");
      /*  if (f.exists())
            DBHelper.deleteDatabase(context,Enums.DBName.path);
      */  OutputStream output = new FileOutputStream(f);
        output.write(buffer);
        output.close();
    }
}
