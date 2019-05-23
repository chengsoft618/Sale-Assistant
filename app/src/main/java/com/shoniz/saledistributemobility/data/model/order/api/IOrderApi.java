package com.shoniz.saledistributemobility.data.model.order.api;

import com.shoniz.saledistributemobility.data.model.ShortcutAvailability;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.data.model.order.OrderDetailEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderEntity;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderDetailCompleteData;
import com.shoniz.saledistributemobility.data.model.customer.UnvisitedCustomerReasonEntity;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.List;

/**
 * Created by 921235 on 5/12/2018.
 */

public interface IOrderApi {

    public List<OrderEntity> getOrderVerification() throws BaseException;

    public List<OrderDetailEntity> getOrderDetailVerification() throws BaseException;

    OrderCompleteData fetchOrder(long orderNo) throws BaseException;

    public List<OrderCompleteData> fetchOrderAll() throws BaseException;

    List<OrderDetailCompleteData> fetchOrderDetail(long orderNo) throws BaseException;

    public List<UnvisitedCustomerReasonEntity> getSaleNotReason(String persianDate) throws BaseException;

    public void verify(List<Long> ids, int roleId) throws BaseException;

    public void reject(long id, String comment) throws BaseException;

    public void sentTo(long id, int userId, String comment,int roleId) throws BaseException;

    public void cancelVerification(Long id,String message) throws BaseException;

    List<ShortcutAvailability> syncShortcutsAvailability() throws BaseException;



}
