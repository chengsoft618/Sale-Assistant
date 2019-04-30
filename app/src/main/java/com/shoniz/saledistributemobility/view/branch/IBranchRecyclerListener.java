package com.shoniz.saledistributemobility.view.branch;

import android.view.View;

import com.shoniz.saledistributemobility.infrastructure.recycler.IRecyclerListener;

public interface IBranchRecyclerListener extends IRecyclerListener {
    void onBranchItemClick(View view, int branchId);
}
