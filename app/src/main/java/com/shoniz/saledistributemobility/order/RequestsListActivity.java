package com.shoniz.saledistributemobility.order;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.framework.service.update.IAppUpdater;
import com.shoniz.saledistributemobility.databinding.ActivityRequestBinding;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.view.base.BaseActivity;
import com.shoniz.saledistributemobility.view.customer.ILocationChange;
import com.shoniz.saledistributemobility.data.model.log.ILogRepository;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.ExceptionHandler;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.location.LocationManagementService;
import com.shoniz.saledistributemobility.location.LocationResultCallBack;
import com.shoniz.saledistributemobility.utility.dialog.AsyncTaskDialog;
import com.shoniz.saledistributemobility.utility.dialog.OnProgressUpdate;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;

import javax.inject.Inject;

public class RequestsListActivity extends BaseActivity<RequestListViewModel, ActivityRequestBinding> {
    public Location lastLocation = null;

    @Inject
    ViewModelProvider.Factory factory;

    @Inject
    CommonPackage commonPackage;

    @Inject
    IAppUpdater appUpdater;

//    @Inject
//    public AppDataUpdate appDataUpdate;
    @Inject
    ILogRepository logRepository;

    RequestsListPageAdapter adapter;
    ViewPager viewPager;
    boolean mBounded;

    LocationManagementService mServer;
    LocationResultCallBack locationResultCallBack = location -> {
        lastLocation = location;
        for (Fragment fragment : adapter.getLocationListenerFragments())
            ((ILocationChange) fragment).onChange(location);

    };
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBounded = false;
            mServer = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBounded = true;
            LocationManagementService.LocalBinder mLocalBinder = (LocationManagementService.LocalBinder) service;
            mServer = mLocalBinder.getServerInstance();
            mServer.setCurrentActivity(RequestsListActivity.this);
            mServer.getCurrentLocation(locationResultCallBack);
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_request;
    }

    @Override
    public RequestListViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(RequestListViewModel.class);
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
    public void onDestroy() {
        super.onDestroy();
        //gc();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getLayoutInflater().inflate(R.layout.activity_request, null);
        try {
            SharedOrderViewModel sharedOrderViewModel = ViewModelProviders.of(this).get(SharedOrderViewModel.class);
            sharedOrderViewModel.isRefresh.observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    if (aBoolean)
                        refreshAdapter();
                }
            });
        } catch (Exception e) {
            int a = 1;
        }

        //View v = getLayoutInflater().inflate(R.layout.viewpage_requests_list, null);
        viewPager = v.findViewById(R.id.viewpager_request_list);
        try {

            TabLayout tabLayout = v.findViewById(R.id.tab_layout_request_list);
            tabLayout.setupWithViewPager(viewPager);
            setContentView(v);
            v.findViewById(R.id.fabUpdate).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sync();
                }
            });
            adapter = new RequestsListPageAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);

        } catch (Exception ex) {
            int a = 0;
        }
    }

    private void refreshAdapter() {
        adapter = new RequestsListPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private void sync() {
        RunnableMethod runDo = new RunnableMethod() {
            @Override
            public Object run(Object param, OnProgressUpdate onProgressUpdate) {
                AsyncResult runnableModel = new AsyncResult();
                try {
                    appUpdater.setAppUpdateListener(message -> onProgressUpdate.onProgressUpdate(message));
                    appUpdater.updateOrder();
                } catch (InOutError inOutError) {
                    runnableModel.exception = inOutError;
                } catch (Exception e) {
                    e.getMessage();
                }
                return runnableModel;
            }
        };

        RunnableMethod<AsyncResult, Object> runPost =
                new RunnableMethod<AsyncResult, Object>() {
                    @Override
                    public Object run(AsyncResult runnableModel, OnProgressUpdate onProgressUpdate) {
                        refreshAdapter();
                        if (runnableModel.hasError()) {
                            ExceptionHandler.handle(runnableModel.exception, RequestsListActivity.this);
                        }
                        return null;
                    }
                };

        AsyncTaskDialog asyncTaskDialog = new AsyncTaskDialog(this,
                getString(R.string.wait),
                getString(R.string.get_order_list), null, runDo, runPost);
        asyncTaskDialog.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshAdapter();
    }

    @Override
    protected void onPause() {
        if (mServer != null)
            mServer.stopGettingCurrentLocation();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBounded) {
            unbindService(mConnection);
            mBounded = false;
            locationResultCallBack = null;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0 && mServer != null)
            mServer.turnLocationOn(this);
    }


}
