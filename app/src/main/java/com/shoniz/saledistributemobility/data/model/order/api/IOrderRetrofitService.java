package com.shoniz.saledistributemobility.data.model.order.api;

import com.shoniz.saledistributemobility.data.model.ShortcutAvailability;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.data.model.order.OrderDetailEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderEntity;
import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.model.customer.UnvisitedCustomerReasonEntity;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderDetailCompleteData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by 921235 on 5/12/2018.
 */

public interface IOrderRetrofitService  {

    @POST("getOrderVerification")
    Call<List<OrderEntity>> getNotIssuedOrders(@Body ApiParameter parameter);

    @POST("getOrderVerification")
    Call<List<OrderCompleteData>> getOrderAll(@Body ApiParameter parameter);

    @POST("GetOrder")
    Call<OrderCompleteData> getOrder(@Body ApiParameter parameter);

    @POST("getOrderDetailVerification")
    Call<List<OrderDetailEntity>> getNotIssuedOrdersDetail(@Body ApiParameter parameter);

    @POST("GetSaleNotResaon")
    Call<List<UnvisitedCustomerReasonEntity>> getSaleNotReason(@Body ApiParameter parameter);

    @POST("setOrderVerifyAction")
    Call<Void> verify(@Body ApiParameter parameter);

    @POST("rejectOrderVerifyAction")
    Call<Void> reject(@Body ApiParameter parameter);

    @POST("OrderSendToForVerify")
    Call<Void> sendTo(@Body ApiParameter parameter);

    @POST("CancelOrderVerifyAction")
    Call<Void> cancelVerification(@Body ApiParameter parameter);

    @POST("GetOrderDetailVerifyList")
    Call<List<OrderDetailCompleteData>> fetchOrderDetail(@Body ApiParameter apiParameter);

    @POST("GetShortcutsAvailability")
    Call<List<ShortcutAvailability>> syncShortcutsAvailability(@Body ApiParameter apiParameter);
}
