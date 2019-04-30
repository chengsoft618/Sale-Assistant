package com.shoniz.saledistributemobility.view.main;

import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.api.ApiNetworkException;
import com.shoniz.saledistributemobility.data.api.core.ICoreApi;
import com.shoniz.saledistributemobility.data.api.download.IFileDownloader;
import com.shoniz.saledistributemobility.data.model.app.api.IAppApi;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;
import com.shoniz.saledistributemobility.infrastructure.version.VersionData;
import com.shoniz.saledistributemobility.utility.PersianCalendar;
import com.shoniz.saledistributemobility.view.base.viewmodel.BaseViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainViewModel extends BaseViewModel<IMainNavigator> {

    private IAppApi appApi;
    private CommonPackage commonPackage;
    private IFileDownloader fileDownloader;
    private ISettingRepository settingRepository;
    ICoreApi coreApi;
    public MutableLiveData<String> persianDate = new MutableLiveData<>();

    @Inject
    public MainViewModel(CommonPackage commonPackage, ICoreApi coreApi, IAppApi appApi, IFileDownloader fileDownloader, ISettingRepository settingRepository) {
        this.commonPackage = commonPackage;
        this.appApi = appApi;
        this.fileDownloader = fileDownloader;
        this.settingRepository = settingRepository;
        this.coreApi = coreApi;
    }


    public void updateData() {
        settingRepository.setIsInitialedApplication(true);
        if (!commonPackage.getUtility().getVersionName().equals(settingRepository.getPreviousVersionName())) {

            if (commonPackage.getUtility().getVersionName().compareTo("1.6.0.0") < 0) {
                settingRepository.removePref(commonPackage.getContext().getString(R.string.pref_key_cheque_duration_day));
            }
            getNavigator().updateData(true);
                    }
//        if (settingRepository.getUnchangedOrdersNoInCardindeForEdit() == 0)
//            settingRepository.setUnchangedOrdersNoInCardindeForEdit(0L);
    }

    public void checkLastVersion() {
       if(!settingRepository.getApkLastVersion().isEmpty()&& !settingRepository.getApkLastVersion().equals(commonPackage.getUtility().getVersionName()))
       {
           getNavigator().showNewApkNotification();
       }

    }


}
