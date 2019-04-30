package com.shoniz.saledistributemobility.view.ordering.detail.printissue;

import android.arch.lifecycle.ViewModelProvider;

import com.shoniz.saledistributemobility.view.base.viewmodel.ViewModelProviderFactory;
import com.shoniz.saledistributemobility.view.branch.BranchViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class PrintIssueActivityModule {

    @Provides
    ViewModelProvider.Factory getFactory(PrintIssueViewModel model){
        return new ViewModelProviderFactory<>(model);
    }



}
