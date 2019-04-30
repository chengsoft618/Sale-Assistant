package com.shoniz.saledistributemobility.view.main;

import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;
import com.shoniz.saledistributemobility.view.base.viewpager.PagerAdapter;
import com.shoniz.saledistributemobility.view.ordering.operation.OperationActivity;
import com.shoniz.saledistributemobility.view.ordering.operation.cancel.VerifyCancelFragment;
import com.shoniz.saledistributemobility.view.ordering.operation.verify.VerifyFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    ViewModelProvider.Factory getFactory(MainViewModel model){
        return new ViewModelProviderFactory<>(model);
    }



}
