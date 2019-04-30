package com.shoniz.saledistributemobility.view.customer.info.bought;

/**
 * Created by ferdos.s on 7/31/2017.
 */

public class CustomerBuyModel {

    public static class Column{
        public static final String CUSTOMER_ID="CustomerID";
        public static final String PERSON_ID="PersonID";
        public static final String YEAR_TYPE_ID ="YearTypeID";
        public static final String AMOUNT_BUY_THIS_YEAR ="AmountBuyThisYear";
        public static final String QTY_MAIN_UNIT_BUY_THIS_YEAR ="QtyMainUnitBuyThisYear";
        public static final String QTY_SUB_UNIT_BUY_THIS_YEAR ="QtySubUnitBuyThisYear";
        public static final String AMOUNT_BUY_RETURNED_THIS_YEAR ="AmountBuyReturnedThisYear";
        public static final String QTY_MAIN_UNIT_BUY_RETURNED_THIS_YEAR ="QtyMainUnitBuyReturnedThisYear";
        public static final String YEAR_TYPE ="YearType";
    }

    public int CustomerID;
    public int PersonID;
    public String YearTypeID;
    public String AmountBuyThisYear;
    public String QtyMainUnitBuyThisYear;
    public String QtySubUnitBuyThisYear;
    public String AmountBuyReturnedThisYear;
    public String QtyMainUnitBuyReturnedThisYear;
    public String YearType;


}
