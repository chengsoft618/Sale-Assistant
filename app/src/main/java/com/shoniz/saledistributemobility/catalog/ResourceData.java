package com.shoniz.saledistributemobility.catalog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.data.sqlite.DBHelper;
import com.shoniz.saledistributemobility.utility.data.sqlite.SqliteConsts;
import com.shoniz.saledistributemobility.view.catalog.subcategory.SubCategoryDetailModel;
import com.shoniz.saledistributemobility.view.catalog.subcategory.SubCategoryModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ferdos.s on 5/29/2017.
 */

public class ResourceData {


    public static List<ResourceModel> getShortcutStored(Context context) throws IOException {
        DBHelper db = null;
        List<ResourceModel> lst = new ArrayList<>();
//        Cursor cursor = null;
//        try {
//            db = Common.getUserDataBase(context);
//            cursor = db.select("SELECT * FROM ImageVersion ");
//            if (cursor.getCount() > 0) {
//                cursor.moveToFirst();
//                do {
//                    ResourceModel model = new ResourceModel();
//                    model.ResourceFileId = cursor.getInt(cursor.getColumnIndex(ProductImageModel.Column.Shortcut));
//                    model.VersionNo = cursor.getInt(cursor.getColumnIndex(ProductImageModel.Column.VersionNo));
//                    lst.add(model);
//                } while (cursor.moveToNext());
//            }
//        } finally {
//            if (cursor != null)
//                cursor.close();
//            if (db != null)
//                db.close();
//        }
        return lst;
    }

    public static List<ResourceModel> getResourceStored(Context context) throws IOException {
        DBHelper db = null;
        List<ResourceModel> lst = new ArrayList<>();
        Cursor cursor = null;
        try {
            db = Common.getUserDataBase(context);
            cursor = db.select("SELECT * FROM FileResource ");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    ResourceModel model = new ResourceModel();
                    model.ResourceFileId = cursor.getInt(cursor.getColumnIndex(ResourceModel.Column.ResourceFileId));
                    model.VersionNo = cursor.getInt(cursor.getColumnIndex(ResourceModel.Column.VersionNo));
                    lst.add(model);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return lst;
    }

    public static List<ProfileCategoryModel> getProfileCategory(Context context) throws IOException {
        DBHelper db = null;
        List<ProfileCategoryModel> lst = new ArrayList<>();
        Cursor cursor = null;
        try {
            db = Common.getSaleDataBase(context);
            cursor = db.select("SELECT * FROM ProfileCategory ");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    ProfileCategoryModel model = new ProfileCategoryModel();
                    model.ProfileCategoryId = cursor.getInt(cursor.getColumnIndex(ProfileCategoryModel.Column.ProfileCategoryId));
                    model.ProfileName = cursor.getString(cursor.getColumnIndex(ProfileCategoryModel.Column.ProfileName));
                    lst.add(model);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return lst;
    }

    public static List<SubCategoryModel> getSubCategory(Context context, int categoryId) throws IOException {
        DBHelper db = null;
        List<SubCategoryModel> lst = new ArrayList<>();
        Cursor cursor = null;
        try {
            db = Common.getSaleDataBase(context);
            cursor = db.select("SELECT * from SubCategory  WHERE CategoryId=" + categoryId + " Order by Sort");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    SubCategoryModel model = new SubCategoryModel();
                    model.CategoryId = cursor.getInt(cursor.getColumnIndex(SubCategoryModel.Column.CategoryId));
                    model.ResourceFileId = cursor.getInt(cursor.getColumnIndex(SubCategoryModel.Column.ResourceFileId));
                    model.Sort = cursor.getInt(cursor.getColumnIndex(SubCategoryModel.Column.Sort));
                    model.SubCategoryId = cursor.getInt(cursor.getColumnIndex(SubCategoryModel.Column.SubCategoryId));
                    model.SubCategoryName = cursor.getString(cursor.getColumnIndex(SubCategoryModel.Column.SubCategoryName));

                    lst.add(model);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return lst;
    }


    public static SubCategoryDetailModel getShortcutInfo(Context context, int shortcut) throws IOException {
        DBHelper db = null;
        SubCategoryDetailModel model = new SubCategoryDetailModel();
        Cursor cursor = null;
        try {
            db = Common.getSaleDataBase(context);
            cursor = db.select("SELECT\n" +
                    "\t*\n" +
                    "FROM\n" +
                    "\tSubCategoryDetail s\n" +
                    "INNER JOIN Coding c ON CAST (s.Shortcut AS text) = c.Shortcut\n" +
                    "INNER JOIN SubCategory sc ON sc.SubCategoryId = s.SubCategoryId\n" +
                    "INNER JOIN Category cc ON cc.CategoryId = sc.CategoryId\n" +
                    "WHERE\n" +
                    "\tc.Shortcut =" + shortcut);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                model.Shortcut = cursor.getInt(cursor.getColumnIndex(SubCategoryDetailModel.Column.Shortcut));
                model.SubCategoryDetailId = cursor.getInt(cursor.getColumnIndex(SubCategoryDetailModel.Column.SubCategoryDetailId));
                model.CategoryId = cursor.getInt(cursor.getColumnIndex(SubCategoryDetailModel.Column.CategoryId));
                model.SubCategoryId = cursor.getInt(cursor.getColumnIndex(SubCategoryDetailModel.Column.SubCategoryId));
                model.Sort = cursor.getInt(cursor.getColumnIndex(SubCategoryDetailModel.Column.Sort));
                model.ProductDescription = cursor.getString(cursor.getColumnIndex(SubCategoryDetailModel.Column.ProductDescription));
                model.SalePrice = cursor.getString(cursor.getColumnIndex(SubCategoryDetailModel.Column.SalePrice));
                model.NetWeight = cursor.getString(cursor.getColumnIndex(SubCategoryDetailModel.Column.NetWeight));
                model.ProductName = cursor.getString(cursor.getColumnIndex(SubCategoryDetailModel.Column.ProductName));

            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return model;
    }

    public static List<SubCategoryDetailModel> getSubCategoryDetail(Context context, int subCategoryId) throws IOException {
        DBHelper db = null;
        List<SubCategoryDetailModel> lst = new ArrayList<>();
        Cursor cursor = null;
        try {
            db = Common.getSaleDataBase(context);
            cursor = db.select("SELECT * from SubCategoryDetail s INNER JOIN Coding c" +
                    " on cast(s.Shortcut as text)=c.Shortcut  WHERE SubCategoryId=" + subCategoryId);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    SubCategoryDetailModel model = new SubCategoryDetailModel();
                    model.Shortcut = cursor.getInt(cursor.getColumnIndex(SubCategoryDetailModel.Column.Shortcut));
                    model.SubCategoryDetailId = cursor.getInt(cursor.getColumnIndex(SubCategoryDetailModel.Column.SubCategoryDetailId));
                    model.SubCategoryId = cursor.getInt(cursor.getColumnIndex(SubCategoryDetailModel.Column.SubCategoryId));
                    model.Sort = cursor.getInt(cursor.getColumnIndex(SubCategoryDetailModel.Column.Sort));
                    model.ProductDescription = cursor.getString(cursor.getColumnIndex(SubCategoryDetailModel.Column.ProductDescription));
                    model.SalePrice = cursor.getString(cursor.getColumnIndex(SubCategoryDetailModel.Column.SalePrice));
                    model.NetWeight = cursor.getString(cursor.getColumnIndex(SubCategoryDetailModel.Column.NetWeight));
                    model.ProductName = cursor.getString(cursor.getColumnIndex(SubCategoryDetailModel.Column.ProductName));

                    lst.add(model);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return lst;
    }

    public static SubCategoryDetailModel getShortcut(Context context, String shortcut) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        try {
            db = Common.getSaleDataBase(context);
            cursor = db.select("SELECT *  FROM Coding c WHERE c.Shortcut ='" + shortcut + "'");
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                SubCategoryDetailModel model = new SubCategoryDetailModel();
                model.Shortcut = cursor.getInt(cursor.getColumnIndex(SubCategoryDetailModel.Column.Shortcut));
                model.SalePrice = cursor.getString(cursor.getColumnIndex(SubCategoryDetailModel.Column.SalePrice));
                model.NetWeight = cursor.getString(cursor.getColumnIndex(SubCategoryDetailModel.Column.NetWeight));
                model.ProductName = cursor.getString(cursor.getColumnIndex(SubCategoryDetailModel.Column.ProductName));
                model.ProductDescription = cursor.getString(cursor.getColumnIndex(SubCategoryDetailModel.Column.ProductDescription));
                return model;
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return null;
    }

    public static void insertShortcutStored(Context context, ProductImageModel shortcutModel) throws IOException, SQLException {
//        DBHelper db = Common.getUserDataBase(context);
//        db.beginTransaction();
//        try {
//            ContentValues values = new ContentValues();
//            values.put(ProductImageModel.Column.Shortcut, shortcutModel.Shortcut);
//            values.put(ProductImageModel.Column.VersionNo, shortcutModel.VersionNo);
//            db.getSQLiteDatabase().delete(SqliteConsts.TableName.IMAGE_VERSION,
//                    ProductImageModel.Column.Shortcut + "=?",
//                    new String[]{"" + shortcutModel.Shortcut});
//            db.getSQLiteDatabase().insertOrThrow(SqliteConsts.TableName.IMAGE_VERSION, null, values);
//            db.setTransactionSuccessful();
//        } finally {
//            db.endTransaction();
//        }
    }

    public static void insertResourceStored(Context context, ResourceModel resourceModel) throws IOException, SQLException {
        DBHelper db = Common.getUserDataBase(context);
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(ResourceModel.Column.ResourceFileId, resourceModel.ResourceFileId);
            values.put(ResourceModel.Column.VersionNo, resourceModel.VersionNo);
            db.getSQLiteDatabase().delete(SqliteConsts.TableName.FILE_RESOURCE,
                    ResourceModel.Column.ResourceFileId + "=?",
                    new String[]{"" + resourceModel.ResourceFileId});
            db.getSQLiteDatabase().insertOrThrow(SqliteConsts.TableName.FILE_RESOURCE, null, values);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public static void insertProfileCategoryAll(Context context, List<ProfileCategoryModel> models) throws IOException, SQLException {
        DBHelper db = Common.getSaleDataBase(context);
        db.beginTransaction();
        try {
            for (ProfileCategoryModel model : models) {
                ContentValues values = new ContentValues();
                values.put(ProfileCategoryModel.Column.ProfileCategoryId, model.ProfileCategoryId);
                values.put(ProfileCategoryModel.Column.ProfileName, model.ProfileName);
                db.getSQLiteDatabase().delete(SqliteConsts.TableName.PROFILE_CATEGORY,
                        ProfileCategoryModel.Column.ProfileCategoryId + "=?",
                        new String[]{"" + model.ProfileCategoryId});
                db.getSQLiteDatabase().insertOrThrow(SqliteConsts.TableName.PROFILE_CATEGORY, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public static void insertCategoryAll(Context context, List<CategoryModel> models) throws IOException, SQLException {
        DBHelper db = Common.getSaleDataBase(context);
        db.beginTransaction();
        try {
            for (CategoryModel model : models) {
                ContentValues values = new ContentValues();
                values.put(CategoryModel.Column.CategoryId, model.CategoryId);
                values.put(CategoryModel.Column.CategoryName, model.CategoryName);
                values.put(CategoryModel.Column.ProfileCategoryId, model.ProfileCategoryId);
                values.put(CategoryModel.Column.ResourceFileId, model.ResourceFileId);

                values.put(CategoryModel.Column.Sort, model.Sort);
                db.getSQLiteDatabase().delete(SqliteConsts.TableName.CATEGORY,
                        CategoryModel.Column.CategoryId + "=?",
                        new String[]{"" + model.CategoryId});
                db.getSQLiteDatabase().insertOrThrow(SqliteConsts.TableName.CATEGORY, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public static void insertSubCategoryAll(Context context, List<SubCategoryModel> models) throws IOException, SQLException {
        DBHelper db = Common.getSaleDataBase(context);
        db.beginTransaction();
        try {
            for (SubCategoryModel model : models) {
                ContentValues values = new ContentValues();
                values.put(SubCategoryModel.Column.CategoryId, model.CategoryId);
                values.put(SubCategoryModel.Column.ResourceFileId, model.ResourceFileId);
                values.put(SubCategoryModel.Column.Sort, model.Sort);
                values.put(SubCategoryModel.Column.SubCategoryId, model.SubCategoryId);
                values.put(SubCategoryModel.Column.SubCategoryName, model.SubCategoryName);
                db.getSQLiteDatabase().delete(SqliteConsts.TableName.SUB_CATEGORY,
                        SubCategoryModel.Column.SubCategoryId + "=?",
                        new String[]{"" + model.SubCategoryId});
                db.getSQLiteDatabase().insertOrThrow(SqliteConsts.TableName.SUB_CATEGORY, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public static void insertSubCategoryDetailAll(Context context, List<SubCategoryDetailModel> models) throws IOException, SQLException {
        DBHelper db = Common.getSaleDataBase(context);
        db.beginTransaction();
        try {
            for (SubCategoryDetailModel model : models) {
                ContentValues values = new ContentValues();
                values.put(SubCategoryDetailModel.Column.Shortcut, model.Shortcut);
                values.put(SubCategoryDetailModel.Column.Sort, model.Sort);
                values.put(SubCategoryDetailModel.Column.SubCategoryDetailId, model.SubCategoryDetailId);
                values.put(SubCategoryDetailModel.Column.SubCategoryId, model.SubCategoryId);
                db.getSQLiteDatabase().delete(SqliteConsts.TableName.SUB_CATEGORY_DETAIL,
                        SubCategoryDetailModel.Column.SubCategoryDetailId + "=?",
                        new String[]{"" + model.SubCategoryDetailId});
                db.getSQLiteDatabase().insertOrThrow(SqliteConsts.TableName.SUB_CATEGORY_DETAIL, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public static List<CategoryModel> getCategory(Context context, int profileCategoryId) throws IOException {
        DBHelper db = Common.getSaleDataBase(context);
        String command = "SELECT CategoryId ,CategoryName,ResourceFileId,Sort FROM Category Where ProfileCategoryId=" + profileCategoryId + " Order by sort ";
        List<CategoryModel> lst = new ArrayList<>();
        Cursor cursor = null;
        try {
            db = Common.getSaleDataBase(context);
            cursor = db.select(command);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    CategoryModel model = new CategoryModel();
                    model.CategoryId = cursor.getInt(cursor.getColumnIndex(CategoryModel.Column.CategoryId));
                    model.CategoryName = cursor.getString(cursor.getColumnIndex(CategoryModel.Column.CategoryName));
                    model.ResourceFileId = cursor.getInt(cursor.getColumnIndex(CategoryModel.Column.ResourceFileId));
                    model.Sort = cursor.getInt(cursor.getColumnIndex(CategoryModel.Column.Sort));
                    lst.add(model);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return lst;
    }


    public static SubCategoryModel getSubCategoryByShortcut(Context context, int shortcut) throws IOException, HandleException {
        DBHelper db = null;
        Cursor cursor = null;
        try {
            db = Common.getSaleDataBase(context);
            cursor = db.select("SELECT * from SubCategory sc INNER JOIN SubCategoryDetail scd ON sc.SubCategoryId=scd.SubCategoryId\n" +
                    "WHERE scd.Shortcut= " + shortcut);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                SubCategoryModel model = new SubCategoryModel();
                model.CategoryId = cursor.getInt(cursor.getColumnIndex(SubCategoryModel.Column.CategoryId));
                model.ResourceFileId = cursor.getInt(cursor.getColumnIndex(SubCategoryModel.Column.ResourceFileId));
                model.Sort = cursor.getInt(cursor.getColumnIndex(SubCategoryModel.Column.Sort));
                model.SubCategoryId = cursor.getInt(cursor.getColumnIndex(SubCategoryModel.Column.SubCategoryId));
                model.SubCategoryName = cursor.getString(cursor.getColumnIndex(SubCategoryModel.Column.SubCategoryName));
                return model;
            } else {
                throw new HandleException(context, new SQLException("گروه مورد نظر پیدا نشد"));
            }

        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }

    }
}
