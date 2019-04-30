package com.shoniz.saledistributemobility.view.ordering.operation;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;
import com.shoniz.saledistributemobility.view.base.viewpager.PagerAdapter;
import com.shoniz.saledistributemobility.view.base.viewpager.PagerViewModel;
import com.shoniz.saledistributemobility.view.ordering.operation.cancel.VerifyCancelFragment;
import com.shoniz.saledistributemobility.view.ordering.operation.cancel.VerifyCancelViewModel;
import com.shoniz.saledistributemobility.view.ordering.operation.verify.VerifyFragment;

import javax.inject.Named;
import javax.inject.Scope;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class OrderVerifyActivityModule {


    @Provides
    @Named("VerificationSharedViewModel")
    ViewModelProvider.Factory getFactory2(VerificationSharedViewModel model){
        return new ViewModelProviderFactory<>(model);
    }

    @Provides
    ViewModelProvider.Factory getFactory(OperationViewModel model){
        return new ViewModelProviderFactory<>(model);
    }

    @Provides
    PagerAdapter provideOperationPagerAdapter(OperationActivity activity){
        return new PagerAdapter(activity.getSupportFragmentManager()
                , activity
                , VerifyFragment.newInstance()
                , VerifyCancelFragment.newInstance());
    }

}
