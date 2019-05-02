package com.shoniz.saledistributemobility.view.path.customerlist;

import com.shoniz.saledistributemobility.data.model.cardindex.ICardIndexRepository;
import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.framework.service.order.ICardIndexService;
import com.shoniz.saledistributemobility.order.OrderDataOld;
import com.shoniz.saledistributemobility.order.detail.OrderDetailData;
import com.shoniz.saledistributemobility.view.base.viewmodel.BaseViewModel;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexBusiness;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexDetailModel;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexModel;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexOldDb;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBasicModel;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

public class CustomerListViewModel extends BaseViewModel<ICustomerListNavigator> {

    ICardIndexService cardIndexService;
    CommonPackage commonPackage;

    @Inject
    public CustomerListViewModel(CommonPackage commonPackage, ICardIndexService cardIndexService) {
        this.commonPackage = commonPackage;
        this.cardIndexService = cardIndexService;
    }

    public void startCustomerActivityToEdit(CustomerBasicModel customerBasicModel) {
        try {
            cardIndexService.makeOrderReadyToEdit(customerBasicModel.UnIssuedOrderNo);
        } catch (Exception e) {
            UncaughtException ex = new UncaughtException(commonPackage, e);
            getNavigator().handleError(ex);
        }

        getNavigator().startCustomerActivity(customerBasicModel.PersonID);
    }


}
