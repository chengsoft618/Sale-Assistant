package com.shoniz.saledistributemobility.view.customer.cardindex;

import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;
import com.shoniz.saledistributemobility.view.customer.SharedCustomerViewModel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class CardIndexFragmentModule {


    @Provides
    @Named("SharedCustomerViewModel")
    ViewModelProvider.Factory getFactory2(SharedCustomerViewModel model){
        return new ViewModelProviderFactory<>(model);
    }

    @Provides
    @Named("CardIndexFragmentViewModel")
    ViewModelProvider.Factory getFactory(CardIndexFragmentViewModel model){
        return new ViewModelProviderFactory<>(model);
    }



}
