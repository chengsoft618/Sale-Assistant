package com.shoniz.saledistributemobility.data.model.update;

import com.shoniz.saledistributemobility.app.repository.update.IBasicUpdateRepository;
import com.shoniz.saledistributemobility.data.model.app.api.IAppApi;
import com.shoniz.saledistributemobility.data.model.update.api.IBasicUpdateApi;
import com.shoniz.saledistributemobility.data.model.user.IUserRepository;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import javax.inject.Inject;

public class BasicUpdateRepository extends UpdateBase implements IBasicUpdateRepository {

    IUserRepository userRepository;
    ISettingRepository settingRepository;
    IAppApi appApi;

    @Inject
    public BasicUpdateRepository(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void updateUsers() throws BaseException {
        setUpdateMessage("در حال بروزرسانی کاربران...");
        userRepository.sync();
        settingRepository.setCurrentRoleId(userRepository.getUserMainRoleId(
                settingRepository.getEmployeeInfoEntity().EmployeeId
        ));
    }

    @Override
    public void updateEmployee() throws BaseException {
        setUpdateMessage("در حال بروزرسانی اطلاعات کاربر...");
        settingRepository.setEmployeeInfoEntity(
                appApi.getEmployeeInfoEntity()
        );
    }
}
