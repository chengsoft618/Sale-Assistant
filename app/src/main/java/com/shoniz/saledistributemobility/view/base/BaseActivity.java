package com.shoniz.saledistributemobility.view.base;

import android.Manifest;
import android.app.DownloadManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.shoniz.saledistributemobility.data.model.location.ILocationRepository;
import com.shoniz.saledistributemobility.data.model.log.ILogRepository;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.ConnectionException;
import com.shoniz.saledistributemobility.framework.exception.OldApiException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.ExceptionHandler;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.infrastructure.install.DownloadCompleteReceiver;
import com.shoniz.saledistributemobility.infrastructure.wialon.WialonWorker;
import com.shoniz.saledistributemobility.location.LocationHelper;
import com.shoniz.saledistributemobility.location.LocationPermission;
import com.shoniz.saledistributemobility.message.gcm.GcmApi;
import com.shoniz.saledistributemobility.view.base.progress.ProgressDialog;
import com.shoniz.saledistributemobility.view.base.viewmodel.INavigator;

import java.io.IOException;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by 921235 on 5/6/2018.
 */

public abstract class BaseActivity<V extends ViewModel, B extends ViewDataBinding>
        extends DaggerAppCompatActivity
        implements INavigator {
    protected B mViewDataBinding;
    protected V mViewModel;
    DownloadCompleteReceiver downloadCompleteReceiver = new DownloadCompleteReceiver();

    String PROGRESS_DIALOG = "ProgressDialog";

    LocationManager locationManager;
    LocationListener locationListener;

    LocationRequest locationRequest;

    @Inject
    protected ILocationRepository locationRepository;
    @Inject
    protected ISettingRepository settingRepository;
    @Inject
    protected ILogRepository logRepository;
    @Inject
    protected CommonPackage commonPackage;

    SharedLocationViewModel sharedLocationViewModel;
    private ProgressDialog progressDialog;
    private BroadcastReceiver mGpsSwitchStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
                final Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent1);
            }
        }
    };

    private void registerLocation() {

        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        LocationCallback locationCallback = new LocationCallback() {
            public void onLocationResult(LocationResult locationResult) {
                locationRepository.insert(
                        LocationHelper.convertLocationsToLocationEntities(locationResult.getLocations())
                );
                //==WialonWorker.setWorker();
            }
        };
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(settingRepository.getLocationUpdateIntervalSeconds());
        locationRequest.setFastestInterval(settingRepository.getLocationUpdateIntervalSeconds());
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setMaxWaitTime(settingRepository.getLocationMaxWaitTime());

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                locationRepository.insert(
                        LocationHelper.convertLocationToLocationEntity(location)
                );
                try {
                    WialonWorker.setWorker();
                } catch (Exception e) {
                    UncaughtException uncaughtException1 = new UncaughtException(commonPackage, e);
                    uncaughtException1.userMessage = "WialonWorker";
                    ExceptionHandler.handle(uncaughtException1, commonPackage.getContext());
                }

                sharedLocationViewModel.location.setValue(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
                turnLocationOn(BaseActivity.this);
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                fusedLocationClient.requestLocationUpdates(locationRequest,
                        locationCallback, null);

                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        settingRepository.getLocationUpdateIntervalSeconds(),
                        settingRepository.getLocationMinDistance(),
                        locationListener);
            }
        } else {
            fusedLocationClient.requestLocationUpdates(locationRequest,
                    locationCallback, null);

            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    settingRepository.getLocationUpdateIntervalSeconds(),
                    settingRepository.getLocationMinDistance(),
                    locationListener);
        }


        //registerToManageLocationStatus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //bgc();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0 && LocationPermission.MULTIPLE_PERMISSIONS_KEY == requestCode)
            turnLocationOn(this);
    }

    public final void turnLocationOn(final Context context) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            LocationSettingsRequest.Builder builder =
                    new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

            SettingsClient client = LocationServices.getSettingsClient(context);

            Task task = client.checkLocationSettings(builder.build());

            task.addOnFailureListener(exception -> {
                try {
                    ((ResolvableApiException) exception).startResolutionForResult(
                            BaseActivity.this,
                            LocationPermission.MULTIPLE_PERMISSIONS_KEY);
                } catch (IntentSender.SendIntentException e) {

                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @LayoutRes
    public abstract int getLayoutId();

    public abstract V getViewModel();

    public abstract int getBindingVariable();

    public boolean isEnableLocation(){return false;}

    public void onChangeLocation(Location location){}

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.setLifecycleOwner(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        commonPackage.getUtility().setDefaultLanguage();

        registerReceiver(downloadCompleteReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH) {
            registerReceiver(mGpsSwitchStateReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
            LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean gps_on = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!gps_on) {
                final Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        }
        if (isEnableLocation()) {
            registerLocation();
        }
    }

    @Override
    protected void onPause() {
        unregisterReceiver(downloadCompleteReceiver);
        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT_WATCH) {
            unregisterReceiver(mGpsSwitchStateReceiver);
        }
        if (isEnableLocation()) {
            locationManager.removeUpdates(locationListener);
        }
        super.onPause();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setLocationObserve();
            performDataBinding();
        } catch (Exception ex) {
            ex.getMessage();
        }

    }

    private void setLocationObserve() {
        if (isEnableLocation()) {
            sharedLocationViewModel = ViewModelProviders.of(this).get(SharedLocationViewModel.class);
            sharedLocationViewModel.location.observe(this, new Observer<Location>() {
                @Override
                public void onChanged(@Nullable Location location) {
                    onChangeLocation(location);
                }
            });
        }
    }

    @Override
    public void handleError(BaseException exceptions) {
        onEndProgress();
        ExceptionHandler.handle(exceptions, this);
    }

    @Override
    public void onBeginProgress() {
        progressDialog = ProgressDialog.newInstance();
        progressDialog.setCancelable(false);
        progressDialog.show(getSupportFragmentManager(), PROGRESS_DIALOG);
    }

    @Override
    public void setProgressSize(int progresSize) {
        progressDialog.setProgressSize(progresSize);
    }

    @Override
    public void showInProgress(String message) {
        progressDialog.showInProgress(message);
    }

    @Override
    public void showSimpleProgress(String message) {
        progressDialog.showSimpleMassage(message);
    }

    @Override
    public void onEndProgress() {
        try {
            if (progressDialog != null && progressDialog.getActivity() != null)
                progressDialog.dismiss();
        } catch (Exception e) {
        }
    }

    @Override
    public void showInProgress(int stringRes) {
        progressDialog.showInProgress(stringRes);
    }


    @Override
    public void showSnackBar(String title) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, title, Snackbar.LENGTH_LONG)
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
    }


    @Override
    public void showSnackBar(String title, String btnString, Runnable runnable) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, title, Snackbar.LENGTH_LONG)
                .setAction(btnString, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        runnable.run();
                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
    }


}
