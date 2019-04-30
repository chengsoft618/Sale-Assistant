package com.shoniz.saledistributemobility.order.unsent;


import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class UnSentOrdersFragmentModule {

    @Provides
    @Named("UnSentOrderViewModelFactory")
    ViewModelProvider.Factory getFactory(UnSentOrderViewModel model){
        return new ViewModelProviderFactory<>(model);
    }
}
