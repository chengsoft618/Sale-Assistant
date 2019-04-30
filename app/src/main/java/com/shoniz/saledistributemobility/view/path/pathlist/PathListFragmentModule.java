package com.shoniz.saledistributemobility.view.path.pathlist;

import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;
import com.shoniz.saledistributemobility.view.main.MainViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class PathListFragmentModule {

    @Provides
    ViewModelProvider.Factory getFactory(PathListViewModel model){
        return new ViewModelProviderFactory<>(model);
    }



}
