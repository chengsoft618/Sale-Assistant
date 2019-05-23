package com.shoniz.saledistributemobility.view.ordering.sent;


import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class SentOrdersFragmentModule {

    @Provides
    @Named("SentOrderViewModelFactory")
    ViewModelProvider.Factory getFactory(SentOrderViewModel model){
        return new ViewModelProviderFactory<>(model);
    }
}
