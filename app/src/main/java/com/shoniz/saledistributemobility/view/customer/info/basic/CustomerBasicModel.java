package com.shoniz.saledistributemobility.view.customer.info.basic;


import com.shoniz.saledistributemobility.order.IsSelectedItem;

public class CustomerBasicModel extends IsSelectedItem {


    public static class Column{
        public static final String CUSTOMER_ID="CustomerID";
        public static final String PERSON_ID="PersonID";
        public static final String PERSON_NAME ="PersonName";
        public static final String CONTACT_NAME ="ContactName";
        public static final String ADDRESS ="Address";
        public static final String TEL_NO ="TelNo";
        public static final String CELL_NO ="CellNo";
        public static final String OWNER_TYPE="OwnerType";
        public static final String CUSTOMER_TYPE="CustomerType";
        public static final String ACTIVE_SEASON="ActiveSeason";
        public static final String MAX_CREDIT="MaxCredit";
        public static final String NOT_SALE_REASON_DATE ="NotSaleReasonDate";
        public static final String NOT_SALE_REASON_DESC ="NotSaleReasonDesc";
        public static final String PATH_CODE="PathCode";
        public static final String PATH_NAME="PathName";
        public static final String IS_ACTIVE="IsActive";
        public static final String ACCOUNT_REMAIN="AccountRemain";
        public static final String CREDIT_REMAIN="CreditRemain";
        public static final String CLASS_NAMES="ClassNames";
        public static final String LATITUDE="Latitude";
        public static final String LONGITUDE="Longitude";
        public static final String UnIssuedOrderNo="UnIssuedOrderNo";
        public static final String NeededCreditAmount="NeededCreditAmount";
    }

    public boolean isSelectedToUpdate = false;

    public int CustomerID; //CustomerID
    public int PersonID; //PersonID
    public String PersonName; //PersonName
    public String ContactName; //ContactName
    public String Address; //Address
    public String TelNo; //TelNo
    public String CellNo; //CellNo
    public String OwnerType; //OwnerType
    public String CustomerType; //CustomerType
    public String ActiveSeason; //CustomerTypeID
    public String MaxCredit; //ActiveSeason
    public String NotSaleReasonDate; //MaxCredit
    public String NotSaleReasonDesc; //NotSaleReasonDate
    public int PathCode; //NotSaleReasonDesc
    public String PathName; //PathCode
    public boolean IsActive; //PathName
    public int CreditRemain; //IsActive
    public int AccountRemain; //CreditRemain
    public String ClassNames; //AccountRemain
    public String Latitude; //ClassNames
    public String Longitude; //Latitude
    public long UnIssuedOrderNo; //Longitude
    public int NotSellReasonID;
    public long NeededCreditAmount;
    public String LastRegDate;
    public int LastVisitDays;
    public int daysCountAfter45DaysFromLastVisiting;

}




