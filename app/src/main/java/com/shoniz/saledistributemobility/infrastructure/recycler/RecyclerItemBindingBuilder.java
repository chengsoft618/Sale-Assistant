package com.shoniz.saledistributemobility.infrastructure.recycler;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shoniz.saledistributemobility.data.MetaData;


public abstract class RecyclerItemBindingBuilder<T extends ViewDataBinding, M extends MetaData, L extends IRecyclerListener, VM extends RecyclerItemViewModel> {


    Class<VM> viewModelClass;

    public VM getViewModel() {
        return viewModel;
    }

    VM viewModel;

    public RecyclerItemBindingBuilder(ViewGroup view, Class<VM> viewModelClass) {
        this.viewModelClass = viewModelClass;
        try {
            viewModel = viewModelClass.newInstance();
        } catch (Exception e) {
            e.getMessage();
        }


        binding =  DataBindingUtil.inflate(
                (LayoutInflater)view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE),
                getLayoutId(),
                view,
                false);
    }
    @LayoutRes
    public abstract int getLayoutId();
    public abstract int getViewModelNameInXml();
    public void setSelectedItemBackgroundColor(boolean isSelectedItem){}
    public  T binding;
    public T getBinding(){
        return binding;
    }
    public RecyclerItemBindingBuilder setModel(M model){
        viewModel.setModel(model);
        return this;
    }
    public  RecyclerItemBindingBuilder setVisibility(boolean visibility){
        viewModel.setVisible(visibility);
        return this;
    }
    public RecyclerItemBindingBuilder setChecked(boolean checked){
        viewModel.setSelected(checked);
        return this;
    }

    public RecyclerItemBindingBuilder setListener(IRecyclerItemListener listener){
        viewModel.setListener(listener);
        return this;
    }
    public RecyclerItemBindingBuilder setRecyclerListener(L listener){
        viewModel.setRecyclerListener(listener);
        return this;
    }

    public void ExecuteBinding(){
        binding.setVariable(getViewModelNameInXml(), viewModel);
        binding.executePendingBindings();
    }



}
