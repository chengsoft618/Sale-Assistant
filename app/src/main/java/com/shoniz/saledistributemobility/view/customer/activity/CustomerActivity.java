package com.shoniz.saledistributemobility.view.customer.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.cardindex.CardIndexDetailData;
import com.shoniz.saledistributemobility.data.model.cardindex.CardIndexRepository;
import com.shoniz.saledistributemobility.data.model.cardindex.ICardIndexRepository;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBasicEntity;
import com.shoniz.saledistributemobility.data.model.customer.ICustomerRepository;
import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.model.order.OrderRepository;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderDetailCompleteData;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.view.customer.CustomerData;
import com.shoniz.saledistributemobility.view.customer.CustomerPageAdapter;
import com.shoniz.saledistributemobility.view.customer.SharedCustomerViewModel;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexBusiness;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBasicModel;
import com.shoniz.saledistributemobility.databinding.ActivityCustomerBinding;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.view.base.BaseActivity;
import com.shoniz.saledistributemobility.view.path.outofpath.CustomerPreference;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class CustomerActivity extends BaseActivity<CustomerViewModel, ActivityCustomerBinding>
        implements ICustomerNavigator {
    @Inject
    CommonPackage commonPackage;

    @Inject
    ISettingRepository settingRepository;

    @Inject
    IOrderRepository orderRepository;

    @Inject
    ICardIndexRepository cardIndexRepository;

    @Inject
    @Named("SharedCustomerViewModel")
    ViewModelProvider.Factory factoryShared;

    @Inject
    @Named("CustomerViewModel")
    ViewModelProvider.Factory factory;

    SharedCustomerViewModel sharedCustomerActivityViewModel;

    int personID;
    ViewPager viewPager;

    CustomerPageAdapter adapter;

    @Inject
    ICustomerRepository customerRepository;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //gc();
    }

    public static void startActivity(Activity context, int personID) {
        Intent intent = new Intent(context, CustomerActivity.class);
        intent.putExtra("PersonId", personID);
        context.startActivity(intent);
    }

    private void gc() {
        adapter = null;
        viewPager = null;
        Runtime.getRuntime().gc();
        System.gc();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            sharedCustomerActivityViewModel = ViewModelProviders.of(this, factoryShared).get(SharedCustomerViewModel.class);
            sharedCustomerActivityViewModel.addCatalogResizeListener(catalogResizeListener);

            personID = getIntent().getExtras().getInt(CustomerPreference.PersonId);

            if (getIntent().getExtras().getBoolean(CustomerPreference.IsActivityForJustGettingRequest, false)) {
                adapter = new CustomerPageAdapter(getSupportFragmentManager(), personID);
                setTitle("ثبت درخواست مشتری شماره: " + personID);
            } else {
                CustomerBasicEntity customerBasicModel = customerRepository.getCustomerBase(personID);
                adapter = new CustomerPageAdapter(getSupportFragmentManager(), customerBasicModel);
                setTitle(customerBasicModel.PersonName);
            }
            viewPager = mViewDataBinding.getRoot().findViewById(R.id.viewpager_customer_info);
            viewPager.setAdapter(adapter);
            TabLayout tabLayout = mViewDataBinding.getRoot().findViewById(R.id.tab_layout_customer_info);
            tabLayout.setupWithViewPager(viewPager);

            Toolbar toolbar = mViewDataBinding.getRoot().findViewById(R.id.toolbarListener);
            setSupportActionBar(toolbar);
            getSupportActionBar().hide();


        } catch (Exception ex) {
            handleError(new UncaughtException(commonPackage, ex));
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

    @Override
    public int getLayoutId() {
        return R.layout.activity_customer;
    }

    @Override
    public CustomerViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(CustomerViewModel.class);
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
        try {
            cardIndexRepository.removeUnchangedCardindexForEdit(orderRepository,
                    settingRepository, cardIndexRepository, commonPackage);
        } catch (IOException e) {
            UncaughtException exception = new UncaughtException(commonPackage, e);
            handleError(exception);
        }
        finish();
    }

    private SharedCustomerViewModel.OnCatalogResizeListener catalogResizeListener = new SharedCustomerViewModel.OnCatalogResizeListener() {
        @Override
        public void onCatalogIncreaseSize() {
            mViewDataBinding.includeLayout.getRoot().findViewById(R.id.tab_layout_customer_info).setVisibility(View.GONE);
        }

        @Override
        public void onCatalogReduceSize() {
            mViewDataBinding.includeLayout.getRoot().findViewById(R.id.tab_layout_customer_info).setVisibility(View.VISIBLE);
        }
    };
}
