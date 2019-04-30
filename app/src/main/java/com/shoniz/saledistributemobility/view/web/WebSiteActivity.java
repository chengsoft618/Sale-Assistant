package com.shoniz.saledistributemobility.view.web;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.api.download.IFileDownloader;
import com.shoniz.saledistributemobility.databinding.ActivityWebSiteBinding;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.view.base.BaseActivity;

import javax.inject.Inject;

public class WebSiteActivity extends BaseActivity<WebSiteViewModel, ActivityWebSiteBinding>
        implements IWebSiteNavigator {


    private static String ACTION_REPORT = "ACTION_REPORT";
    private static String ACTION_LOGS = "ACTION_LOGS";

    @Inject
    ViewModelProvider.Factory factory;

    @Inject
    CommonPackage commonPackage;

    @Inject
    IFileDownloader fileDownloader;

    public static void startReport(Context context) {
        Intent intent = new Intent(context, WebSiteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(ACTION_REPORT);
        context.startActivity(intent);
    }

    public static void startChangeLog(Context context) {
        Intent intent = new Intent(context, WebSiteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(ACTION_LOGS);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_site;
    }

    @Override
    public WebSiteViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(WebSiteViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public boolean isEnableLocation() {
        return true;
    }

    @Override
    public void onChangeLocation(Location location) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWebView();
        mViewModel.setNavigator(this);
        String action = getIntent().getAction();
        if (action != null && action.equals(ACTION_REPORT)) {
            mViewModel.loadReport();
        } else {
            mViewModel.loadChangeLogs();
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        mViewDataBinding.webViewReport.setSaveEnabled(true);
        mViewDataBinding.webViewReport.getSettings().setDomStorageEnabled(true);
        mViewDataBinding.webViewReport.getSettings().setJavaScriptEnabled(true);
        mViewDataBinding.webViewReport.getSettings().setAllowFileAccess(true);
        mViewDataBinding.webViewReport.getSettings()
                .setCacheMode(WebSettings.LOAD_DEFAULT);
        mViewDataBinding.webViewReport.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });


        mViewDataBinding.webViewReport.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
//                String str = consoleMessage.message() + " -- From line "
//                        + consoleMessage.lineNumber() + " of "
//                        + consoleMessage.sourceId();
//                Common.toast(WebSiteActivity.this, str);
                return super.onConsoleMessage(consoleMessage);
            }
        });

        mViewDataBinding.webViewReport.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {


                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

//                try {
//                    fileDownloader.download(
//                            url,
//                            fileName,
//                            commonPackage.getContext().getString(R.string.common_downloading_apk),
//                            commonPackage.getContext().getString(R.string.common_mime_type));
//                } catch (InOutError inOutError) {
//                    handleError(inOutError);
//                }

            }
        });
    }

    @Override
    public void loadUrl(String url) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            finish();
            return;
        }
        mViewDataBinding.webViewReport.loadUrl(url);

    }
}
