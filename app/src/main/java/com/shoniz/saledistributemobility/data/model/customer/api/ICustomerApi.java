package com.shoniz.saledistributemobility.data.model.customer.api;

import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBasicEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBoughtEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerChequeEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerCreditEntity;
import com.shoniz.saledistributemobility.data.model.location.LocationEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderDetailEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderEntity;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.List;

public interface ICustomerApi {
    void sendCustomerLocation(int customerId, LocationEntity locationEntity) throws BaseException;

   List<CustomerBasicEntity> getEmployeeCustomerBaseInfoByPath(int pathId) throws BaseException;
   List<CustomerChequeEntity> getEmployeeCustomerChequeByPath(int pathId) throws BaseException;
   List<CustomerBoughtEntity> getEmployeeCustomerBoughtByPath(int pathId) throws BaseException;
   List<CustomerCreditEntity> getEmployeeCustomerCreditByPath(int pathId) throws BaseException;
   List<OrderEntity> getOrderByPath(int pathId) throws BaseException;
   List<OrderDetailEntity> getOrderDetailByPath(int pathId) throws BaseException;


   List<CustomerBasicEntity> getCustomerBaseInfoById(int personId) throws BaseException;
   List<CustomerChequeEntity> getEmployeeCustomerChequeById(int personId) throws BaseException;
   List<CustomerBoughtEntity> getEmployeeCustomerBoughtById(int personId) throws BaseException;
   List<CustomerCreditEntity> getEmployeeCustomerCreditById(int personId) throws BaseException;
   List<OrderEntity> getOrderByPersonId(int personId) throws BaseException;
   List<OrderDetailEntity> getOrderDetailByPersonId(int personId) throws BaseException;
}
