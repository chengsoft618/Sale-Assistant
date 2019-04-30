package com.shoniz.saledistributemobility.view.customer.info.basic;

import android.location.Location;

import com.shoniz.saledistributemobility.data.model.location.LocationEntity;
import com.shoniz.saledistributemobility.data.model.message.MessageData;
import com.shoniz.saledistributemobility.data.model.message.MessageEntity;
import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;
import com.shoniz.saledistributemobility.location.LocationHelper;
import com.shoniz.saledistributemobility.view.base.viewmodel.BaseViewModel;
import com.shoniz.saledistributemobility.view.customer.CustomerBusiness;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CustomerBaseViewModel extends BaseViewModel<ICustomerBaseNavigator> {
    @Inject
    public CustomerBaseViewModel() {
    }


}
