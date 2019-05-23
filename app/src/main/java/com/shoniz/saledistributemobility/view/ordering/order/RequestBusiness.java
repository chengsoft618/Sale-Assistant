package com.shoniz.saledistributemobility.view.ordering.order;

import android.content.Context;

import com.shoniz.saledistributemobility.view.ordering.sent.SentOrderModel;
import com.shoniz.saledistributemobility.view.ordering.unsent.UnsentOrderModel;

import java.util.List;


public class RequestBusiness {

    public static List<SentOrderModel> getOrderedRequestList(Context context,
                                                             boolean shouldShowJustNotIssuedOrder,
                                                             boolean shouldShowJustTodayOrder,
                                                             int personId) throws Exception{
        return RequestListData.getOrderedRequestList(context,
                shouldShowJustNotIssuedOrder,
                shouldShowJustTodayOrder,
                personId
        );
    }

    public static SentOrderModel getOrderedRequest(Context context, long orderNo) throws Exception{
        return RequestListData.getOrderedRequest(context,orderNo);
    }
    public static void deleteOrder(Context context,long orderNo) throws Exception{
        RequestListData.deleteOrder(context,orderNo);
    }

    public static List<UnsentOrderModel> getUnsentRequestList(Context context) throws Exception{
        return RequestListData.getUnsentRequestList(context);
    }

//    public static List<UnvisitedCustomerModel__> getUnvisitedCustomerList(Context context) throws Exception{
//        return RequestListData.getUnvisitedCustomerList(context);
//    }
//    public static void makeOrderReadyToEdit(Context context, long orderNo) throws IOException {
//        CardIndexModel cardIndexModel = OrderDataOld.mapOrderHeaderToCardIndex(context, orderNo);
//        CardIndexBusiness.DeleteCardIndex(context, cardIndexModel.PersonID);
//        List<CardIndexDetailModel> cardIndexDetailModels = OrderDetailData.mapOrderDetailsToCardIndises(context, orderNo);
//        CardIndexOldDb.insertCardIndex(context, cardIndexModel);
//       // CardIndexOldDb.saveDescription(context,cardIndexModel.PersonID,cardIndexModel.AccDesc,cardIndexModel.SaleDesc);
//        for (CardIndexDetailModel cardIndexDetailModel: cardIndexDetailModels) {
//            CardIndexOldDb.insertCardIndexDetail(context, cardIndexDetailModel);
//        }
//
//        //addToShareprefTempOrdersList(context, orderNo);
//
//
//        //RequestBusiness.deleteOrder(context, orderNo);
//    }

//    private static void addToShareprefTempOrdersList(Context context, long orderNo){
//        SharedPreferences preferences;
//        String DEFAULT_PREFERENCE_POSTFIX = "_preferences";
//        preferences = context.getSharedPreferences(context.getPackageName() + DEFAULT_PREFERENCE_POSTFIX, Context.MODE_PRIVATE);
//
//        String currentOrdersString = preferences.getString(context.getString(R.string.pref_key_unchanged_orders_no_in_cardinde_for_edit),"");
//        List<Long> currentOrders = StringHelper.deserialize(currentOrdersString);
//        currentOrders.add(orderNo);
//        currentOrdersString = StringHelper.serialize(currentOrders);
//        preferences.edit().putString(context.getString(R.string.pref_key_unchanged_orders_no_in_cardinde_for_edit), currentOrdersString).apply();
//    }

//    public static List<OrderedRequestListModel> getUnorderedRequestList(Context context) throws Exception{
//        RequestListData requestListData = new RequestListData();
//        return requestListData.getOrderedRequestList(context);
//    }
}
