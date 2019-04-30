package com.shoniz.saledistributemobility.order.detail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexDetailModel;
import com.shoniz.saledistributemobility.utility.data.sqlite.DBHelper;
import com.shoniz.saledistributemobility.utility.data.sqlite.SqliteConsts;
import com.shoniz.saledistributemobility.order.OrderModel;
import com.shoniz.saledistributemobility.order.sent.OrderStatistics;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.PersianCalendar;
import com.shoniz.saledistributemobility.utility.StringHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aghazadeh.a on 1/23/2018.
 */

public class OrderDetailData {

    public static void insert(Context context, List<OrderDetailModel> lst) throws IOException,SQLException {
        DBHelper db = Common.getSaleDataBase(context);
        db.beginTransaction();
        try {
            db.getSQLiteDatabase().delete(SqliteConsts.TableName.ORDER_DETAIL, OrderModel.OrderColumn.ORDER_NO+"=?",
                    new String[]{lst.get(0).OrderNo+""} );
            ContentValues values = new ContentValues();
            for (OrderDetailModel model : lst) {
                values.put(OrderDetailModel.Column.IS_BONUS, model.IsBonus);
                values.put(OrderDetailModel.Column.ORDER_NO, model.OrderNo);
                values.put(OrderDetailModel.Column.PRICE, model.Price);
                values.put(OrderDetailModel.Column.QTY, model.Qty);
                values.put(OrderDetailModel.Column.SHORTCUT, model.Shortcut);
                values.put(OrderDetailModel.Column.UNIT_ID, model.UnitId);
                values.put(OrderDetailModel.Column.ROW, model.Row);
                db.getSQLiteDatabase().insertOrThrow(SqliteConsts.TableName.ORDER_DETAIL, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }


    public static List<CardIndexDetailModel> mapOrderDetailsToCardIndises(Context context, long orderNo)throws IOException  {
        DBHelper db = null;
        Cursor cursor = null;
        List<CardIndexDetailModel> cardIndexDetailModelList = new ArrayList<>();
        try {
            db = Common.getSaleDataBase(context);

            String command = StringHelper.GenerateMessage(context, R.string.order_pivot_details,
                    orderNo + "");
            cursor = db.select(command);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    CardIndexDetailModel cardIndexDetailModel = new CardIndexDetailModel();

                    cardIndexDetailModel.Shortcut = cursor.getString(cursor.getColumnIndex(CardIndexDetailModel.Column.Shortcut));//
                    cardIndexDetailModel.PersonId = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.PersonId));//
                    cardIndexDetailModel.OrderNo = cursor.getLong(cursor.getColumnIndex(CardIndexDetailModel.Column.Order_NO));
                    cardIndexDetailModel.RequestCarton = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Request_Carton));
                    cardIndexDetailModel.RequestPackage = cursor.getInt(cursor.getColumnIndex(CardIndexDetailModel.Column.Request_Package));

                    cardIndexDetailModelList.add(cardIndexDetailModel);
                } while (cursor.moveToNext());
            }
            return cardIndexDetailModelList;
        }
        finally {
            if(cursor != null)
                cursor.close();
            if(db != null)
                db.close();
        }
    }

    public static List<OrderCompleteDetailModel> getOrderCompleteDetails(Context context, long orderNo) throws IOException {
        DBHelper db = null;
        Cursor cursor = null;
        List<OrderCompleteDetailModel> orders = new ArrayList<>();
        try {
            db = Common.getSaleDataBase(context);

            String command = StringHelper.GenerateMessage(context, R.string.order_detail,
                    orderNo + "");
            cursor = db.select(command);

            int row=1;
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    OrderCompleteDetailModel order = new OrderCompleteDetailModel();
                    order.Row=row;
                    order.ProductName = cursor.getString(cursor.getColumnIndex(OrderCompleteDetailModel.Column.ProductName));
                    order.IsBonus = cursor.getInt(cursor.getColumnIndex(OrderCompleteDetailModel.Column.IS_BONUS)) != 0;
                    order.QtyCartoon = cursor.getInt(cursor.getColumnIndex(OrderCompleteDetailModel.Column.QtyCartoon));
                    order.QtyPackage = cursor.getInt(cursor.getColumnIndex(OrderCompleteDetailModel.Column.QtyPackage));
                    order.PriceCartoon = cursor.getInt(cursor.getColumnIndex(OrderCompleteDetailModel.Column.PriceCartoon));
                    order.PricePackage = cursor.getInt(cursor.getColumnIndex(OrderCompleteDetailModel.Column.PricePackage));
                    order.OrderNo = cursor.getInt(cursor.getColumnIndex(OrderCompleteDetailModel.Column.ORDER_NO));
                    order.Shortcut = cursor.getString(cursor.getColumnIndex(OrderCompleteDetailModel.Column.SHORTCUT));
                    row++;
                    orders.add(order);
                }
                while (cursor.moveToNext());
            }
            return orders;
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
    }

    public static OrderStatistics getOrdersStatistics(Context context,
                                                      boolean shouldShowJustNotIssuedOrder,
                                                      boolean shouldShowJustTodayOrder
                                                      ) throws Exception {
        DBHelper db = null;
        Cursor cursor = null;
        List<OrderCompleteDetailModel> orders = new ArrayList<>();
        OrderStatistics orderStatistics = null;
        try {
            db = Common.getSaleDataBase(context);
            orderStatistics = new OrderStatistics();
            String command = StringHelper.GenerateMessage(context, R.string.orders_statistics,
                    shouldShowJustTodayOrder ? " AND o.RegDate = '" + PersianCalendar.getPersianDate() + "' " : " ",
                    shouldShowJustNotIssuedOrder ? " AND o.IsIssued = 0 " : " "
                    );
            cursor = db.select(command);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                    orderStatistics.TotalPrice = cursor.getLong(cursor.getColumnIndex(OrderStatistics.Column.TotalPrice));
                    orderStatistics.TotalWeight = cursor.getFloat(cursor.getColumnIndex(OrderStatistics.Column.TotalWeight));
                    orderStatistics.TotalNetWeight = cursor.getFloat(cursor.getColumnIndex(OrderStatistics.Column.TotalNetWeight));
                    orderStatistics.TotalQtyCarton = cursor.getFloat(cursor.getColumnIndex(OrderStatistics.Column.TotalQtyCarton));
                    orderStatistics.TotalQtyPackage = cursor.getFloat(cursor.getColumnIndex(OrderStatistics.Column.TotalQtyPackage));
                    orderStatistics.TotalRetailerCount = cursor.getFloat(cursor.getColumnIndex(OrderStatistics.Column.TotalRetailerCount));
                    orderStatistics.TotalWholesalerCount = cursor.getFloat(cursor.getColumnIndex(OrderStatistics.Column.TotalWholesalerCount));
            }
            return orderStatistics;
        } finally {
            if (cursor != null)
                cursor.close();
            if (db != null)
                db.close();
        }
    }
}
