package com.shoniz.saledistributemobility.view.ordering.detail.printissue;

import android.arch.lifecycle.MutableLiveData;
import android.bluetooth.BluetoothDevice;
import android.graphics.Bitmap;

import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.framework.printer.BluetoothPrinter;
import com.shoniz.saledistributemobility.framework.printer.PortablePrinter;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;
import com.shoniz.saledistributemobility.view.base.viewmodel.BaseViewModel;

import org.json.JSONException;

import javax.inject.Inject;

public class PrintIssueViewModel extends BaseViewModel<IPrintIssueNavigator> {

    @Inject
    CommonPackage commonPackage;
    @Inject
    BluetoothPrinter bluetoothPrinter;

    String printJson = "";

    PortablePrinter portablePrinter;
    BluetoothDevice selectedDevice;

    public MutableLiveData<Bitmap> issueBitmap = new MutableLiveData<>();

    @Inject
    public PrintIssueViewModel() {
    }

    public void init(String issueData) {
        printJson = issueData;
        Bitmap bitmap = null;
        try {
            bitmap = IssueBitmapFactory.createCustomerBitmap(commonPackage.getContext(), printJson);
            issueBitmap.setValue(bitmap);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        issueBitmap.setValue(bitmap);
//        bluetoothPrinter.registerPrinter(new HprtPrinter(commonPackage.getContext()));
//        bluetoothPrinter.registerPrinter(new BtxPrinter(commonPackage.getContext()));

    }


    public void onBtnCustomerPrintClick() {
        try {
            issueBitmap.setValue(IssueBitmapFactory.createCustomerBitmap(commonPackage.getContext(), printJson));
            printIssue();
        } catch (JSONException e) {
            getNavigator().handleError(new UncaughtException(commonPackage, e));
        }

    }

    public void onBtnCompanyPrintClick() {
        try {
            issueBitmap.setValue(IssueBitmapFactory.createCompanyBitmap(commonPackage.getContext(), printJson));
            printIssue();
        } catch (JSONException e) {
            getNavigator().handleError(new UncaughtException(commonPackage, e));
        }
    }

    private void printIssue() {
        CommonAsyncTask.RunnableDo<Object, AsyncResult<Object>> runnableDo = (param, onProgress) -> {
            AsyncResult<Object> asyncResult = new AsyncResult<Object>();
            try {
                PortablePrinter printer = bluetoothPrinter.getOnlinePrinter();
                printer.print(issueBitmap.getValue());

            } catch (BaseException e) {
                asyncResult.exception = e;
            }
            return asyncResult;
        };
        CommonAsyncTask.RunnablePost<AsyncResult<Object>> postRunnable = result -> {

            if (result.hasError()) {
                getNavigator().handleError(result.exception);
            }
        };

        asyncCall(runnableDo, postRunnable, 0);
    }


//    private PortablePrinter printerEvent = new PortablePrinter.PrinterEvent() {
//        @Override
//        public void onConnected() {
//            printIssue();
//        }
//    };

//
//    String printJson = "{\n" +
//            "  \"BranchName\": \"دفتر پخش تبريز\",\n" +
//            "  \"Id\": 36937,\n" +
//            "  \"TotalPrice\": \"2,018,000\",\n" +
//            "  \"Weight\": \"7.00\",\n" +
//            "  \"CartonCount\": \"0\",\n" +
//            "  \"PacketCount\": \"11\",\n" +
//            "  \"TotalDiscount\": \"0\",\n" +
//            "  \"TotalAdditions\": \"0\",\n" +
//            "  \"PayableDiscription\": \"مبلغ قابل پرداخت \",\n" +
//            "  \"Payable\": \"2,018,000\",\n" +
//            "  \"Detail\": [\n" +
//            "    {\n" +
//            "      \"Row\": 1,\n" +
//            "      \"QTY\": 1,\n" +
//            "      \"Price\": 330000,\n" +
//            "      \"SumPrice\": 330000,\n" +
//            "      \"Unit\": \"ب\",\n" +
//            "      \"Reward\": \" \",\n" +
//            "      \"ProductName\": \"شکلات شونيز تلخ 100\"\n" +
//            "    },\n" +
//            "    {\n" +
//            "      \"Row\": 2,\n" +
//            "      \"QTY\": 3,\n" +
//            "      \"Price\": 210000,\n" +
//            "      \"SumPrice\": 630000,\n" +
//            "      \"Unit\": \"ب\",\n" +
//            "      \"Reward\": \" \",\n" +
//            "      \"ProductName\": \"بايکيت فندقي بزرگ قرمز\"\n" +
//            "    },\n" +
//            "    {\n" +
//            "      \"Row\": 3,\n" +
//            "      \"QTY\": 3,\n" +
//            "      \"Price\": 220000,\n" +
//            "      \"SumPrice\": 660000,\n" +
//            "      \"Unit\": \"ب\",\n" +
//            "      \"Reward\": \" \",\n" +
//            "      \"ProductName\": \"بايکيت تلخ بزرگ\"\n" +
//            "    },\n" +
//            "    {\n" +
//            "      \"Row\": 4,\n" +
//            "      \"QTY\": 4,\n" +
//            "      \"Price\": 99500,\n" +
//            "      \"SumPrice\": 398000,\n" +
//            "      \"Unit\": \"ب\",\n" +
//            "      \"Reward\": \" \",\n" +
//            "      \"ProductName\": \"اترنو\"\n" +
//            "    }\n" +
//            "  ],\n" +
//            "  \"Discount\": [\n" +
//            "    {\n" +
//            "      \"Row\": 1,\n" +
//            "      \"DiscountName\": \"\",\n" +
//            "      \"SumPrice\": \"\"\n" +
//            "    }\n" +
//            "  ],\n" +
//            "  \"Additions\": [],\n" +
//            "  \"Slogan\": \"صادر کننده ممتاز ملي در سال 1397 براي هفتمين سال متوالي\",\n" +
//            "  \"CustomerId\": 2593,\n" +
//            "  \"CustomerName\": \"سوپر عرفان\",\n" +
//            "  \"CustomerMobile\": \"0\",\n" +
//            "  \"VisitorName\": \"رضا عادل زاده\",\n" +
//            "  \"VisitorMobile\": \"09143128052\",\n" +
//            "  \"PayableLetters\": \"دو ميليون و هيجده هزار ريال \",\n" +
//            "  \"date\": \"1397/08/20\"\n" +
//            "}";


}
