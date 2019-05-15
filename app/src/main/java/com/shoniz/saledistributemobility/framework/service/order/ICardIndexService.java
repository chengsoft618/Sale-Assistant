package com.shoniz.saledistributemobility.framework.service.order;

import java.io.IOException;

public interface ICardIndexService {
    void makeOrderReadyToEdit(long orderNo) throws IOException;
    void removeUnchangedCardIndexForEdit() throws IOException;
    boolean isEmptyCardIndex(int personId);
//    SentOrderModel getOrderedRequest(long orderNo) throws BaseException;
//    void deleteOrder(long orderNo) throws BaseException;
//    List<UnsentOrderModel> getUnsentRequestList() throws BaseException;
//    List<UnvisitedCustomerModel__> getUnvisitedCustomerList() throws BaseException;
}
