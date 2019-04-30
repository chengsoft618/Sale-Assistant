package com.shoniz.saledistributemobility.view.web;

import com.shoniz.saledistributemobility.data.api.ApiNetworkException;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;
import com.shoniz.saledistributemobility.utility.data.api.ApiConsts;
import com.shoniz.saledistributemobility.utility.device.Connection;
import com.shoniz.saledistributemobility.view.base.viewmodel.BaseViewModel;

import javax.inject.Inject;

public class WebSiteViewModel extends BaseViewModel<IWebSiteNavigator> {

    @Inject
    CommonPackage commonPackage;

    @Inject
    public WebSiteViewModel() {

    }

    public void loadReport() {
        CommonAsyncTask.RunnableDo<Object, AsyncResult<String>> runnableDo = (param, onProgress) -> {
            AsyncResult<String> asyncResult = new AsyncResult<String>();
            try {
                asyncResult.model = Connection.getReportUrl(commonPackage.getContext());
            } catch (Exception e) {
                e.getStackTrace();
                asyncResult.exception = new ApiNetworkException(commonPackage, e);
            }
            return asyncResult;
        };
        CommonAsyncTask.RunnablePost<AsyncResult<String>> postRunnable = result -> {

            if (!result.hasError() && result.model != null) {
                try {
                    String url = result.model + ApiConsts.ReportLogin + commonPackage.getDevice().getIMEI();
                    getNavigator().loadUrl(url);
                } catch (Exception e) {
                    getNavigator().handleError(new ApiNetworkException(commonPackage, e));
                }
            }
        };
        CommonAsyncTask commonAsyncTask = new CommonAsyncTask(null, runnableDo, postRunnable, null);
        commonAsyncTask.run();
    }

    public void loadChangeLogs() {
        CommonAsyncTask.RunnableDo<Object, AsyncResult<String>> runnableDo = (param, onProgress) -> {
            AsyncResult<String> asyncResult = new AsyncResult<String>();
            try {
                asyncResult.model = Connection.getReportUrl(commonPackage.getContext()) + ApiConsts.CHANGE_LOGS;
            } catch (Exception e) {
                e.getStackTrace();
                asyncResult.exception = new ApiNetworkException(commonPackage, e);
            }
            return asyncResult;
        };
        CommonAsyncTask.RunnablePost<AsyncResult<String>> postRunnable = result -> {

            if (!result.hasError() && result.model != null) {
                try {
                    String url = result.model;
                    //getNavigator().loadUrl(url);
                    getNavigator().loadUrl("www.google.com");
                } catch (Exception e) {
                    getNavigator().handleError(new ApiNetworkException(commonPackage, e));
                }
            }
        };
        CommonAsyncTask commonAsyncTask = new CommonAsyncTask(null, runnableDo, postRunnable, null);
        commonAsyncTask.run();
    }


}
