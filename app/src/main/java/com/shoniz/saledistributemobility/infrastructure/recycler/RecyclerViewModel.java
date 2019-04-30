package com.shoniz.saledistributemobility.infrastructure.recycler;

import android.arch.lifecycle.MutableLiveData;
import android.view.View;

import com.shoniz.saledistributemobility.data.MetaData;
import com.shoniz.saledistributemobility.view.base.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerViewModel<M extends MetaData,
        I,
        N extends IRecyclerNavigator//,
        //L extends IRecyclerListener
        > extends BaseViewModel<N>
        implements IRecyclerItemListener<I> {

    public MutableLiveData<SelectingMode> selectingMode = new MutableLiveData<>();
    public MutableLiveData<Boolean> isVisibleItem = new MutableLiveData<>();

    public MutableLiveData<List<I>> selectedKeys = new MutableLiveData<>();
    public MutableLiveData<I> selectedKey = new MutableLiveData<>();

    private N recyclerNavigator;
    public IRecyclerActivityNavigator recyclerActivityListener;
//    private L recyclerListner;
    private IRecyclerToolbarListener recyclerToolbarListener;

    public abstract MutableLiveData<List<M>> getMutableList();

    public M getModelById(I id){
        for(M m : getMutableList().getValue()){
            //if(String.valueOf(m.getId()) == String.valueOf(id))
            if(id.equals(m.getId()))
                return m;
        }
        return null;
    }

    public void setRecyclerActivityListener(IRecyclerActivityNavigator recyclerActivityListener) {
        this.recyclerActivityListener = recyclerActivityListener;
    }

    public void setRecyclerNavigator(N recyclerNavigator) {
        this.recyclerNavigator = recyclerNavigator;
    }

    public RecyclerViewModel() {
        selectingMode.setValue(SelectingMode.MultipleSelect);
        isVisibleItem.setValue(false);
        List<I> list = new ArrayList<>();
        selectedKeys.setValue(list);
//        liveModels = new MutableLiveData<>();
    }

    public int getRecyclerModelSize() {
        if (getMutableList() == null || getMutableList().getValue() == null)
            return 0;
        return getRecyclerModels().size();
    }

    public void clearRecyclerModel() {
        getMutableList().setValue(new ArrayList<M>());
    }

    //protected abstract I getModelId(M m);

    public List<M> getRecyclerModels() {
        return getMutableList().getValue();
    }

    public M getRecyclerModel(int index) {
        return getMutableList().getValue().get(index);
    }

    public void setRecyclerModels(List<M> models) {
        getMutableList().setValue(models);
        if (recyclerActivityListener != null)
            recyclerActivityListener.onModelChange();
    }

    @Override
    public void onSelectedChange(I id, boolean status) {
        if (status)
            selectedKeys.getValue().add(id);
        else {
            selectedKeys.getValue().remove(id);
        }
        if (recyclerToolbarListener != null)
            recyclerToolbarListener.onSelectedChange(status);
    }


    public void selectAll() {
        selectedKeys.getValue().clear();
        recyclerActivityListener.onSelectAll();
    }

    public void unSelectAll() {
        selectedKeys.getValue().clear();
        recyclerActivityListener.onUnSelectAll();
    }

    public boolean isSelected(I id) {
        return selectedKeys.getValue().contains(id);
    }

    public List<I> getSelectedKeys() {
        return selectedKeys.getValue();
    }

    @Override
    public void onLongClick(View v, I id) {
        if (selectingMode.getValue() == SelectingMode.MultipleSelect)
            if (!getVisiblity()) {
                isVisibleItem.setValue(true);
                recyclerActivityListener.onModelChange();
            }
        if (recyclerNavigator != null)
            recyclerActivityListener.onLongClick();
    }

    @Override
    public void onClick(View v, I id) {
        if (selectingMode.getValue() == SelectingMode.SingleSelect) {
            selectedKey.setValue(id);
            recyclerActivityListener.onSingleItemSelected();
        }
    }

    public void hideSelectivity() {
        unSelectAll();
        isVisibleItem.setValue(false);
        recyclerActivityListener.onModelChange();
    }
    public void showSelectivity() {
        isVisibleItem.setValue(true);
        recyclerActivityListener.onModelChange();
    }

    public boolean isSelectable() {
        return selectingMode.getValue() == SelectingMode.MultipleSelect;
    }

    public boolean getVisiblity() {
        return isVisibleItem.getValue();
    }

    public void setVisibility(boolean status) {
        isVisibleItem.setValue(status);
    }

    public boolean onBackClicked() {
        if (getVisiblity()) {
            setVisibility(false);
            recyclerActivityListener.onModelChange();
            return false;
        }
        return true;
    }

    public void setRecyclerToolbarListener(IRecyclerToolbarListener recyclerToolbarListener) {
        this.recyclerToolbarListener = recyclerToolbarListener;
    }

    public void refreshRecycler(){
        recyclerActivityListener.onModelChange();
    }

    public enum SelectingMode {
        None,
        SingleSelect,
        MultipleSelect
    }
}
