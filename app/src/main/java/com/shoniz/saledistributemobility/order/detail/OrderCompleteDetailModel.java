package com.shoniz.saledistributemobility.order.detail;

import com.shoniz.saledistributemobility.order.IsSelectedItem;

public class OrderCompleteDetailModel extends IsSelectedItem {
    public static class Column{
        public static final String ORDER_NO ="OrderNo";
        public static final String ProductName ="ProductName";
        public static final String IS_BONUS="IsBonus";
        public static final String PRICE ="Price";
        public static final String QtyCartoon ="QtyCartoon";
        public static final String QtyPackage ="QtyPackage";
        public static final String PriceCartoon ="PriceCartoon";
        public static final String PricePackage ="PricePackage";
        public static final String SHORTCUT ="Shortcut";
    }


    public int Row;
    public long OrderNo;
    public String ProductName;
    public boolean IsBonus;
    public int Price;
    public int QtyCartoon;
    public int QtyPackage;
    public int PriceCartoon;
    public int PricePackage;
    public String Shortcut;
}

