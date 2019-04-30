package com.shoniz.saledistributemobility.view.tracking;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.location.ILocationRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;
import com.shoniz.saledistributemobility.infrastructure.wialon.Wialon;
import com.shoniz.saledistributemobility.view.base.viewmodel.BaseViewModel;

import javax.inject.Inject;

public class TrackingViewModel extends BaseViewModel<ITrackingNavigator> {

    public String url = Wialon.Url;
    private CommonPackage commonPackage;
    private ILocationRepository locationRepository;

    @Inject
    public TrackingViewModel(CommonPackage commonPackage, ILocationRepository locationRepository) {
        this.commonPackage = commonPackage;
        this.locationRepository = locationRepository;
    }

    public void load() {
    }

    public void startExactTracking(int userId) {
        CommonAsyncTask.RunnableDo<Object, AsyncResult<String>> runnableDo = (param, onProgress) -> {
            AsyncResult<String> asyncResult = new AsyncResult<>();
            try {
                asyncResult.model=  locationRepository.startExactTracking(userId);
            } catch (InOutError inOutError) {
                asyncResult.exception =   inOutError;

            } catch (Exception e) {
                asyncResult.exception = new UncaughtException(commonPackage, e);
            }
            return asyncResult;
        };
        CommonAsyncTask.RunnablePost<AsyncResult<String>> postRunnable = result -> {
            if (result.hasError()) {
                getNavigator().handleError(result.exception);
            }else {
                getNavigator().showSnackBar(commonPackage.getContext().getString(R.string.tracking_stop_after,result.model));
            }
        };
        asyncCall(runnableDo, postRunnable, 1);

    }

    public void onStartExactTracking() {
        getNavigator().onStartExactTracking();
    }

    public void stopExactTracking(int userId) {
        CommonAsyncTask.RunnableDo<Object, AsyncResult> runnableDo = (param, onProgress) -> {
            AsyncResult asyncResult = new AsyncResult();

            try {
                locationRepository.stopExactTracking(userId);
            } catch (InOutError inOutError) {
                asyncResult.exception =   inOutError;

            } catch (Exception e) {
                asyncResult.exception = new UncaughtException(commonPackage, e);
            }

            return asyncResult;
        };
        CommonAsyncTask.RunnablePost<AsyncResult> postRunnable = result -> {
            if (result.hasError()) {
                getNavigator().handleError(result.exception);
            }
        };
        asyncCall(runnableDo, postRunnable, 1);
    }

    public void onStopExactTracking() {
        getNavigator().onStopExactTracking();
    }


}
