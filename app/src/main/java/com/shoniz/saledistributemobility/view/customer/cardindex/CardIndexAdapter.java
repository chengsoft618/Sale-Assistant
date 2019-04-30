package com.shoniz.saledistributemobility.view.customer.cardindex;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.framework.Utility;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.SimpleAsyncTask;
import com.shoniz.saledistributemobility.utility.StringHelper;
import com.shoniz.saledistributemobility.utility.dialog.OnProgressUpdate;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;
import com.shoniz.saledistributemobility.view.base.BaseFragment;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;


public class CardIndexAdapter extends RecyclerView.Adapter<CardIndexAdapter.CustomerCardIndexViewHolder> {
    private final CardIndexAdapter.OnCustomerCardIndexListener onCustomerCardIndexListener;
    private List<CardIndexDetailModel> mValues;
    private Hashtable<Integer, Integer> shortcutsAvailability;
    private BaseFragment baseFragment;
    private boolean isProductDetailsShown = false;

    public CardIndexAdapter(List<CardIndexDetailModel> items,
                            CardIndexAdapter.OnCustomerCardIndexListener onCustomerCardIndexListener,
                            Hashtable<Integer, Integer> shortcutsAvailability,
                            BaseFragment baseFragment) {
        mValues = items;
        this.onCustomerCardIndexListener = onCustomerCardIndexListener;
        this.shortcutsAvailability = shortcutsAvailability;
        this.baseFragment = baseFragment;
    }

    public void setModel(List<CardIndexDetailModel> models, Hashtable<Integer, Integer> temp) {
        applyDiffUtillUpdate(models, temp);
//            mValues = models;
//            notifyDataSetChanged();
    }

    public void setModelAndNotify(List<CardIndexDetailModel> models, Hashtable<Integer, Integer> temp) {
//        applyDiffUtillUpdate(models, temp);
        mValues = models;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(mValues.get(position).Shortcut);
    }

    @Override
    public CardIndexAdapter.CustomerCardIndexViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_customer_card_index, parent, false);
        return new CardIndexAdapter.CustomerCardIndexViewHolder(view);
    }

    public void toggleCardindexView() {
        isProductDetailsShown = !isProductDetailsShown;
        notifyDataSetChanged();
    }

    public boolean isProductDetailsShown() {
        return isProductDetailsShown;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerCardIndexViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(CardIndexAdapter.CustomerCardIndexViewHolder holder, int pos) {
        CardIndexDetailModel model = null;
        try {
            model = mValues.get(pos);
            int dateTextColor = holder.itemView.getResources().getColor(R.color.md_blue_100);
            int transparentColor = holder.itemView.getResources().getColor(R.color.transparent);


            if (isProductDetailsShown) {
                holder.txtProductName.setVisibility(View.VISIBLE);
                holder.txtPrice.setVisibility(View.VISIBLE);
                holder.txtExistenceCarton.setVisibility(View.GONE);
                holder.txtTour1Carton.setVisibility(View.GONE);
                holder.txtTour2Carton.setVisibility(View.GONE);
                holder.txtTour3Carton.setVisibility(View.GONE);
                holder.imgCartonPackage.setVisibility(View.GONE);
            } else {
                holder.txtProductName.setVisibility(View.GONE);
                holder.txtPrice.setVisibility(View.GONE);
                holder.txtExistenceCarton.setVisibility(View.VISIBLE);
                holder.txtTour1Carton.setVisibility(View.VISIBLE);
                holder.txtTour2Carton.setVisibility(View.VISIBLE);
                holder.txtTour3Carton.setVisibility(View.VISIBLE);
                holder.imgCartonPackage.setVisibility(View.VISIBLE);
            }

            if (model.IsSelected) {
                holder.mView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.md_yellow_100));
            } else {
                holder.mView.setBackgroundColor(transparentColor);
            }


            holder.txtCode.setText(

                    StringHelper.GenerateMessage("{0}",
                            model.Shortcut));
            if (model.RequestCarton > 0 || model.RequestPackage > 0) {
                holder.txtRequestCarton.setText(
                        StringHelper.GenerateMessage("{0}|{1}",
                                model.RequestCarton,
                                model.RequestPackage));
                holder.txtRequestCarton.setBackgroundColor(dateTextColor);
            } else {
                holder.txtRequestCarton.setText("");
                holder.txtRequestCarton.setBackgroundColor(transparentColor);
            }


            if (model.ExistenceCarton > 0 || model.ExistencePackage > 0) {
                holder.txtExistenceCarton.setText(
                        StringHelper.GenerateMessage("{0}|{1}",
                                model.ExistenceCarton,
                                model.ExistencePackage));
                holder.txtExistenceCarton.setBackgroundColor(dateTextColor);
            } else {
                holder.txtExistenceCarton.setText("");
                holder.txtExistenceCarton.setBackgroundColor(transparentColor);
            }


            if (model.CardIndexHistory.QtyCarton1 > 0 || model.CardIndexHistory.QtyPackge1 > 0) {
                holder.txtTour1Carton.setText(
                        StringHelper.GenerateMessage("{0}|{1}",
                                model.CardIndexHistory.QtyCarton1,
                                model.CardIndexHistory.QtyPackge1));
                holder.txtTour1Carton.setBackgroundColor(dateTextColor);
            } else {
                holder.txtTour1Carton.setText("");
                holder.txtTour1Carton.setBackgroundColor(transparentColor);
            }

            if (model.CardIndexHistory.QtyCarton2 > 0 || model.CardIndexHistory.QtyPackge2 > 0) {
                holder.txtTour2Carton.setText(
                        StringHelper.GenerateMessage("{0}|{1}",
                                model.CardIndexHistory.QtyCarton2,
                                model.CardIndexHistory.QtyPackge2));
                holder.txtTour2Carton.setBackgroundColor(dateTextColor);
            } else {
                holder.txtTour2Carton.setText("");
                holder.txtTour2Carton.setBackgroundColor(transparentColor);
            }

            if (model.CardIndexHistory.QtyCarton3 > 0 || model.CardIndexHistory.QtyPackge3 > 0) {
                holder.txtTour3Carton.setText(
                        StringHelper.GenerateMessage("{0}|{1}",
                                model.CardIndexHistory.QtyCarton3,
                                model.CardIndexHistory.QtyPackge3));
                holder.txtTour3Carton.setBackgroundColor(dateTextColor);
            } else {
                holder.txtTour3Carton.setText("");
                holder.txtTour3Carton.setBackgroundColor(transparentColor);
            }


            holder.txtProductName.setText(model.ProductName);
            holder.txtPrice.setText(
                    Common.getCurrencyFormat(model.RequestCarton * model.CartonPrice + model.RequestPackage * model.PackagePrice));


            Integer shortcut = Integer.parseInt(model.Shortcut);
            int color = R.color.md_grey_200;
            if (shortcutsAvailability.containsKey(shortcut)) {
                int availability = shortcutsAvailability.get(shortcut);
                color = Utility.getAvailabilityColor(availability);
            }
            holder.txtRequestCarton.setBackgroundColor(
                    holder.itemView.getResources().getColor(color)
            );

            holder.txtExistenceCarton.setBackgroundColor(
                    holder.itemView.getResources().getColor(R.color.md_blue_200)
            );


            String sum = StringHelper.GenerateMessage("{0}|{1}",
                    model.CardIndexHistory.QtyCarton1
                            + model.CardIndexHistory.QtyCarton2
                            + model.CardIndexHistory.QtyCarton3,
                    model.CardIndexHistory.QtyPackge1
                            + model.CardIndexHistory.QtyPackge2
                            + model.CardIndexHistory.QtyPackge3);

            holder.txtSumCarton.setText(sum);
            CardIndexDetailModel finalModel = model;
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCustomerCardIndexListener != null) {
                        onCustomerCardIndexListener.onItemClick(finalModel);
                    }
                }
            });

            holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onCustomerCardIndexListener != null) {
                        onCustomerCardIndexListener.onItemLongClick(finalModel);
                        notifyDataSetChanged();
                    }

                    return true;
                }
            });
        } catch (Exception ex) {
            UncaughtException uncaughtException = new UncaughtException(baseFragment.commonPackage, ex);
            if (model == null) {
                uncaughtException.userMessage = "pos : " + pos + " model is null" + " list size" + mValues.size();
            } else {
                uncaughtException.userMessage = "pos : " + pos + " model:" + (new Gson()).toJson(model) + " list size" + mValues.size();
            }
            baseFragment.handleError(uncaughtException);
        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setShortcutSelected(int shortcut, RunnableMethod<Integer, Object> runnable) {
        List<CardIndexDetailModel> newModel = new ArrayList<>();
        for (CardIndexDetailModel oldItem : mValues) {
            try {
                CardIndexDetailModel item = oldItem.clone();
                item.IsSelected = Integer.parseInt(item.Shortcut) == shortcut;
                newModel.add(item);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        applyDiffUtillUpdate(newModel, shortcutsAvailability);


        scrollToPos(shortcut, runnable);
    }

    public void scrollToPos(int shortcut, RunnableMethod<Integer, Object> runnable) {
        RunnableMethod<Integer, List<Integer>> doRun = new RunnableMethod<Integer, List<Integer>>() {
            @Override
            public List<Integer> run(Integer param, OnProgressUpdate onProgressUpdate) {
                int pos = -1;
                boolean notFound = false;
                for (int i = 0; i < mValues.size(); i++) {
                    if (Integer.parseInt(mValues.get(i).Shortcut) == shortcut) {
                        pos = i;
                        notFound = true;
                    }
                }
                if (notFound && pos != -1 && runnable != null) {
                    runnable.run(pos, null);
                }
                return null;
            }
        };


        SimpleAsyncTask asyncTask = new SimpleAsyncTask(null, doRun, null, null);
        asyncTask.run();
    }

    private void applyDiffUtillUpdate(List<CardIndexDetailModel> newModel, Hashtable<Integer, Integer> newShortcutsAvailability) {
        CardIndexDiffUtilCallback diffCallback = new CardIndexDiffUtilCallback(mValues, newModel,
                shortcutsAvailability, newShortcutsAvailability);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        //mValues.clear();
        //mValues = new LinkedList<>();
        // mValues.addAll(newModel);

        mValues = new LinkedList<>();
        for (CardIndexDetailModel oldItem : newModel) {
            try {
                CardIndexDetailModel item = oldItem.clone();
                mValues.add(item);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        shortcutsAvailability = newShortcutsAvailability;

        diffResult.dispatchUpdatesTo(this);


    }

    public void setUnSelected() {

        RunnableMethod doRun = new RunnableMethod() {
            @Override
            public Object run(Object param, OnProgressUpdate onProgressUpdate) {
                for (int i = 0; i < mValues.size(); i++) {
                    if (mValues.get(i).IsSelected) {
                        mValues.get(i).IsSelected = false;
                    }
                }
                onProgressUpdate.onProgressUpdate(0);
                return null;
            }
        };


        SimpleAsyncTask asyncTask = new SimpleAsyncTask(null, doRun, null, null);
        asyncTask.run();
    }

    public interface OnCustomerCardIndexListener {
        void onItemClick(CardIndexDetailModel model);

        void onItemLongClick(CardIndexDetailModel model);
    }

    public class CustomerCardIndexViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtCode;
        public final TextView txtRequestCarton;
        //        public final TextView txtRequestPackage;
        public final TextView txtExistenceCarton;
        //        public final TextView txtExistencePackage;
        public final TextView txtTour1Carton;
        public final TextView txtTour2Carton;
        public final TextView txtTour3Carton;
        //        public final TextView txtTour1Package;
//        public final TextView txtTour2Package;
//        public final TextView txtTour3Package;
        public final TextView txtSumCarton;
        public final TextView txtProductName;
        public final TextView txtPrice;
        public final ImageView imgCartonPackage;
//        public final TextView txtSumPackage;

        public CardIndexDetailModel model;

        public CustomerCardIndexViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtCode = itemView.findViewById(R.id.txt_code);
            txtRequestCarton = itemView.findViewById(R.id.txt_request_carton);
//            txtRequestPackage = itemView.findViewById(R.id.txt_request_package);
            txtExistenceCarton = itemView.findViewById(R.id.txt_existence_carton);
//            txtExistencePackage = itemView.findViewById(R.id.txt_existence_package);
            txtTour1Carton = itemView.findViewById(R.id.txt_tour1_carton);
            txtTour2Carton = itemView.findViewById(R.id.txt_tour2_carton);
            txtTour3Carton = itemView.findViewById(R.id.txt_tour3_carton);
//            txtTour1Package = itemView.findViewById(R.id.txt_tour1_package);
//            txtTour2Package = itemView.findViewById(R.id.txt_tour2_package);
//            txtTour3Package = itemView.findViewById(R.id.txt_tour3_package);
            txtSumCarton = itemView.findViewById(R.id.txt_sum_carton);
//            txtSumPackage = itemView.findViewById(R.id.txt_sum_package);
            txtProductName = itemView.findViewById(R.id.txt_product_name);
            txtPrice = itemView.findViewById(R.id.txt_tour_price);
            imgCartonPackage = itemView.findViewById(R.id.img_carton_package);
        }
    }

}
