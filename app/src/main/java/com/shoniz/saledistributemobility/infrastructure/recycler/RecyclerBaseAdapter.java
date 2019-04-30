package com.shoniz.saledistributemobility.infrastructure.recycler;

import android.support.v7.widget.RecyclerView;

import com.shoniz.saledistributemobility.databinding.ItemEmptyViewBinding;

public abstract class RecyclerBaseAdapter extends RecyclerView.Adapter<RecyclerBaseViewHolder> {
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    @Override
    public void onBindViewHolder(RecyclerBaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    protected class EmptyViewHolderRecycler extends RecyclerBaseViewHolder {//} implements RecyclerEmptyViewModel.EmptyItemViewModelListener {

        private final ItemEmptyViewBinding mBinding;

        public EmptyViewHolderRecycler(ItemEmptyViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            RecyclerEmptyViewModel emptyItemViewModel = new RecyclerEmptyViewModel();
            mBinding.setViewModel(emptyItemViewModel);
        }

//        @Override
//        public void onRetryClick() {
//            mListener.onRetryClick();
//        }
    }

//    public interface BaseAdapterListener {
//        void onRetryClick();
//    }

}
