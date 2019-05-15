package com.shoniz.saledistributemobility.view.main;

import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;
import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    ViewModelProvider.Factory getFactory(MainViewModel model){
        return new ViewModelProviderFactory<>(model);
    }



}
