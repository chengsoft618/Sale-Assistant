package com.shoniz.saledistributemobility.infrastructure.recycler;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shoniz.saledistributemobility.view.base.BaseFragment;

public class RecyclerHelper<B extends RecyclerItemBindingBuilder>
        implements IRecyclerActivityNavigator {

    RecyclerView boundRecyclerView;
    RecyclerAdapter recyclerAdapter;
    RecyclerViewModel mvViewModel;
    AppCompatActivity activity;
    IRecyclerNavigator recyclerNavigator;

    public static <B extends RecyclerItemBindingBuilder> RecyclerHelper<B> build(
            RecyclerViewModel mViewModel,
            AppCompatActivity activity,
            IRecyclerNavigator iRecyclerNavigator,
            RecyclerView boundRecyclerView,
            Class<B> itemBindingClass) {
        return new RecyclerHelper<B>().init(
                mViewModel,
                activity,
                iRecyclerNavigator,
                boundRecyclerView,
                itemBindingClass);
    }

    private RecyclerHelper() {
    }

    private RecyclerHelper<B> init(
            RecyclerViewModel mViewModel,
            AppCompatActivity activity,
            IRecyclerNavigator iRecyclerNavigator,


            RecyclerView boundRecyclerView,
            Class<B> itemBindingClass) {

        this.activity = activity;
        this.recyclerNavigator = iRecyclerNavigator;
        this.mvViewModel = mViewModel;
        this.boundRecyclerView = boundRecyclerView;

        if (iRecyclerNavigator != null)
            mViewModel.setRecyclerNavigator(iRecyclerNavigator);
        mViewModel.setRecyclerActivityListener(this);
        this.recyclerAdapter = new RecyclerAdapter(mViewModel, itemBindingClass);
        this.boundRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        this.boundRecyclerView.setAdapter(this.recyclerAdapter);

        return this;
    }

    public RecyclerHelper<B> setSelectingModel(RecyclerViewModel.SelectingMode selectingMode) {
        mvViewModel.selectingMode.setValue(selectingMode);
        return this;
    }

//    public <B extends RecyclerItemBindingBuilder, M extends RecyclerToolbar> void configAdapter(RecyclerFragment activity,
//                                                                                                        RecyclerView boundRecyclerView,
//                                                                                                        Class<B> itemBindingClass, M toolbarListener) {
//        configAdapter(activity,
//                boundRecyclerView,
//                itemBindingClass);
//        this.toolbarListener = toolbarListener;
//    }


    @Override
    public void onModelChange() {
        recyclerAdapter.refreshRecycler();
//        if(recyclerListener != null)
//            recyclerListener.onModelChange();
    }

    @Override
    public void onSelectAll() {
        recyclerAdapter.RefreshRecyclerToSelectAll();
    }

    @Override
    public void onUnSelectAll() {
        recyclerAdapter.RefreshRecyclerToUnSelectAll();
    }


    @Override
    public void onLongClick() {
        //
        if (recyclerNavigator instanceof BaseFragment && mvViewModel.hasActiveActionMode)
            ((BaseFragment) recyclerNavigator).showActionMode();
    }

    @Override
    public void onSingleItemSelected() {
        recyclerAdapter.refreshRecyclerToSingleSelect();
    }

  /*  @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (getBaseActivity().getActionBar() != null)
            getBaseActivity().getActionBar().hide();
        super.onCreateOptionsMenu(menu, inflater);
    }*/


}
