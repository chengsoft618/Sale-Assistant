package com.shoniz.saledistributemobility.framework.service.order;

import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.order.sent.SentOrderModel;
import com.shoniz.saledistributemobility.order.unsent.UnsentOrderModel;
import com.shoniz.saledistributemobility.order.unvisited.UnvisitedCustomerModel;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBasicModel;

import java.io.IOException;
import java.util.List;

public interface ICardIndexService {
    void makeOrderReadyToEdit(long orderNo) throws IOException;
    void removeUnchangedCardIndexForEdit() throws IOException;
//    SentOrderModel getOrderedRequest(long orderNo) throws BaseException;
//    void deleteOrder(long orderNo) throws BaseException;
//    List<UnsentOrderModel> getUnsentRequestList() throws BaseException;
//    List<UnvisitedCustomerModel> getUnvisitedCustomerList() throws BaseException;
}
