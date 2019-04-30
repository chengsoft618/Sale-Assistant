package com.shoniz.saledistributemobility.order.detail;

import com.shoniz.saledistributemobility.order.OrderModel;

/**
 * Created by 921235 on 2/19/2018.
 */

public class OrderCompleteModel extends OrderModel {
    public int CustomerID;
    public String PersonName;
    public String Address;
    public String TelNo;
    public String CellNo;
    public String ContactName;
    public int PathCode;
    public  String PathName;
    public boolean InPath;

    public static class OrderCompleteColumn {
        public static String CustomerID = "CustomerID";
        public static String PersonName = "PersonName";
        public static String Address = "Address";
        public static String TelNo = "TelNo";
        public static String CellNo = "CellNo";
        public static String ContactName = "ContactName";
        public static String PathCode = "PathCode";
        public static  String PathName = "PathName";
        public static String InPath = "InPath";
    }
}
