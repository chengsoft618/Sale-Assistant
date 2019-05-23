package com.shoniz.saledistributemobility.view.ordering.unsent;

/**
 * Created by ferdos.s on 1/17/2018.
 */

public class UnsentOrderModel {
    public int PersonID;
    public String PersonName;
    public String PathName;
    public String RegDate;
    public String ErrorMessage;
    public int InPath;
    public boolean IsSent;
    public String CustomerType;
    public boolean isSelectedToUpdate = false;

    public static class  UnorderedColumns{
        public static String PersonID= "PersonID";
        public static String PersonName= "PersonName";
        public static String PathName= "PathName";
        public static String RegDate= "RegDate";
        public static String ErrorMessage= "ErrorMessage";
        public static String InPath= "InPath";
        public static String IsSent= "IsSent";
        public static String CustomerType= "CustomerType";
    }
}
