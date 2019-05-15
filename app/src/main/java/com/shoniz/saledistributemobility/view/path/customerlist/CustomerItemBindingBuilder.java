package com.shoniz.saledistributemobility.view.path.customerlist;

import android.view.ViewGroup;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.customer.CustomerData;
import com.shoniz.saledistributemobility.databinding.ItemCustomerBinding;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerItemBindingBuilder;

public class CustomerItemBindingBuilder extends RecyclerItemBindingBuilder<ItemCustomerBinding,
        CustomerData,
        ICustomerRecyclerListener,
        CustomerItemViewModel> {

    public CustomerItemBindingBuilder(ViewGroup view) {
        super(view, CustomerItemViewModel.class);
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
