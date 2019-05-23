package com.shoniz.saledistributemobility.view.ordering.sent;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.utility.StringHelper;

import java.util.List;


public class SentOrderAdapter extends RecyclerView.Adapter<SentOrderAdapter.ViewHolder> {
    private List<SentOrderModel> list;
    private final OnRequestActionListener listener;

    public SentOrderAdapter(List<SentOrderModel> items, OnRequestActionListener onRequestActionListener) {
        list = items;
        this.listener = onRequestActionListener;
    }

    public void updateList(List<SentOrderModel> items){
        list = items;
        notifyDataSetChanged();
    }
    public void updateListOnly(List<SentOrderModel> items){
        list = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ordered_request, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = list.get(position);
        holder.txtDate.setText( holder.mItem .RegDate);
        holder.txtRow.setText(""+ holder.mItem .Row);
        if( holder.mItem .ErrorMessage != null && holder.mItem .ErrorMessage != "" && ! holder.mItem .ErrorMessage.isEmpty()) {
            holder.txtErrorMessage.setText( holder.mItem .ErrorMessage);
            holder.txtErrorMessage.setVisibility(View.VISIBLE);
        }else{
            holder.txtErrorMessage.setVisibility(View.GONE);
        }
        holder.txtWeight.setText(StringHelper.GenerateMessage("وزن: ({1}){0} کیلوگرم",holder.mItem.OrderWeight,holder.mItem.OrderNetWeight));
        holder.txtPrice.setText(StringHelper.GenerateMessage("{0} ريال",holder.mItem.Price));
        holder.txtOrderNo.setText(""+ holder.mItem.OrderNo);
        holder.txtCustomerType.setText( holder.mItem.CustomerType);
        holder.txtQtyCartoon.setText(StringHelper.GenerateMessage("کارتن: {0}", holder.mItem.QtyCartoon));
        holder.txtQtyPackage.setText(StringHelper.GenerateMessage("بسته: {0}", holder.mItem.QtyPackage));
        holder.txtPathName.setText(  holder.mItem.PathName);
        holder.txtVarity.setText(StringHelper.GenerateMessage("تنوع: {0}",holder.mItem.Varity));
        if(holder.mItem.IsIssued) holder.btnIsIssued.setVisibility(View.VISIBLE);
        else holder.btnIsIssued.setVisibility(View.INVISIBLE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.txtCustomerInfo.setText(
                    Html.fromHtml(
                            StringHelper.GenerateMessage(
                                    holder.mView.getResources().getString(
                                            R.string.request_list_customer_info_html
                                    ),
                                    holder.mItem.PersonName,
                                    holder.mItem.PersonID + ""
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
                            holder.mItem.PersonName,
                            holder.mItem.PersonID+""
                            )
                    )
            );
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v,holder.mItem);
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongClick(v,holder.mItem);
                return true;
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
        public final TextView txtPrice;
        public final TextView txtRow;
        public final TextView txtWeight;
        public final TextView txtDate;
        public final TextView txtOrderNo;
        public final TextView txtErrorMessage;
        public final TextView txtVarity;
        public final TextView txtQtyCartoon;
        public final TextView txtQtyPackage;
        public final TextView txtCustomerType;
        public final TextView txtPathName;
        public final Button btnIsIssued;
        public SentOrderModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            txtRow = view.findViewById(R.id.txt_row);
            txtCustomerInfo = view.findViewById(R.id.txt_customer_info);
            txtPrice = view.findViewById(R.id.txt_price);
            txtWeight = view.findViewById(R.id.txt_weight);
            txtDate = view.findViewById(R.id.txt_date);
            txtOrderNo = view.findViewById(R.id.txt_order_no);
            txtVarity = view.findViewById(R.id.txt_variety);
            txtCustomerType = view.findViewById(R.id.txtCustomerType);
            txtQtyCartoon = view.findViewById(R.id.txtQtyCarton);
            txtQtyPackage = view.findViewById(R.id.txtQtyPackage);
            txtPathName = view.findViewById(R.id.txtPathName);
            txtErrorMessage = view.findViewById(R.id.txt_message);
            btnIsIssued = view.findViewById(R.id.btnIsIssued);
        }
    }

    public interface OnRequestActionListener {
        void onClick(View v,SentOrderModel sentOrderModel);
        void onLongClick(View v,SentOrderModel sentOrderModel);
        }


}
