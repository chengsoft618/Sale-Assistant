package com.shoniz.saledistributemobility.view.customer.info.cheque;

/**
 * Created by ferdos.s on 7/30/2017.
 */

public class CustomerChequeModel {


    public static class Column{
        public static final String CUSTOMER_ID="CustomerID";
        public static final String PERSON_ID="PersonID";
        public static final String SERIAL_NUMBER ="SerialNumber";
        public static final String BANK_NAME ="BankName";
        public static final String BANK_BRANCH_NAME ="BankBranchName";
        public static final String PERSON_NAME ="PersonName";
        public static final String TOTAL_PAYMENT ="TotalPayment";
        public static final String FLOW_DATE="FlowDate";
        public static final String REASON_NAME="ReasonName";
        public static final String TYPE_CHEQUE="TypeCheque";
        public static final String CONDITION_ID="ConditionID";
        public static final String PaymentDate="PaymentDate";
        public static final String DueDate="DueDate";
    }

    public int PersonID;
    public int CustomerID;
    public String SerialNumber;
    public String BankName;
    public String BankBranchName;
    public String PersonName;
    public String TotalPayment;
    public String FlowDate;
    public String ReasonName;
    public String TypeCheque;
    public String PaymentDate;
    public String DueDate;
    public int ConditionID;
}
