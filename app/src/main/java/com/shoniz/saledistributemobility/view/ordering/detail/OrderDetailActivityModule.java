package com.shoniz.saledistributemobility.view.ordering.detail;

import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class OrderDetailActivityModule {

    @Provides
    ViewModelProvider.Factory getFactory(OrderDetailViewModel model){
        return new ViewModelProviderFactory<>(model);
    }



}
