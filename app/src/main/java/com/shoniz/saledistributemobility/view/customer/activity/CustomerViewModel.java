package com.shoniz.saledistributemobility.view.customer.activity;

import android.content.Context;

import com.shoniz.saledistributemobility.data.model.cardindex.ICardIndexRepository;
import com.shoniz.saledistributemobility.data.model.customer.ICustomerRepository;
import com.shoniz.saledistributemobility.data.model.message.IMessageRepository;
import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.ExceptionHandler;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.order.RequestBusiness;
import com.shoniz.saledistributemobility.view.base.viewmodel.BaseViewModel;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBasicModel;

import java.io.IOException;

import javax.inject.Inject;

public class CustomerViewModel extends BaseViewModel<ICustomerNavigator> {

    @Inject
    ICardIndexRepository cardIndexRepository;
    @Inject
    ISettingRepository settingRepository;
    @Inject
    IOrderRepository orderRepository;

    CommonPackage commonPackage;

    @Inject
    public CustomerViewModel(CommonPackage commonPackage, IOrderRepository orderRepository, ICardIndexRepository cardIndexRepository) {
        this.commonPackage = commonPackage;
        this.orderRepository = orderRepository;
        this.cardIndexRepository = cardIndexRepository;
        this.commonPackage = commonPackage;
    }

    public void load() {
    }



}
