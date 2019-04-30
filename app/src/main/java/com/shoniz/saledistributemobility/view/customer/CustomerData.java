package com.shoniz.saledistributemobility.view.customer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.shoniz.saledistributemobility.utility.data.pref.AppPref;
import com.shoniz.saledistributemobility.utility.data.sqlite.DBHelper;
import com.shoniz.saledistributemobility.utility.data.sqlite.SqliteConsts;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBasicModel;
import com.shoniz.saledistributemobility.view.customer.info.bought.CustomerBuyModel;
import com.shoniz.saledistributemobility.view.customer.info.cheque.CustomerChequeModel;
import com.shoniz.saledistributemobility.view.customer.info.credit.CustomerCreditModel;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.Enums;
import com.shoniz.saledistributemobility.utility.StringHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ferdos.s on 6/8/2017.
 */

public class CustomerData {
    public static void createOrderDb(Context context, String dbFileBase64) throws IOException {
        byte[] buffer = StringHelper.getByteFromBase64(dbFileBase64);
        File f = new File(DBHelper.getDatabasePath(context)
                + "/" + Enums.DBName.Order + ".db");
      /*  if (f.exists())
            DBHelper.deleteDatabase(context, Enums.DBName.Order);
     */
        OutputStream output = new FileOutputStream(f);
        output.write(buffer);
        output.close();
    }


    public static void createCustomerBaseInfoDb(Context context, String dbFileBase64) throws IOException {
        byte[] buffer = StringHelper.getByteFromBase64(dbFileBase64);
        File f = new File(DBHelper.getDatabasePath(context)
                + "/" + Enums.DBName.CustomerBase + ".db");
        /* if (f.exists())
         */
        DBHelper.deleteDatabase(context, Enums.DBName.CustomerBase);
        OutputStream output = new FileOutputStream(f);
        output.write(buffer);
        output.close();
    }

    public static void createSaleDb(Context context, String dbFileBase64) throws IOException {
        byte[] buffer = StringHelper.getByteFromBase64(dbFileBase64);
        File f = new File(DBHelper.getDatabasePath(context)
                + "/" + Enums.DBName.SaleDatabase + ".db");
        if (f.exists())
            DBHelper.deleteDatabase(context, Enums.DBName.SaleDatabase);
        OutputStream output = new FileOutputStream(f);
        output.write(buffer);
        output.close();
    }

    public static void updateCustomersBaseInfo(Context context, List<CustomerBasicModel> models) throws IOException, SQLException {
        //TODO:CHECK this
        DBHelper db = Common.getSaleDataBase(context);
        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            for (CustomerBasicModel model : models) {
                values.put(CustomerBasicModel.Column.CUSTOMER_ID, model.CustomerID);
                values.put(CustomerBasicModel.Column.PERSON_ID, model.PersonID);
                values.put(CustomerBasicModel.Column.PERSON_NAME, model.PersonName);
                values.put(CustomerBasicModel.Column.CONTACT_NAME, model.ContactName);
                values.put(CustomerBasicModel.Column.ADDRESS, model.Address);
                values.put(CustomerBasicModel.Column.TEL_NO, model.TelNo);
                values.put(CustomerBasicModel.Column.CELL_NO, model.CellNo);
                values.put(CustomerBasicModel.Column.OWNER_TYPE, model.OwnerType);
                values.put(CustomerBasicModel.Column.CUSTOMER_TYPE, model.CustomerType);
                values.put(CustomerBasicModel.Column.ACTIVE_SEASON, model.ActiveSeason);
                values.put(CustomerBasicModel.Column.MAX_CREDIT, model.MaxCredit);
                values.put(CustomerBasicModel.Column.NOT_SALE_REASON_DATE, model.NotSaleReasonDate);
                values.put(CustomerBasicModel.Column.PATH_CODE, model.PathCode);
                values.put(CustomerBasicModel.Column.PATH_NAME, model.PathName);
                values.put(CustomerBasicModel.Column.IS_ACTIVE, model.IsActive);
                values.put(CustomerBasicModel.Column.LATITUDE, model.Latitude);
                values.put(CustomerBasicModel.Column.LONGITUDE, model.Longitude);
                db.getSQLiteDatabase().update(SqliteConsts.TableName.CUSTOMER_BASE, values, CustomerBasicModel.Column.PERSON_ID + " = " + model.PersonID, null);
            }
            db.setTransactionSuccessful();


        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void insertBaseInfo(Context context, List<CustomerBasicModel> models) throws IOException, SQLException {
        //TODO:CHECK this
        DBHelper db = Common.getSaleDataBase(context);
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (CustomerBasicModel model : models) {
                values.put(CustomerBasicModel.Column.CUSTOMER_ID, model.CustomerID);
                values.put(CustomerBasicModel.Column.PERSON_ID, model.PersonID);
                values.put(CustomerBasicModel.Column.PERSON_NAME, model.PersonName);
                values.put(CustomerBasicModel.Column.CONTACT_NAME, model.ContactName);
                values.put(CustomerBasicModel.Column.ADDRESS, model.Address);
                values.put(CustomerBasicModel.Column.TEL_NO, model.TelNo);
                values.put(CustomerBasicModel.Column.CELL_NO, model.CellNo);
                values.put(CustomerBasicModel.Column.OWNER_TYPE, model.OwnerType);
                values.put(CustomerBasicModel.Column.CUSTOMER_TYPE, model.CustomerType);
                values.put(CustomerBasicModel.Column.ACTIVE_SEASON, model.ActiveSeason);
                values.put(CustomerBasicModel.Column.MAX_CREDIT, model.MaxCredit);
                values.put(CustomerBasicModel.Column.NOT_SALE_REASON_DATE, model.NotSaleReasonDate);
                values.put(CustomerBasicModel.Column.PATH_CODE, model.PathCode);
                values.put(CustomerBasicModel.Column.PATH_NAME, model.PathName);
                values.put(CustomerBasicModel.Column.IS_ACTIVE, model.IsActive);
                values.put(CustomerBasicModel.Column.LATITUDE, model.Latitude);
                values.put(CustomerBasicModel.Column.LONGITUDE, model.Longitude);
                db.getSQLiteDatabase().insert(SqliteConsts.TableName.CUSTOMER_BASE, null, values);
            }
            db.setTransactionSuccessful();


        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static CustomerBasicModel getCustomerBaseInfo(Context context, int PersonId) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        CustomerBasicModel model = new CustomerBasicModel();
        try {
            db = Common.getSaleDataBase(context);
            cursor = db.select("SELECT * FROM CustomerBase Where personID = " + PersonId);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                model.CustomerID = cursor.getInt(cursor.getColumnIndex(CustomerBasicModel.Column.CUSTOMER_ID));
                model.PersonID = cursor.getInt(cursor.getColumnIndex(CustomerBasicModel.Column.PERSON_ID));
                model.PersonName = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.PERSON_NAME));
                model.Address = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.ADDRESS));
                model.TelNo = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.TEL_NO));
                model.CellNo = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.CELL_NO));
                model.ContactName = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.CONTACT_NAME));
                model.ActiveSeason = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.ACTIVE_SEASON));
                model.OwnerType = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.OWNER_TYPE));
                model.CustomerType = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.CUSTOMER_TYPE));
                model.MaxCredit = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.MAX_CREDIT));
                model.NotSaleReasonDate = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.NOT_SALE_REASON_DATE));
                model.NotSaleReasonDesc = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.NOT_SALE_REASON_DESC));
                model.PathCode = cursor.getInt(cursor.getColumnIndex(CustomerBasicModel.Column.PATH_CODE));
                model.PathName = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.PATH_NAME));
                model.AccountRemain = cursor.getInt(cursor.getColumnIndex(CustomerBasicModel.Column.ACCOUNT_REMAIN));
                model.CreditRemain = cursor.getInt(cursor.getColumnIndex(CustomerBasicModel.Column.CREDIT_REMAIN));
                model.IsActive = cursor.getInt(cursor.getColumnIndex(CustomerBasicModel.Column.IS_ACTIVE)) == 1;
                model.Latitude = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.LATITUDE));
                model.Longitude = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.LONGITUDE));
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return model;
    }

    public static boolean isCustomerExist(Context context, int PersonId) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        try {
            db = Common.getSaleDataBase(context);
            cursor = db.select("SELECT * FROM CustomerBase Where personID = " + PersonId);
            return cursor.getCount() > 0;
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
    }


    public static List<CustomerBasicModel> getCustomerBaseInfoByPath(Context context, int pathCode) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        List<CustomerBasicModel> customerBasicModels = new LinkedList<>();
        try {
            db = Common.getSaleDataBase(context);
            String isActive = " ";
            String classNames = " ";
            if (!AppPref.isCustomerClassNameB(context)) {
                classNames = " AND ClassNames='A'";
            }

            if (AppPref.isActiveCustomerChecked(context)) {
                isActive = " AND IsActive=1 ";
            }

            cursor = db.select("SELECT cb. *, " +
                    " ifnull((Select OrderNo from \"Order\" o1 Where o1.PersonID = cb.PersonId AND o1.IsIssued = 0 ), 0) AS UnIssuedOrderNo " +
                    " FROM CustomerBase cb  Where PathCode = " +
                    pathCode + isActive + classNames);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    CustomerBasicModel model = new CustomerBasicModel();
                    model.CustomerID = cursor.getInt(cursor.getColumnIndex(CustomerBasicModel.Column.CUSTOMER_ID));
                    model.PersonID = cursor.getInt(cursor.getColumnIndex(CustomerBasicModel.Column.PERSON_ID));
                    model.PersonName = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.PERSON_NAME));
                    model.Address = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.ADDRESS));
                    model.TelNo = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.TEL_NO));
                    model.CellNo = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.CELL_NO));
                    model.OwnerType = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.OWNER_TYPE));
                    model.CustomerType = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.CUSTOMER_TYPE));
                    model.MaxCredit = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.MAX_CREDIT));
                    model.NotSaleReasonDate = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.NOT_SALE_REASON_DATE));
                    model.PathCode = cursor.getInt(cursor.getColumnIndex(CustomerBasicModel.Column.PATH_CODE));
                    model.PathName = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.PATH_NAME));
                    model.IsActive = cursor.getInt(cursor.getColumnIndex(CustomerBasicModel.Column.IS_ACTIVE)) == 1;
                    model.ClassNames = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.CLASS_NAMES));
                    model.Latitude = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.LATITUDE));
                    model.Longitude = cursor.getString(cursor.getColumnIndex(CustomerBasicModel.Column.LONGITUDE));
                    model.UnIssuedOrderNo = cursor.getLong(cursor.getColumnIndex(CustomerBasicModel.Column.UnIssuedOrderNo));
                    customerBasicModels.add(model);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return customerBasicModels;
    }
    //endregion

    //region  CustomerCredit
    public static void createCustomerCreditDb(Context context, String dbFileBase64) throws IOException {
        byte[] buffer = StringHelper.getByteFromBase64(dbFileBase64);
        File f = new File(DBHelper.getDatabasePath(context)
                + "/" + Enums.DBName.CustomerCredit + ".db");
        /* if (f.exists())
         */
        DBHelper.deleteDatabase(context, Enums.DBName.CustomerCredit);
        OutputStream output = new FileOutputStream(f);
        output.write(buffer);
        output.close();
    }

    public static void insertCustomersCredit(Context context, List<CustomerCreditModel> models) throws IOException, SQLException {
        //TODO:CHECK this
        DBHelper db = Common.getSaleDataBase(context);
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (CustomerCreditModel model : models) {
                values.put(CustomerCreditModel.Column.CUSTOMER_ID, model.CustomerID);
                values.put(CustomerCreditModel.Column.PERSON_ID, model.PersonID);
                values.put(CustomerCreditModel.Column.CustomerCreditFirstSixMonth, model.CustomerCreditFirstSixMonth);
                values.put(CustomerCreditModel.Column.CustomerCreditSecondSixMonth, model.CustomerCreditSecondSixMonth);

                db.getSQLiteDatabase().insert(SqliteConsts.TableName.CUSTOMER_CREDIT, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void updateCustomersCredit(Context context, List<CustomerCreditModel> models) throws IOException, SQLException {
        //TODO:CHECK this
        DBHelper db = Common.getSaleDataBase(context);
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (CustomerCreditModel model : models) {
                values.put(CustomerCreditModel.Column.CUSTOMER_ID, model.CustomerID);
                values.put(CustomerCreditModel.Column.PERSON_ID, model.PersonID);
                values.put(CustomerCreditModel.Column.CustomerCreditFirstSixMonth, model.CustomerCreditFirstSixMonth);
                values.put(CustomerCreditModel.Column.CustomerCreditSecondSixMonth, model.CustomerCreditSecondSixMonth);

                db.getSQLiteDatabase().update(SqliteConsts.TableName.CUSTOMER_CREDIT, values,
                        CustomerBasicModel.Column.PERSON_ID + " = " + model.PersonID, null);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static List<CustomerCreditModel> getCustomerCreditByPath(Context context, int pathCode) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        List<CustomerCreditModel> customerBasicModels = new LinkedList<>();
        try {
            db = Common.getSaleDataBase(context);
            cursor = db.select("SELECT * FROM CustomerCredit Where PathCode = " + pathCode);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    CustomerCreditModel model = new CustomerCreditModel();
                    model.CustomerID = cursor.getInt(cursor.getColumnIndex(CustomerCreditModel.Column.CUSTOMER_ID));
                    model.PersonID = cursor.getInt(cursor.getColumnIndex(CustomerCreditModel.Column.PERSON_ID));
                    model.CustomerCreditFirstSixMonth = cursor.getInt(cursor.getColumnIndex(CustomerCreditModel.Column.CustomerCreditFirstSixMonth));
                    model.CustomerCreditSecondSixMonth = cursor.getInt(cursor.getColumnIndex(CustomerCreditModel.Column.CustomerCreditSecondSixMonth));

                    customerBasicModels.add(model);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return customerBasicModels;
    }

    public static CustomerCreditModel getCustomerCredit(Context context, int PersonId) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        CustomerCreditModel model = new CustomerCreditModel();
        try {
            db = Common.getSaleDataBase(context);
            cursor = db.select("SELECT * FROM CustomerCredit Where personID = " + PersonId);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                model.CustomerID = cursor.getInt(cursor.getColumnIndex(CustomerCreditModel.Column.CUSTOMER_ID));
                model.PersonID = cursor.getInt(cursor.getColumnIndex(CustomerCreditModel.Column.PERSON_ID));
                model.CustomerCreditFirstSixMonth = cursor.getInt(cursor.getColumnIndex(CustomerCreditModel.Column.CustomerCreditFirstSixMonth));
                model.CustomerCreditSecondSixMonth = cursor.getInt(cursor.getColumnIndex(CustomerCreditModel.Column.CustomerCreditSecondSixMonth));
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return model;
    }
    //endregion


    //region  CustomerBuyAndBuyReturned
    public static void createCustomerBuyDb(Context context, String dbFileBase64) throws IOException {
        byte[] buffer = StringHelper.getByteFromBase64(dbFileBase64);
        File f = new File(DBHelper.getDatabasePath(context)
                + "/" + Enums.DBName.CustomerBuy + ".db");
       /* if (f.exists())
            DBHelper.deleteDatabase(context, Enums.DBName.CustomerBuy);
        */
        OutputStream output = new FileOutputStream(f);
        output.write(buffer);
        output.close();

        //   FileManager.backUp(context, path + dbname.toString() + ".db", "mnt/sdcard/customerBuy.db");
    }

    public static void insertCustomersBuy(Context context, List<CustomerBuyModel> models) throws IOException, SQLException {
        //TODO:CHECK this
        DBHelper db = Common.getSaleDataBase(context);
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (CustomerBuyModel model : models) {
                values.put(CustomerBuyModel.Column.CUSTOMER_ID, model.CustomerID);
                values.put(CustomerBuyModel.Column.PERSON_ID, model.PersonID);
                values.put(CustomerBuyModel.Column.YEAR_TYPE_ID, model.YearTypeID);
                values.put(CustomerBuyModel.Column.AMOUNT_BUY_THIS_YEAR, model.AmountBuyThisYear);
                values.put(CustomerBuyModel.Column.QTY_MAIN_UNIT_BUY_THIS_YEAR, model.QtyMainUnitBuyThisYear);
                values.put(CustomerBuyModel.Column.QTY_SUB_UNIT_BUY_THIS_YEAR, model.QtySubUnitBuyThisYear);
                values.put(CustomerBuyModel.Column.AMOUNT_BUY_RETURNED_THIS_YEAR, model.AmountBuyReturnedThisYear);
                values.put(CustomerBuyModel.Column.QTY_MAIN_UNIT_BUY_RETURNED_THIS_YEAR, model.QtyMainUnitBuyReturnedThisYear);
                values.put(CustomerBuyModel.Column.YEAR_TYPE, model.YearType);

                db.getSQLiteDatabase().insert(SqliteConsts.TableName.CustomerBuy, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void updateCustomersBuy(Context context, List<CustomerBuyModel> models) throws IOException, SQLException {
        //TODO:CHECK this
        DBHelper db = Common.getSaleDataBase(context);
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (CustomerBuyModel model : models) {
                values.put(CustomerBuyModel.Column.CUSTOMER_ID, model.CustomerID);
                values.put(CustomerBuyModel.Column.PERSON_ID, model.PersonID);
                values.put(CustomerBuyModel.Column.YEAR_TYPE_ID, model.YearTypeID);
                values.put(CustomerBuyModel.Column.AMOUNT_BUY_THIS_YEAR, model.AmountBuyThisYear);
                values.put(CustomerBuyModel.Column.QTY_MAIN_UNIT_BUY_THIS_YEAR, model.QtyMainUnitBuyThisYear);
                values.put(CustomerBuyModel.Column.QTY_SUB_UNIT_BUY_THIS_YEAR, model.QtySubUnitBuyThisYear);
                values.put(CustomerBuyModel.Column.AMOUNT_BUY_RETURNED_THIS_YEAR, model.AmountBuyReturnedThisYear);
                values.put(CustomerBuyModel.Column.QTY_MAIN_UNIT_BUY_RETURNED_THIS_YEAR, model.QtyMainUnitBuyReturnedThisYear);
                values.put(CustomerBuyModel.Column.YEAR_TYPE, model.YearType);

                db.getSQLiteDatabase().update(SqliteConsts.TableName.CustomerBuy, values, CustomerBuyModel.Column.PERSON_ID + " = " + model.PersonID +
                        " AND " + CustomerBuyModel.Column.YEAR_TYPE_ID + " = " + model.YearTypeID, null);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static List<CustomerBuyModel> getCustomerBuyByPersonId(Context context, int PersonId) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        List<CustomerBuyModel> customerBasicModels = new LinkedList<>();
        try {
            db = Common.getSaleDataBase(context);
            cursor = db.select("SELECT * FROM CustomerBuy Where personID = " + PersonId);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    CustomerBuyModel model = new CustomerBuyModel();
                    model.CustomerID = cursor.getInt(cursor.getColumnIndex(CustomerBuyModel.Column.CUSTOMER_ID));
                    model.PersonID = cursor.getInt(cursor.getColumnIndex(CustomerBuyModel.Column.PERSON_ID));
                    model.YearTypeID = cursor.getString(cursor.getColumnIndex(CustomerBuyModel.Column.YEAR_TYPE_ID));
                    model.AmountBuyThisYear = cursor.getString(cursor.getColumnIndex(CustomerBuyModel.Column.AMOUNT_BUY_THIS_YEAR));
                    model.QtyMainUnitBuyThisYear = cursor.getString(cursor.getColumnIndex(CustomerBuyModel.Column.QTY_MAIN_UNIT_BUY_THIS_YEAR));
                    model.QtySubUnitBuyThisYear = cursor.getString(cursor.getColumnIndex(CustomerBuyModel.Column.QTY_SUB_UNIT_BUY_THIS_YEAR));
                    model.AmountBuyReturnedThisYear = cursor.getString(cursor.getColumnIndex(CustomerBuyModel.Column.AMOUNT_BUY_RETURNED_THIS_YEAR));
                    model.QtyMainUnitBuyReturnedThisYear = cursor.getString(cursor.getColumnIndex(CustomerBuyModel.Column.QTY_MAIN_UNIT_BUY_RETURNED_THIS_YEAR));
                    model.YearType = cursor.getString(cursor.getColumnIndex(CustomerBuyModel.Column.YEAR_TYPE));
                    customerBasicModels.add(model);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return customerBasicModels;
    }
    //endregion

    //region  CustomerCheque
    public static void createCustomerChequeDb(Context context, String dbFileBase64) throws IOException {
        byte[] buffer = StringHelper.getByteFromBase64(dbFileBase64);
        File f = new File(DBHelper.getDatabasePath(context)
                + "/" + Enums.DBName.CustomerCheque + ".db");
       /* if (f.exists())
            DBHelper.deleteDatabase(context, Enums.DBName.CustomerCheque);
       */
        OutputStream output = new FileOutputStream(f);
        output.write(buffer);
        output.close();
    }

    public static void insertCustomersCheque(Context context, List<CustomerChequeModel> models) throws IOException, SQLException {
        //TODO:CHECK this
        DBHelper db = Common.getSaleDataBase(context);
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (CustomerChequeModel model : models) {
                values.put(CustomerChequeModel.Column.CUSTOMER_ID, model.CustomerID);
                values.put(CustomerChequeModel.Column.PERSON_ID, model.PersonID);
                values.put(CustomerChequeModel.Column.SERIAL_NUMBER, model.SerialNumber);
                values.put(CustomerChequeModel.Column.BANK_NAME, model.BankName);
                values.put(CustomerChequeModel.Column.BANK_BRANCH_NAME, model.BankBranchName);
                values.put(CustomerChequeModel.Column.PERSON_NAME, model.PersonName);
                values.put(CustomerChequeModel.Column.TOTAL_PAYMENT, model.TotalPayment);
                values.put(CustomerChequeModel.Column.FLOW_DATE, model.FlowDate);
                values.put(CustomerChequeModel.Column.REASON_NAME, model.ReasonName);
                values.put(CustomerChequeModel.Column.TYPE_CHEQUE, model.TypeCheque);

                db.getSQLiteDatabase().insert(SqliteConsts.TableName.CustomerCheque, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static void updateCustomersCheque(Context context, List<CustomerChequeModel> models) throws IOException, SQLException {
        //TODO:CHECK this
        DBHelper db = Common.getSaleDataBase(context);
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (CustomerChequeModel model : models) {
                values.put(CustomerChequeModel.Column.CUSTOMER_ID, model.CustomerID);
                values.put(CustomerChequeModel.Column.PERSON_ID, model.PersonID);
                values.put(CustomerChequeModel.Column.SERIAL_NUMBER, model.SerialNumber);
                values.put(CustomerChequeModel.Column.BANK_NAME, model.BankName);
                values.put(CustomerChequeModel.Column.BANK_BRANCH_NAME, model.BankBranchName);
                values.put(CustomerChequeModel.Column.PERSON_NAME, model.PersonName);
                values.put(CustomerChequeModel.Column.TOTAL_PAYMENT, model.TotalPayment);
                values.put(CustomerChequeModel.Column.FLOW_DATE, model.FlowDate);
                values.put(CustomerChequeModel.Column.REASON_NAME, model.ReasonName);
                values.put(CustomerChequeModel.Column.TYPE_CHEQUE, model.TypeCheque);

                db.getSQLiteDatabase().update(SqliteConsts.TableName.CustomerCheque, values,
                        CustomerChequeModel.Column.PERSON_ID + " = " + model.PersonID + " AND " + CustomerChequeModel.Column.SERIAL_NUMBER + " = " + model.SerialNumber, null);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public static List<CustomerChequeModel> getCustomerChequeByPersonId(Context context, int personID) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        List<CustomerChequeModel> lst = new ArrayList<>();
        try {
            db = Common.getSaleDataBase(context);
            cursor = db.select("SELECT * FROM CustomerCheque Where personID = " + personID);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    CustomerChequeModel model = new CustomerChequeModel();
                    model.CustomerID = cursor.getInt(cursor.getColumnIndex(CustomerChequeModel.Column.CUSTOMER_ID));
                    model.PersonID = cursor.getInt(cursor.getColumnIndex(CustomerChequeModel.Column.PERSON_ID));
                    model.SerialNumber = cursor.getString(cursor.getColumnIndex(CustomerChequeModel.Column.SERIAL_NUMBER));
                    model.BankName = cursor.getString(cursor.getColumnIndex(CustomerChequeModel.Column.BANK_NAME));
                    model.BankBranchName = cursor.getString(cursor.getColumnIndex(CustomerChequeModel.Column.BANK_BRANCH_NAME));
                    model.PersonName = cursor.getString(cursor.getColumnIndex(CustomerChequeModel.Column.PERSON_NAME));
                    model.TotalPayment = cursor.getString(cursor.getColumnIndex(CustomerChequeModel.Column.TOTAL_PAYMENT));
                    model.FlowDate = cursor.getString(cursor.getColumnIndex(CustomerChequeModel.Column.FLOW_DATE));
                    model.DueDate = cursor.getString(cursor.getColumnIndex(CustomerChequeModel.Column.DueDate));
                    model.PaymentDate = cursor.getString(cursor.getColumnIndex(CustomerChequeModel.Column.PaymentDate));
                    model.ReasonName = cursor.getString(cursor.getColumnIndex(CustomerChequeModel.Column.REASON_NAME));
                    model.TypeCheque = cursor.getString(cursor.getColumnIndex(CustomerChequeModel.Column.TYPE_CHEQUE));
                    model.ConditionID = cursor.getInt(cursor.getColumnIndex(CustomerChequeModel.Column.CONDITION_ID));
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


    public static CustomerChequeModel getCustomerCheque(Context context, int personID, int chequeSerial) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        try {
            db = Common.getSaleDataBase(context);
            cursor = db.select("SELECT * FROM CustomerCheque Where personID = " + personID +
                    " AND SerialNumber = " + chequeSerial);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                CustomerChequeModel model = new CustomerChequeModel();
                model.CustomerID = cursor.getInt(cursor.getColumnIndex(CustomerChequeModel.Column.CUSTOMER_ID));
                model.PersonID = cursor.getInt(cursor.getColumnIndex(CustomerChequeModel.Column.PERSON_ID));
                model.SerialNumber = cursor.getString(cursor.getColumnIndex(CustomerChequeModel.Column.SERIAL_NUMBER));
                model.BankName = cursor.getString(cursor.getColumnIndex(CustomerChequeModel.Column.BANK_NAME));
                model.BankBranchName = cursor.getString(cursor.getColumnIndex(CustomerChequeModel.Column.BANK_BRANCH_NAME));
                model.PersonName = cursor.getString(cursor.getColumnIndex(CustomerChequeModel.Column.PERSON_NAME));
                model.TotalPayment = cursor.getString(cursor.getColumnIndex(CustomerChequeModel.Column.TOTAL_PAYMENT));
                model.FlowDate = cursor.getString(cursor.getColumnIndex(CustomerChequeModel.Column.FLOW_DATE));
                model.ReasonName = cursor.getString(cursor.getColumnIndex(CustomerChequeModel.Column.REASON_NAME));
                model.TypeCheque = cursor.getString(cursor.getColumnIndex(CustomerChequeModel.Column.TYPE_CHEQUE));
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

/*    public static List<GRVModel.CardModel> getGeneralCardCustomerChequeByPersonId(int personID) throws IOException {
        DBHelper db = Common.getSaleDataBase(Enums.DBName.CustomerCheque);
        String command = "SELECT SerialNumber Id, BankName Title, 'مبلغ: ' || TotalPayment || ' - تاریخ: ' || FlowDate Detail,-1 IconId  FROM CustomerCheque Where personID = " + personID;
        return GeneralData__961222.MakeGeneralRecycleViewList(db, command);
    }*/


    //endregion
}
