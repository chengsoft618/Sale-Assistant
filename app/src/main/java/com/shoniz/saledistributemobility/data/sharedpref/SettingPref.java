package com.shoniz.saledistributemobility.data.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.app.BranchEntity;
import com.shoniz.saledistributemobility.framework.Utility;
import com.shoniz.saledistributemobility.utility.PersianCalendar;
import com.shoniz.saledistributemobility.view.entity.EmployeeInfoEntity;

import java.util.List;


//TODO : add ISettingApi into this class
public class SettingPref implements ISettingPref {

    private Context context;
    private SharedPreferences preferences;
    public void clearAll(){
        preferences.edit().clear().apply();
    }

    public SettingPref(Context context){
        this.context = context;
        String DEFAULT_PREFERENCE_POSTFIX = "_preferences";
        preferences = context.getSharedPreferences(context.getPackageName() + DEFAULT_PREFERENCE_POSTFIX, Context.MODE_PRIVATE);
    }

    @Override
    public int getCurrentRoleId() {
        return Integer.parseInt(preferences.getString(context.getString(R.string.pref_key_role_id), "0"));
    }

    @Override
    public void setCurrentRoleId(int roleId) {
        preferences.edit().putString(context.getString(R.string.pref_key_role_id), ""+roleId).apply();
    }

    @Override
    public String getPreviousVersionName() {
        return   preferences.getString(context.getString(R.string.pref_key_version_name), "1.0.0.0") ;
    }

    @Override
    public void setCurrentVersionName(String versionName) {
        preferences.edit().putString(context.getString(R.string.pref_key_version_name),versionName).apply();
    }

    @Override
    public int getLocationUpdateIntervalSeconds() {
        return Integer.parseInt(preferences.getString(context.getString(R.string.pref_key_location_interval),""+ (1000 * 5 * 60)));
    }

    @Override
    public int getSyncIntervalSeconds() {
        return Integer.parseInt(preferences.getString(context.getString(R.string.pref_key_sync_interval),""+ (1000 * 30 * 60)));
    }

    @Override
    public String getLastDailyUpdateDate() {
        return preferences.getString(context.getString(R.string.pref_key_last_daily_update_date), PersianCalendar.getPersianDate());
    }

    @Override
    public boolean getShowVerificationOrder() {
        return preferences.getString(context.getString(R.string.pref_key_show_verification_order), "0").equals("1");
    }

    @Override
    public void setLastDailyUpdateDate() {
        preferences.edit().putString(context.getString(R.string.pref_key_last_daily_update_date), PersianCalendar.getPersianDate()).apply();
    }

    @Override
    public void setLocationUpdateIntervalSeconds(int interval) {
        preferences.edit().putString(context.getString(R.string.pref_key_location_interval), ""+interval).apply();
    }

    @Override
    public void setAllSetting(List<SettingEntity> settingEntities) {
        for (SettingEntity setting: settingEntities) {
            preferences.edit().putString(setting.key,setting.value).apply();
        }
    }

    @Override
    public long getTrackingUpdateIntervalSeconds() {
        return Long.parseLong(preferences.getString(context.getString(R.string.pref_key_tracking_interval),""+( 1000 * 60*60)));
    }

    @Override
    public long getStopTrackingAfter() {
        return Long.parseLong(preferences.getString(context.getString(R.string.pref_key_stop_tracking_after),""+( 1000 * 60*10)));
    }


    @Override
    public void setTrackingUpdateIntervalSeconds(int interval) {
        preferences.edit().putString(context.getString(R.string.pref_key_tracking_interval), ""+interval).apply();
    }

    @Override
    public boolean getIsInTrackingMode() {
        return preferences.getBoolean(context.getString(R.string.pref_key_tracking_mode), false);
    }

    @Override
    public void setIsInTrackingMode(boolean isInTracking) {
        preferences.edit().putBoolean(context.getString(R.string.pref_key_tracking_mode), isInTracking).apply();
    }

    @Override
    public String getWiolonIp() {
        return preferences.getString(context.getString(R.string.pref_key_wiolon_ip), "2.186.229.209");
    }

    @Override
    public int getWiolonPort() {
        return Integer.parseInt(preferences.getString(context.getString(R.string.pref_key_wiolon_port),""+ (20332)));

    }

    @Override
    public long getLocationMaxWaitTime() {
        return Long.parseLong(preferences.getString(context.getString(R.string.pref_key_location_max_wait_time),""+ (30*1000*60)));
    }

    @Override
    public String getStartTrackingTime() {
        return preferences.getString(context.getString(R.string.pref_key_start_tracking_time),"09:00:00");
    }

    @Override
    public String getStopTrackingTime() {
        return preferences.getString(context.getString(R.string.pref_key_stop_tracking_time),"02:00:00");
    }

    @Override
    public boolean isEnableLocationTracking() {
        return preferences.getString(context.getString(R.string.pref_key_enable_location_tracking),"0").equals("1");
    }

    @Override
    public boolean isCheckSaleNotReason() {
        return preferences.getString(context.getString(R.string.pref_key_is_check_sale_not_reason),"0").equals("1");
    }

    @Override
    public boolean isTokenCloudMessagingSave() {
        return preferences.getBoolean(context.getString(R.string.pref_key_is_token_cloud_messaging_save),false);
    }

    @Override
    public void setTokenCloudMessagingSave(boolean flag) {
        preferences.edit().putBoolean(context.getString(R.string.pref_key_is_token_cloud_messaging_save), flag).apply();
    }

    @Override
    public String getCloudToken() {
        return preferences.getString(context.getString(R.string.pref_key_cloud_token),"");
    }

    @Override
    public void setCloudToken(String token) {
        preferences.edit().putString(context.getString(R.string.pref_key_cloud_token), "").apply();
    }

    @Override
    public BranchEntity getBranchEntity() {
        BranchEntity branchEntity = new BranchEntity();
        branchEntity.BranchCode = preferences.getInt(context.getString(R.string.pref_key_branch_code), 0);
        branchEntity.BranchName = preferences.getString(context.getString(R.string.pref_key_branch_name), "");
        branchEntity.BranchPersianName = preferences.getString(context.getString(R.string.pref_key_branch_branchPersianName), "");
        branchEntity.LanIp = preferences.getString(context.getString(R.string.pref_key_branch_lanIp), "");
        branchEntity.WanIp1 = preferences.getString(context.getString(R.string.pref_key_branch_wanIp1), "");
        branchEntity.WanIp2 = preferences.getString(context.getString(R.string.pref_key_branch_wanIp2), "");
        branchEntity.ServicePort = preferences.getString(context.getString(R.string.pref_key_branch_service_port), "");
        branchEntity.ReportsPort = preferences.getString(context.getString(R.string.pref_key_branch_reports_port), "");
        return branchEntity.BranchCode == 0 ? null : branchEntity;
    }

    @Override
    public void setBranchEntity(BranchEntity branchEntity) {
        preferences.edit().putInt(context.getString(R.string.pref_key_branch_code), branchEntity.BranchCode).apply();
        preferences.edit().putString(context.getString(R.string.pref_key_branch_name), branchEntity.BranchName).apply();
        preferences.edit().putString(context.getString(R.string.pref_key_branch_branchPersianName), branchEntity.BranchPersianName).apply();
        preferences.edit().putString(context.getString(R.string.pref_key_branch_lanIp), branchEntity.LanIp).apply();
        preferences.edit().putString(context.getString(R.string.pref_key_branch_wanIp1), branchEntity.WanIp1).apply();
        preferences.edit().putString(context.getString(R.string.pref_key_branch_wanIp2), branchEntity.WanIp2).apply();
        preferences.edit().putString(context.getString(R.string.pref_key_branch_service_port), branchEntity.ServicePort).apply();
        preferences.edit().putString(context.getString(R.string.pref_key_branch_reports_port), branchEntity.ReportsPort).apply();
    }

    @Override
    public void switchUrlPriority() {
        String wanIp1 = preferences.getString(context.getString(R.string.pref_key_branch_wanIp1), "");
        String wanIp2 = preferences.getString(context.getString(R.string.pref_key_branch_wanIp2), "");

        preferences.edit().putString(context.getString(R.string.pref_key_branch_wanIp1), wanIp2).apply();
        preferences.edit().putString(context.getString(R.string.pref_key_branch_wanIp2), wanIp1).apply();
    }

    @Override
    public EmployeeInfoEntity getEmployeeInfoEntity() {
        EmployeeInfoEntity employeeInfoEntity = new EmployeeInfoEntity();
        employeeInfoEntity.EmployeeId = preferences.getInt(context.getString(R.string.pref_key_employee_id), 0);
        employeeInfoEntity.EmployeeName = preferences.getString(context.getString(R.string.pref_key_employee_name), "");
        employeeInfoEntity.Tel = preferences.getString(context.getString(R.string.pref_key_employee_tel), "");
        employeeInfoEntity.Photo = preferences.getString(context.getString(R.string.pref_key_employee_photo), "");

        return employeeInfoEntity;
    }

    @Override
    public void setEmployeeInfoEntity(EmployeeInfoEntity employeeInfoEntity) {
        preferences.edit().putInt(context.getString(R.string.pref_key_employee_id), employeeInfoEntity.EmployeeId).apply();
        preferences.edit().putString(context.getString(R.string.pref_key_employee_name), employeeInfoEntity.EmployeeName).apply();
        preferences.edit().putString(context.getString(R.string.pref_key_employee_tel), employeeInfoEntity.Tel).apply();
        preferences.edit().putString(context.getString(R.string.pref_key_employee_photo), employeeInfoEntity.Photo).apply();
    }

    @Override
    public void setIsInitialedApplication(boolean status) {
        preferences.edit().putBoolean(context.getString(R.string.pref_key_is_application_initialed), status).apply();
    }

    @Override
    public boolean getIsInitialedApplication() {
        return preferences.getBoolean(context.getString(R.string.pref_key_is_application_initialed), false);
    }

    @Override
    public int getLocationMinDistance() {
        return Integer.parseInt(preferences.getString(context.getString(R.string.pref_key_location_min_distance), "10"));
    }

    @Override
    public int getChequeDurationDay() {
        String str=preferences.getString(context.getString(R.string.pref_key_cheque_duration_day), "30");
        if(str==null || str.isEmpty() )
            str="30";
        return Integer.parseInt(str);
    }

    @Override
    public void setDownloadId(long id) {
        preferences.edit().putLong(context.getString(R.string.pref_key_download_id), id).apply();
    }

    @Override
    public long getDownloadId() {
        return  preferences.getLong(context.getString(R.string.pref_key_download_id), 0);
    }

    @Override
    public int getCustomerLocationInterval() {
        return Integer.parseInt(preferences.getString(context.getString(R.string.pref_key_customer_location_interval), "5000"));
    }

    @Override
    public boolean isCheckNotSendOrder() {
        return preferences.getString(context.getString(R.string.pref_key_is_check_not_send_order),"0").equals("1");
    }

    @Override
    public int getAllowanceIssuePrintTime() {
        return preferences.getInt(context.getString(R.string.pref_key_allowance_issue_print_time),0);
    }

    @Override
    public void setAllowanceIssuePrintTime(int times) {
        preferences.edit().putInt(context.getString(R.string.pref_key_allowance_issue_print_time), times).apply();
    }

    @Override
    public String getLastCompletePathVisitDesc() {
        return preferences.getString(context.getString(R.string.pref_key_last_Complete_path_visit_desc),"");
    }

    @Override
    public String getApkLastVersion() {
        return preferences.getString(context.getString(R.string.pref_key_apk_last_version),"");
    }

    @Override
    public void setLastCompletePathVisitDesc(String desc) {
        preferences.edit().putString(context.getString(R.string.pref_key_last_Complete_path_visit_desc), desc).apply();
    }

    @Override
    public long getCustomerPointIntervalSeconds() {
        return Integer.parseInt(preferences.getString(context.getString(R.string.pref_key_customer_location_interval), "3000"));
    }

    @Override
    public void removePref(String prefKey) {
        preferences.edit().remove(prefKey);
    }

    @Override
    public long getUnchangedOrdersNoInCardindeForEdit() {
        return  preferences.getLong(context.getString(R.string.pref_key_unchanged_order_no_in_cardinde_for_edit), 0L);
    }

    @Override
    public void setUnchangedOrdersNoInCardindeForEdit(long orderNo) {
        preferences.edit().putLong(context.getString(R.string.pref_key_unchanged_order_no_in_cardinde_for_edit), orderNo).apply();
    }
}
