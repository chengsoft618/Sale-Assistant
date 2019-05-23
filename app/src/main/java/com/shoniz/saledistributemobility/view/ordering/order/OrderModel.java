package com.shoniz.saledistributemobility.view.ordering.order;

public class OrderModel extends IsSelectedItem {


    public static class OrderColumn {
        public static final String ORDER_NO="OrderNo";
        public static final String ORDER_SERIAL="OrderSerial";
        public static final String REG_DATE ="RegDate";
        public static final String PERSON_ID="PersonID";
        public static final String TOTAL_AMOUNT ="TotalAmount";
        public static final String BONUS_AMOUNT ="BonusAmount";
        public static final String IS_ISSUED ="IsIssued";
        public static final String ACC_DESC ="AccDesc";
        public static final String SALES_DESC ="SalesDesc";
        public static final String CHEQUE_DURATION ="ChequeDuration";
        public static final String INVOICE_REMAINS ="InvoiceRemains";
        public static final String ORDER_TYPE_ID ="OrderTypeID";
        public static final String ORDER_WEIGHT ="OrderWeight";
        public static final String ORDER_NET_WEIGHT ="OrderNetWeight";
        public static final String ORDER_STATUS ="OrderStatus";
        public static final String VARITY ="Varity";
        public static final String SenderId ="SenderId";
        public static final String ActionDate ="ActionDate";
        public static final String ActionId ="ActionId";
        public static final String Comment ="Comment";
        public static final String UserId ="UserId";
        public static final String Verifiable ="Verifiable";
        public static final String NeededCreditAmount ="NeededCreditAmount";
        public static final String ResponseDoc ="ResponseDoc";
        public static final String ClientOrderNo ="ClientOrderNo";
        public static final String IssuePrintedTime ="IssuePrintedTime";
    }

    public Long OrderNo;
    public Long OrderSerial;
    public String RegDate;
    public int PersonID;
    public int TotalAmount;
    public int BonusAmount;
    public boolean IsIssued;
    public String AccDesc;
    public String SalesDesc;
    public int ChequeDuration;
    public int InvoiceRemains;
    public int OrderTypeID;
    public int OrderWeight;
    public int OrderNetWeight;
    public int Varity;
    public int SenderId;
    public Long ActionDate;
    public int ActionId;
    public String Comment;
    public int UserId;
    public int Verifiable;
    public long NeededCreditAmount;
    public String ResponseDoc;
    public int IssuePrintedTime;
    public String OrderStatus;
}
