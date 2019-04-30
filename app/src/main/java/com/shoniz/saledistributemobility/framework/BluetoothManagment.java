package com.shoniz.saledistributemobility.framework;

import android.bluetooth.BluetoothAdapter;

public class BluetoothManagment {
    public static void turnOnBluetooth(){
        setBluetooth(true);
    }

    public static void turnOffBluetooth(){
        setBluetooth(false);
    }

    public static boolean isBluetoothEnabled(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter.isEnabled();
    }

    private static void setBluetooth(boolean enable){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean isEnabled = bluetoothAdapter.isEnabled();
        if (enable && !isEnabled) {
            bluetoothAdapter.enable();
        }
        else if(!enable && isEnabled) {
            bluetoothAdapter.disable();
        }
    }
}
