package com.shoniz.saledistributemobility.order.unvisited__;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexBusiness;
import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.utility.StringHelper;

import java.io.IOException;
import java.util.List;


public class UnvisitedCustomerAdapter__ { //extends RecyclerView.Adapter<UnvisitedCustomerAdapter__.ViewHolder> {
//    private   List<UnvisitedCustomerModel__> customerModels;
//    private final OnRequestActionListener listener;
//    private List<ReasonModel__> reasonModels;
////    private List<UnvisitedCustomerModel__> selectedUnvisitedCustomers = new ArrayList<>() ;
//
//    public UnvisitedCustomerAdapter__(List<UnvisitedCustomerModel__> items, List<ReasonModel__> reasonModels, OnRequestActionListener onRequestActionListener) {
//        customerModels = items;
//        this.listener = onRequestActionListener;
//        this.reasonModels = reasonModels;
//    }
//
//    public  void setModel(List<UnvisitedCustomerModel__> items){
//        customerModels = items;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_unvisited_customer, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = customerModels.get(position);
//        holder.txtPath.setText(customerModels.get(position).PathName );
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            holder.txtCustomerInfo.setText(
//                    Html.fromHtml(
//                            StringHelper.GenerateMessage(
//                                    holder.mView.getResources().getString(
//                                            R.string.request_list_customer_info_html
//                                    ),
//                                    customerModels.get(position).PersonName,
//                                    customerModels.get(position).PersonID + ""
//                            )
//                            , Html.FROM_HTML_MODE_LEGACY
//                    )
//            );
//        } else {
//            holder.txtCustomerInfo.setText(
//                    Html.fromHtml(StringHelper.GenerateMessage(
//                            holder.mView.getResources().getString(
//                                    R.string.request_list_customer_info_html
//                            ),
//                            customerModels.get(position).PersonName,
//                            customerModels.get(position).PersonID+""
//                            )
//                    )
//            );
//        }
//        if(holder.mItem.IsSent){
//            holder.mView.setBackgroundColor(holder.mView.getContext().getResources().getColor(R.color.md_blue_50));
//        }else
//        {
//            holder.mView.setBackgroundColor(holder.mView.getContext().getResources().getColor(R.color.transparent));
//        }
//        holder.txtErrorMessage.setText(holder.mItem.ErrorMessage);
//        RadioButton radioButton = holder.radioGroup.findViewById(holder.mItem.reasonModel.NotSallReasonID);
//        holder.radioGroup.clearCheck();
//        radioButton.setChecked(true);
//
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(listener!=null)
//                    listener.onClick(holder.mItem);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return customerModels.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
//        public final TextView txtCustomerInfo;
//        public final TextView txtDate;
//        public final TextView txtPath;
//        public final TextView txtErrorMessage;
//        public final RadioGroup radioGroup;
//        public UnvisitedCustomerModel__ mItem;
//
//        public ViewHolder(View view) {
//            super(view);
//            mView = view;
//            txtCustomerInfo = view.findViewById(R.id.txt_customer_info);
//            txtDate = view.findViewById(R.id.txt_date);
//            txtPath = view.findViewById(R.id.txt_path);
//            txtErrorMessage = view.findViewById(R.id.txt_message);
//            radioGroup = view.findViewById(R.id.toggleGroup);
//
//            createRadioButton(this);
//        }
//    }
//
//    public interface OnRequestActionListener {
//        void onClick(UnvisitedCustomerModel__ unvisitedCustomerModel);
//    }
//
//
//    private void createRadioButton(final ViewHolder holder) {
//        final RadioButton[] rb = new RadioButton[reasonModels.size()];
//        RadioGroup rg = holder.radioGroup; //create the RadioGroup
//        rg.setOrientation(RadioGroup.HORIZONTAL);//or RadioGroup.VERTICAL
//        for (int i = 0; i < reasonModels.size(); i++) {
//            rb[i] = new RadioButton(holder.mView.getContext());
//            rb[i].setText(reasonModels.get(i).NotSallReasonText);
//            rb[i].setId(reasonModels.get(i).NotSallReasonID);
//
////            Drawable buttonDrawable = holder.mView.getContext().getResources().getDrawable(R.drawable.radio_selector);
////            buttonDrawable.mutate();
////            rb[i].setBackgroundDrawable(buttonDrawable);
//
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            lp.setMargins(15, 0, 15, 0);
//            lp.weight = 1.0f;
//            lp.gravity = Gravity.RIGHT;
//            rb[i].setLayoutParams(lp);
//
//            rb[i].setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.radio_selector, 0);
//            rb[i].setButtonDrawable(R.color.transparent);
//
//            if (i == 0)
//                rb[i].setChecked(true);
//            rg.addView(rb[i]);
//
//            rb[i].setOnClickListener(new RadioGroup.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    holder.mItem.reasonModel.NotSallReasonID = holder.radioGroup.getCheckedRadioButtonId();
//                    try {
//                        CardIndexBusiness.insertUnVisitedReason(holder.mView.getContext(), holder.mItem.PersonID, holder.mItem.reasonModel.NotSallReasonID);
//                    } catch (IOException e) {
//                        new HandleException(holder.mView.getContext(), e);
//                    }
//
//                }
//            });
//        }
//        //ll.addView(rg);//you add the whole RadioGroup to the layout
//
//    }
//
//    public List<UnvisitedCustomerModel__> getUnvisitedList(){
//        return customerModels;
//    }

}
