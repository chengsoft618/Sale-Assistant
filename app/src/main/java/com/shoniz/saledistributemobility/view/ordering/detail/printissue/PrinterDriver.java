package com.shoniz.saledistributemobility.view.ordering.detail.printissue;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.framework.Utility;
import com.shoniz.saledistributemobility.view.base.progress.ProgressDialog;

import org.json.JSONException;

public class PrinterDriver {
//    String printerName;
//    public PrinterDriver() {
//
//        printerName = Utility.getPreference(this, C.field.PrinterName, "");
//        printAddress = Utility.getPreference(this, C.field.PrintAddress, "");
//        if (b != null) {
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
//    }
//
//            private void printCustomer(Bitmap factorBitmap) throws JSONException {
//
//        AsyncTask<String, String, String> task=new AsyncTask<String, String, String>() {
//            ProgressDialog progress;
//            @Override
//            protected void onPreExecute() {
//                progress = ProgressDialog.show(PrintActivity.this, getString(R.string.please_wait),
//                        getString(R.string.print_factor_customer), true);
//                super.onPreExecute();
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//
//                if(printerName.equals(C.Constant.HPRT_TP801))
//                {
//                    if (PRT == null) {
//                        Utility.toast(PrintActivity.this,
//                                getString(R.string.no_printer_object),
//                                Utility.ToastMode.Information);
//
//
//                    }
//                    if (!PRT.IsOpen()) {
//                        Utility.toast(PrintActivity.this,
//                                getString(R.string.warming_connect),
//                                Utility.ToastMode.Information);
//
//
//
//                    }else
//                    {
//                        printFactorForCompanyHRPT(factor);
//                    }
//
//
//                }else
//                {
//                    printFactorForCustomerBIX(factor);
//                }
//                return null;
//            }
//            @Override
//            protected void onPostExecute(String result) {
//                progress.dismiss();
//                super.onPostExecute(result);
//            }
//
//        };
//        task.execute("");
//
//
//    }
//
//    private void printCompany() throws JSONException {
//        AsyncTask<String, String, String> task=new AsyncTask<String, String, String>() {
//            ProgressDialog progress;
//            @Override
//            protected void onPreExecute() {
//                progress = ProgressDialog.show(PrintActivity.this, getString(R.string.please_wait),
//                        getString(R.string.print_factor_company), true);
//                super.onPreExecute();
//            }
//
//            @Override
//            protected String doInBackground(String... params) {
//                try {
//                    factor = new IssueBitmapsMaker(
//                            PrintActivity.this,  printJson);
//                } catch (JSONException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                if(printerName.equals(C.Constant.HPRT_TP801))
//                {
//                    if (PRT == null) {
//                        Utility.toast(PrintActivity.this,
//                                getString(R.string.no_printer_object),
//                                Utility.ToastMode.Information);
//
//
//                    }
//                    if (!PRT.IsOpen()) {
//                        Utility.toast(PrintActivity.this,
//                                getString(R.string.warming_connect),
//                                Utility.ToastMode.Information);
//
//                    }else
//                    {
//                        printFactorForCompanyHRPT(factor);
//                    }
//
//
//                }else
//                {
//                    printFactorForCompanyBIX(factor);
//                }
//                return null;
//            }
//            @Override
//            protected void onPostExecute(String result) {
//                progress.dismiss();
//                super.onPostExecute(result);
//            }
//
//        };
//        task.execute("");
//
//    }
//
//    }

}
