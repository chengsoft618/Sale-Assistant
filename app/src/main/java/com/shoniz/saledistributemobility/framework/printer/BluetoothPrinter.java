package com.shoniz.saledistributemobility.framework.printer;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;

import com.shoniz.saledistributemobility.framework.BluetoothManagment;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.bluetooth.BluetoothIsDisabledException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

public class BluetoothPrinter {

    List<PortablePrinter> portablePrinters = new ArrayList<>();
    Set<BluetoothDevice> pairedDevices;
    List<BluetoothDevice> pairedPrinterDevices;

    CommonPackage commonPackage;

    @Inject
    public BluetoothPrinter(CommonPackage commonPackage) {
        this.commonPackage = commonPackage;
    }

    public PortablePrinter getOnlinePrinter() throws NotFoundOnlinePrinterException, BluetoothIsDisabledException, NotFoundPairedPrinterException {
        checkBluetooth();
        setPairedPrintersList();
        List<PortablePrinter> portablePrinters =
                PortablePrintersFactory
                        .getRegisteredPrinters(
                                commonPackage.getContext(),
                                pairedPrinterDevices
                        );
        for (PortablePrinter printer : portablePrinters) {
            if (printer.connect())
                return printer;
        }
        throw new NotFoundOnlinePrinterException(commonPackage);
    }

    private void checkBluetooth() throws BluetoothIsDisabledException {
        if (!BluetoothManagment.isBluetoothEnabled()) {
            BluetoothManagment.turnOnBluetooth();
            throw new BluetoothIsDisabledException(commonPackage);
        }
    }

    private void setPairedPrintersList() throws NotFoundPairedPrinterException {
        pairedPrinterDevices = new ArrayList<>();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        pairedDevices = mBluetoothAdapter.getBondedDevices();

        for (BluetoothDevice bt : pairedDevices) {
                pairedPrinterDevices.add(bt);
        }

        if (pairedPrinterDevices.size() == 0) {
            throw new NotFoundPairedPrinterException(commonPackage);
        }
    }


//    private void checkPrint(final String strIsConnected)
//    {
//
//        new AsyncTask<Object, Object, Boolean>() {
//
//            @Override
//            protected void onPreExecute() {
//                    if (printAddress == null) {
//                        return;
//                    } else if (!printAddress.contains(":")) {
//                        return;
//                    } else if (printAddress.length() != 17) {
//                        return;
//                    }
//                }
//                super.onPreExecute();
//            }
//
//            @Override
//            protected Boolean doInBackground(Object... params) {
//                boolean flag = false;
//                try {
//
//
//                    if(printerName.equals(C.Constant.HPRT_TP801))
//                    {
//                        prt = new PRTAndroidPrint(PrintActivity.this,
//                                "Bluetooth");
//                        prt.InitPort();
//                        flag = prt.OpenPort(printAddress);
//
//                    }else
//                    {
//                        flag=initBixolon();
//                        // ConnectDeviceBixolon();
//                    }
//
//
//                } catch (Exception e) {
//                    Utility.toast(PrintActivity.this,e.getMessage(),
//                            Utility.ToastMode.Information);
//                }
//
//                return flag;
//            }
//
//            @Override
//            protected void onPostExecute(Boolean flag) {
//
//                if (!flag) {
//                    Utility.toast(PrintActivity.this,
//                            getString(R.string.error_printer),
//                            Utility.ToastMode.Error);
//
//                    txtTips.setText(getString(R.string.error_printer));
//
//                } else {
//                    Utility.toast(PrintActivity.this,
//                            getString(R.string.connected_printer),
//                            Utility.ToastMode.Information);
//
//                    txtTips.setText(getString(R.string.connected_printer));
//
//                }
//
//                prConnectPrinter.setVisibility(View.GONE);
//                super.onPostExecute(flag);
//            }
//        }.execute();
//
//    }
}
