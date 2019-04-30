package com.shoniz.saledistributemobility.view.dialog.orderdescription;

import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;
import com.shoniz.saledistributemobility.view.main.MainViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class DescriptionDialogModule {

    @Provides
    ViewModelProvider.Factory getFactory(DescriptionDialogViewModel model){
        return new ViewModelProviderFactory<>(model);
    }



}
