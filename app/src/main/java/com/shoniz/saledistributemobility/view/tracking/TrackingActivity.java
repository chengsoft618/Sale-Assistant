package com.shoniz.saledistributemobility.view.tracking;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.user.UserEntity;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.data.sharedpref.api.ISettingApi;
import com.shoniz.saledistributemobility.databinding.ActivityTrackingBinding;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.view.base.BaseActivity;
import com.shoniz.saledistributemobility.view.dialog.userselect.UserSelectDialog;

import javax.inject.Inject;


public class TrackingActivity extends BaseActivity<TrackingViewModel, ActivityTrackingBinding>
        implements ITrackingNavigator {

    @Inject
    ViewModelProvider.Factory factory;
    @Inject
    CommonPackage commonPackage;

    @Inject
    ISettingApi settingApi;

    @Inject
    ISettingRepository settingRepository;

    @Inject
    UserSelectDialog userSelectDialog;

    public static Intent newIntent(Context context) {
        return new Intent(context, TrackingActivity.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tracking;
    }

    @Override
    public TrackingViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(TrackingViewModel.class);
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
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            mViewModel.setNavigator(this);
            mViewModel.load();
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    @Override
    public void onStartExactTracking() {
        userSelectDialog.show(this, userEntity -> {
            mViewModel.startExactTracking(userEntity.userId);
        });
    }

    @Override
    public void onStopExactTracking() {
        userSelectDialog.show(this, (UserEntity userEntity) -> {
            mViewModel.stopExactTracking(userEntity.userId);
        });
    }


}
