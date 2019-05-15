package com.shoniz.saledistributemobility.view.path.customerlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.framework.StringHelper;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBasicModel;
import com.shoniz.saledistributemobility.view.entity.ReasonEntity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ferdos.s on 7/26/2017.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private List<CustomerBasicModel> list;
    private CustomerListListener listener;
    private Context mContext;

//    public Boolean isActivePathOfPerson = false;
    HashMap<Integer, String> unvisitedReasons=new HashMap<>();

    public CustomerAdapter(Context context, List<CustomerBasicModel> model,
                           CustomerListListener customerListListener, List<ReasonEntity> reasonEntities) {
        this.listener = customerListListener;
        list = model;
        mContext = context;
        for (ReasonEntity r : reasonEntities) {
            unvisitedReasons.put(r.NotSallReasonID, r.NotSallReasonText);
        }
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_customer, parent, false);
        return new CustomerViewHolder(view);
    }

//    private Context getContext() {
//        return mContext;
//    }

    @Override
    public void onBindViewHolder(@NonNull final CustomerViewHolder holder, int position) {
        holder.mItem = list.get(position);
        holder.txtAddress.setText(com.shoniz.saledistributemobility.utility.StringHelper.GenerateMessage(mContext, R.string.customer_card_address,
                holder.mItem.Address,
                holder.mItem.TelNo));
        holder.txtRow.setText("" + (position + 1));

        holder.txtPersonName.setText(holder.mItem.PersonID + " - " + holder.mItem.PersonName);

//        if (isSelectableMode()) {
//            holder.chkSelect.setVisibility(View.VISIBLE);
//        } else {
//            holder.chkSelect.setVisibility(View.GONE);
//        }

        holder.txtClassNames.setText(holder.mItem.ClassNames);

        if (holder.mItem.IsActive) {
            holder.constraintLayout.setBackgroundColor(holder.mView.getContext().getResources().getColor(R.color.transparent));
        } else {
            holder.constraintLayout.setBackgroundColor(holder.mView.getContext().getResources().getColor(R.color.md_grey_200));
        }

        String desc = "";
        int bgColor = 0;
        if (holder.mItem.UnIssuedOrderNo > 0) {
            desc = "درخواست فاکتور نشده دارد";
            if (holder.mItem.NeededCreditAmount > 0) {
                desc += " - کمبود اعتبار دارد: " + StringHelper.getCurrencyFormat( holder.mItem.NeededCreditAmount) + " ریال ";
                bgColor = holder.mView.getContext().getResources().getColor(R.color.md_red_50);
            } else bgColor = holder.mView.getContext().getResources().getColor(R.color.md_blue_50);
        } else if (holder.mItem.NotSellReasonID > 0) {
            bgColor = holder.mView.getContext().getResources().getColor(R.color.md_light_green_50);
            desc = "علت عدم ویزیت: " + unvisitedReasons.get(holder.mItem.NotSellReasonID);
        }

        holder.txtLastDaysOfVisit.setText(
                (holder.mItem.LastVisitDays > -1 ?
                    holder.mItem.LastVisitDays : " ? ") + " روز قبل");
        if(holder.mItem.daysCountAfter45DaysFromLastVisiting > 45)
            bgColor = holder.mView.getContext().getResources().getColor(R.color.md_light_green_50);

        holder.constraintLayout.setBackgroundColor(bgColor);
        holder.txt_order_description.setText(desc);

//        holder.chkSelect.setChecked(holder.mItem.isSelectedToUpdate);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(holder.mItem);
//                if (isSelectableMode()) {
//                    holder.chkSelect.performClick();
//                } else
            }
        });


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

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(holder.mItem);
                return true;
            }
        });
    }

//    public void clearSelected() {
//        this.selectedIds.clear();
//    }
//
//    public List<CustomerBasicModel> getList() {
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

//    public void performPathUpdateClick() {
//        listener.onUpdateClick(selectedIds);
//    }

//    public void setAllItemSelected(boolean b) {
//        for (CustomerBasicModel p :
//                getList()) {
//            p.isSelectedToUpdate = b;
//            addSelectedList(p.PathCode);
//        }
//    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtPersonName;
        public final TextView txtRow;
        public final TextView txtAddress;
        public final CheckBox chkSelect;
        public final TextView txtClassNames;
        public final TextView txt_order_description;
        public final TextView txtLastDaysOfVisit;
        CustomerBasicModel mItem;
        public final ConstraintLayout constraintLayout;


        public CustomerViewHolder(View view) {
            super(view);
            mView = view;
            chkSelect = view.findViewById(R.id.chkSelect);
            txtRow = view.findViewById(R.id.txtRow);
            txtPersonName = itemView.findViewById(R.id.txtPersonName);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            constraintLayout = view.findViewById(R.id.card_root);
            txtClassNames = view.findViewById(R.id.txt_class_names);
            txt_order_description = view.findViewById(R.id.txt_order_description);
            txtLastDaysOfVisit = view.findViewById(R.id.txtLastDaysOfVisit);
        }
    }

    public interface CustomerListListener {
        void onClick(CustomerBasicModel customerBasicModel);

//        void onUpdateClick(List<Integer> selectedIds);
//
//        void onItemCheck(CustomerBasicModel customerBasicModel);

        void onLongClick(CustomerBasicModel customerBasicModel);
    }


}
