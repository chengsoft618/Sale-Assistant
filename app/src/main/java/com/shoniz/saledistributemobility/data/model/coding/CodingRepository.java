package com.shoniz.saledistributemobility.data.model.coding;

import android.arch.persistence.db.SimpleSQLiteQuery;

import com.shoniz.saledistributemobility.data.api.ApiNetworkException;
import com.shoniz.saledistributemobility.data.model.customer.CustomerAddressEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBasicEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBoughtEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerChequeEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerCreditEntity;
import com.shoniz.saledistributemobility.data.model.customer.ICustomerRepository;
import com.shoniz.saledistributemobility.data.model.customer.api.ICustomerApi;
import com.shoniz.saledistributemobility.data.model.customer.db.ICustomerDao;
import com.shoniz.saledistributemobility.data.model.order.OrderDetailEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderEntity;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.utility.data.pref.AppPref;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBasicModel;

import java.util.ArrayList;
import java.util.List;

public class CodingRepository implements ICodingRepository {

    private ICodingDao codingDao;
    CommonPackage commonPackage;

    public CodingRepository(ICodingDao codingDao, CommonPackage commonPackage){
        this.codingDao = codingDao;
        this.commonPackage = commonPackage;
    }


    @Override
    public CodingEntity getProduct(String shortcut) {
        return codingDao.getProduct(shortcut);
    }
}
