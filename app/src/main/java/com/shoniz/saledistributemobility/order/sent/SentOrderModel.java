package com.shoniz.saledistributemobility.order.sent;

import com.shoniz.saledistributemobility.order.unsent.UnsentOrderModel;

/**
 * Created by ferdos.s on 1/17/2018.
 */

public class SentOrderModel extends UnsentOrderModel {
    public int Row;
    public long Price;
    public float OrderWeight;
    public float OrderNetWeight;
    public String OrderStatus;
    public long OrderNo;
    public int Varity;
    public int QtyPackage;
    public int QtyCartoon;
    public boolean IsIssued;

    public static class OrderedColumns{
        public static String Price = "Price";
        public static String OrderWeight = "OrderWeight";
        public static String OrderNo = "OrderNo";
        public static String Varity = "Varity";
        public static String IsIssued = "IsIssued";
        public static String CustomerType = "CustomerType";
        public static String QtyPackage = "QtyPackage";
        public static String QtyCartoon = "QtyCartoon";
        public static String OrderNetWeight = "OrderNetWeight";
        public static String OrderStatus = "OrderStatus";
    }
}
