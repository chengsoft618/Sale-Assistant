package com.shoniz.saledistributemobility.view.branch;

import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class BranchActivityModule {

    @Provides
    ViewModelProvider.Factory getFactory(BranchViewModel model){
        return new ViewModelProviderFactory<>(model);
    }



}
