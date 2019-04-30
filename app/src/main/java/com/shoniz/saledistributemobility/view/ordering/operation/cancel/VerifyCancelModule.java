package com.shoniz.saledistributemobility.view.ordering.operation.cancel;

import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;
import com.shoniz.saledistributemobility.view.ordering.operation.VerificationSharedViewModel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class VerifyCancelModule {

    @Provides
    @Named("VerificationSharedViewModel")
    ViewModelProvider.Factory getFactory(VerificationSharedViewModel model){
        return new ViewModelProviderFactory<>(model);
    }

    @Provides
    @Named("VerifyCancelViewModelFactory")
    ViewModelProvider.Factory getFactory2(VerifyCancelViewModel model){
        return new ViewModelProviderFactory<>(model);
    }
}
