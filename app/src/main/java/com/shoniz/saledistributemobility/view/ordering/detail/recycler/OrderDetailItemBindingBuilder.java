package com.shoniz.saledistributemobility.view.ordering.detail.recycler;

import android.view.ViewGroup;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.databinding.ItemVerifyCancelBinding;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerItemBindingBuilder;

public class OrderDetailItemBindingBuilder extends RecyclerItemBindingBuilder<ItemVerifyCancelBinding,
        OrderCompleteData,
        IOrderDetailRecyclerListener,
        OrderDetailItemViewModel> {


    public OrderDetailItemBindingBuilder(ViewGroup view) {
        super(view, OrderDetailItemViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_order_detail;
    }

    @Override
    public int getViewModelNameInXml() {
        return BR.viewModel;
    }
}
