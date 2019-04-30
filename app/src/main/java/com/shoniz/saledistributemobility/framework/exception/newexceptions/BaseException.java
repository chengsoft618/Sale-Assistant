package com.shoniz.saledistributemobility.framework.exception.newexceptions;

import com.google.gson.Gson;
import com.shoniz.saledistributemobility.data.model.log.LogEntity;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingPref;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.data.sharedpref.SettingPref;
import com.shoniz.saledistributemobility.data.sharedpref.SettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.log.ILoggable;
import com.shoniz.saledistributemobility.utility.PersianCalendar;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Stack;

public abstract class BaseException extends Exception implements ILoggable, Cloneable, Serializable {

    ISettingPref settingPref;
    protected CommonPackage commonPackage;

    public Stack<BaseException> exceptionStack = new Stack<>();
    public String userMessage = "";
    public String systemMessage = "";
    public String userTitle = "";
    public long imei = 0;
    public String branchName = "";
    public int employeeId = 0;
    public long date = 0;
    public int version = 0;

    public HashMap<String, String> usefulData = new HashMap<>();

    public BaseException(CommonPackage commonPackage) {
        this.commonPackage = commonPackage;
        //  this.branchEntity = branchEntity;
        exceptionStack.push(this);
        init();
    }

    public BaseException(CommonPackage commonPackage, BaseException parentException) {
        this.commonPackage = commonPackage;
        // this.branchEntity = branchEntity;
        if (parentException != null)
            exceptionStack = parentException.exceptionStack;
        exceptionStack.push(this);
        init();
    }

    void init() {
        imei = commonPackage.getDevice().getIMEI();
        // branchName =branchEntity.BranchName;
        employeeId = 0; //Todo emplyee id
        date = new Date().getTime();
        version = commonPackage.getUtility().getVersionCode();
    }

    public void attachException(BaseException targetException) {
        exceptionStack.addAll(targetException.exceptionStack);
    }

    public static BaseException attachException(BaseException sourceException, BaseException targetException) {
        if (sourceException == null)
            sourceException = targetException;
        else
            sourceException.exceptionStack.addAll(targetException.exceptionStack);
        return sourceException;
    }

    public LogEntity getLogEntity() {
        settingPref = new SettingPref(commonPackage.getContext());
        Throwable rootCause = getRootThrowable();

        LogEntity logEntity = new LogEntity();
        logEntity.batteryStatus = commonPackage.getDevice().getBatteryCurrentLevel() + "";
        logEntity.date = PersianCalendar.getPersianDateTime()  ;
        logEntity.imei = commonPackage.getDevice().getIMEI() + "";
        logEntity.currentCulture = Locale.getDefault().getDisplayLanguage();
        logEntity.stackTrace = new Gson().toJson(getStackTrace());
        logEntity.usefulData = new Gson().toJson(usefulData);
        logEntity.version = commonPackage.getUtility().getVersionName();
        logEntity.userName = settingPref.getEmployeeInfoEntity().EmployeeName;
        logEntity.userId = settingPref.getEmployeeInfoEntity().EmployeeId + "";
        logEntity.branchName = settingPref.getBranchEntity().BranchName;
        logEntity.userMessage = userMessage;
        logEntity.systemMessage =systemMessage;
        logEntity.className = rootCause.getStackTrace()[0].getClassName();
        logEntity.methodName = rootCause.getStackTrace()[0].getMethodName();
        logEntity.os = commonPackage.getDevice().getAndroidSdkVersion();

        return logEntity;
    }

    private Throwable getRootThrowable() {
        Throwable rootCause = this;
        while (rootCause.getCause() != null && rootCause.getCause() != rootCause)
            rootCause = rootCause.getCause();
        return rootCause;
    }
}

