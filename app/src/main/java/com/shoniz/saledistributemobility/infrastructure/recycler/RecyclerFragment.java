package com.shoniz.saledistributemobility.infrastructure.recycler;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shoniz.saledistributemobility.view.base.BaseFragment;

public abstract class RecyclerFragment
        <T extends ViewDataBinding, V extends RecyclerViewModel>
        extends BaseFragment<T, V>
        implements IRecyclerActivityNavigator {

    RecyclerView boundRecyclerView;
    RecyclerAdapter recyclerAdapter;




    public <B extends RecyclerItemBindingBuilder> void configAdapter(
            RecyclerFragment activity,
            RecyclerView boundRecyclerView,
            Class<B> itemBindingClass) {
        if (activity instanceof IRecyclerNavigator)
            mViewModel.setRecyclerNavigator((IRecyclerNavigator) activity);
//        if (mViewModel instanceof IRecyclerListener)
//            mViewModel.setRecyclerListner((IRecyclerListener) mViewModel);
        mViewModel.setRecyclerActivityListener(this);
        this.boundRecyclerView = boundRecyclerView;
        this.boundRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerAdapter = new RecyclerAdapter(mViewModel, itemBindingClass);
        mViewModel.setRecyclerActivityListener(this);
        boundRecyclerView.setAdapter(recyclerAdapter);
//        if(mViewModel.canSelect())
//            mViewModel.setVisibleActionModel(false);
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
    public void onSelectAll(){
        recyclerAdapter.RefreshRecyclerToSelectAll();
    }
    @Override
    public void onUnSelectAll(){
        recyclerAdapter.RefreshRecyclerToUnSelectAll();
    }



    @Override
    public void onLongClick() {
        getViewModel().hasActiveActionMode =true;
        showActionMode();
    }

  /*  @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (getBaseActivity().getActionBar() != null)
            getBaseActivity().getActionBar().hide();
        super.onCreateOptionsMenu(menu, inflater);
    }*/


}
