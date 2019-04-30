package com.shoniz.saledistributemobility.view.ordering.operation.cancel;

import android.view.ViewGroup;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.databinding.ItemVerifyCancelBinding;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerItemBindingBuilder;

public class VerifyCancelItemBindingBuilder extends RecyclerItemBindingBuilder<ItemVerifyCancelBinding,
        OrderCompleteData,
        IVerifyCancelRecyclerListener,
        VerifyCancelItemViewModel> {


    public VerifyCancelItemBindingBuilder(ViewGroup view) {
        super(view, VerifyCancelItemViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_verify_cancel;
    }

    @Override
    public int getViewModelNameInXml() {
        return BR.viewModel;
    }
}
