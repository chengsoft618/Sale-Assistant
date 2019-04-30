package com.shoniz.saledistributemobility.view.catalog;

import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;
import com.shoniz.saledistributemobility.view.customer.SharedCustomerViewModel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class CatalogFragmentModule {


    @Provides
    @Named("SharedCustomerViewModel")
    ViewModelProvider.Factory getFactory2(SharedCustomerViewModel model){
        return new ViewModelProviderFactory<>(model);
    }


    @Provides
    @Named("CatalogFragmentViewModel")
    ViewModelProvider.Factory getFactory(CatalogFragmentViewModel model){
        return new ViewModelProviderFactory<>(model);
    }



}
