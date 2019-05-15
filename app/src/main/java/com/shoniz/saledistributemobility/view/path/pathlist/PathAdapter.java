package com.shoniz.saledistributemobility.view.path.pathlist;

import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.view.path.PathModel__;

import java.util.LinkedList;
import java.util.List;


public class PathAdapter{
//    extends
//} RecyclerView.Adapter<PathAdapter.ViewHolder> {
//    private boolean selectableMode = false;
//    private boolean allSelectedMode = false;
//    private List<PathModel__> list;
////    private final OnPathListener listener;
//
//    private List<Integer> selectedIds = new LinkedList<>();
//
////    public PathAdapter(List<PathModel__> items, OnPathListener onPathListener) {
////        list = items;
////        selectableMode = false;
////        this.listener = onPathListener;
////    }
//
//    public void setModel(List<PathModel__> items) {
//        this.list = items;
//    }
//
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_path, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    public void onBindViewHolder(@NonNull final ViewHolder holder, int xx) {
//        int position = holder.getAdapterPosition();
//        final PathModel__ pathModel = list.get(position);
//        holder.mItem = pathModel;
//        holder.txtPathDescription.setText(pathModel.PathDescription);
//        holder.txtPathName.setText(pathModel.PathCode + " - " + list.get(position).PathName);
//        holder.txtRow.setText("" + (position + 1));
//        holder.txtWholesalerCount.setText("عمده " + pathModel.WholesalerCount);
//        holder.txtRetailerCount.setText("خرده " + pathModel.RetailerCount);
//        holder.txtAllCustomer.setText("کل مشتریان" + pathModel.CustomerCount);
//
//
//        if (isSelectableMode()) {
//            holder.chkSelect.setVisibility(View.VISIBLE);
//        } else {
//            holder.chkSelect.setVisibility(View.GONE);
//        }
//        holder.chkSelect.setChecked(holder.mItem.isSelectedToUpdate);
//
//        if (holder.mItem.IsActive)
//            holder.constraintLayout.setBackgroundColor(holder.itemView.getResources().getColor(R.color.item_highlight));
//        else
//            holder.constraintLayout.setBackgroundColor(holder.itemView.getResources().getColor(R.color.transparent));
//
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isSelectableMode()) {
//                    holder.chkSelect.performClick();
//                } else
//                    listener.onClick(holder.mItem);
//            }
//        });
//
//
//        holder.chkSelect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (holder.chkSelect.isChecked()) {
//                    holder.mItem.isSelectedToUpdate = true;
//                    selectedIds.add(holder.mItem.PathCode);
//                } else {
//                    if (selectedIds.contains(holder.mItem.PathCode))
//                        selectedIds.remove(new Integer(holder.mItem.PathCode));
//                    holder.mItem.isSelectedToUpdate = false;
//                }
//                listener.onItemCheck(holder.mItem);
//            }
//        });
//
//
//        holder.imgDownload.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                listener.onUpdateClick(holder.mItem);
//            }
//        });
//
//        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (listener != null)
//                    listener.onLongClick(holder.mItem);
//                return true;
//            }
//        });
//    }
//
//    public void notifyDataSetChangedHandler() {
//        Handler handler = new Handler();
//
//        final Runnable r = new Runnable() {
//            public void run() {
//                notifyDataSetChanged();
//            }
//        };
//        handler.post(r);
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    public void clearSelected() {
//        this.selectedIds.clear();
//    }
//
//    public List<PathModel__> getList() {
//        return list;
//    }
//
//    public void addSelectedList(int id) {
//        this.selectedIds.add(id);
//    }
//
//    public void removeSelectedList(int id) {
//        if (selectedIds.contains(id))
//            this.selectedIds.remove(id);
//    }
//
//    public boolean isSelectableMode() {
//        return selectableMode;
//    }
//
//    public void setSelectableMode(boolean selectableMode) {
//        this.selectableMode = selectableMode;
//
//    }
//
//    public boolean isAllSelectedMode() {
//        return allSelectedMode;
//    }
//
//    public void setAllSelectedMode(boolean allSelectedMode) {
//        this.allSelectedMode = allSelectedMode;
//    }
//
//    public List<Integer> getSelectedList() {
//        return this.selectedIds;
//    }
//
////    public void performPathUpdateClick() {
////        listener.onUpdateClick(selectedIds);
////    }
//
//    public void setAllItemSelected(boolean b) {
//        for (PathModel__ p :
//                getList()) {
//            p.isSelectedToUpdate = b;
//            addSelectedList(p.PathCode);
//        }
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
//        public final TextView txtPathName;
//        public final TextView txtRow;
//        public final TextView txtPathDescription;
//        public final TextView txtAllCustomer;
//        public final TextView txtRetailerCount;
//        public final TextView txtWholesalerCount;
//        public final ConstraintLayout constraintLayout;
//        public final CheckBox chkSelect;
//        public final ImageView imgDownload;
//        public PathModel__ mItem;
//
//        public ViewHolder(View view) {
//            super(view);
//            mView = view;
//            txtPathName = view.findViewById(R.id.txt_order_no);
//            txtRow = view.findViewById(R.id.txtRow);
//            txtAllCustomer = view.findViewById(R.id.txtCustomerCount);
//            txtRetailerCount = view.findViewById(R.id.txtRetailerCount);
//            txtWholesalerCount = view.findViewById(R.id.txtWholesalerCount);
//            txtPathDescription = view.findViewById(R.id.txt_path_description);
//            constraintLayout = view.findViewById(R.id.whole_path_card);
//            chkSelect = view.findViewById(R.id.chkSelect);
//            imgDownload = view.findViewById(R.id.img_download);
//        }
//    }
}
