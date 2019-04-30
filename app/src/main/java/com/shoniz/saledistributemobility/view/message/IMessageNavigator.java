package com.shoniz.saledistributemobility.view.message;

import com.shoniz.saledistributemobility.data.model.message.MessageEntity;
import com.shoniz.saledistributemobility.infrastructure.recycler.IRecyclerNavigator;
import com.shoniz.saledistributemobility.view.base.viewmodel.INavigator;

import java.util.List;

public interface IMessageNavigator extends IRecyclerNavigator<IMessageRecyclerListener> {

    void updateRecycler();
}
