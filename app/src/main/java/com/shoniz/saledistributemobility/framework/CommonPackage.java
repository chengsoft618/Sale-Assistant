package com.shoniz.saledistributemobility.framework;

import android.content.Context;

import com.shoniz.saledistributemobility.data.sharedpref.ISettingPref;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;

public class CommonPackage {
    protected Device device;
    Utility utility;
    Context context;
    ISettingPref settingPref;

    public CommonPackage(Device device,  Utility utility, Context context, ISettingPref settingPref) {
        this.device = device;
        this.utility = utility;
        this.context = context;
        this.settingPref = settingPref;
    }

    public Device getDevice() {
        return device;
    }

    public Utility getUtility() {
        return utility;
    }

    public Context getContext() {
        return context;
    }

    public ISettingPref getSettingPref() {
        return settingPref;
    }

}
