package com.shoniz.saledistributemobility.data.model.customer.api;

import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBasicEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBoughtEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerChequeEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerCreditEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderData;
import com.shoniz.saledistributemobility.data.model.order.OrderDetailEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by 921235 on 5/12/2018.
 */

public interface ICustomerRetrofitService {
    @POST("SetCustomerPoint")
    Call<List<Void>> setLocation(@Body ApiParameter parameter);

    @POST("GetEmployeeCustomerBaseInfoByPath")
    Call<List<CustomerBasicEntity>> getEmployeeCustomerBaseInfoByPath(@Body ApiParameter parameter);

    @POST("GetEmployeeCustomerChequeListByPath")
    Call<List<CustomerChequeEntity>> getEmployeeCustomerChequeByPath(@Body ApiParameter parameter);

    @POST("GetEmployeeCustomerSumBuyAndBuyReturnedByPath")
    Call<List<CustomerBoughtEntity>> getEmployeeCustomerBoughtByPath(@Body ApiParameter parameter);

    @POST("GetEmployeeCustomerCreditByPath")
    Call<List<CustomerCreditEntity>> getEmployeeCustomerCreditByPath(@Body ApiParameter parameter);

    @POST("GetOrderByPath")
    Call<List<OrderEntity>> getOrderByPath(@Body ApiParameter parameter);

    @POST("GetOrderDetailByPath")
    Call<List<OrderDetailEntity>> getOrderDetailByPath(@Body ApiParameter parameter);



    @POST("GetEmployeeCustomerBaseInfoByPersonIds")
    Call<List<CustomerBasicEntity>> getCustomerBaseInfoById(@Body ApiParameter parameter);

    @POST("GetEmployeeCustomerChequeListByPersonIds")
    Call<List<CustomerChequeEntity>> getEmployeeCustomerChequeById(@Body ApiParameter parameter);

    @POST("GetEmployeeCustomerSumBuyAndBuyReturnedByPersonIds")
    Call<List<CustomerBoughtEntity>> getEmployeeCustomerBoughtById(@Body ApiParameter parameter);

    @POST("GetEmployeeCustomerCreditByPersonIds")
    Call<List<CustomerCreditEntity>> getEmployeeCustomerCreditById(@Body ApiParameter parameter);

    @POST("GetOdrderByPersonIDs")
    Call<List<OrderEntity>> getOrderByPersonId(@Body ApiParameter parameter);

    @POST("GetOrderDetailByPersonIds")
    Call<List<OrderDetailEntity>> getOrderDetailByPersonId(@Body ApiParameter parameter);

    @POST("sendReasonAll")
    Call<Void> setUnvisitingReason(@Body ApiParameter parameter);
}
