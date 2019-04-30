package com.shoniz.saledistributemobility.data.sharedpref.api;

import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.data.model.order.OrderDetailEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderEntity;
import com.shoniz.saledistributemobility.data.sharedpref.SettingEntity;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.List;

/**
 * Created by 921235 on 5/12/2018.
 */

public interface ISettingApi {
    public List<SettingEntity> getUserSettings() throws BaseException;
}
