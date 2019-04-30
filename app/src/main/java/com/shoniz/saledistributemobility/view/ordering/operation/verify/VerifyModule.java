package com.shoniz.saledistributemobility.view.ordering.operation.verify;

import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;
import com.shoniz.saledistributemobility.view.ordering.operation.VerificationSharedViewModel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class VerifyModule {

    @Provides
    @Named("VerificationSharedViewModel")
    ViewModelProvider.Factory getFactory(VerificationSharedViewModel model){
        return new ViewModelProviderFactory<>(model);
    }

    @Provides
    @Named("VerifyViewModelFactory")
    ViewModelProvider.Factory getFactory2(VerifyViewModel model){
        return new ViewModelProviderFactory<>(model);
    }

}
