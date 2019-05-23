package com.shoniz.saledistributemobility.view.path;

import com.shoniz.saledistributemobility.view.ordering.order.IsSelectedItem;

/**
 * Created by ferdos.s on 6/7/2017.
 */

public class PathModel__ extends IsSelectedItem {

    public static class Column{
        public static final String PATH_CODE="PathCode";
        public static final String PATH_DESCRIPTION="PathDescription";
        public static final String PATH_END_POINT ="PathEndPoint";
        public static final String PATH_NAME ="PathName";
        public static final String PATH_START_POINT ="PathStartPoint";
        public static final String VISIT_PATH_ROW ="VisitPathRow";
        public static final String ZONE_CODE ="ZoneCode";
        public static final String CUSTOMER_COUNT="CustomerCount";
        public static final String WHOLESALER_COUNT="WholesalerCount";
        public static final String RETAILER_COUNT="RetailerCount";
        public static final String TOUR_PERIOD="TourPeriod";
        public static final String IS_ACTIVE="IsActive";
        public static final String Persian_Date = "PersianDate";
    }

    public boolean isSelectedToUpdate = false;

    public int PathCode ;
    public String PathDescription ;
    public String PathEndPoint ;
    public String PathName ;
    public String PersianDate ;
    public String PathStartPoint ;
    public int TourPeriod ;
    public int VisitPathRow ;
    public int ZoneCode;
    public int CustomerCount ;
    public int WholesalerCount ;
    public int RetailerCount ;
    public boolean IsActive;

}
