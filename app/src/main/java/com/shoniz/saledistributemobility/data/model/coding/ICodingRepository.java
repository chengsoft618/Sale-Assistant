package com.shoniz.saledistributemobility.data.model.coding;

import com.shoniz.saledistributemobility.data.model.customer.CustomerAddressEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBasicEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerCreditEntity;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBasicModel;

import java.util.List;

public interface ICodingRepository {

    CodingEntity getProduct(String shortcut);
}
