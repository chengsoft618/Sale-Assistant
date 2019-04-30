package com.shoniz.saledistributemobility.order.unvisited;

/**
 * Created by ferdos.s on 1/31/2018.
 */

public class ReasonDto {

    public static class Column{
        public static final String NotSallReasonID ="NotSallReasonID";
        public static final String PersianDate="PersianDate";
        public static final String PersonID="PersonID";
        public static final String Description="Description";

    }

    public int NotSallReasonID = 1;
    public int PersonID ;
    public String PersianDate;
    public String Description;
}
