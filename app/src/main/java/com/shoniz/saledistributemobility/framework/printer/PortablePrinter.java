package com.shoniz.saledistributemobility.framework.printer;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Bitmap;

public abstract class PortablePrinter {

    String MAC_ADDRESS;
    Context context;

    public PortablePrinter(Context context, BluetoothDevice bluetoothDevice) {
        this.MAC_ADDRESS = bluetoothDevice.getAddress();
        this.bluetoothDevice = bluetoothDevice;
        this.context = context;
    }

    public abstract void print(Bitmap printBitmap);
    public abstract boolean connect();
    protected BluetoothDevice bluetoothDevice;

    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof PortablePrinter))
            return false;
        return this.MAC_ADDRESS == ((PortablePrinter)obj).MAC_ADDRESS;
    }
}
