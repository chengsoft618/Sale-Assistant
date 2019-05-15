package com.shoniz.saledistributemobility.view.path.pathlist;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.view.View;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.customer.ICustomerRepository;
import com.shoniz.saledistributemobility.data.model.message.IMessageRepository;
import com.shoniz.saledistributemobility.data.model.message.MessageData;
import com.shoniz.saledistributemobility.data.model.path.IPathRepository;
import com.shoniz.saledistributemobility.data.model.path.PathListData;
import com.shoniz.saledistributemobility.data.model.path.db.PathEntity;
import com.shoniz.saledistributemobility.data.model.update.IAppUpdateListener;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.framework.service.update.IAppUpdater;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerViewModel;
import com.shoniz.saledistributemobility.view.customer.CustomerOldData;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

public class PathListViewModel extends RecyclerViewModel<PathListData, Integer, IPathListNavigator>
        implements IPathRecyclerListener {

    ICustomerRepository customerRepository;
    CommonPackage commonPackage;

    IPathRepository pathRepository;
    IMessageRepository messageRepository;
    ISettingRepository settingRepository;
    IAppUpdater appUpdater;

    Context context;
    MutableLiveData<List<PathListData>> paths = new MutableLiveData<>();

    @Inject
    public PathListViewModel(CommonPackage commonPackage, ICustomerRepository customerRepository,
                             IAppUpdater appUpdater, IPathRepository pathRepository, ISettingRepository settingRepository,
    IMessageRepository messageRepository) {
        this.commonPackage = commonPackage;
        this.customerRepository = customerRepository;
        context = commonPackage.getContext();
        this.appUpdater = appUpdater;
        this.pathRepository = pathRepository;
        this.settingRepository = settingRepository;
        this.messageRepository = messageRepository;
    }

    public void init() {
        paths.setValue(pathRepository.getAllPaths());
    }

    public void initOutOfPath(int personId) {
        try {
            boolean isKnownCustomer = CustomerOldData.isCustomerExist(context, personId);
            if (isKnownCustomer) {
                getNavigator().goToCustomerPage(personId, isKnownCustomer);
                return;
            }
        } catch (IOException e) {
            getNavigator().handleError(new InOutError(commonPackage, e, "خطا در دسترسی به شبکه"));
            return;
        }
        int branchCode = settingRepository.getBranchEntity().BranchCode;
        int branchCapacity = 1000000;
        if (personId < branchCapacity)
            personId = branchCode * branchCapacity + personId;
        else if (personId < branchCode * branchCapacity || personId > ((branchCode + 1) * branchCapacity - 1)) {
            commonPackage.getUtility().toast("کد مشتری شما در محدوده مشتریان دفتر نمی باشد");
            return;
        }

        syncCustomerInfoById(personId);

    }

    public void sendEndOfDailyVisitReport(String desc) {
        settingRepository.setEndOfDailyWorkDesc(desc);

        CommonAsyncTask.RunnableDo<Object, AsyncResult<List<MessageData>>> runnableDo = (param, onProgress) -> {
            AsyncResult<List<MessageData>> asyncResult = new AsyncResult<>();
            try {
                messageRepository.sendEndOfDailyVisitMessage(desc);
            } catch (BaseException ex) {
                asyncResult.exception = ex;
            } catch (Exception ex) {
                asyncResult.exception = new UncaughtException(commonPackage, ex);
            }
            return asyncResult;
        };
        CommonAsyncTask.RunnablePost<AsyncResult<List<MessageData>>> postRunnable = result -> {

            if (result.hasError()) {
                getNavigator().handleError(result.exception);
            } else {
                getNavigator().showSnackBar(context.getString(R.string.path_send_message_successfully));
            }
        };
        asyncCall(runnableDo, postRunnable);
    }

    public String getEndOfDailyVisitDesc() {
        return settingRepository.getEndOfDailyVisitDesc();
    }

    @Override
    public MutableLiveData<List<PathListData>> getMutableList() {
        return paths;
    }

    @Override
    public void onPathClick(View view, PathListData pathListData) {
        if (!customerRepository.isPathSync(pathListData.getId())) {
            updatePath(pathListData.getId(), getRunCustomerListFragmentPostRunnable(pathListData.pathEntity));
        }
        getNavigator().goToCustomerList(pathListData.pathEntity);
    }

    @Override
    public void onPathDownloadClick(View view, PathListData pathListData) {
        updatePath(pathListData.getId(), getSimplePostRunnable());
    }

    public void updatePath(int pathId, CommonAsyncTask.RunnablePost<AsyncResult> callback) {
        CommonAsyncTask.RunnableDo<Object, AsyncResult> runnableDo = (param, onProgress) -> {
            AsyncResult result = new AsyncResult();
            try {
                onProgress.onUpdate(commonPackage.getContext().getString(R.string.common_get_setting));

                appUpdater.setAppUpdateListener(message -> onProgress.onUpdate(message));
                appUpdater.updateWholeInfoOfPath(pathId);
            } catch (BaseException e) {
                result.exception = new UncaughtException(commonPackage, e, "لطفا از ارتباط اینترنت خود مطمئن شده و دوباره تلاش نمایید.");
            }
            return result;
        };


        CommonAsyncTask commonAsyncTask = new CommonAsyncTask(
                () -> getNavigator().onBeginProgress()
                , runnableDo, callback, getOnPreProgressRunnable("در حال بروز رسانی اطلاعات مسیر"));
        commonAsyncTask.run();
    }

    public void updateAllPaths(IAppUpdateListener listener) throws BaseException {
        appUpdater.setAppUpdateListener(listener);
        appUpdater.updatePrePath();
        settingRepository.setUnchangedOrdersNoInCardindeForEdit(0L);

        //appUpdater.updatePrePath();
    }

    public void syncCustomerInfoById(int personId) {
        CommonAsyncTask.RunnableDo<Object, AsyncResult> runnableDo = (param, onProgress) -> {
            AsyncResult result = new AsyncResult();
            try {
                appUpdater.updateWholeInfoOfPerson(personId);
            } catch (BaseException ex) {
                result.exception = ex;
            }
            return result;
        };
        CommonAsyncTask.RunnablePost<AsyncResult> postRunnable = param -> {
            getNavigator().onEndProgress();
            boolean couldFindCustomer = param.hasError();
            if (param.hasError()) {
                getNavigator().handleError(new UncaughtException(commonPackage, param.exception, "شما می توانید برای شماره وارد کرده درخواست ثبت نمایید. ولی احتمال اشتباه بودن شماره مشتری وجود دارد. لطفا از صحت این شماره مطمئن شوید."));
            }
            getNavigator().goToCustomerPage(personId, couldFindCustomer);
        };
        CommonAsyncTask.OnProgress onProgress = new CommonAsyncTask.OnProgress() {
            @Override
            public void onUpdate(Object... objects) {
                getNavigator().showSimpleProgress(objects[0].toString());
            }
        };

        CommonAsyncTask commonAsyncTask = new CommonAsyncTask(getPreRunnable(), runnableDo, postRunnable, onProgress);
        commonAsyncTask.run();
    }

    private Runnable getPreRunnable(){
        return () -> {
            getNavigator().onBeginProgress();
            getNavigator().setProgressSize(1);
        };
    }

    private CommonAsyncTask.RunnablePost<AsyncResult> getSimplePostRunnable() {
        return param -> {
            getNavigator().onEndProgress();
            if (param.hasError()) {
                getNavigator().handleError(param.exception);
            }
        };
    }

    private CommonAsyncTask.RunnablePost<AsyncResult> getRunCustomerListFragmentPostRunnable(PathEntity pathEntity) {
        return param -> {
            getNavigator().onEndProgress();
            if (param.hasError()) {
                getNavigator().handleError(param.exception);
                return;
            }
            getNavigator().goToCustomerList(pathEntity);
        };
    }

    private CommonAsyncTask.OnProgress getOnPreProgressRunnable(String message) {
        return new CommonAsyncTask.OnProgress() {
            @Override
            public void onUpdate(Object... objects) {
                getNavigator().showInProgress(message);
            }
        };
    }
}


