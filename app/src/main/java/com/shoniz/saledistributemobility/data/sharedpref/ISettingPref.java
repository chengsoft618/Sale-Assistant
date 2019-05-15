package com.shoniz.saledistributemobility.data.sharedpref;


import com.shoniz.saledistributemobility.data.model.app.BranchEntity;
import com.shoniz.saledistributemobility.view.entity.EmployeeInfoEntity;

import java.util.List;

public interface ISettingPref {
    int getCurrentRoleId();
    void clearAll();
    void setCurrentRoleId(int roleId);
    String getPreviousVersionName();
    String getLastDailyUpdateDate();
    boolean getShowVerificationOrder();
    void setCurrentVersionName(String versionName);
    int getLocationUpdateIntervalSeconds();
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
    boolean isCheckSaleNotReason();
    String getCloudToken();
    void setCloudToken(String token);
    void setTokenCloudMessagingSave(boolean flag);

    BranchEntity getBranchEntity();
    void setBranchEntity(BranchEntity branchEntity);
    void switchUrlPriority();

    EmployeeInfoEntity getEmployeeInfoEntity();
    void setEmployeeInfoEntity(EmployeeInfoEntity employeeInfoEntity);

    void setIsInitialedApplication(boolean status);
    boolean getIsInitialedApplication();

    int getLocationMinDistance();

    int getChequeDurationDay();

    void setDownloadId(long id);
    long  getDownloadId();

    int getCustomerLocationInterval();

    boolean isCheckNotSendOrder();

    int getAllowanceIssuePrintTime();
    void setAllowanceIssuePrintTime(int times);

    String getLastCompletePathVisitDesc();
    String getApkLastVersion();

    void setLastCompletePathVisitDesc(String desc);

    long getCustomerPointIntervalSeconds();

    void removePref(String prefKey);

    long getUnchangedOrdersNoInCardindeForEdit();
    void setUnchangedOrdersNoInCardindeForEdit(long orderNo);

    void setInactiveCustomerChecked(boolean value);
    boolean isInactiveCustomerChecked();
    boolean isClassNameBCustomerChecked();
    void setClassNameBCustomerCheckboxStatus(boolean value);
}
