package com.shoniz.saledistributemobility.data.model.log;

import com.shoniz.saledistributemobility.framework.serialization.ILogSerializable;

import java.io.Serializable;

public class LogEntity implements ILogSerializable, Serializable {
    public String stackTrace = "";
    public String methodName = "";
    public String className = "";
    public String systemMessage ="2222";
    public String userMessage ="";
    public String type = "";
    public String userName = "";
    public String userId = "";
    public String imei = "";
    public String date = "";
    public String version = "";
    public String batteryStatus = "";
    public String wifiStatus = "";
    public String internetStatus = "";
    public String usefulData = "";
    public String currentCulture = "";
    public String branchName = "";
    public String os = "";
}
