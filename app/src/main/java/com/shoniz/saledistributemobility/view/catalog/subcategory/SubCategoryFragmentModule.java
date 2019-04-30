package com.shoniz.saledistributemobility.view.catalog.subcategory;

import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;
import com.shoniz.saledistributemobility.view.customer.SharedCustomerViewModel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class SubCategoryFragmentModule {


    @Provides
    @Named("SharedCustomerViewModel")
    ViewModelProvider.Factory getFactory2(SharedCustomerViewModel model){
        return new ViewModelProviderFactory<>(model);
    }


    @Provides
    @Named("SubCategoryFragmentViewModel")
    ViewModelProvider.Factory getFactory(SubCategoryFragmentViewModel model){
        return new ViewModelProviderFactory<>(model);
    }



}
