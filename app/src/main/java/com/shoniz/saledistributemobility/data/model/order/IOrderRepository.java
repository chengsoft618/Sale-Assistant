package com.shoniz.saledistributemobility.data.model.order;


import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderDetailCompleteData;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.view.entity.ReasonEntity;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by 921235 on 5/7/2018.
 */

public interface IOrderRepository {

    List<OrderData> getOrdersToVerify(int userId) throws InOutError;
    List<OrderData> getVerifiedOrdersToCancel(int userId) throws InOutError;

//    List<OrderEntity> getOrderNotIssued() throws InOutError;
    List<OrderDetailEntity>  getOrderDetailNotIssued() throws BaseException;
//    List<OrderEntity> getOrderVerification() throws InOutError;



    //void sync() throws BaseException;

    int isReasonSendAll();

    void verifyOrder(List<Long> orderIds) throws BaseException;

    void cancelVerify(Long orderId,String message) throws BaseException;

    void rejectVerify(long orderId, String comment) throws BaseException;

    void sendTo(long orderId, int userId, String comment,int roleId) throws BaseException;


    OrderCompleteData fetchOrderFromApi(long orderNo) throws BaseException;
    List<OrderCompleteData> fetchOrderAllFromApi() throws BaseException;
    List<OrderDetailCompleteData> fetchOrderDetailFromApi(long orderNo) throws BaseException;


    OrderCompleteData getOrderComplete(long orderNo)  ;

    List<OrderDetailCompleteData> getOrderDetailComplete(long orderNo)  ;

    Hashtable<Integer,Integer> getShortcutsAvailability();

    void syncShortcutsAvailability() throws BaseException;
    List<ReasonEntity> getUnvisitingReasons();

    void saveUnvisitingReason(UnvisitedCustomerReasonEntity reasonDto);
}
