package com.shoniz.saledistributemobility.utility.device;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.shoniz.saledistributemobility.data.model.app.BranchEntity;
import com.shoniz.saledistributemobility.data.sharedpref.SettingPref;
import com.shoniz.saledistributemobility.data.sharedpref.SettingRepository;
import com.shoniz.saledistributemobility.framework.exception.ConnectionException;
import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.utility.data.api.ApiConsts;
import com.shoniz.saledistributemobility.utility.Enums;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;

/**
 * Created by ferdos.s on 5/31/2017.
 */


public class Connection {

    public static String getShonizApiUrl(Context context) throws ConnectionException {
        //return ApiConsts.OFFICE_URL_BASE_SITE;

        Boolean testConnection;
        HandleException handleException=new HandleException(context);
        try {
            String url = ApiConsts.CENTRAL_LOCAL_URL_BASE_SITE;
            //TODO : Change ConnectionType.mobile to wifi
            testConnection = checkConnection(context, url+ApiConsts.IsOnline, Enums.ConnectionType.mobile);
            if (testConnection) return url;
        } catch (Exception e) {
            handleException.AddException(e);
        }

        try {
            String url = ApiConsts.CENTRAL_EXTERNAL_URL_BASE_SITE;
            testConnection = checkConnection(context, url+ApiConsts.IsOnline,
                    Enums.ConnectionType.mobile);
            if (testConnection) return url;
        } catch (Exception e) {
            handleException.AddException(e);
        }

        try {
            String url = ApiConsts.CENTRAL_DEVELOP_URL_BASE_SITE;
            testConnection = checkConnection(context, url+ApiConsts.IsOnline,
                    Enums.ConnectionType.mobile);
            if (testConnection) return url;
        } catch (Exception e) {
            handleException.AddException(e);
        }
        throw new ConnectionException(context,handleException);
    }

    public static String getApiUrl(Context context) throws ConnectionException {
        //return ApiConsts.OFFICE_URL_BASE_SITE;
        BranchEntity branchEntity= new SettingPref(context).getBranchEntity();
        Boolean testConnection;
        HandleException handleException=new HandleException(context);
        try {
            String url = MessageFormat.format("http://{0}:{1}/{2}",
                    branchEntity.LanIp,
                    branchEntity.ServicePort,
                    ApiConsts.API_PRE_ROUTE);
            //TODO : Change ConnectionType.mobile to wifi
            testConnection = checkConnection(context, url+ApiConsts.IsOnline, Enums.ConnectionType.mobile);
            if (testConnection) return url;
        } catch (Exception e) {
            handleException.AddException(e);
        }

        try {
            String url = MessageFormat.format("http://{0}:{1}/{2}",
                    branchEntity.WanIp1,
                    branchEntity.ServicePort,
                    ApiConsts.API_PRE_ROUTE);
            testConnection = checkConnection(context, url+ApiConsts.IsOnline,
                    Enums.ConnectionType.mobile);
            if (testConnection) return url;
        } catch (Exception e) {
            handleException.AddException(e);
        }

        try {
            String url = MessageFormat.format("http://{0}:{1}/{2}",
                    branchEntity.WanIp2,
                    branchEntity.ServicePort,
                    ApiConsts.API_PRE_ROUTE);
            testConnection = checkConnection(context, url+ApiConsts.IsOnline,
                    Enums.ConnectionType.mobile);
            if (testConnection) return url;
        } catch (Exception e) {
            handleException.AddException(e);
        }
        throw new ConnectionException(context,handleException);
    }

    public static String getReportUrl(Context context) throws ConnectionException {
        //return ApiConsts.OFFICE_URL_BASE_SITE;
        BranchEntity branchEntity= new SettingPref(context).getBranchEntity();
        Boolean testConnection;
        HandleException handleException=new HandleException(context);
        try {
            String url = MessageFormat.format("http://{0}:{1}/{2}",
                    branchEntity.LanIp,
                    branchEntity.ReportsPort,
                    "");
            //TODO : Change ConnectionType.mobile to wifi
            testConnection = checkConnection(context, url, Enums.ConnectionType.mobile);
            if (testConnection) return url;
        } catch (Exception e) {
            handleException.AddException(e);
        }

        try {
            String url = MessageFormat.format("http://{0}:{1}/{2}",
                    branchEntity.WanIp1,
                    branchEntity.ReportsPort,
                    "");
            testConnection = checkConnection(context, url,
                    Enums.ConnectionType.mobile);
            if (testConnection) return url;
        } catch (Exception e) {
            handleException.AddException(e);
        }

        try {
            String url = MessageFormat.format("http://{0}:{1}/{2}",
                    branchEntity.WanIp2,
                    branchEntity.ReportsPort,
                   "");
            testConnection = checkConnection(context, url,
                    Enums.ConnectionType.mobile);
            if (testConnection) return url;
        } catch (Exception e) {
            handleException.AddException(e);
        }
        throw new ConnectionException(context,handleException);
    }

    private static boolean checkConnection(Context context, String apiUrl, Enums.ConnectionType connectionType) throws IOException {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager
                .getNetworkInfo(connectionType.ordinal());
        if(!netInfo.isConnected()){
            netInfo = connectivityManager
                    .getNetworkInfo(Enums.ConnectionType.wifi.ordinal());
        }
        if (netInfo != null && netInfo.isConnected()) {
            URL url = new URL(apiUrl);

            HttpURLConnection urlC = (HttpURLConnection) url
                    .openConnection();

            //urlC.setRequestProperty("Connection", "close");
            urlC.setConnectTimeout(1000 * 1);
            urlC.connect();

            return urlC.getResponseCode() == 200;
        }
        return false;
    }


    public static boolean checkInternetConnection() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) SharedApp_RR.getContext()
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = connectivityManager
//                .getNetworkInfo(connectivityManager.TYPE_WIFI);
//
//        if (netInfo != null && netInfo.isConnected())
//            //Wifi is open

        String wifi = "http://www.logo_factor.com";

        URL url = null;
        try {
            url = new URL(wifi);


            HttpURLConnection urlC = (HttpURLConnection) url
                    .openConnection();

            urlC.setRequestProperty("Connection", "close");
            urlC.setConnectTimeout(1000 * 5);
            urlC.connect();

            return urlC.getResponseCode() == 200;
        } catch (IOException e) {
            return false;
        }
    }


}
