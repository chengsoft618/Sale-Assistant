package com.shoniz.saledistributemobility.view.customer.info.credit;

/**
 * Created by ferdos.s on 7/3/2017.
 */

public class CustomerCreditModel {


    public static class Column{
        public static final String CUSTOMER_ID="CustomerID";
        public static final String PERSON_ID="PersonID";
        public static final String CustomerCreditFirstSixMonth ="CustomerCreditFirstSixMonth";
        public static final String CustomerCreditSecondSixMonth ="CustomerCreditSecondSixMonth";
    }

    public int CustomerID;
    public int PersonID;
    public int CustomerCreditFirstSixMonth;
    public int CustomerCreditSecondSixMonth;
}
