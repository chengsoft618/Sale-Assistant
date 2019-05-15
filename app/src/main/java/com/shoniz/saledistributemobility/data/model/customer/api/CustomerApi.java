package com.shoniz.saledistributemobility.data.model.customer.api;

import com.shoniz.saledistributemobility.data.model.customer.CustomerBasicEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBoughtEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerChequeEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerCreditEntity;
import com.shoniz.saledistributemobility.data.model.location.LocationEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderDetailEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderEntity;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.data.model.customer.UnvisitedReasonData;

import java.util.List;

public class CustomerApi implements ICustomerApi {


    ICustomerApi customerApi;
    public CustomerApi(ICustomerApi customerApi){
        this.customerApi = customerApi;
    }

    @Override
    public void sendCustomerLocation(int customerId, LocationEntity locationEntity) throws BaseException {
        customerApi.sendCustomerLocation(customerId, locationEntity);
    }

    @Override
    public List<CustomerBasicEntity> getEmployeeCustomerBaseInfoByPath(int pathId) throws BaseException {
        return customerApi.getEmployeeCustomerBaseInfoByPath(pathId);
    }

    @Override
    public List<CustomerChequeEntity> getEmployeeCustomerChequeByPath(int pathId) throws BaseException {
        return customerApi.getEmployeeCustomerChequeByPath(pathId);
    }

    @Override
    public List<CustomerBoughtEntity> getEmployeeCustomerBoughtByPath(int pathId) throws BaseException {
        return customerApi.getEmployeeCustomerBoughtByPath(pathId);
    }

    @Override
    public List<CustomerCreditEntity> getEmployeeCustomerCreditByPath(int pathId) throws BaseException {
        return customerApi.getEmployeeCustomerCreditByPath(pathId);
    }

    @Override
    public List<OrderEntity> getOrderByPath(int pathId) throws BaseException {
        return customerApi.getOrderByPath(pathId);
    }

    @Override
    public List<OrderDetailEntity> getOrderDetailByPath(int pathId) throws BaseException {
        return customerApi.getOrderDetailByPath(pathId);
    }

    @Override
    public List<CustomerBasicEntity> getCustomerBaseInfoById(int personId) throws BaseException {
        return customerApi.getCustomerBaseInfoById(personId);
    }

    @Override
    public List<CustomerChequeEntity> getEmployeeCustomerChequeById(int personId) throws BaseException {
        return customerApi.getEmployeeCustomerChequeById(personId);
    }

    @Override
    public List<CustomerBoughtEntity> getEmployeeCustomerBoughtById(int personId) throws BaseException{
        return customerApi.getEmployeeCustomerBoughtById(personId);
    }

    @Override
    public List<CustomerCreditEntity> getEmployeeCustomerCreditById(int personId) throws BaseException {
        return customerApi.getEmployeeCustomerCreditById(personId);
    }

    @Override
    public List<OrderEntity> getOrderByPersonId(int personId) throws BaseException {
        return customerApi.getOrderByPersonId(personId);
    }

    @Override
    public List<OrderDetailEntity> getOrderDetailByPersonId(int personId) throws BaseException {
        return customerApi.getOrderDetailByPersonId(personId);
    }

    @Override
    public void sendUnvisitedCustomerReason(UnvisitedReasonData unvisitedReasonData) throws BaseException {
        customerApi.sendUnvisitedCustomerReason(unvisitedReasonData);
    }


}
