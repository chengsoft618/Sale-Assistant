package com.shoniz.saledistributemobility.view.entrance;

import android.Manifest;

public class AppPermissions {
    public static String[] GetPermissions() {
        return new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN
        };
    }
}
