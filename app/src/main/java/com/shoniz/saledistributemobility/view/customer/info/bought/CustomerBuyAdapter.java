package com.shoniz.saledistributemobility.view.customer.info.bought;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.view.path.PathModel__;

import java.util.List;

public class CustomerBuyAdapter extends RecyclerView.Adapter<CustomerBuyAdapter.CustomerBuyViewHolder> {
    private final List<CustomerBuyModel> mValues;
    private final CustomerBuyAdapter.OnCustomerBuyListener onCustomerBuyListener;

    public CustomerBuyAdapter(List<CustomerBuyModel> items,
                              CustomerBuyAdapter.OnCustomerBuyListener onCustomerBuyListener) {
        mValues = items;
        this.onCustomerBuyListener = onCustomerBuyListener;
    }

    @Override
    public CustomerBuyAdapter.CustomerBuyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_customer_buy, parent, false);
        return new CustomerBuyAdapter.CustomerBuyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final CustomerBuyAdapter.CustomerBuyViewHolder holder, int position) {
        holder.model = mValues.get(position);
        holder.txtYearTypeID.setText(holder.model.YearType);
        holder.txtAmountBuyThisYear.setText(holder.txtAmountBuyThisYear.getText() + ": " + holder.model.AmountBuyThisYear);
        holder.txtQtyMainUnitBuyThisYear.setText(holder.txtQtyMainUnitBuyThisYear.getText() + ": " + holder.model.QtyMainUnitBuyThisYear);
        holder.txtQtySubUnitBuyThisYear.setText(holder.txtQtySubUnitBuyThisYear.getText() + ": " + holder.model.QtySubUnitBuyThisYear);
        holder.txtAmountBuyReturnedThisYear.setText(holder.txtAmountBuyReturnedThisYear.getText() + ": " + holder.model.AmountBuyReturnedThisYear);
        holder.txtQtyMainUnitBuyReturnedThisYear.setText(holder.txtQtyMainUnitBuyReturnedThisYear.getText() + ": " + holder.model.QtyMainUnitBuyReturnedThisYear);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class CustomerBuyViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtYearTypeID;
        public final TextView txtAmountBuyThisYear;
        public final TextView txtQtyMainUnitBuyThisYear;
        public final TextView txtQtySubUnitBuyThisYear;
        public final TextView txtAmountBuyReturnedThisYear;
        public final TextView txtQtyMainUnitBuyReturnedThisYear;

        public CustomerBuyModel model;

        public CustomerBuyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            txtYearTypeID = itemView.findViewById(R.id.txtYearTypeID);
            txtAmountBuyThisYear = itemView.findViewById(R.id.txtAmountBuyThisYear);
            txtQtyMainUnitBuyThisYear = itemView.findViewById(R.id.txtQtyMainUnitBuyThisYear);
            txtQtySubUnitBuyThisYear = itemView.findViewById(R.id.txtQtySubUnitBuyThisYear);
            txtAmountBuyReturnedThisYear = itemView.findViewById(R.id.txtAmountBuyReturnedThisYear);
            txtQtyMainUnitBuyReturnedThisYear = itemView.findViewById(R.id.txtQtyMainUnitBuyReturnedThisYear);


        }
    }

    public interface OnCustomerBuyListener {
        void onItemCheck(PathModel__ pathModel);
    }
}

