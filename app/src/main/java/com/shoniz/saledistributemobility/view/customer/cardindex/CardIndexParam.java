package com.shoniz.saledistributemobility.view.customer.cardindex;

/**
 * Created by ferdos.s on 1/14/2018.
 */

public class CardIndexParam {
    public String Shortcut;
    public Long OrderNo;
    public int PersonId;
    public int Carton;
    public int Package;

    public static class Column{
        public static String Shortcut = "Shortcut";
        public static String OrderNo = "OrderNo";
        public static String PersonId = "PersonId";
        public static String Carton = "Carton";
        public static String Package = "Package";
    }
}
