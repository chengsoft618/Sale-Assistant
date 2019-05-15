package com.shoniz.saledistributemobility.view.path.pathlist;

import android.content.Context;
import android.database.Cursor;

import com.shoniz.saledistributemobility.utility.data.sqlite.DBHelper;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.view.path.PathModel__;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ferdos.s on 6/8/2017.
 */

public class PathData {

//    public static void updatePaths(Context context, List<PathModel__> models) throws IOException, SQLException {
//        //TODO:CHECK this
//        DBHelper db = Common.getSaleDataBase(context);
//        db.beginTransaction();
//        try {
//            ContentValues values = new ContentValues();
//            for (PathModel__ model : models) {
//                values.put(PathModel__.Column.CUSTOMER_COUNT, model.CustomerCount);
//                values.put(PathModel__.Column.PATH_CODE, model.PathCode);
//                values.put(PathModel__.Column.PATH_DESCRIPTION, model.PathDescription);
//                values.put(PathModel__.Column.PATH_NAME, model.PathName);
//                values.put(PathModel__.Column.PATH_START_POINT, model.PathStartPoint);
//                values.put(PathModel__.Column.PATH_END_POINT, model.PathEndPoint);
//                values.put(PathModel__.Column.RETAILER_COUNT, model.RetailerCount);
//                values.put(PathModel__.Column.VISIT_PATH_ROW, model.VisitPathRow);
//                values.put(PathModel__.Column.WHOLESALER_COUNT, model.WholesalerCount);
//                values.put(PathModel__.Column.ZONE_CODE, model.ZoneCode);
//                db.getSQLiteDatabase().update(SqliteConsts.TableName.PATH, values,PathModel__.Column.PATH_CODE +" = "+ model.PathCode,null);
//            }
//            db.setTransactionSuccessful();
//        } finally {
//            db.endTransaction();
//            db.close();
//        }
//    }

    public static List<PathModel__> getAllPaths(Context context) throws IOException {
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

//    public static void createPath(Context context,String dbFileBase64) throws IOException {
//        byte[] buffer = StringHelper.getByteFromBase64(dbFileBase64);
//        File f = new File(DBHelper.getDatabasePath(context)
//                + "/" + Enums.DBName.path + ".db");
//      /*  if (f.exists())
//            DBHelper.deleteDatabase(context,Enums.DBName.path);
//      */  OutputStream output = new FileOutputStream(f);
//        output.write(buffer);
//        output.close();
//    }
}
