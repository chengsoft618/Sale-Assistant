package com.shoniz.saledistributemobility.data.sharedpref;

import com.shoniz.saledistributemobility.data.model.app.BranchEntity;
import com.shoniz.saledistributemobility.data.sharedpref.api.ISettingApi;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.view.entity.EmployeeInfoEntity;

import java.util.List;

public class SettingRepository implements ISettingRepository {

    private ISettingApi settingApi;
    private ISettingPref settingPref;

    public SettingRepository( ISettingApi settingApi, ISettingPref settingPref) {
        //super(context);
        this.settingApi = settingApi;
        this.settingPref = settingPref;
    }

    @Override
    public void sync() throws BaseException {
        setAllSetting(settingApi.getUserSettings());

    }

    @Override public void clearAll(){settingPref.clearAll();}
    @Override public void setCurrentRoleId(int roleId){settingPref.setCurrentRoleId(roleId);}
    @Override public void setCurrentVersionName(String versionName){settingPref.setCurrentVersionName(versionName);}
    @Override public void setLocationUpdateIntervalSeconds(int interval){settingPref.setLocationUpdateIntervalSeconds(interval);}
    @Override public void setAllSetting(List<SettingEntity> settingEntities){settingPref.setAllSetting(settingEntities);}
    @Override public void setLastDailyUpdateDate(){settingPref.setLastDailyUpdateDate();}
    @Override public void setTrackingUpdateIntervalSeconds(int interval){settingPref.setTrackingUpdateIntervalSeconds(interval);}
    @Override public void setIsInTrackingMode(boolean isInTracking){settingPref.setIsInTrackingMode(isInTracking);}
    @Override public void setTokenCloudMessagingSave(boolean flag){settingPref.setTokenCloudMessagingSave(flag);}
    @Override public String getCloudToken() {return settingPref.getCloudToken();}
    @Override public void setCloudToken(String token) {settingPref.setCloudToken(token);}

    @Override public void setBranchEntity(BranchEntity branchEntity){settingPref.setBranchEntity(branchEntity);}
    @Override
    public void switchUrlPriority() {

    }

    @Override public void setEmployeeInfoEntity(EmployeeInfoEntity employeeInfoEntity){
        settingPref.setEmployeeInfoEntity(employeeInfoEntity);}
    @Override public boolean getShowVerificationOrder(){return settingPref.getShowVerificationOrder();}
    @Override public int getCurrentRoleId(){return settingPref.getCurrentRoleId();}

    @Override
    public String getApkLastVersion() {
        return settingPref.getApkLastVersion();
    }

    @Override public long getTrackingUpdateIntervalSeconds(){return settingPref.getTrackingUpdateIntervalSeconds();}
    @Override public String getPreviousVersionName(){return settingPref.getPreviousVersionName();}
    @Override public String getLastDailyUpdateDate(){return settingPref.getLastDailyUpdateDate();}
    @Override public int getLocationUpdateIntervalSeconds(){return settingPref.getLocationUpdateIntervalSeconds();}

    @Override
    public int getCustomerLocationInterval() {
        return settingPref.getCustomerLocationInterval();
    }

    @Override
    public int getLocationMinDistance() {
        return settingPref.getLocationMinDistance();
    }

    @Override public int getSyncIntervalSeconds(){return settingPref.getSyncIntervalSeconds();}
    @Override public long getStopTrackingAfter(){return settingPref.getStopTrackingAfter();}
    @Override public boolean getIsInTrackingMode(){return settingPref.getIsInTrackingMode();}
    @Override public String getWiolonIp(){return settingPref.getWiolonIp();}
    @Override public int getWiolonPort(){return settingPref.getWiolonPort();}
    @Override public long getLocationMaxWaitTime(){return settingPref.getLocationMaxWaitTime();}
    @Override public String getStartTrackingTime(){return settingPref.getStartTrackingTime();}
    @Override public String getStopTrackingTime(){return settingPref.getStopTrackingTime();}
    @Override public boolean isEnableLocationTracking(){return settingPref.isEnableLocationTracking();}
    @Override public boolean isTokenCloudMessagingSave(){return settingPref.isTokenCloudMessagingSave();}
    @Override public boolean isNotSaleReasonChecked(){return settingPref.isCheckSaleNotReason();}

    @Override
    public boolean isCheckNotSendOrder() {
        return settingPref.isCheckNotSendOrder();
    }

    @Override public BranchEntity getBranchEntity(){return settingPref.getBranchEntity();}
    @Override public EmployeeInfoEntity getEmployeeInfoEntity(){return settingPref.getEmployeeInfoEntity();}
    @Override public void setIsInitialedApplication(boolean status) {settingPref.setIsInitialedApplication(status);}
    @Override public boolean isInitialedApplication() { return settingPref.getIsInitialedApplication();}

    @Override
    public int getChequeDurationDay() {
        return settingPref.getChequeDurationDay();
    }

    @Override
    public String getEndOfDailyVisitDesc() {
        return  settingPref.getLastCompletePathVisitDesc();
    }

    @Override
    public void setEndOfDailyWorkDesc(String desc) {
        settingPref.setLastCompletePathVisitDesc(desc);
    }

    @Override
    public long getCustomerPointIntervalSeconds() {
        return  settingPref.getCustomerPointIntervalSeconds();
    }

    @Override
    public int getAllowanceIssuePrintTime() {
        return settingPref.getAllowanceIssuePrintTime();
    }

    @Override
    public void setAllowanceIssuePrintTime(int times) {
        settingPref.setAllowanceIssuePrintTime(times);
    }

    @Override
    public void removePref(String prefKey) {
        settingPref.removePref(prefKey);
    }

    @Override
    public long getUnchangedOrdersNoInCardindeForEdit() {
        return settingPref.getUnchangedOrdersNoInCardindeForEdit();
    }

    @Override
    public void setUnchangedOrdersNoInCardindeForEdit(Long order) {
        settingPref.setUnchangedOrdersNoInCardindeForEdit(order);
    }

    @Override
    public void setInactiveCustomerChecked(boolean value){
        settingPref.setInactiveCustomerChecked(value);
    }
    @Override
    public boolean isInactiveCustomerChecked(){
        return settingPref.isInactiveCustomerChecked();
    }
    @Override
    public boolean isClassNameBCustomerChecked(){
        return settingPref.isClassNameBCustomerChecked();
    }
    @Override
    public void setCustomerClassNameBCheckbox(boolean value){
        settingPref.setClassNameBCustomerCheckboxStatus(value);
    }
}
