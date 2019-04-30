package com.shoniz.saledistributemobility.utility.data.pref;

import android.content.Context;

import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.PersianCalendar;

/**
 * Created by ferdos.s on 5/31/2017.
 */

public class AppPref {


    public static boolean isAppInit(Context context) {
        PreferenceManager pm = Common.getPref(context);
        return pm.get(AppModel.IS_INIT_APP, false);
    }


    public static void setAppInit(Context context) {
        PreferenceManager pm = Common.getPref(context);
        pm.set(AppModel.IS_INIT_APP, true);
    }

    public static boolean allowDailyUpdate(Context context) {
        PreferenceManager pm = Common.getPref(context);
        return pm.get(AppModel.LAST_DAILY_UPDATE_DATE, "").equals(PersianCalendar.getPersianDate());
    }

    public static int getChequeDuration(Context context) {
        PreferenceManager pm = Common.getPref(context);
        return pm.get(AppModel.CHEQUE_DURATION_DAY, 30);
    }


    public static void setDailyUpdate(Context context) {
        PreferenceManager pm = Common.getPref(context);
        pm.set(AppModel.LAST_DAILY_UPDATE_DATE, PersianCalendar.getPersianDate());
    }

    public static void setReasonSendAll(Context context,boolean flag) {
        PreferenceManager pm = Common.getPref(context);
        pm.set(AppModel.IS_SEND_REASON_ALL, flag);
    }
    public static boolean isReasonSendAll(Context context) {
        PreferenceManager pm = Common.getPref(context);
        return pm.get(AppModel.IS_SEND_REASON_ALL, false);
    }

    public static void setActiveCustomerChecked(Context context, boolean value) {
        PreferenceManager pm = Common.getPref(context);
        pm.set(AppModel.IS_ACTIVE_CUSTOMER_CHECKED, value);
    }

    public static boolean isActiveCustomerChecked(Context context) {
        PreferenceManager pm = Common.getPref(context);
        return pm.get(AppModel.IS_ACTIVE_CUSTOMER_CHECKED, true);
    }

    public static boolean isCustomerClassNameB(Context context) {
        PreferenceManager pm = Common.getPref(context);
        return pm.get(AppModel.CUSTOER_CLASS_NAMES, false);
    }

    public  static void setCustomerClassNameB(Context context, boolean value) {
        PreferenceManager pm = Common.getPref(context);
        pm.set(AppModel.CUSTOER_CLASS_NAMES, value);
    }

    public  static void removeAllSharedpref(Context context) {
        PreferenceManager pm = Common.getPref(context);
        pm.getSharedPreferences().edit().clear().apply();
    }
}
