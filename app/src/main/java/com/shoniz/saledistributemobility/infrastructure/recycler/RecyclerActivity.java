package com.shoniz.saledistributemobility.infrastructure.recycler;

import android.databinding.ViewDataBinding;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shoniz.saledistributemobility.view.base.BaseActivity;

public abstract class RecyclerActivity
        <T extends ViewDataBinding, V extends RecyclerViewModel>
        extends BaseActivity<V, T>
        implements IRecyclerActivityNavigator {

    RecyclerView boundRecyclerView;
    RecyclerAdapter recyclerAdapter;
    RecyclerToolbar toolbar;

    ActionMode actionMode;
    //IRecyclerNavigator recyclerListener;

    public <B  extends RecyclerItemBindingBuilder> void configAdapter(
            RecyclerActivity activity,
            RecyclerView boundRecyclerView,
            Class<B> itemBindingClass) {
        if(activity instanceof IRecyclerNavigator)
            mViewModel.setRecyclerNavigator((IRecyclerNavigator)activity);
//        if(mViewModel instanceof IRecyclerListener)
//            mViewModel.setRecyclerListner((IRecyclerListener)mViewModel);
        mViewModel.setRecyclerActivityListener(this);
        this.boundRecyclerView = boundRecyclerView;
        this.boundRecyclerView.setLayoutManager( new LinearLayoutManager(activity));
        recyclerAdapter = new RecyclerAdapter(mViewModel, itemBindingClass);
        mViewModel.setRecyclerActivityListener(this);
        boundRecyclerView.setAdapter(recyclerAdapter);
    }




    public <M extends RecyclerToolbar> void setToolbar(M toolbar){
        this.toolbar = toolbar;
    }

    @Override
    public void onModelChange() {
        recyclerAdapter.refreshRecycler();
        //if(recyclerListener != null)
            //recyclerListener.onModelChange();
    }

    @Override
    public void onBackPressed() {
        if (mViewModel.onBackClicked()) finish();
    }

    @Override
    public void onLongClick(){
        actionMode = startSupportActionMode(toolbar);
        //if(recyclerListener != null)
            //recyclerListener.onLongClick();
    }

//    public void setRecyclerNavigator(IRecyclerNavigator recyclerListener) {
//        this.recyclerListener = recyclerListener;
//    }
}
