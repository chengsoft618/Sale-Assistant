package com.shoniz.saledistributemobility.view.message;

import android.view.ViewGroup;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.message.MessageData;
import com.shoniz.saledistributemobility.databinding.ItemMessageBinding;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerItemBindingBuilder;

public class MessageItemBindingBuilder extends RecyclerItemBindingBuilder<ItemMessageBinding,
        MessageData,
        IMessageRecyclerListener,
        MessageItemViewModel> {


    public MessageItemBindingBuilder(ViewGroup view) {
        super(view, MessageItemViewModel.class);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_message;
    }

    @Override
    public int getViewModelNameInXml() {
        return BR.viewModel;
    }

}
