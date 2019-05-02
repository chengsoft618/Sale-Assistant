package com.shoniz.saledistributemobility.view.branch;

import android.arch.lifecycle.MutableLiveData;
import android.view.View;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.framework.service.update.IAppUpdater;
import com.shoniz.saledistributemobility.data.database.SaleDatabase;
import com.shoniz.saledistributemobility.data.model.app.BranchData;
import com.shoniz.saledistributemobility.data.model.app.BranchEntity;
import com.shoniz.saledistributemobility.data.model.app.IAppRepository;
import com.shoniz.saledistributemobility.data.model.app.IShonizRepository;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.ExceptionWorkerService;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerViewModel;
import com.shoniz.saledistributemobility.utility.SimpleAsyncTask;
import com.shoniz.saledistributemobility.utility.dialog.OnProgressUpdate;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;

import java.util.List;

import javax.inject.Inject;

public class BranchViewModel extends RecyclerViewModel<BranchData,
        Integer,
        IBranchNavigator> implements IBranchRecyclerListener {

    //TODO GSM REMOVED
//    public MutableLiveData<String> googleCloudText = new MutableLiveData<>();
//    public MutableLiveData<Integer> googleCloudTextColor = new MutableLiveData<>();

    public MutableLiveData<Boolean> areBranchesListLoadedSuccessfully = new MutableLiveData<>();
    public MutableLiveData<String> deviceIMEI = new MutableLiveData<>();
    public MutableLiveData<List<BranchData>> branches = new MutableLiveData<>();
    public MutableLiveData<BranchEntity> selectedBranch = new MutableLiveData<>();


    CommonPackage commonPackage;
    SaleDatabase saleDatabase;
    ISettingRepository settingRepository;
    IAppRepository appRepository;
    IShonizRepository shonizRepository;
    IAppUpdater appUpdater;

    private boolean isReselectBranch = false;


    @Inject
    public BranchViewModel(
            CommonPackage commonPackage, SaleDatabase saleDatabase, ISettingRepository settingRepository,
            IAppRepository appRepository, IShonizRepository shonizRepository, IAppUpdater appUpdater) {
        this.commonPackage = commonPackage;
        this.saleDatabase = saleDatabase;
        this.settingRepository = settingRepository;
        this.appRepository = appRepository;
        this.shonizRepository = shonizRepository;
        this.appUpdater = appUpdater;
    }

    public void load(boolean isReselectBranch) {
        this.isReselectBranch = isReselectBranch;
        logRemainedErrors();

        try {
            if (isReselectBranch || !settingRepository.isInitialedApplication()) {
                //TODO GSM REMOVED
                //sendGoogleTokenToServer();
                loadBranchList();
            } else {
                getNavigator().loadMainActivity();
            }
        } catch (Exception ex) {
            UncaughtException exception =
                    new UncaughtException(commonPackage, ex);
            exception.userMessage = "خطا در تست وضعیت برنامه در ابتدا";
            getNavigator().handleError(exception);
            areBranchesListLoadedSuccessfully.setValue(false);
        }
    }

    private void init() {
        deviceIMEI.setValue(commonPackage.getDevice().getIMEI() + "");
        commonPackage.getUtility().setDefaultLanguage();
        //TODO GSM REMOVED
//        RegistrationIntentService.CreateInstance(commonPackage.getContext());
        selectedBranch.setValue(null);
        areBranchesListLoadedSuccessfully.setValue(false);
    }

    private void logRemainedErrors() {
        RunnableMethod<Object, Boolean> runDo = new RunnableMethod<Object, Boolean>() {
            @Override
            public Boolean run(Object param, OnProgressUpdate onProgressUpdate) {

                return ExceptionWorkerService.sendRemainedLogs(commonPackage);
            }
        };

        SimpleAsyncTask asyncTask = new SimpleAsyncTask(null, runDo, null, null);
        asyncTask.run();
    }


//TODO GSM REMOVED
//    private void sendGoogleTokenToServer() {
//        if (settingRepository.isTokenCloudMessagingSave())
//            RegistrationIntentService.sendRegistrationToServer(
//                    commonPackage.getContext(),
//                    settingRepository.getCloudToken()
//            );
//    }


    private void loadBranchList() {
        init();
        Runnable runnablePre = () -> {
            getNavigator().onBeginAsync();
        };
        CommonAsyncTask.RunnableDo<Object, AsyncResult<List<BranchData>>> runnableDo = (param, onProgress) -> {
            AsyncResult result = new AsyncResult<List<BranchData>>();
            try {
                result.model = shonizRepository.getShonizBranchData();
            } catch (Exception e) {
                result.exception = new UncaughtException(commonPackage, e);
                result.exception.userMessage = "خطا در بارگذاری دفاتر";
            }
            return result;
        };
        CommonAsyncTask.RunnablePost<AsyncResult<List<BranchData>>> postRunnable = param -> {
            if (param.hasError()) {
                getNavigator().handleError(param.exception);
                areBranchesListLoadedSuccessfully.setValue(false);
            } else {
                branches.setValue(param.model);
                areBranchesListLoadedSuccessfully.setValue(true);
            }
            getNavigator().onEndAsync();
        };
        CommonAsyncTask.OnProgress onProgress = objects -> {
            getNavigator().onAsyncUpdate(commonPackage.getContext().getString(R.string.get_branch));
        };
        CommonAsyncTask commonAsyncTask = new CommonAsyncTask(runnablePre, runnableDo, postRunnable, onProgress);
        commonAsyncTask.run();
    }

    public void btnBranchSelectClicked() {
        if (!areBranchesListLoadedSuccessfully.getValue()) {
            loadBranchList();
            return;
        }

        if (selectedBranch.getValue() == null) {
            getNavigator().handleError(new NoBranchSelectedError(commonPackage));
            return;
        }

        Runnable runnablePre = () -> getNavigator().onBeginAsync();
        UpdateAppData(runnablePre);
    }

    private void UpdateAppData(Runnable runnablePre) {
        CommonAsyncTask.RunnableDo<Object, AsyncResult> runnableDo = (param, onProgress) -> {
            AsyncResult result = new AsyncResult();
            try {
                onProgress.onUpdate(commonPackage.getContext().getString(R.string.common_get_setting));
                settingRepository.setBranchEntity(selectedBranch.getValue());

                appUpdater.setAppUpdateListener(message -> onProgress.onUpdate(message));
                appUpdater.updateWholeData();
                appUpdater.setAppInitializationStatus();
            } catch (BaseException e) {
                result.exception = new UncaughtException(commonPackage, e, "لطفا از ارتباط اینترنت خود مطمئن شده و دوباره تلاش نمایید.");
            }
            return result;
        };
        CommonAsyncTask.RunnablePost<AsyncResult> postRunnable = param -> {
            getNavigator().onEndAsync();
            if (param.hasError()) {
                getNavigator().handleError(param.exception);
            } else {
                getNavigator().loadMainActivity();
            }
        };

        CommonAsyncTask.OnProgress onProgress = new CommonAsyncTask.OnProgress() {
            @Override
            public void onUpdate(Object... objects) {
                getNavigator().onAsyncUpdate(objects[0].toString());
            }
        };
        CommonAsyncTask commonAsyncTask = new CommonAsyncTask(runnablePre, runnableDo, postRunnable, onProgress);
        commonAsyncTask.run();
    }

    @Override
    public MutableLiveData<List<BranchData>> getMutableList() {
        return branches;
    }

    @Override
    public void onBranchItemClick(View view, int branchId) {
        selectedBranch.setValue(getModelById(branchId).branchEntity);
    }

    //TODO GSM REMOVED
//    BroadcastReceiver getCloudMessageBroadcastReceiver() {
//        return new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                try {
//                    Bundle bundle = intent.getExtras();
//                    if (bundle == null)
//                        return;
//                    boolean sentToken = bundle.getBoolean(GcmPreferences.SENT_TOKEN_TO_SERVER);
//
//                    if (sentToken) {
//                        googleCloudText.setValue(context.getString(R.string.common_cloud_register));
//                        googleCloudTextColor.setValue(R.color.textDarkPrimary);
//                    } else {
//                        googleCloudText.setValue(context.getString(R.string.common_cloud_not_register));
//                        googleCloudTextColor.setValue(R.color.colorAccent);
//                    }
//
//
//                } catch (Exception e) {
//                    UncaughtException exception =
//                            new UncaughtException(
//                                    commonPackage
//                                    , e);
//                    exception.userMessage = "خطا در onReceive اطلاعات توکن-broadcast";
//                    getNavigator().handleError(exception);
//                }
//            }
//        };
//    }


}
