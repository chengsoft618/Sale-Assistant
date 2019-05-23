package com.shoniz.saledistributemobility.view.ordering.sent;

import android.content.Context;

import com.shoniz.saledistributemobility.view.ordering.order.detail.OrderDetailData;

/**
 * Created by 921235 on 3/17/2018.
 */

public class OrderStatistics {
   public long TotalPrice;
   public float TotalWeight;
   public float TotalNetWeight;
   public float TotalQtyCarton;
   public float TotalQtyPackage;
   public float TotalWholesalerCount;
   public float TotalRetailerCount;

   public static class Column{
       public static String TotalPrice = "TotalPrice";
       public static String TotalWeight = "TotalWeight";
       public static String TotalNetWeight = "TotalNetWeight";
       public static String TotalQtyCarton = "TotalQtyCarton";
       public static String TotalQtyPackage = "TotalQtyPackage";
       public static String TotalWholesalerCount = "TotalWholesalerCount";
       public static String TotalRetailerCount = "TotalRetailerCount";
   }

   public static OrderStatistics getOrderStatistic(Context context,
                                                   boolean shouldShowJustNotIssuedOrder,
                                                   boolean shouldShowJustTodayOrder)
                                                            throws Exception {
       return OrderDetailData.getOrdersStatistics(context,
                                        shouldShowJustNotIssuedOrder,
                                        shouldShowJustTodayOrder);
   }
}
