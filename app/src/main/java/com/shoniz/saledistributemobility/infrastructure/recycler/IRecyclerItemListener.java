package com.shoniz.saledistributemobility.infrastructure.recycler;

import android.view.View;

public interface IRecyclerItemListener<I> {
    void onSelectedChange(I id, boolean status);
    void onLongClick(View v, I Id);
    void onClick(View v, I Id);
}
