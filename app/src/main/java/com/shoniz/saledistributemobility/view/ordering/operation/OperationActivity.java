package com.shoniz.saledistributemobility.view.ordering.operation;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.databinding.ActivityOrderVerifyBinding;
import com.shoniz.saledistributemobility.view.base.BaseActivity;
import com.shoniz.saledistributemobility.view.base.viewpager.PagerAdapter;

import javax.inject.Inject;
import javax.inject.Named;


public class OperationActivity extends BaseActivity<OperationViewModel, ActivityOrderVerifyBinding>
        implements IOperationNavigator {

    @Inject
    ViewModelProvider.Factory factory;


    @Inject
    @Named("VerificationSharedViewModel")
    ViewModelProvider.Factory factoryShared;

    @Inject
    ApiParameter apiParameter;

    @Inject
    PagerAdapter pagerAdapter;

    VerificationSharedViewModel verificationSharedViewModel;

    public static Intent newIntent(Context context) {
        return new Intent(context, OperationActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verificationSharedViewModel = ViewModelProviders.of(this, factoryShared).get(VerificationSharedViewModel.class);
        mViewModel.setNavigator(this);
        mViewModel.setSharedModel(verificationSharedViewModel);
        mViewModel.load();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_verify;
    }

    @Override
    public OperationViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(OperationViewModel.class);
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
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void updateFragments() {
        try {
            mViewDataBinding.viewpagerVerification.setAdapter(pagerAdapter);
            mViewDataBinding.tabLayoutVerification.setupWithViewPager(mViewDataBinding.viewpagerVerification);
            mViewDataBinding.viewpagerVerification.addOnPageChangeListener(pagerAdapter);
        } catch (Exception ex) {
            int a = 0;
        }
    }
}
