package com.shoniz.saledistributemobility.view.customer.info.cheque;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.view.path.PathModel__;

import java.util.List;

/**
 * Created by ferdos.s on 7/30/2017.
 */

public class CustomerChequeAdapter extends RecyclerView.Adapter<CustomerChequeAdapter.CustomerChequeViewHolder>{
    private final List<CustomerChequeModel> mValues;
    private final CustomerChequeAdapter.OnCustomerChequeListener onCustomerChequeListener;

    public CustomerChequeAdapter(List<CustomerChequeModel> items,
                                 CustomerChequeAdapter.OnCustomerChequeListener onCustomerChequeListener) {
        mValues = items;
        this.onCustomerChequeListener = onCustomerChequeListener;
    }

    @Override
    public CustomerChequeAdapter.CustomerChequeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_customer_cheque, parent, false);
        return new CustomerChequeAdapter.CustomerChequeViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final CustomerChequeAdapter.CustomerChequeViewHolder holder, int position) {
        holder.model = mValues.get(position);
        holder.txtBankBranchName.setText("شعبه: " + holder.model.BankBranchName);
        holder.txtBankName.setText(holder.model.BankName);
        holder.txtDueDate.setText("تاریخ سررسید: " + holder.model.DueDate);
        holder.txtPaymentDate.setText("تاریخ دریافت: " + holder.model.PaymentDate);
        holder.txtTotalPayment.setText("مبلغ: " + holder.model.TotalPayment);
        holder.txtSerialNumber.setText("سریال چک: " + holder.model.SerialNumber);
        holder.txtPersonName.setText("بنام: " + holder.model.PersonName);
        holder.txtReasonName.setText("دلیل : " + holder.model.ReasonName);
        holder.txtTypeCheque.setText("نوع چک: " + holder.model.TypeCheque);
        switch (holder.model.ConditionID) {
            case 1:
            case 6:
                holder.clRoot.setBackgroundColor(holder.mView.getResources().getColor(R.color.transparent));
                break;
            case 3:
                holder.clRoot.setBackgroundColor(holder.mView.getResources().getColor(R.color.md_yellow_100));
                break;
            case 4:
            case 7:
                holder.clRoot.setBackgroundColor(holder.mView.getResources().getColor(R.color.md_green_100));
                break;
            case 5:
                holder.clRoot.setBackgroundColor(holder.mView.getResources().getColor(R.color.md_red_100));
                break;
        }


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class CustomerChequeViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtSerialNumber;
        public final TextView txtBankName;
        public final TextView txtBankBranchName;
        public final TextView txtPersonName;
        public final TextView txtTotalPayment;
        public final TextView txtDueDate;
        public final TextView txtPaymentDate;
        public final TextView txtReasonName;
        public final TextView txtTypeCheque;
        public final ConstraintLayout clRoot;

        public CustomerChequeModel model;

        public CustomerChequeViewHolder (View itemView) {
            super(itemView);
            mView = itemView;
            clRoot = itemView.findViewById(R.id.cl_root);
            txtSerialNumber = itemView.findViewById(R.id.txtSerialNumber);
            txtBankName = itemView.findViewById(R.id.txtBankName);
            txtBankBranchName = itemView.findViewById(R.id.txtBankBranchName);
            txtPersonName = itemView.findViewById(R.id.txtPersonName);
            txtTotalPayment = itemView.findViewById(R.id.txtTotalPayment);
            txtDueDate = itemView.findViewById(R.id.txtDueDate);
            txtPaymentDate = itemView.findViewById(R.id.txtPaymentDate);
            txtReasonName = itemView.findViewById(R.id.txtReasonName);
            txtTypeCheque = itemView.findViewById(R.id.txtTypeCheque);
        }
    }

    public interface OnCustomerChequeListener {
        void onItemCheck(PathModel__ pathModel);
    }


}
