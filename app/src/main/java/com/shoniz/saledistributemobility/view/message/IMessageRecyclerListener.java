package com.shoniz.saledistributemobility.view.message;

import android.view.View;

import com.shoniz.saledistributemobility.infrastructure.recycler.IRecyclerListener;

public interface IMessageRecyclerListener extends IRecyclerListener {
    void onDeleteItemClick(View view, int SendId);
}