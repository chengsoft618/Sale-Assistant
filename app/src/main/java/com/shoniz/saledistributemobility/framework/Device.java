package com.shoniz.saledistributemobility.framework;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.inputmethodservice.InputMethodService;
import android.os.BatteryManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by ferdos.s on 6/1/2017.
 */

public class Device {


    Context context;


    public Device(Context context) {
        this.context = context;
    }

    @SuppressLint("MissingPermission")
    public long getIMEI() {
        TelephonyManager telephonyManager1 = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        long imei=0;
        if(!Build.FINGERPRINT.contains("generic"))
        {
            imei = Long.parseLong(telephonyManager1.getDeviceId());
        }
        // for test

        if (imei == 0 || imei == 354772085408801L || imei == 357947074151599L)
            imei = 353316093220302L; //  imei == 352222081272654L HAJI || imei == 770777707777072L 55555
            //imei =353316096482735L; //  imei == 352222081272654L HAJI || imei == 770777707777072L 55555
            //355695056697517L Motasadiye HAJI 850023 || 355695056698929 Bipanah 860003
           //352222081236113L Khodayii 356980050391914L : Soltani  //357812087595226L Alizadeh Tehran
        return imei;
    }

    @SuppressLint("MissingPermission")
    public long getActualIMEI() {
        TelephonyManager telephonyManager1 = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        long imei=0;
        if(!Build.FINGERPRINT.contains("generic"))
        {
            imei = Long.parseLong(telephonyManager1.getDeviceId());
        }
        return imei;
    }


    public static float getBatteryCurrentLevel(Context context){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        return level / (float)scale;
    }

    public float getBatteryCurrentLevel(){
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        return level / (float)scale;
    }

    public String getAndroidSdkVersion(){
        return "Android: " + Build.VERSION.RELEASE
                + " - SDK: " + Build.VERSION.SDK_INT
                + " - Model: " + Build.MODEL;
    }

    public static boolean isPackageInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent == null) {
            return false;
        }
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}
