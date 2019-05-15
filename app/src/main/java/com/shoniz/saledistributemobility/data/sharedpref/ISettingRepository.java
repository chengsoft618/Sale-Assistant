package com.shoniz.saledistributemobility.data.sharedpref;


import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.data.model.app.BranchEntity;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.view.entity.EmployeeInfoEntity;

import java.util.List;

public interface ISettingRepository {
    void sync() throws BaseException;

    int getCurrentRoleId();
    String getApkLastVersion();
    void clearAll();
    void setCurrentRoleId(int roleId);
    String getPreviousVersionName();
    String getLastDailyUpdateDate();
    boolean getShowVerificationOrder();
    void setCurrentVersionName(String versionName);

    int getLocationUpdateIntervalSeconds();
    int getCustomerLocationInterval();
    int getLocationMinDistance();
    int getSyncIntervalSeconds();
    void setLocationUpdateIntervalSeconds(int interval);
    void setAllSetting(List<SettingEntity> settingEntities);

    void setLastDailyUpdateDate();
    long getTrackingUpdateIntervalSeconds();
    long getStopTrackingAfter();
    void setTrackingUpdateIntervalSeconds(int interval);

    boolean getIsInTrackingMode();
    void setIsInTrackingMode(boolean isInTracking);

    String getWiolonIp();
    int getWiolonPort();

    long getLocationMaxWaitTime();

    String getStartTrackingTime();
    String getStopTrackingTime();
    boolean isEnableLocationTracking();
    boolean isTokenCloudMessagingSave();
    void setTokenCloudMessagingSave(boolean flag);
    String getCloudToken();
    void setCloudToken(String token);
    boolean isNotSaleReasonChecked();
    boolean isCheckNotSendOrder();

    BranchEntity getBranchEntity();
    void setBranchEntity(BranchEntity branchEntity);
    void switchUrlPriority();

    EmployeeInfoEntity getEmployeeInfoEntity();
    void setEmployeeInfoEntity(EmployeeInfoEntity employeeInfoEntity);
    void setIsInitialedApplication(boolean status);
    boolean isInitialedApplication();

    int getChequeDurationDay();

    String getEndOfDailyVisitDesc();

    void setEndOfDailyWorkDesc(String personIdText);

    long getCustomerPointIntervalSeconds();

    int getAllowanceIssuePrintTime();
    void setAllowanceIssuePrintTime(int times);

    void removePref(String prefKey);

    long getUnchangedOrdersNoInCardindeForEdit();
    void setUnchangedOrdersNoInCardindeForEdit(Long order);

    void setInactiveCustomerChecked(boolean value);
    boolean isInactiveCustomerChecked();
    boolean isClassNameBCustomerChecked();
    void setCustomerClassNameBCheckbox(boolean value);
}
