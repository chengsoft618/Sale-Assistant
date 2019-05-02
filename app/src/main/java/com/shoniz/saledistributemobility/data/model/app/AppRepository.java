package com.shoniz.saledistributemobility.data.model.app;

import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.data.model.app.api.IAppApi;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.view.entity.EmployeeInfoEntity;

import javax.inject.Inject;

public class AppRepository implements IAppRepository {

    private IAppApi appApi;
    private ISettingRepository settingRepository;
    @Inject
    public AppRepository(ISettingRepository settingRepository, IAppApi appApi) {
        this.appApi = appApi;
        this.settingRepository = settingRepository;
    }



    @Override
    public EmployeeInfoEntity getEmployeeInfo() {
        return settingRepository.getEmployeeInfoEntity();
    }

    @Override
    public void syncEmployeeInfo() throws BaseException {
        settingRepository.setEmployeeInfoEntity(appApi.getEmployeeInfoEntity());
    }
}
