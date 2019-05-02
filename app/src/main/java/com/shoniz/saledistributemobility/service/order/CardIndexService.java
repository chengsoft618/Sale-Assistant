package com.shoniz.saledistributemobility.service.order;

import com.shoniz.saledistributemobility.data.model.cardindex.CardIndexDetailData;
import com.shoniz.saledistributemobility.data.model.cardindex.ICardIndexRepository;
import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderDetailCompleteData;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.service.order.ICardIndexService;
import com.shoniz.saledistributemobility.order.OrderDataOld;
import com.shoniz.saledistributemobility.order.detail.OrderDetailData;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexBusiness;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexDetailModel;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexModel;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexOldDb;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBasicModel;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class CardIndexService implements ICardIndexService {

    ICardIndexRepository cardIndexRepository;
    ISettingRepository settingRepository;
    IOrderRepository orderRepository;
    CommonPackage commonPackage;

    @Inject
    public CardIndexService(ICardIndexRepository cardIndexRepository, CommonPackage commonPackage, ISettingRepository settingRepository, IOrderRepository orderRepository) {
        this.cardIndexRepository = cardIndexRepository;
        this.commonPackage = commonPackage;
        this.settingRepository = settingRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void makeOrderReadyToEdit(long orderNo) throws IOException {
        if (settingRepository.getUnchangedOrdersNoInCardindeForEdit() != 0
                && settingRepository.getUnchangedOrdersNoInCardindeForEdit() != orderNo) {
            removeUnchangedCardIndexForEdit();
        }

        CardIndexModel cardIndexModel = OrderDataOld.mapOrderHeaderToCardIndex(commonPackage.getContext(), orderNo);
        CardIndexBusiness.DeleteCardIndex(commonPackage.getContext(), cardIndexModel.PersonID);
        List<CardIndexDetailModel> cardIndexDetailModels = OrderDetailData.mapOrderDetailsToCardIndises(commonPackage.getContext(), orderNo);
        CardIndexOldDb.insertCardIndex(commonPackage.getContext(), cardIndexModel);
        for (CardIndexDetailModel cardIndexDetailModel: cardIndexDetailModels) {
            CardIndexOldDb.insertCardIndexDetail(commonPackage.getContext(), cardIndexDetailModel);
        }

        settingRepository.setUnchangedOrdersNoInCardindeForEdit(orderNo);
    }

    @Override
    public void removeUnchangedCardIndexForEdit() throws IOException {
        Long orderNo = settingRepository.getUnchangedOrdersNoInCardindeForEdit();
        if (orderNo == 0) return;
        boolean isEqualFlag = true;
        OrderCompleteData order = orderRepository.getOrderComplete(orderNo);
        if (order == null) {
            settingRepository.setUnchangedOrdersNoInCardindeForEdit(0L);
            return;
        }
        List<OrderDetailCompleteData> orderDetails = orderRepository.getOrderDetailComplete(orderNo);
        List<CardIndexDetailData> cardIndexDetailData = cardIndexRepository.getCardIndexDetail(order.PersonID);

        if (orderDetails.size() != cardIndexDetailData.size()) return;

        Collections.sort(orderDetails);
        Collections.sort(cardIndexDetailData);

        for (int i = orderDetails.size() - 1; i >= 0; i--) {
            if (!isEqualItem(orderDetails.get(i), cardIndexDetailData.get(i))) {
                settingRepository.setUnchangedOrdersNoInCardindeForEdit(0L);
                return;
            }
        }

        CardIndexBusiness.DeleteCardIndex(commonPackage.getContext(), order.PersonID);
    }

//    public static SentOrderModel getOrderedRequest(Context context, long orderNo) throws Exception{
//        return RequestListData.getOrderedRequest(context,orderNo);
//    }
//    public static void deleteOrder(Context context,long orderNo) throws Exception{
//        RequestListData.deleteOrder(context,orderNo);
//    }
//
//    public static List<UnsentOrderModel> getUnsentRequestList(Context context) throws Exception{
//        return RequestListData.getUnsentRequestList(context);
//    }
//
//    public static List<UnvisitedCustomerModel> getUnvisitedCustomerList(Context context) throws Exception{
//        return RequestListData.getUnvisitedCustomerList(context);
//    }

    private boolean isEqualItem(OrderDetailCompleteData orderDetailCompleteData, CardIndexDetailData cardIndexDetailData) {
        if (orderDetailCompleteData.Shortcut != Integer.parseInt(cardIndexDetailData.cardIndexDetailEntity.Shortcut)
                || (orderDetailCompleteData.UnitID == 1 && orderDetailCompleteData.Qty != cardIndexDetailData.cardIndexDetailEntity.RequestCarton)
                || (orderDetailCompleteData.UnitID == 2 && orderDetailCompleteData.Qty != cardIndexDetailData.cardIndexDetailEntity.RequestPackage)
                ) {
            return false;
        }
        return true;
    }
}
