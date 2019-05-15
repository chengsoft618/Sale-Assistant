package com.shoniz.saledistributemobility.view.branch;

import android.arch.lifecycle.MutableLiveData;
import android.view.View;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.app.BranchData;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerItemViewModel;

public class BranchItemViewModel extends RecyclerItemViewModel<BranchData,
    IBranchRecyclerListener> {

    public MutableLiveData<Integer> itemBackgroundColor = new MutableLiveData<>();

    public BranchItemViewModel() {
        this.itemBackgroundColor.setValue(R.color.md_white_1000);
    }

    public void setItemBackgroundColor(Integer itemColor) {
        this.itemBackgroundColor.setValue(itemColor);
    }

    public boolean onClick(View v){
        getRecyclerListener().onBranchItemClick(v, getModel().getId());
        super.onClick(v);
        return false;
    }
}
