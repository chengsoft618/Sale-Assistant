package com.shoniz.saledistributemobility.view.ordering.order.detail;

import com.shoniz.saledistributemobility.view.ordering.order.IsSelectedItem;

public class OrderDetailModel extends IsSelectedItem {
    public static class Column{
        public static final String ORDER_NO ="OrderNo";
        public static final String IS_BONUS="IsBonus";
        public static final String ROW ="Row";
        public static final String PRICE ="Price";
        public static final String QTY ="Qty";
        public static final String UNIT_ID ="UnitId";
        public static final String SHORTCUT ="Shortcut";
    }
    public long OrderNo;
    public boolean IsBonus;
    public int Row;
    public int Price;
    public int Qty;
    public int UnitId;
    public String Shortcut;
}

