package com.shoniz.saledistributemobility.order.detail;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.utility.Common;

import java.util.List;


public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    private final List<OrderCompleteDetailModel> list;
    private final OnOrderDetailListener listener;
    public OrderDetailAdapter(List<OrderCompleteDetailModel> items, OnOrderDetailListener onOrderDetailListener) {
        list = items;
        this.listener = onOrderDetailListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_detail, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = list.get(position);
        if(list.get(position).IsBonus)
        holder.imgIsBonus.setImageResource(R.drawable.ic_medal);
        Context context=holder.mView.getContext();
//            holder.itemView.setBackgroundColor(holder.txtPriceCartoon.getContext().getResources().getColor(R.color.md_yellow_A200));
        holder.txtProductName.setText(list.get(position).ProductName);
        holder.txtRow.setText(""+list.get(position).Row);
        holder.txtQty.setText(
                (list.get(position).QtyCartoon > 0 ? list.get(position).QtyCartoon + " کارتن " : "")
                +(list.get(position).QtyPackage > 0 ? list.get(position).QtyPackage + " بسته " : "" )
        );
       // holder.txtQtyPackage.setText(context.getString(R.string.order_package)+ ": " + list.get(position).QtyPackage + "");
        holder.txtPrice.setText(Common.getCurrencyFormat(list.get(position).PriceCartoon + list.get(position).PricePackage) + " تومان");
        holder.txtShortcut.setText(list.get(position).Shortcut);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public List<OrderCompleteDetailModel> getList() {
        return list;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtProductName;
        public final TextView txtQty;
        public final TextView txtRow;
       // public final TextView txtQtyPackage;
        public final TextView txtPrice;
       // public final TextView txtPricePackage;
        public final TextView txtShortcut;
        public final ImageView imgIsBonus;
        public OrderCompleteDetailModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            txtProductName = view.findViewById(R.id.txtProductName);
            txtShortcut = view.findViewById(R.id.txtShortcut);
            txtQty = view.findViewById(R.id.txtQty);
            txtRow = view.findViewById(R.id.txtRow);
           // txtQtyPackage = view.findViewById(R.id.txtQtyPackage);
            txtPrice = view.findViewById(R.id.txtPrice);
            //txtPricePackage = view.findViewById(R.id.txtPricePackage);
            imgIsBonus = view.findViewById(R.id.imgIsBonus);
        }
    }

    public interface OnOrderDetailListener {

    }


}
