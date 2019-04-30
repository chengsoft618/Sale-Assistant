package com.shoniz.saledistributemobility.view.customer.activity;

import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;
import com.shoniz.saledistributemobility.view.customer.SharedCustomerViewModel;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexFragmentViewModel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class CustomerActivityModule {



    @Provides
    @Named("SharedCustomerViewModel")
    ViewModelProvider.Factory getFactory2(SharedCustomerViewModel model){
        return new ViewModelProviderFactory<>(model);
    }

    @Provides
    @Named("CustomerViewModel")
    ViewModelProvider.Factory getFactory(CustomerViewModel model){
        return new ViewModelProviderFactory<>(model);
    }

}
