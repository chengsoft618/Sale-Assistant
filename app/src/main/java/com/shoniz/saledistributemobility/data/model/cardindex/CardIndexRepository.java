package com.shoniz.saledistributemobility.data.model.cardindex;


import com.shoniz.saledistributemobility.data.model.customer.ICustomerRepository;
import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderDetailCompleteData;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.view.ordering.order.OrderDataOld;
import com.shoniz.saledistributemobility.view.ordering.order.detail.OrderDetailData;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexBusiness;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexDetailModel;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexModel;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexOldDb;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class CardIndexRepository implements ICardIndexRepository {

    ICardIndexDataDao cardIndexDataDao;
    CommonPackage commonPackage;
    IOrderRepository orderRepository;
    ISettingRepository settingRepository;

    @Inject
    public CardIndexRepository(ICardIndexDataDao cardIndexDataDao) {
        this.cardIndexDataDao = cardIndexDataDao;
    }

    @Override
    public List<CardIndexDetailData> getCardIndexDetail(int personId) {
        return cardIndexDataDao.getCardIndexDetail(personId);
    }

    @Override
    public int getCardIndexCount(int personId) {
        return cardIndexDataDao.getCardIndexCount(personId);
    }

    @Override
    public List<CardIndexData> getAllCardIndices() {
        return cardIndexDataDao.getAllCardIndices();
    }



//    public void removeUnchangedCardIndexForEdit() throws IOException {
//
//        Long orderNo = settingRepository.getUnchangedOrdersNoInCardindeForEdit();
//        if (orderNo == 0) return;
//        boolean isEqualFlag = true;
//        OrderCompleteData order = orderRepository.getOrderComplete(orderNo);
//        if (order == null) {
//            settingRepository.setUnchangedOrdersNoInCardindeForEdit(0L);
//            return;
//        }
//        List<OrderDetailCompleteData> orderDetails = orderRepository.getOrderDetailComplete(orderNo);
//        List<CardIndexDetailData> cardIndexDetailData = cardIndexRepository.getCardIndexDetail(order.PersonID);
//
//        if (orderDetails.size() != cardIndexDetailData.size()) return;
//
//        Collections.sort(orderDetails);
//        Collections.sort(cardIndexDetailData);
//
//        for (int i = orderDetails.size() - 1; i >= 0; i--) {
//            if (!isEqualItem(orderDetails.get(i), cardIndexDetailData.get(i))) {
//                settingRepository.setUnchangedOrdersNoInCardindeForEdit(0L);
//                return;
//            }
//        }
//
//     CardIndexBusiness.DeleteCardIndex(commonPackage.getContext(), order.PersonID);
//    }



}
