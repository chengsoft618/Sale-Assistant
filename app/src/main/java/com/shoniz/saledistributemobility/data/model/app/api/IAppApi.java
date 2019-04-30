package com.shoniz.saledistributemobility.data.model.app.api;

import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.data.model.user.RoleEntity;
import com.shoniz.saledistributemobility.data.model.user.UserEntity;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.infrastructure.version.VersionData;
import com.shoniz.saledistributemobility.view.entity.EmployeeInfoEntity;

import java.util.List;

public interface IAppApi {
    VersionData getAppVersionLink() throws BaseException, ApiException;
    VersionData getGooglePlayServicesVersionLink() throws BaseException, ApiException;
    String getChromeLink() throws BaseException, ApiException;

    EmployeeInfoEntity getEmployeeInfoEntity() throws BaseException;
}
