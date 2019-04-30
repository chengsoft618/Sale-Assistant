package com.shoniz.saledistributemobility.view.message;

import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class MessageActivityModule {

    @Provides
    ViewModelProvider.Factory getFactory(MessageViewModel model){
        return new ViewModelProviderFactory<>(model);
    }
}
