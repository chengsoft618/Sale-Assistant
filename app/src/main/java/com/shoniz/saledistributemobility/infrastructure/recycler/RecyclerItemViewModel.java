package com.shoniz.saledistributemobility.infrastructure.recycler;

import android.view.View;

import com.shoniz.saledistributemobility.data.MetaData;
import com.shoniz.saledistributemobility.view.base.viewmodel.BaseViewModel;

public abstract class RecyclerItemViewModel<M extends MetaData,
        //I,
        L extends IRecyclerListener> extends BaseViewModel {
    private boolean isSelected = false;
    private boolean isVisible = false;
    private IRecyclerItemListener listener;
    private L recyclerListener;
    private M model;

    public void setRecyclerListener(L recyclerListener) {
        this.recyclerListener = recyclerListener;
    }

    public L getRecyclerListener() {
        return recyclerListener;
    }

    public void setModel(M model){
        this.model = model;
    }
    public M getModel(){
        return model;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    //public abstract I getId();

    public IRecyclerItemListener getListener() {
        return listener;
    }

    public void setListener(IRecyclerItemListener listener) {
        this.listener = listener;
    }

    public boolean onLongClick(View v){
        if(getListener() != null)
            //getListener().onLongClick(getId());
            getListener().onLongClick(v, model.getId());

        return false;
    }

    public boolean onClick(View v){
        if(getListener() != null)
            //getListener().onLongClick(getId());
            getListener().onClick(v, model.getId());

        return false;
    }

    public void onCheckedChanged(boolean checked) {
        if(getListener() != null)
//            getListener().onSelectedChange(getId(), checked);
            getListener().onSelectedChange(model.getId(), checked);
    }
}
