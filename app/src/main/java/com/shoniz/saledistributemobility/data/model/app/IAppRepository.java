package com.shoniz.saledistributemobility.data.model.app;


import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.view.entity.EmployeeInfoEntity;

public interface IAppRepository {

    EmployeeInfoEntity getEmployeeInfo();

    void syncEmployeeInfo() throws BaseException;
}
