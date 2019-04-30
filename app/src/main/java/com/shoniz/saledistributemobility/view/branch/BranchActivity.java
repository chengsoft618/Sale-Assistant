package com.shoniz.saledistributemobility.view.branch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.RequiresApi;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.customer.ICustomerRepository;
import com.shoniz.saledistributemobility.data.model.user.IUserRepository;
import com.shoniz.saledistributemobility.databinding.ActivityBranchBinding;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerHelper;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerViewModel;
import com.shoniz.saledistributemobility.view.base.BaseActivity;
import com.shoniz.saledistributemobility.view.main.MainActivity;

import javax.inject.Inject;

public class BranchActivity extends BaseActivity<BranchViewModel, ActivityBranchBinding>
        implements IBranchNavigator {

    public static final String RE_SELECT_BRANCH = "RE_SELECT_BRANCH";

    PowerManager.WakeLock wakeLock;

    @Inject
    ViewModelProvider.Factory factory;
    @Inject
    IUserRepository userRepository;
    @Inject
    ICustomerRepository customerRepository;

    public static void createInstance(Activity context) {
        Intent intent = new Intent(context, BranchActivity.class);
        context.startActivity(intent);
        context.finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        boolean isReselectBranch =
                bundle != null &&
                bundle.getBoolean(RE_SELECT_BRANCH, false);
       // customerRepository.getCustomerAddress(111);
        mViewModel.setNavigator(this);
        mViewModel.load(isReselectBranch);

    }

    @SuppressLint("InvalidWakeLockTag")
    private void keepAppAlive() {
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        if (powerManager != null)
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    "MyWakelockTag");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (wakeLock != null && wakeLock.isHeld())
            wakeLock.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        keepAppAlive();
        if (wakeLock != null)
            wakeLock.acquire(1000 * 60 * 3);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_branch;
    }

    @Override
    public BranchViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(BranchViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void onBeginAsync() {
        onBeginProgress();
        setProgressSize(1);
    }

    @Override
    public void onAsyncUpdate(String message) {
        showSimpleProgress(message);
    }

    @Override
    public void onEndAsync() {
        RecyclerHelper.build(
                mViewModel,
                this,
                this,
                mViewDataBinding.recyclerGeneralList,
                BranchItemBindingBuilder.class)
                .setSelectingModel(
                        RecyclerViewModel.SelectingMode.SingleSelect
                );
        onEndProgress();

    }

    @Override
    public void loadMainActivity() {
        MainActivity.createInstance(this);
        if (commonPackage.getDevice().getActualIMEI()==0 ||
                commonPackage.getDevice().getIMEI()!=commonPackage.getDevice().getActualIMEI() ){
            finish();
        }else{
        finish();
        System.exit(10);
        }
    }
}

