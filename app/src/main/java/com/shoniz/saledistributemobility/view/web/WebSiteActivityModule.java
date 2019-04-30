package com.shoniz.saledistributemobility.view.web;

import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class WebSiteActivityModule {

    @Provides
    ViewModelProvider.Factory getFactory(WebSiteViewModel model){
        return new ViewModelProviderFactory<>(model);
    }



}
