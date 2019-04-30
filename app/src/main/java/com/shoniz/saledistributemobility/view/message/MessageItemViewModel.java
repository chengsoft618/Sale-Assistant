package com.shoniz.saledistributemobility.view.message;

import android.arch.lifecycle.MutableLiveData;
import android.view.View;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.app.BranchData;
import com.shoniz.saledistributemobility.data.model.message.MessageData;
import com.shoniz.saledistributemobility.infrastructure.recycler.IRecyclerListener;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerItemViewModel;
import com.shoniz.saledistributemobility.view.branch.IBranchRecyclerListener;

public class MessageItemViewModel extends RecyclerItemViewModel<MessageData,
        IMessageRecyclerListener> {

    public boolean onDeleteClick(View v){
        getRecyclerListener().onDeleteItemClick(v, getModel().getId());
        super.onClick(v);
        return false;
    }
}
