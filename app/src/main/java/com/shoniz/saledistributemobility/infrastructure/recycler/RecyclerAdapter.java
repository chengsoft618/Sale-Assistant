package com.shoniz.saledistributemobility.infrastructure.recycler;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.shoniz.saledistributemobility.data.MetaData;
import com.shoniz.saledistributemobility.databinding.ItemEmptyViewBinding;

import java.util.List;

public class RecyclerAdapter<B extends RecyclerItemBindingBuilder> extends RecyclerBaseAdapter {
    RecyclerViewModel recyclerViewModel;
    int layoutId;
    Class<B> builderClass;

    public RecyclerAdapter(RecyclerViewModel recyclerViewModel, Class<B> builderClass) {
        this.recyclerViewModel = recyclerViewModel;
        this.builderClass = builderClass;

    }

    @Override
    public void onBindViewHolder(RecyclerBaseViewHolder holder, int position) {
        try {
            holder.onBind(position);
        } catch (Exception e) {
            Exception exception = e;
        }
    }

    @NonNull
    @Override
    public RecyclerBaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                try {
                    RecyclerItemBindingBuilder builder =
                            builderClass.
                                    getDeclaredConstructor(ViewGroup.class).
                                    newInstance(parent);
                    return new RecyclerViewHolderRecycler(builder);
                } catch (Exception e) {
                    Exception ed = e;
                }

            case VIEW_TYPE_EMPTY:
            default:
                try {
                    ItemEmptyViewBinding emptyViewBinding = ItemEmptyViewBinding
                            .inflate(LayoutInflater.from(parent.getContext()), parent, false);
                    return new EmptyViewHolderRecycler(emptyViewBinding);

                } catch (Exception ex) {
                    ex.getMessage();
                }
                return null;

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (recyclerViewModel.getRecyclerModelSize() == 0) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }

    @Override
    public int getItemCount() {
        if (recyclerViewModel.getRecyclerModelSize() == 0)
            return 1;
        else return recyclerViewModel.getRecyclerModelSize();
    }

    public void setAdapterModel(List<B> models) {
        recyclerViewModel.setRecyclerModels(models);
        notifyDataSetChanged();
    }

    public void clearItems() {
        recyclerViewModel.clearRecyclerModel();
    }

    private SelectingMode selectingMode = SelectingMode.normal;

    public void refreshRecycler() {
        selectingMode = SelectingMode.normal;
        notifyDataSetChanged();
    }
    public void RefreshRecyclerToSelectAll(){
        selectingMode = SelectingMode.select_all;
        notifyDataSetChanged();}
    public void RefreshRecyclerToUnSelectAll(){
        selectingMode = SelectingMode.un_select_all;
        notifyDataSetChanged();
    }
    public void refreshRecyclerToSingleSelect(){
        selectingMode = SelectingMode.single_selected;
        notifyDataSetChanged();
    }

    enum SelectingMode{
        normal,
        select_all,
        un_select_all,
        single_selected
    }

    class RecyclerViewHolderRecycler<T extends RecyclerItemBindingBuilder> extends RecyclerBaseViewHolder {
        T builder;

        public RecyclerViewHolderRecycler(T builder) {
            super(builder.getBinding().getRoot());
            this.builder = builder;
        }

        @Override
        public void onBind(final int position) {
            try {
                MetaData model = recyclerViewModel.getRecyclerModel(position);
                model.RowNumber = position + 1;
                builder.setModel(model)
                        .setListener(recyclerViewModel);

                if(recyclerViewModel instanceof IRecyclerListener)
                        builder.setRecyclerListener((IRecyclerListener) recyclerViewModel);

                if(selectingMode == SelectingMode.single_selected){
                    builder.setSelectedItemBackgroundColor(model.getId().equals(recyclerViewModel.selectedKey.getValue()));
                }
                else {
                    boolean isSelected;
                    if (selectingMode != SelectingMode.normal) {
                        isSelected = selectingMode == SelectingMode.select_all;
                    } else {
                        isSelected = recyclerViewModel.isSelected(model.getId());
                    }

                    if (recyclerViewModel.isSelectable()) {
                        builder.setVisibility(recyclerViewModel.getVisiblity())
                                .setChecked(isSelected);
                    }
                }

                 builder.ExecuteBinding();
            } catch (Exception e) {
                Exception exception = e;
            }

        }
    }
}
