package com.shoniz.saledistributemobility.framework.printer;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.graphics.Bitmap;

import PRTAndroidSDK.PRTAndroidPrint;

public class HprtPrinter extends PortablePrinter {

    PRTAndroidPrint prt;

    public HprtPrinter(Context context, BluetoothDevice bluetoothDevice) {
        super(context, bluetoothDevice);
    }

    @Override
    public void print(Bitmap printBitmap) {
        prt.PRTSetPagePrintModel();
        prt.PRTReset();
        prt.PRTPrintBitmap(printBitmap, 0);
        prt.PRTFeedLines(240);
        prt.PRTFeedLines(240);
        if (prt.PRTCapPaperCut()) {
            prt.PRTPaperCut(true); // true:half cut
        }
    }

    @Override
    public boolean connect() {
        prt = new PRTAndroidPrint(context,
                "Bluetooth");
        prt.InitPort();
        return prt.OpenPort(MAC_ADDRESS);
    }
}
