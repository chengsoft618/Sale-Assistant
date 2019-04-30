package com.shoniz.saledistributemobility.framework;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.shoniz.saledistributemobility.BuildConfig;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utility {

    private Context context;

    public Utility(Context context) {
        this.context = context;
    }

    public static <T> T getObject(String josn, TypeToken<T> typeToken) {
        Gson gson = new GsonBuilder().setLenient().create();
        return gson.fromJson(josn, typeToken.getType());
    }

    public static String getJson(Object obj) {
        Gson gson = new GsonBuilder().setLenient().create();
        return gson.toJson(obj);
    }

    public void toast(String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }

    public void toast(int resId) {
        Toast.makeText(context, context.getString(resId), Toast.LENGTH_LONG).show();
    }

    public int getVersionCode() {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    public String getVersionName() {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    ;

    public void installApk(String file) {

        if(android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            File newApk = new File(file);
            intent.setDataAndType(Uri.fromFile(newApk), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }else{
            Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", new File(file));
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        }

    }

    public boolean isRunningService(Class serviceClass) {

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void openWialonWebSite(Context context) {
        String url = "http://logo_factor.wialon.ir/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        context.startActivity(i);
    }

    public void setDefaultLanguage() {
        String languageToLoad = "en";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());

    }

    public static void writeFile(String mValue) {

        try {
            String filename = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/Shoniz.log";
            FileWriter fw = new FileWriter(filename, true);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",Locale.US);
            String dateTimeFormat = sdf.format(new Date());
            fw.write(dateTimeFormat+"\t:"+mValue + "\n\n");
            fw.close();
        } catch (IOException ioe) {
            ioe.getMessage();
        }

    }

    public boolean canWritePath(String downloadPath) {
        File file = new File(downloadPath);
        return file.isDirectory() && file.canWrite();
    }

    public String getFileNameFromUrl(String url) {
        int index = url.lastIndexOf("/");
        return  url.substring(index + 1);
    }

    public static int getAvailabilityColor(int availability) {
        switch (availability){
            case 2:
                return  R.color.md_green_200;


            case 1:
                return  R.color.md_yellow_200;

            case 0:
                return  R.color.md_red_300;


            default:
              return   R.color.md_grey_200;
        }
    }
}
