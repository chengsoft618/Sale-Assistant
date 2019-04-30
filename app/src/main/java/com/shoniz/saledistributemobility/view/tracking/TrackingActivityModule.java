package com.shoniz.saledistributemobility.view.tracking;

import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class TrackingActivityModule {

    @Provides
    ViewModelProvider.Factory getFactory(TrackingViewModel model){
        return new ViewModelProviderFactory<>(model);
    }



}
