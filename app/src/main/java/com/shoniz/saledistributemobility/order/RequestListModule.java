package com.shoniz.saledistributemobility.order;

import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class RequestListModule {
    @Provides
    ViewModelProvider.Factory getFactory(RequestListViewModel model){
        return new ViewModelProviderFactory<>(model);
    }
}
