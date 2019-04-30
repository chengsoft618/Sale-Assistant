package com.shoniz.saledistributemobility.view.ordering.operation.verify;

import android.view.ViewGroup;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.databinding.ItemVerifyBinding;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerItemBindingBuilder;

public class VerifyItemBindingBuilder extends RecyclerItemBindingBuilder<ItemVerifyBinding,
        OrderCompleteData,
        IVerifyRecyclerListener,
        VerifyItemViewModel> {


    public VerifyItemBindingBuilder(ViewGroup view) {
        super(view, VerifyItemViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_verify;
    }

    @Override
    public int getViewModelNameInXml() {
        return BR.viewModel;
    }

}
