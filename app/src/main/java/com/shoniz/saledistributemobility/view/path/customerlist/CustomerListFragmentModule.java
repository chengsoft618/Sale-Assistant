package com.shoniz.saledistributemobility.view.path.customerlist;

import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;
import com.shoniz.saledistributemobility.view.path.pathlist.PathListViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class CustomerListFragmentModule {

    @Provides
    ViewModelProvider.Factory getFactory(CustomerListViewModel model){
        return new ViewModelProviderFactory<>(model);
    }



}
