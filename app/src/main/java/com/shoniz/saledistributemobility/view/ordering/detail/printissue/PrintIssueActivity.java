package com.shoniz.saledistributemobility.view.ordering.detail.printissue;


import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.databinding.ActivityPrintIssueBinding;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.view.base.BaseActivity;

import org.json.JSONException;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

public class PrintIssueActivity extends BaseActivity<PrintIssueViewModel, ActivityPrintIssueBinding>
implements IPrintIssueNavigator{

    public static String PrintIssueJsonBundleName = "PrintIssueJsonBundleName";

    @Inject
    ViewModelProvider.Factory factory;
    @Inject
    CommonPackage commonPackage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_print_issue;
    }

    @Override
    public PrintIssueViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(PrintIssueViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public boolean isEnableLocation() {
        return false;
    }

    @Override
    public void onChangeLocation(Location location) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel.setNavigator(this);
        mViewModel.init(getIntent().getExtras().getString(PrintIssueActivity.PrintIssueJsonBundleName));
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mReceiver);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        // Bluetooth has been turned off;
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        // Bluetooth is turning off;
                        break;
                    case BluetoothAdapter.STATE_ON:
                        // Bluetooth has been on
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        // Bluetooth is turning on
                        break;
                }
            }
        }
    };



    //    private Button btnBT = null;
//
//    private Button btnPrintCompany = null;
//    private Button btnPrintCustomer = null;
//    private TextView txtTips = null;
//
//    IssueBitmapsMaker factor;
//    // PrintStock stock;
//
//    public enum PrintType {
//        Factor, Stock
//    }
//    private BluetoothAdapter mBtAdapter;
//    private PRTAndroidPrint PRT = null;
//    private String printAddress = "";
//    private String printerName = "";
//    ProgressBar prConnectPrinter;
//    private String printJson = "";
//    private PrintType printType;
//    private POSPrinter posPrinter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_print_factor);
//        Bundle b = getIntent().getExtras();
//        btnPrintCustomer = (Button) findViewById(R.id.btnPrintCustomer);
//        btnPrintCompany = (Button) findViewById(R.id.btnPrintCompany);
//        printerName =Utility.getPreference(this,C.field.PrinterName,"");
//        printAddress=Utility.getPreference(this,C.field.PrintAddress,"");
//        if (b != null)
//        {
//            printJson = b.getString(C.field.Extra);
//            printType = PrintType.values()[b.getInt(C.field.Type)];
//            switch (printType) {
//                case Factor:
//                    break;
//                case Stock:
//                    btnPrintCustomer.setText(" چاپ موجودی");
//                    btnPrintCompany.setVisibility(View.GONE);
//            }
//        }
//        prConnectPrinter = (ProgressBar) findViewById(R.id.prConnectPrinter);
//        btnBT = (Button) findViewById(R.id.btnBT);
//
//        txtTips = (TextView) findViewById(R.id.txtTips);
//
//        // Add Printer List
//
//        btnBT.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                if (PRT != null) {
//                    PRT.CloseProt();
//                }
//
//                Intent serverIntent = new Intent(PrintActivity.this,
//                        DeviceListActivity.class);
//                serverIntent.putExtras(getIntent().getExtras());
//                startActivity(serverIntent);
//                finish();
//
//                return;
//            }
//        });
//
//        btnPrintCustomer.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                try {
//
//                    switch (printType) {
//                        case Factor:
//                            printCustomer();
//
//
//                            break;
//                        case Stock:
///*                            stock = new PrintStock(
//                                    PrintActivity.this, PRT, printJson);
//                            stock.Print();*/
//                            break;
//
//                        default:
//                            break;
//                    }
//
//                } catch (Exception e) {
//                    Utility.toast(PrintActivity.this,
//                            getString(R.string.Error) + e.getMessage(),
//                            Utility.ToastMode.Information);
//
//                }
//            }
//
//
//        });
//        btnPrintCompany.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                try {
//                    printCompany();
//                } catch (Exception e) {
//                    Utility.toast(PrintActivity.this,
//                            getString(R.string.Error) + e.getMessage(),
//                            Utility.ToastMode.Information);
//                }
//            }
//        });
//
//        try {
//            WebView wvPrint = (WebView) findViewById(R.id.wvPrint);
//
//            String html = "<html><body><div style=\"text-align: center\"><img align=\"center\" src='{IMAGE_URL}' /></div></body></html>";
//            Bitmap bitmap = null;
//
//            wvPrint.postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    EnableBluetooth();
//
//                }
//            }, 500);
//            // Convert bitmap to Base64 encoded image for web
//
//            switch (printType) {
//                case Factor:
//                    factor = new IssueBitmapsMaker(
//                            PrintActivity.this, printJson);
//                    bitmap = factor.GetBitmap();
//
//                    break;
//                case Stock:
//                   /* stock = new PrintStock(
//                            PrintActivity.this, PRT, printJson);
//                    bitmap = stock.GetBitmap();*/
//
//                    break;
//
//                default:
//                    break;
//            }
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100,
//                    byteArrayOutputStream);
//            byte[] byteArray = byteArrayOutputStream.toByteArray();
//            String imgageBase64 = Base64.encodeToString(byteArray,
//                    Base64.DEFAULT);
//            String image = "data:image/png;base64," + imgageBase64;
//
//            html = html.replace("{IMAGE_URL}", image);
//            wvPrint.loadDataWithBaseURL("file:///android_asset/", html,
//                    "text/html", "utf-8", "");
//
//
//        } catch (Exception e) {
//            Utility.toast(PrintActivity.this,
//                    getString(R.string.Error) + e.getMessage(),
//                    Utility.ToastMode.Information);
//        }
//
//    }
//
///*    boolean ConnectDeviceBixolon() throws IOException
//    {
//        try{
//            UUID uuid= UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
//            mmSocket = mmDevice
//                    .createRfcommSocketToServiceRecord(uuid);
//            mmSocket.connect();
//            mmOutStream=mmSocket.getOutputStream();
//            return true;
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            return false;
//        }
//
//    }*/
//
//

//
//    // call back by scan bluetooth printer
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        try {
//            switch (resultCode) {
//                case 10:
//                    posPrinter=null;
//                    String strIsConnected = data.getExtras().getString(
//                            "is_connected");
//                    prConnectPrinter.setVisibility(View.VISIBLE);
//                    EnableBluetooth();
//                    prConnectPrinter.setVisibility(View.GONE);
//            }
//        } catch (Exception e) {
//            Utility.toast(PrintActivity.this,
//                    getString(R.string.Error) + e.getMessage(),
//                    Utility.ToastMode.Information);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//

//
//
//
//    // EnableBluetooth
//    private boolean EnableBluetooth() {
//        boolean bRet = false;
//        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (mBtAdapter != null) {
//            if (mBtAdapter.isEnabled())
//            {
//                checkPrint("OK");
//                return true;
//            }
//
//            mBtAdapter.enable();
//            try {
//                Thread.sleep(1500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            if (mBtAdapter.isEnabled()) {
//                checkPrint("OK");
//                bRet = true;
//
//            }
//        } else {
//
//        }
//        return bRet;
//    }
//
//    @Override
//    protected void onDestroy() {
//        try {
//            PRT.ClosePort();
//            PRT.PRTCloseConnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            posPrinter.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//
//
//        try {
//            mBtAdapter.disable();
//
//            mBtAdapter.cancelDiscovery();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        super.onDestroy();
//    }
//
//    private boolean initBixolon() {
//        boolean flag=true;
//        try {
//            if (posPrinter == null) {
//                posPrinter = new POSPrinter(this);
//                BXLConfigLoader bxlConfigLoader = new BXLConfigLoader(this);
//
//                try {
//                    bxlConfigLoader.openFile();
//                } catch (Exception e) {
//                    flag=false;
//                    e.printStackTrace();
//                    bxlConfigLoader.newFile();
//                }
//                try {
//                    try {
//                        for (Object entry : bxlConfigLoader.getEntries()) {
//                            JposEntry jposEntry = (JposEntry) entry;
//                            bxlConfigLoader.removeEntry(jposEntry.getLogicalName());
//                        }
//                    } catch (Exception e) {
//                        flag=false;
//                        e.printStackTrace();
//                    }
//
//                    try {
//                        bxlConfigLoader.addEntry(printerName, BXLConfigLoader.DEVICE_CATEGORY_POS_PRINTER, printerName, BXLConfigLoader.DEVICE_BUS_BLUETOOTH, printAddress);
//
//                        bxlConfigLoader.saveFile();
//                    } catch (Exception e) {
//                        flag=false;
//                        e.printStackTrace();
//                    }
//                    posPrinter.open(printerName);
//                    posPrinter.getState();
//                    posPrinter.claim(0);
//
//                    posPrinter.setDeviceEnabled(true);
//                } catch (NumberFormatException | JposException e) {
//                    flag=false;
//                    e.printStackTrace();
//                }
//            }
//        } catch (Exception e) {
//            flag=false;
//            e.printStackTrace();
//        }
//        if(!flag)
//            posPrinter=null;
//        return flag;
//    }
//
//    private void printFactorForCompanyBIX(IssueBitmapsMaker prtFactor)
//    {
//        try
//        {
//            int width=POSPrinterConst.PTR_BM_ASIS;
//            int alignment=POSPrinterConst.PTR_BM_CENTER;
//            ByteBuffer buffer = ByteBuffer.allocate(4);
//            buffer.put((byte) POSPrinterConst.PTR_S_RECEIPT);
//            buffer.put((byte) 50);  // brightness  buffer.put((byte) 0x00);   // dummy  buffer.put((byte) 0x00);   // dummy
//            Bitmap mainFactor = prtFactor.GetBitmap();
//            Bitmap bCustomerFactor = prtFactor.printCustomerVersion();
//            Bitmap bCustomerSign = prtFactor.printSignCustomer();
//            Bitmap tFactor = prtFactor.combineImages(bCustomerFactor, mainFactor,bCustomerSign);
//            posPrinter.printBitmap(buffer.getInt(0), tFactor, width, alignment);
//        }
//        catch (JposException e)
//        {
//            e.printStackTrace();
//            Utility.toast(this, e.getMessage());
//        }
//    }
//
//    private void printFactorForCompanyHRPT(IssueBitmapsMaker prtFactor)
//    {
//        try
//        {
//
//            Bitmap mainFactor = prtFactor.GetBitmap();
//            Bitmap bCustomerFactor = prtFactor.printCustomerVersion();
//            Bitmap bCustomerSign = prtFactor.printSignCustomer();
//            Bitmap tFactor = prtFactor.combineImages(bCustomerFactor, mainFactor,bCustomerSign);
//            PRT.PRTSetPagePrintModel();
//            PRT.PRTReset();
//            PRT.PRTPrintBitmap(tFactor, 0);
//            PRT.PRTFeedLines(240);
//            PRT.PRTFeedLines(240);
//
//            if (PRT.PRTCapPaperCut()) {
//                PRT.PRTPaperCut(true); // true:half cut
//
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            Utility.toast(this, e.getMessage());
//        }
//    }
//
//    private void printFactorForCustomerHRPT(IssueBitmapsMaker prtFactor)
//    {
//        try
//        {
//
//            Bitmap mainFactor = prtFactor.GetBitmap();
//            Bitmap bCustomerFactor = prtFactor.printCompanyVersion();
//            Bitmap tFactor = prtFactor.combineImages(bCustomerFactor, mainFactor);
//            PRT.PRTSetPagePrintModel();
//            PRT.PRTReset();
//            PRT.PRTPrintBitmap(tFactor, 0);
//            PRT.PRTFeedLines(240);
//            PRT.PRTFeedLines(240);
//            if (PRT.PRTCapPaperCut()) {
//                PRT.PRTPaperCut(true); // true:half cut
//
//            }
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            Utility.toast(this, e.getMessage());
//        }
//    }
//    private void printFactorForCustomerBIX(IssueBitmapsMaker prtFactor)
//    {
//        try
//        {
//
//            int width=POSPrinterConst.PTR_BM_ASIS;
//            int alignment=POSPrinterConst.PTR_BM_CENTER;
//
//            ByteBuffer buffer = ByteBuffer.allocate(4);
//            buffer.put((byte) POSPrinterConst.PTR_S_RECEIPT);
//            buffer.put((byte) 50);  // brightness  buffer.put((byte) 0x00);   // dummy  buffer.put((byte) 0x00);   // dummy
//            Bitmap mainFactor = prtFactor.GetBitmap();
//            Bitmap bCustomerFactor = prtFactor.printCompanyVersion();
//            Bitmap tFactor = prtFactor.combineImages(bCustomerFactor, mainFactor);
//            posPrinter.printBitmap(buffer.getInt(0), tFactor, width, alignment);
//        }
//        catch (JposException e)
//        {
//            e.printStackTrace();
//            Utility.toast(this, e.getMessage());
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }



}
