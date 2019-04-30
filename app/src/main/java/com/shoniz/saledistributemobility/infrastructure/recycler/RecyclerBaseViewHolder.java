package com.shoniz.saledistributemobility.infrastructure.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class RecyclerBaseViewHolder extends RecyclerView.ViewHolder {


    public RecyclerBaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void onBind(int position);
}