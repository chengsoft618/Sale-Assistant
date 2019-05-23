package com.shoniz.saledistributemobility.view.ordering.unsent;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.utility.StringHelper;

import java.util.ArrayList;
import java.util.List;


public class UnsentOrderAdapter extends RecyclerView.Adapter<UnsentOrderAdapter.ViewHolder> {
    private boolean selectableMode = false;
    private boolean allSelectedMode = false;
    private final List<UnsentOrderModel> list;
    private final UnsentRequestListener listener;
    private List<Integer> selectedIds = new ArrayList<>();

    public UnsentOrderAdapter(List<UnsentOrderModel> items, UnsentRequestListener unsentRequestListener) {
        list = items;
        this.listener = unsentRequestListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_unsent_request, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = list.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.txtCustomerInfo.setText(
                    Html.fromHtml(
                            StringHelper.GenerateMessage(
                                    holder.mView.getResources().getString(
                                            R.string.request_list_customer_info_html
                                    ),
                                    list.get(position).PersonName,
                                    list.get(position).PersonID + ""
                            )
                            , Html.FROM_HTML_MODE_LEGACY
                    )
            );
        } else {
            holder.txtCustomerInfo.setText(
                    Html.fromHtml(StringHelper.GenerateMessage(
                            holder.mView.getResources().getString(
                                    R.string.request_list_customer_info_html
                            ),
                            list.get(position).PersonName,
                            list.get(position).PersonID+""
                            )
                    )
            );
        }

        if (isSelectableMode()) {
            holder.chkSelect.setVisibility(View.VISIBLE);
        }else
        {
            holder.chkSelect.setVisibility(View.GONE);
        }
        holder.chkSelect.setChecked( holder.mItem.isSelectedToUpdate);
        holder.txtMessage.setText( holder.mItem.ErrorMessage);
        holder.txtPath.setText( holder.mItem.PathName);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelectableMode()) {
                    holder.chkSelect.performClick();
                } else
                    listener.onClick(holder.mItem);
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!isSelectableMode()) {
                    if (null != listener) {
                        setSelectableMode(!isAllSelectedMode());
                        listener.onLongClick();
                        notifyDataSetChanged();
                    }
                }
                return true;
            }
        });

        holder.chkSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.chkSelect.isChecked()) {
                    holder.mItem.isSelectedToUpdate = true;
                    addSelectedList(holder.mItem.PersonID);
                } else {
                    if (selectedIds.contains(holder.mItem.PersonID))
                        selectedIds.remove(new Integer(holder.mItem.PersonID));
                    holder.mItem.isSelectedToUpdate = false;
                }
                listener.onItemCheck(holder.mItem);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtCustomerInfo;
        public final TextView txtPath;
        public final TextView txtMessage;
        public UnsentOrderModel mItem;
        public final CheckBox chkSelect;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtCustomerInfo = view.findViewById(R.id.txt_customer_info);
            chkSelect = view.findViewById(R.id.chkSelect);
            txtMessage = view.findViewById(R.id.txt_message);
            txtPath = view.findViewById(R.id.txt_path);
        }
    }

    public interface UnsentRequestListener {
        void onClick(UnsentOrderModel unsentOrderModel);
        void onItemCheck(UnsentOrderModel unsentOrderModel);
        void onLongClick();
        void onUpdateClick(List<Integer> selectedIds);
        void onDeleteClick(List<Integer> selectedIds);
    }

    public void clearSelected() {
        this.selectedIds.clear();
    }

    public List<UnsentOrderModel> getList() {
        return list;
    }

    public void addSelectedList(int id) {
        if(!this.selectedIds.contains(id)){
            this.selectedIds.add(id);
        }
    }

    public void removeSelectedList(int id) {
        for (int i = 0; i < selectedIds.size(); i++) {
            if (selectedIds.get(i) == id) {
                selectedIds.remove(i);
                break;
            }
        }
    }

    public boolean isSelectableMode() {
        return selectableMode;
    }

    public void setSelectableMode(boolean selectableMode) {
        this.selectableMode = selectableMode;

    }

    public void performUnsentOrderUpdateClick() {
        listener.onUpdateClick(selectedIds);
    }

    public void performUnsentOrderDeleteClick() {
        listener.onDeleteClick(selectedIds);
        notifyDataSetChanged();
    }

    public void setAllItemSelected(boolean b) {
        for (UnsentOrderModel p :
                getList()) {
            p.isSelectedToUpdate=b;
            addSelectedList(p.PersonID);
        }
    }

    public boolean isAllSelectedMode() {
        return allSelectedMode;
    }

    public void setAllSelectedMode(boolean allSelectedMode) {
        this.allSelectedMode = allSelectedMode;
    }

    public List<Integer> getSelectedList() {
        return this.selectedIds;
    }
    public void setSentRequest(UnsentOrderModel unsentOrderModel){
        list.remove(unsentOrderModel);
        notifyDataSetChanged();
    }
}
