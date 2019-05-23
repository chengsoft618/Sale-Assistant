package com.shoniz.saledistributemobility.data.model.customer;

import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.view.entity.ReasonEntity;

import java.util.List;

public interface ICustomerRepository {

    List<CustomerAddressEntity> getCustomerAddress(int personID);
//    void syncCustomerWholeInfoById(int personId) throws BaseException;
//    void syncEmployeeWholeInfoByPath(int pathId) throws BaseException;
    boolean isPathSync(int pathId);
    List<CustomerData> getCustomerBaseInfoByPath(int pathCode, boolean containInactiveCustomers, boolean containClassB);
    CustomerBasicEntity getCustomerBase(int personID);

    CustomerCreditEntity getCustomerCredit(int personID);
    void sendUnvisitedReason(UnvisitedReasonData unvisitedReasonData) throws BaseException;
    void saveUnvisitingReason(UnvisitedCustomerReasonEntity reasonDto);
    List<ReasonEntity> getUnvisitedReasons();



//    CustomerChequeEntity syncEmployeeCustomerChequeByPath(int pathId) throws InOutError;
//    CustomerBoughtEntity syncEmployeeCustomerBoughtByPath(int pathId) throws InOutError;
//    CustomerCreditEntity syncEmployeeCustomerCreditByPath(int pathId) throws InOutError;
//    OrderEntity syncOrderByPath(int pathId) throws InOutError;
//    OrderDetailEntity syncOrderDetailByPath(int pathId) throws InOutError;

}
