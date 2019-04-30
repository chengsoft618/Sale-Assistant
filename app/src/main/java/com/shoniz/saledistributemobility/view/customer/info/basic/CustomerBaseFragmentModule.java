package com.shoniz.saledistributemobility.view.customer.info.basic;

import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class CustomerBaseFragmentModule {

    @Provides
    ViewModelProvider.Factory getFactory(CustomerBaseViewModel model){
        return new ViewModelProviderFactory<>(model);
    }



}
