package com.shoniz.saledistributemobility.view.customer.cardindex;

/**
 * Created by ferdos.s on 1/11/2018.
 */

public class CardIndexModel {
    public static class Column{
        public static final String NeedDate = "NeedDate";
        public static final String OrderNo = "OrderNo";
        public static final String ChequeDuration = "ChequeDuration";
        public static final String PersonID = "PersonID";
        public static final String AccDesc = "AccDesc";
        public static final String SaleDesc = "SaleDesc";
        public static final String ErrorMessage = "ErrorMessage";
        public static final String RegDate = "RegDate";
        public static final String AddressID = "AddressID";
    }

    public String NeedDate;
    public String RegDate;
    public long OrderNo;
    public int ChequeDuration;
    public int PersonID;
    public int AddressID;
    public String SaleDesc="";
    public String AccDesc="";
    public String ErrorMessage;
}
