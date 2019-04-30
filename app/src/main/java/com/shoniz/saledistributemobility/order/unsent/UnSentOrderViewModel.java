package com.shoniz.saledistributemobility.order.unsent;

import com.shoniz.saledistributemobility.data.model.cardindex.CardIndexDetailData;
import com.shoniz.saledistributemobility.data.model.cardindex.ICardIndexRepository;
import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderDetailCompleteData;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.view.base.viewmodel.BaseViewModel;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexBusiness;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class UnSentOrderViewModel extends BaseViewModel<IUnSendOrderNavigator> {

    @Inject
    public UnSentOrderViewModel() {

    }

    @Inject
    IOrderRepository orderRepository;

    @Inject
    ISettingRepository settingRepository;

    @Inject
    ICardIndexRepository cardIndexRepository;

    @Inject
    CommonPackage commonPackage;


}
