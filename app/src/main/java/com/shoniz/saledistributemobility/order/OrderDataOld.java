package com.shoniz.saledistributemobility.order;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;


import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexModel;
import com.shoniz.saledistributemobility.order.detail.OrderCompleteModel;
import com.shoniz.saledistributemobility.utility.data.sqlite.DBHelper;
import com.shoniz.saledistributemobility.utility.data.sqlite.SqliteConsts;
import com.shoniz.saledistributemobility.order.unvisited__.ReasonModel__;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.StringHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class OrderDataOld {

    public static void insert(Context context, OrderModel model) throws IOException, SQLException {

        DBHelper db = Common.getSaleDataBase(context);
        db.beginTransaction();
        db.getSQLiteDatabase().delete(SqliteConsts.TableName.ORDER, OrderModel.OrderColumn.ORDER_NO + "=?",
                new String[]{model.OrderNo + ""});
        try {
            ContentValues values = new ContentValues();
            values.put(OrderModel.OrderColumn.ACC_DESC, model.AccDesc);
            values.put(OrderModel.OrderColumn.CHEQUE_DURATION, model.ChequeDuration);
            values.put(OrderModel.OrderColumn.INVOICE_REMAINS, model.InvoiceRemains);
            values.put(OrderModel.OrderColumn.IS_ISSUED, model.IsIssued);
            values.put(OrderModel.OrderColumn.ORDER_NO, model.OrderNo);
            values.put(OrderModel.OrderColumn.ORDER_SERIAL, model.OrderSerial);
            values.put(OrderModel.OrderColumn.ORDER_TYPE_ID, model.OrderTypeID);
            values.put(OrderModel.OrderColumn.ORDER_WEIGHT, model.OrderWeight);
            values.put(OrderModel.OrderColumn.ORDER_STATUS, model.OrderStatus);
            values.put(OrderModel.OrderColumn.ORDER_NET_WEIGHT, model.OrderNetWeight);
            values.put(OrderModel.OrderColumn.PERSON_ID, model.PersonID);
            values.put(OrderModel.OrderColumn.REG_DATE, model.RegDate);
            values.put(OrderModel.OrderColumn.SALES_DESC, model.SalesDesc);
            values.put(OrderModel.OrderColumn.TOTAL_AMOUNT, model.TotalAmount);
            values.put(OrderModel.OrderColumn.VARITY, model.Varity);
            values.put(OrderModel.OrderColumn.SenderId, model.SenderId);
            values.put(OrderModel.OrderColumn.ActionId, model.ActionId);
            values.put(OrderModel.OrderColumn.ActionDate, model.ActionDate);
            values.put(OrderModel.OrderColumn.Comment, model.Comment);
            values.put(OrderModel.OrderColumn.UserId, model.UserId);
            values.put(OrderModel.OrderColumn.Verifiable, model.Verifiable);
            values.put(OrderModel.OrderColumn.NeededCreditAmount, model.NeededCreditAmount);
            values.put(OrderModel.OrderColumn.IssuePrintedTime, model.IssuePrintedTime);
            values.put(OrderModel.OrderColumn.ResponseDoc, model.ResponseDoc);
            values.put(OrderModel.OrderColumn.ActionDate, (model.ActionDate == null ? "" : String.valueOf(model.ActionDate)));
            db.getSQLiteDatabase().insertOrThrow(SqliteConsts.TableName.ORDER, null, values);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }
    public static void insert(Context context, List<OrderModel> orderModels) throws IOException, SQLException {
        for(OrderModel order : orderModels){
            insert(context, order);
        }
    }


    public static CardIndexModel mapOrderHeaderToCardIndex(Context context, long orderNo) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        CardIndexModel cardIndexModel = new CardIndexModel();
        try {
            db = Common.getSaleDataBase(context);

            String command = StringHelper.GenerateMessage(context, R.string.get_order_header,
                    orderNo + "");
            cursor = db.select(command);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();

                cardIndexModel.AccDesc = cursor.getString(cursor.getColumnIndex(OrderModel.OrderColumn.ACC_DESC));
                cardIndexModel.SaleDesc = cursor.getString(cursor.getColumnIndex(OrderModel.OrderColumn.SALES_DESC));
                cardIndexModel.ChequeDuration = cursor.getInt(cursor.getColumnIndex(OrderModel.OrderColumn.CHEQUE_DURATION));
                cardIndexModel.PersonID = cursor.getInt(cursor.getColumnIndex(OrderModel.OrderColumn.PERSON_ID));
                cardIndexModel.OrderNo = cursor.getLong(cursor.getColumnIndex(OrderModel.OrderColumn.ORDER_NO));
                cardIndexModel.RegDate = cursor.getString(cursor.getColumnIndex(OrderModel.OrderColumn.REG_DATE));
                cardIndexModel.NeedDate= cursor.getString(cursor.getColumnIndex(OrderModel.OrderColumn.REG_DATE));//TODO:need date

            }
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
        return cardIndexModel;
    }

    public static OrderCompleteData getOrderCompleteHeader(Context context, long orderNo) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        OrderCompleteData orderOnlineData = null;
        try {
            db = Common.getSaleDataBase(context);

            String command = StringHelper.GenerateMessage(context, R.string.order_header,
                    orderNo+"");
            cursor = db.select(command);

            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                orderOnlineData = new OrderCompleteData();
                orderOnlineData.PersonID = cursor.getInt(cursor.getColumnIndex(OrderCompleteModel.OrderColumn.PERSON_ID));
                orderOnlineData.ChequeDuration =cursor.getInt(cursor.getColumnIndex(OrderCompleteModel.OrderColumn.CHEQUE_DURATION));
                orderOnlineData.OrderNo =cursor.getLong(cursor.getColumnIndex(OrderCompleteModel.OrderColumn.ORDER_NO));
                orderOnlineData.AccDesc =cursor.getString(cursor.getColumnIndex(OrderCompleteModel.OrderColumn.ACC_DESC));
                orderOnlineData.Address =cursor.getString(cursor.getColumnIndex(OrderCompleteModel.OrderCompleteColumn.Address));
                orderOnlineData.CellNo =cursor.getString(cursor.getColumnIndex(OrderCompleteModel.OrderCompleteColumn.CellNo));
                orderOnlineData.CustomerID =cursor.getInt(cursor.getColumnIndex(OrderCompleteModel.OrderCompleteColumn.CustomerID));
                orderOnlineData.InPath = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(OrderCompleteModel.OrderCompleteColumn.InPath)));
                orderOnlineData.PathCode =cursor.getInt(cursor.getColumnIndex(OrderCompleteModel.OrderCompleteColumn.PathCode));
                orderOnlineData.PathName =cursor.getString(cursor.getColumnIndex(OrderCompleteModel.OrderCompleteColumn.PathName));
                orderOnlineData.CustomerName=cursor.getString(cursor.getColumnIndex(OrderCompleteModel.OrderCompleteColumn.PersonName));
                orderOnlineData.TelNo =cursor.getString(cursor.getColumnIndex(OrderCompleteModel.OrderCompleteColumn.TelNo));
                orderOnlineData.InvoiceRemains =cursor.getInt(cursor.getColumnIndex(OrderCompleteModel.OrderColumn.INVOICE_REMAINS));
                orderOnlineData.IsIssued =cursor.getInt(cursor.getColumnIndex(OrderCompleteModel.OrderColumn.IS_ISSUED)) == 1;
                orderOnlineData.OrderWeight =cursor.getInt(cursor.getColumnIndex(OrderCompleteModel.OrderColumn.ORDER_WEIGHT));
                orderOnlineData.RegDate =cursor.getString(cursor.getColumnIndex(OrderCompleteModel.OrderColumn.REG_DATE));
                orderOnlineData.TotalAmount =cursor.getInt(cursor.getColumnIndex(OrderCompleteModel.OrderColumn.TOTAL_AMOUNT));
                orderOnlineData.Variety =cursor.getInt(cursor.getColumnIndex(OrderCompleteModel.OrderColumn.VARITY));
                orderOnlineData.SalesDesc =cursor.getString(cursor.getColumnIndex(OrderCompleteModel.OrderColumn.SALES_DESC));
                orderOnlineData.BonusAmount =cursor.getInt(cursor.getColumnIndex(OrderCompleteModel.OrderColumn.BONUS_AMOUNT));
                orderOnlineData.SenderId = cursor.getInt(cursor.getColumnIndex(OrderCompleteModel.OrderColumn.SenderId));
                orderOnlineData.ActionId = cursor.getInt(cursor.getColumnIndex(OrderCompleteModel.OrderColumn.ActionId));
                orderOnlineData.ActionDate = cursor.getLong(cursor.getColumnIndex(OrderCompleteModel.OrderColumn.ActionDate));
                orderOnlineData.Comment = cursor.getString(cursor.getColumnIndex(OrderCompleteModel.OrderColumn.Comment));
                orderOnlineData.UserId = cursor.getInt(cursor.getColumnIndex(OrderCompleteModel.OrderColumn.UserId));
            }

        }catch (Exception ex){
            int a = 0;
        }
        finally {
            if(cursor != null)
                cursor.close();
            if(db != null)
                db.close();
        }
        return orderOnlineData;
    }

    public static List<ReasonModel__> getUnvisitingReason(Context context) throws IOException, SQLException {
        DBHelper db = null;
        Cursor cursor = null;
        List<ReasonModel__> reasons = new ArrayList<>();
        try {
            db = Common.getSaleDataBase(context);

            String command = "SELECT * from Reason where NotSallReasonID > 0";
            cursor = db.select(command);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    ReasonModel__ reasonModel = new ReasonModel__();
                    reasonModel.NotSallReasonID = cursor.getInt(cursor.getColumnIndex(ReasonModel__.Column.NotSallReasonID));
                    reasonModel.NotSallReasonText = cursor.getString(cursor.getColumnIndex(ReasonModel__.Column.NotSallReasonText));
                    reasons.add(reasonModel);

                } while (cursor.moveToNext());
            }
            return reasons;
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
    }


}
