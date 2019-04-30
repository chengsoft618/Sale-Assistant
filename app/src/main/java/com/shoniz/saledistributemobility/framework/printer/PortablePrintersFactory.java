package com.shoniz.saledistributemobility.framework.printer;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaExtractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PortablePrintersFactory {

    public static List<PortablePrinter> getRegisteredPrinters(Context context, List<BluetoothDevice> pairedBluetoothDevices){
        List<PortablePrinter> registeredPrinters = new ArrayList<>();
        for(BluetoothDevice bt: pairedBluetoothDevices) {
            switch (bt.getName()){
                case "TP801": registeredPrinters.add(new HprtPrinter(context, bt));
                break;
                case "SPP-R400": registeredPrinters.add(new BtxPrinter(context, bt));
                break;
            }
        }
        return registeredPrinters;
    }
}
