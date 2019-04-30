package com.shoniz.saledistributemobility.framework.printer;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Bitmap;

import com.bxl.config.editor.BXLConfigLoader;

import java.nio.ByteBuffer;

import jpos.JposException;
import jpos.POSPrinter;
import jpos.POSPrinterConst;
import jpos.config.JposEntry;

public class BtxPrinter extends PortablePrinter {


    private POSPrinter posPrinter;

    public BtxPrinter(Context context, BluetoothDevice bluetoothDevice) {
        super(context, bluetoothDevice);

    }

    @Override
    public void print(Bitmap printBitmap) {
        int width=POSPrinterConst.PTR_BM_ASIS;
        int alignment=POSPrinterConst.PTR_BM_CENTER;

        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put((byte) POSPrinterConst.PTR_S_RECEIPT);
        buffer.put((byte) 50);  // brightness  buffer.put((byte) 0x00);   // dummy  buffer.put((byte) 0x00);   // dummy

        try {
            posPrinter.printBitmap(buffer.getInt(0), printBitmap, width, alignment);
            posPrinter.close();
        } catch (JposException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean connect() {
        boolean flag=true;
        try {
            if (posPrinter == null) {
                posPrinter = new POSPrinter(context);
                BXLConfigLoader bxlConfigLoader = new BXLConfigLoader(context);

                try {
                    bxlConfigLoader.openFile();
                } catch (Exception e) {
                    flag=false;
                    e.printStackTrace();
                    bxlConfigLoader.newFile();
                }
                try {
                    try {
                        for (Object entry : bxlConfigLoader.getEntries()) {
                            JposEntry jposEntry = (JposEntry) entry;
                            bxlConfigLoader.removeEntry(jposEntry.getLogicalName());
                        }
                    } catch (Exception e) {
                        flag=false;
                        e.printStackTrace();
                    }

                    try {
                        bxlConfigLoader.addEntry(bluetoothDevice.getName(),
                                BXLConfigLoader.DEVICE_CATEGORY_POS_PRINTER,
                                bluetoothDevice.getName(),
                                BXLConfigLoader.DEVICE_BUS_BLUETOOTH,
                                MAC_ADDRESS);

                        bxlConfigLoader.saveFile();
                    } catch (Exception e) {
                        flag=false;
                        e.printStackTrace();
                    }
                    posPrinter.open(bluetoothDevice.getName());
                    posPrinter.getState();
                    posPrinter.claim(0);

                    posPrinter.setDeviceEnabled(true);
                } catch (NumberFormatException | JposException e) {
                    flag=false;
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            flag=false;
            e.printStackTrace();
        }

        if(!flag)
            posPrinter=null;
        return flag;

    }
}
