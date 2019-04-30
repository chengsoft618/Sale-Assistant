package com.shoniz.saledistributemobility.order.sent;

import android.arch.lifecycle.ViewModelProvider;
import android.os.Build;
import android.support.annotation.RequiresApi;

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
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class SentOrderViewModel extends BaseViewModel<ISendOrderNavigator> {

    @Inject
    public SentOrderViewModel() {

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
