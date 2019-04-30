package com.shoniz.saledistributemobility.view.customer.info.basic;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBasicEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerCreditEntity;
import com.shoniz.saledistributemobility.data.model.customer.ICustomerRepository;
import com.shoniz.saledistributemobility.data.model.location.ILocationRepository;
import com.shoniz.saledistributemobility.data.model.location.LocationEntity;
import com.shoniz.saledistributemobility.data.model.log.ILogRepository;
import com.shoniz.saledistributemobility.data.model.message.MessageData;
import com.shoniz.saledistributemobility.data.model.message.MessageEntity;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.databinding.FragmentCustomerBasicPageBinding;
import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.ExceptionHandler;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;
import com.shoniz.saledistributemobility.infrastructure.wialon.WialonWorker;
import com.shoniz.saledistributemobility.location.LocationHelper;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.dialog.AsyncTaskDialog;
import com.shoniz.saledistributemobility.utility.dialog.ErrorDialog.ErrorDialog;
import com.shoniz.saledistributemobility.utility.dialog.OnProgressUpdate;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;
import com.shoniz.saledistributemobility.utility.dialog.RunnableModel;
import com.shoniz.saledistributemobility.view.base.BaseActivity;
import com.shoniz.saledistributemobility.view.base.BaseFragment;
import com.shoniz.saledistributemobility.view.customer.CustomerBusiness;
import com.shoniz.saledistributemobility.view.customer.CustomerData;
import com.shoniz.saledistributemobility.view.customer.info.credit.CustomerCreditModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CustomerBasicFragment extends BaseFragment<FragmentCustomerBasicPageBinding, CustomerBaseViewModel> {

    @Inject
    ViewModelProvider.Factory factory;
    LocationRequest locationRequest;
    LocationListener locationListener;
    LocationManager locationManager;
    @Inject
    ILocationRepository locationRepository;
    @Inject
    ISettingRepository settingRepository;
    @Inject
    ILogRepository logRepository;

    @Inject
    ICustomerRepository customerRepository;

    private int personID;
    private Location lastLocation;
    private ImageView imgAccuracyStatus;
    private TextView txtAccuracyStatus;
    private Button btnSaveCustomerLocation;

    public static CustomerBasicFragment newInstance(CustomerBasicEntity customerBasicModel) {
        CustomerBasicFragment fragment = new CustomerBasicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CustomerBasicModel.Column.PERSON_ID, customerBasicModel.PersonID);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public CustomerBaseViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CustomerBaseViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void onChangeLocation(Location location) {
        if (imgAccuracyStatus == null || txtAccuracyStatus == null) return;

        if (location == null || location.getAccuracy() == 0 || location.getAccuracy() > 100) {
            imgAccuracyStatus.setImageResource(R.drawable.ic_location_failed);
            txtAccuracyStatus.setText(R.string.gps_status_failed);
            btnSaveCustomerLocation.setEnabled(false);
        } else if (location.getAccuracy() < 35) {
            imgAccuracyStatus.setImageResource(R.drawable.ic_location_good);
            txtAccuracyStatus.setText(R.string.gps_status_good);
            btnSaveCustomerLocation.setEnabled(true);
        } else if (location.getAccuracy() < 55) {
            imgAccuracyStatus.setImageResource(R.drawable.ic_location_normal);
            txtAccuracyStatus.setText(R.string.gps_status_normal);
            btnSaveCustomerLocation.setEnabled(true);
        } else if (location.getAccuracy() < 100) {
            imgAccuracyStatus.setImageResource(R.drawable.ic_location_bad);
            txtAccuracyStatus.setText(R.string.gps_status_bad);
            btnSaveCustomerLocation.setEnabled(true);
        }
        lastLocation = location;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_customer_basic_page;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //gc();
    }

    private void gc() {
        lastLocation = null;
        imgAccuracyStatus = null;
        txtAccuracyStatus = null;
        btnSaveCustomerLocation = null;
        Runtime.getRuntime().gc();
        System.gc();
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            personID = getArguments().getInt(CustomerBasicModel.Column.PERSON_ID);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        try {
            CustomerBasicEntity customerBasicModel = customerRepository.getCustomerBase( personID);
            CustomerCreditEntity customerCreditModel = customerRepository.getCustomerCredit( personID);

            if(customerCreditModel == null) {
                commonPackage.getUtility().toast("اطلاعات مسیر را بروز رسانی کنید.");
                Common.setTextViewText(view, R.id.customer_credit_first_six_month, "اطلاعات مسیر بروز نیست");
                Common.setTextViewText(view, R.id.customer_credit_second_six_month, "اطلاعات مسیر بروز نیست");
            }
            else {
                Common.setTextViewText(view, R.id.customer_credit_first_six_month, Common.getCurrencyFormat(customerCreditModel.CustomerCreditFirstSixMonth));
                Common.setTextViewText(view, R.id.customer_credit_second_six_month, Common.getCurrencyFormat(customerCreditModel.CustomerCreditSecondSixMonth));
            }

            Common.setTextViewText(view, R.id.person_name, customerBasicModel.PersonName);
            Common.setTextViewText(view, R.id.contact_name, customerBasicModel.ContactName);
            Common.setTextViewText(view, R.id.address, customerBasicModel.Address);
            Common.setTextViewText(view, R.id.tel_no, customerBasicModel.TelNo);
            Common.setTextViewText(view, R.id.cell_no, customerBasicModel.CellNo);
            Common.setTextViewText(view, R.id.owner_type, customerBasicModel.OwnerType);
            Common.setTextViewText(view, R.id.customer_type, customerBasicModel.CustomerType);
            Common.setTextViewText(view, R.id.active_season, customerBasicModel.ActiveSeason);
            Common.setTextViewText(view, R.id.max_credit, customerBasicModel.MaxCredit);
            Common.setTextViewText(view, R.id.not_sale_reason_date, customerBasicModel.NotSaleReasonDate);
            Common.setTextViewText(view, R.id.not_sale_reason_desc, customerBasicModel.NotSaleReasonDesc);

            Common.setTextViewText(view, R.id.customer_account_remain,
                    Common.getAmountRemainDescription(getBaseActivity(), customerBasicModel.AccountRemain));
            Common.setTextViewText(view, R.id.customer_credit_remain, Common.getCurrencyFormat(customerBasicModel.CreditRemain));




        } catch (Exception e) {
            HandleException systemException = new HandleException(getBaseActivity(), e);
            ErrorDialog.showDialog((AppCompatActivity) getActivity(),
                    systemException.getUserTitle(), systemException.getUserMessage(),
                    systemException.getSystemMessage());
        }

        btnSaveCustomerLocation = view.findViewById(R.id.btn_save_customer_location);
        btnSaveCustomerLocation.setEnabled(false);
        btnSaveCustomerLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                sendLocation(lastLocation);
            }
        });
        imgAccuracyStatus = view.findViewById(R.id.img_accuracy_status);
        txtAccuracyStatus = view.findViewById(R.id.txt_accuracy_status);
        return view;
    }

    @Override
    public int getFragmentTitle() {
        return 0;
    }

    public void sendLocation(Location lastLocation) {

        CommonAsyncTask.RunnableDo<Object, AsyncResult > runnableDo = (param, onProgress) -> {
            AsyncResult  asyncResult = new AsyncResult<>();
            CustomerBusiness customerBusiness = new CustomerBusiness();
            LocationEntity locationEntity = LocationHelper.convertLocationToLocationEntity(
                    lastLocation);
            try {
                customerBusiness.sendCustomerLocation(getBaseActivity(), personID, locationEntity);
            } catch (Exception e) {
                asyncResult.exception = new UncaughtException(commonPackage, e);
            }
            return asyncResult;
        };
        CommonAsyncTask.RunnablePost<AsyncResult<List<MessageData>>> postRunnable = result -> {
            if (result.hasError() ) {
                handleError(result.exception);
            }else{
                commonPackage.getUtility().toast(R.string.customer_base_register_point);
            }
        };
        CommonAsyncTask commonAsyncTask = new CommonAsyncTask(null, runnableDo, postRunnable, null);
        commonAsyncTask.run();


    }


    private void registerLocation() {

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(settingRepository.getCustomerPointIntervalSeconds());
         locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        locationManager = (LocationManager) getBaseActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                onChangeLocation(location);
                locationRepository.insert(
                        LocationHelper.convertLocationToLocationEntity(location)
                );
                try {
                    WialonWorker.setWorker();
                } catch (Exception e) {
                    UncaughtException uncaughtException1=new UncaughtException(commonPackage, e);
                    uncaughtException1.userMessage="UpdateErrorMessage";
                    ExceptionHandler.handle(uncaughtException1, commonPackage.getContext());
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        //registerToManageLocationStatus();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        registerLocation();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getBaseActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || getBaseActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        settingRepository.getCustomerLocationInterval(),
                        settingRepository.getLocationMinDistance(),
                        locationListener);
            }
        } else {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    settingRepository.getCustomerLocationInterval(),
                    settingRepository.getLocationMinDistance(),
                    locationListener);
        }
    }

    @Override
    public void onDetach() {
        locationManager.removeUpdates(locationListener);
        super.onDetach();
    }



}
