package com.shoniz.saledistributemobility.order;

import android.content.Context;
import android.database.Cursor;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.order.sent.SentOrderModel;
import com.shoniz.saledistributemobility.utility.data.pref.AppPref;
import com.shoniz.saledistributemobility.utility.data.sqlite.DBHelper;
import com.shoniz.saledistributemobility.order.unvisited.ReasonModel;
import com.shoniz.saledistributemobility.order.unsent.UnsentOrderModel;
import com.shoniz.saledistributemobility.order.unvisited.UnvisitedCustomerModel;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.Enums;
import com.shoniz.saledistributemobility.utility.PersianCalendar;
import com.shoniz.saledistributemobility.utility.StringHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ferdos.s on 1/9/2018.
 */

public class RequestListData {


    public static List<SentOrderModel> getOrderedRequestList(Context context,
                                                             boolean shouldShowJustNotIssuedOrder,
                                                             boolean shouldShowJustTodayOrder,
                                                             int personId) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        try {
            db = Common.getSaleDataBase(context);
//            db.attachDataBase(context, Enums.DBName.CustomerBase);
//            db.attachDataBase(context, Enums.DBName.CardIndex);
            List<SentOrderModel> orderedRequestListModels = new ArrayList<>();
            String command = StringHelper.GenerateMessage(context, R.string.ordered_request_list_header,
                    shouldShowJustTodayOrder ? " AND O.RegDate = '" + PersianCalendar.getPersianDate() + "' " : " ",
                    shouldShowJustNotIssuedOrder ? " AND O.IsIssued = 0 " : " ",
                    personId > 0 ? " AND O.PersonId = " + personId : ""
                   );
            int row=1;
            cursor = db.select(command);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    SentOrderModel model = new SentOrderModel();
                    model.Row=row;
                    model.OrderNo = cursor.getLong(cursor.getColumnIndex(SentOrderModel.OrderedColumns.OrderNo));
                    model.Price = cursor.getLong(cursor.getColumnIndex(SentOrderModel.OrderedColumns.Price));
                    model.OrderWeight = cursor.getFloat(cursor.getColumnIndex(SentOrderModel.OrderedColumns.OrderWeight));
                    model.OrderStatus = cursor.getString(cursor.getColumnIndex(SentOrderModel.OrderedColumns.OrderStatus));
                    model.OrderNetWeight = cursor.getFloat(cursor.getColumnIndex(SentOrderModel.OrderedColumns.OrderNetWeight));
                    model.InPath = cursor.getInt(cursor.getColumnIndex(SentOrderModel.UnorderedColumns.InPath));
                    model.PathName = cursor.getString(cursor.getColumnIndex(SentOrderModel.UnorderedColumns.PathName));
                    model.PersonName = cursor.getString(cursor.getColumnIndex(SentOrderModel.UnorderedColumns.PersonName));
                    model.PersonID = cursor.getInt(cursor.getColumnIndex(SentOrderModel.UnorderedColumns.PersonID));
                    model.RegDate = cursor.getString(cursor.getColumnIndex(SentOrderModel.UnorderedColumns.RegDate));
                    //model.ErrorMessage = cursor.getString(cursor.getColumnIndex(SentOrderModel.UnorderedColumns.ErrorMessage));
                    model.Varity = cursor.getInt(cursor.getColumnIndex(SentOrderModel.OrderedColumns.Varity));
                    model.CustomerType = cursor.getString(cursor.getColumnIndex(SentOrderModel.OrderedColumns.CustomerType));
                    model.QtyCartoon = cursor.getInt(cursor.getColumnIndex(SentOrderModel.OrderedColumns.QtyCartoon));
                    model.QtyPackage = cursor.getInt(cursor.getColumnIndex(SentOrderModel.OrderedColumns.QtyPackage));
                    model.IsIssued = cursor.getInt(cursor.getColumnIndex(SentOrderModel.OrderedColumns.IsIssued)) == 1;
                    orderedRequestListModels.add(model);
                    row++;
                } while (cursor.moveToNext());
            }
            return orderedRequestListModels;

        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
    }

    public static SentOrderModel getOrderedRequest(Context context, long orderNo) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        try {
            db = Common.getSaleDataBase(context);
//            db.attachDataBase(context, Enums.DBName.CardIndex);
//            db.attachDataBase(context, Enums.DBName.CustomerBase);

            SentOrderModel model = new SentOrderModel();
            String command = StringHelper.GenerateMessage(context, R.string.ordered_request_list_header," AND O.OrderNo = " + orderNo);
            cursor = db.select(command);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                model.OrderNo = cursor.getLong(cursor.getColumnIndex(SentOrderModel.OrderedColumns.OrderNo));
                model.Price = cursor.getLong(cursor.getColumnIndex(SentOrderModel.OrderedColumns.Price));
                model.OrderWeight = cursor.getFloat(cursor.getColumnIndex(SentOrderModel.OrderedColumns.OrderWeight));
                model.OrderNetWeight = cursor.getFloat(cursor.getColumnIndex(SentOrderModel.OrderedColumns.OrderNetWeight));
                model.OrderStatus = cursor.getString(cursor.getColumnIndex(SentOrderModel.OrderedColumns.OrderStatus));
                model.InPath = cursor.getInt(cursor.getColumnIndex(SentOrderModel.UnorderedColumns.InPath));
                model.PathName = cursor.getString(cursor.getColumnIndex(SentOrderModel.UnorderedColumns.PathName));
                model.PersonName = cursor.getString(cursor.getColumnIndex(SentOrderModel.UnorderedColumns.PersonName));
                model.PersonID = cursor.getInt(cursor.getColumnIndex(SentOrderModel.UnorderedColumns.PersonID));
                model.RegDate = cursor.getString(cursor.getColumnIndex(SentOrderModel.UnorderedColumns.RegDate));
                model.Varity = cursor.getInt(cursor.getColumnIndex(SentOrderModel.OrderedColumns.Varity));
            }
            return model;
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
    }

    public static List<UnsentOrderModel> getUnsentRequestList(Context context) throws IOException {

        DBHelper db = null;
        Cursor cursor = null;
        try {
            db = Common.getSaleDataBase(context);
//            db.attachDataBase(context, Enums.DBName.CardIndex);
//            db.attachDataBase(context, Enums.DBName.path);

            List<UnsentOrderModel> unorderedRequestListModels = new ArrayList<>();
            String command = StringHelper.GenerateMessage(context, R.string.unsent_request_list);
            cursor = db.select(command);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    UnsentOrderModel model = new UnsentOrderModel();
                    model.InPath = cursor.getInt(cursor.getColumnIndex(UnsentOrderModel.UnorderedColumns.InPath));
                    model.PathName = cursor.getString(cursor.getColumnIndex(UnsentOrderModel.UnorderedColumns.PathName));
                    model.PersonName = cursor.getString(cursor.getColumnIndex(UnsentOrderModel.UnorderedColumns.PersonName));
                    model.PersonID = cursor.getInt(cursor.getColumnIndex(UnsentOrderModel.UnorderedColumns.PersonID));
                    model.ErrorMessage = cursor.getString(cursor.getColumnIndex(UnsentOrderModel.UnorderedColumns.ErrorMessage));
                    unorderedRequestListModels.add(model);
                } while (cursor.moveToNext());
            }
            return unorderedRequestListModels;

        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
    }

    public static List<UnvisitedCustomerModel> getUnvisitedCustomerList(Context context) throws IOException {

        DBHelper db = null;
        Cursor cursor = null;
        try {
            db = Common.getSaleDataBase(context);
//            db.attachDataBase(context, Enums.DBName.CardIndex);
//            db.attachDataBase(context, Enums.DBName.path);
//            db.attachDataBase(context, Enums.DBName.Order);
//            db.attachDataBase(context, Enums.DBName.base);
            boolean isSent=AppPref.isReasonSendAll(context);
            List<UnvisitedCustomerModel> unvisitedCustomerModels = new ArrayList<>();
            String command = StringHelper.GenerateMessage(context, R.string.unvisit_customer_list);
            cursor = db.select(command);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    UnvisitedCustomerModel model = new UnvisitedCustomerModel();
                    model.InPath = cursor.getInt(cursor.getColumnIndex(UnvisitedCustomerModel.UnorderedColumns.InPath));
                    model.PathName = cursor.getString(cursor.getColumnIndex(UnvisitedCustomerModel.UnorderedColumns.PathName));
                    model.PersonName = cursor.getString(cursor.getColumnIndex(UnvisitedCustomerModel.UnorderedColumns.PersonName));
                    model.PersonID = cursor.getInt(cursor.getColumnIndex(UnvisitedCustomerModel.UnorderedColumns.PersonID));
                    model.ErrorMessage = cursor.getString(cursor.getColumnIndex(UnvisitedCustomerModel.UnorderedColumns.ErrorMessage));
                    model.IsSent = isSent;
                    model.reasonModel.NotSallReasonID =cursor.getInt(cursor.getColumnIndex(ReasonModel.Column.NotSallReasonID));
                    model.reasonModel.NotSallReasonText =cursor.getString(cursor.getColumnIndex(ReasonModel.Column.NotSallReasonText));
                    unvisitedCustomerModels.add(model);
                } while (cursor.moveToNext());
            }
            return unvisitedCustomerModels;


        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
    }

    public static void deleteOrder(Context context, long orderNo) throws IOException {
        DBHelper db = null;
        try {
            db = Common.getSaleDataBase(context);
            String command = StringHelper.GenerateMessage(
                    context,
                    R.string.delete_order,
                    orderNo+""
            );
            db.execSQL(command);
        }
        finally {
            if(db != null)
                db.close();
        }
    }


}
