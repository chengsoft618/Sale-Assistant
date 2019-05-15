package com.shoniz.saledistributemobility.view.path.pathlist;

import android.view.ViewGroup;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.path.PathListData;
import com.shoniz.saledistributemobility.databinding.ItemPathBinding;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerItemBindingBuilder;

public class PathItemBindingBuilder extends RecyclerItemBindingBuilder<ItemPathBinding,
        PathListData,
        IPathRecyclerListener,
        PathItemViewModel> {

    public PathItemBindingBuilder(ViewGroup view) {
        super(view, PathItemViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_path;
    }

    @Override
    public int getViewModelNameInXml() {
        return BR.viewModel;
    }
}
