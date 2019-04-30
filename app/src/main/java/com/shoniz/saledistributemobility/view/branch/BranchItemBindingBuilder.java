package com.shoniz.saledistributemobility.view.branch;

import android.view.ViewGroup;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.app.BranchData;
import com.shoniz.saledistributemobility.databinding.ItemBranchBinding;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerItemBindingBuilder;

public class BranchItemBindingBuilder extends RecyclerItemBindingBuilder<ItemBranchBinding,
        BranchData,
        IBranchRecyclerListener,
        BranchItemViewModel> {


    public BranchItemBindingBuilder(ViewGroup view) {
        super(view, BranchItemViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_branch;
    }

    @Override
    public int getViewModelNameInXml() {
        return BR.viewModel;
    }

    @Override
    public void setSelectedItemBackgroundColor(boolean isSelectedItem){
        if(isSelectedItem)
            getViewModel().setItemBackgroundColor(R.color.selectedRow);
        else
            getViewModel().setItemBackgroundColor(R.color.transparent);
    }
}
