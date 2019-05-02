package com.shoniz.saledistributemobility.order.sent;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;

import com.shoniz.saledistributemobility.data.model.cardindex.CardIndexDetailData;
import com.shoniz.saledistributemobility.data.model.cardindex.ICardIndexRepository;
import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderDetailCompleteData;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.databinding.FragmentOrderedRequestListBinding;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.ExceptionHandler;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.framework.service.order.ICardIndexService;
import com.shoniz.saledistributemobility.order.SharedOrderViewModel;
import com.shoniz.saledistributemobility.view.base.BaseFragment;
import com.shoniz.saledistributemobility.view.customer.ActionSendType;
import com.shoniz.saledistributemobility.view.customer.activity.CustomerActivity;
import com.shoniz.saledistributemobility.view.customer.SendRequestModel;
import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.order.RequestBusiness;
import com.shoniz.saledistributemobility.order.SendOrderService;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexBusiness;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexFragmentViewModel;
import com.shoniz.saledistributemobility.view.ordering.detail.OrderDetailActivity;
import com.shoniz.saledistributemobility.view.path.SettingConst;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.Notification;
import com.shoniz.saledistributemobility.utility.dialog.ErrorDialog.ErrorDialog;
import com.shoniz.saledistributemobility.utility.dialog.YesNoDialog;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


//public class SentOrdersFragment extends BaseFragment {
public class SentOrdersFragment extends BaseFragment<FragmentOrderedRequestListBinding, SentOrderViewModel> {
    List<SentOrderModel> models;
    SentOrderAdapter sentOrderAdapter;
    LinearLayoutManager layoutManager;
    boolean shouldShowJustNotIssuedOrder = true;
    boolean shouldShowJustTodayOrder = true;
    boolean onlyThisCustomer = true;
    int personId = 0;

    @Inject
    IOrderRepository orderRepository;

    @Inject
    ISettingRepository settingRepository;

    @Inject
    ICardIndexRepository cardIndexRepository;

    @Inject
    ICardIndexService cardIndexService;

    @Inject
    @Named("SentOrderViewModelFactory")
    ViewModelProvider.Factory factory;

    public static BaseFragment newInstance(int personId, boolean onlyThisPerson) {
        SentOrdersFragment fragment = new SentOrdersFragment();
        fragment.personId = personId;
        fragment.onlyThisCustomer = onlyThisPerson;
        return fragment;
    }

    SharedOrderViewModel sharedOrderViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public SentOrderViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(SentOrderViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void onChangeLocation(Location location) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ordered_request_list;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            sharedOrderViewModel = ViewModelProviders.of(getBaseActivity()).get(SharedOrderViewModel.class);
            if (onlyThisCustomer) {
                shouldShowJustNotIssuedOrder = false;
                shouldShowJustTodayOrder = false;
            } else {
                shouldShowJustNotIssuedOrder = Common.getPref(getContext()).get(SettingConst.ShouldShowJustNotIssuedOrder, true);
                shouldShowJustTodayOrder = Common.getPref(commonPackage.getContext()).get(SettingConst.ShouldShowJustTodayOrder, true);
            }
            final View view = inflater.inflate(R.layout.fragment_ordered_request_list, container, false);
            recyclerView = view.findViewById(R.id.ordered_list);
//            models = RequestBusiness.getOrderedRequestList(getContext(),
//                    shouldShowJustNotIssuedOrder,
//                    shouldShowJustTodayOrder,
//                    personId);

            sentOrderAdapter = new SentOrderAdapter(models, onRequestActionListener);
            recyclerView.setAdapter(sentOrderAdapter);
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            //layoutManager.setSmoothScrollbarEnabled(true);

            fillGrid(view);


            CheckBox chkJustNotIssued = view.findViewById(R.id.chkJustNotIssued);
            CheckBox chkJustTodayOrders = view.findViewById(R.id.chkJustTodayOrders);

            chkJustNotIssued.setChecked(shouldShowJustNotIssuedOrder);
            chkJustTodayOrders.setChecked(shouldShowJustTodayOrder);

            chkJustNotIssued.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Common.getPref(getContext()).set(SettingConst.ShouldShowJustNotIssuedOrder, isChecked);
                    shouldShowJustNotIssuedOrder = isChecked;
                    fillGrid(view);
                }
            });

            chkJustTodayOrders.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Common.getPref(getContext()).set(SettingConst.ShouldShowJustTodayOrder, isChecked);
                    shouldShowJustTodayOrder = isChecked;
                    fillGrid(view);
                }
            });
            setHasOptionsMenu(true);
            if (onlyThisCustomer)
                view.findViewById(R.id.layoutStatistics).setVisibility(View.GONE);
            return view;
        } catch (Exception e) {
            HandleException.ShowException(getContext(), e);
        }
        return null;
    }

    @Override
    public int getFragmentTitle() {
        return 0;
    }

    private void fillGrid(View view) {
        try {
            models = RequestBusiness.getOrderedRequestList(getContext(),
                    shouldShowJustNotIssuedOrder,
                    shouldShowJustTodayOrder,
                    personId);
            sentOrderAdapter.updateList(models);

            filStatistics(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filStatistics(View view) throws Exception {
        OrderStatistics orderStatistics = OrderStatistics.getOrderStatistic(getContext(),
                shouldShowJustNotIssuedOrder,
                shouldShowJustTodayOrder);
        ((TextView) view.findViewById(R.id.total_weight)).setText(
                "وزن کل: " + orderStatistics.TotalWeight + "(" + orderStatistics.TotalNetWeight + ")");
        ((TextView) view.findViewById(R.id.total_Price)).setText(
                "قیمت کل: " + Common.getCurrencyFormat(orderStatistics.TotalPrice) + " ریال ");

        ((TextView) view.findViewById(R.id.txtQty)).setText(
                " کارتن : " + Common.getCurrencyFormat((int) orderStatistics.TotalQtyCarton) + " بسته : " + Common.getCurrencyFormat((int) orderStatistics.TotalQtyPackage));
        ((TextView) view.findViewById(R.id.txtCustomerType)).setText(
                " عمده : " + Common.getCurrencyFormat((int) orderStatistics.TotalWholesalerCount) + " خرده : " + Common.getCurrencyFormat((int) orderStatistics.TotalRetailerCount));
    }

    @Override
    public void onResume() {
        super.onResume();
        //fillGrid();
        getActivity().registerReceiver(SendOrderReceiver, new IntentFilter(SendOrderService.SEND_ORDER_COMPLETE));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(SendOrderReceiver);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        gc();
    }

    private void gc() {
        models = null;
        sentOrderAdapter = null;
        layoutManager = null;
        Runtime.getRuntime().gc();
        System.gc();
    }

    BroadcastReceiver SendOrderReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                int personID = bundle.getInt(SendOrderService.PERSON_ID);
                long orderNo = bundle.getLong(SendOrderService.ORDER_NO);
                String response = bundle.getString(SendOrderService.RESPONSE);
                ActionSendType sendType = ActionSendType.values()[bundle.getInt(SendOrderService.ACTION_SEND_TYPE)];
                boolean isError = bundle.getBoolean(SendOrderService.IS_ERROR);
                String errorMessage = bundle.getString(SendOrderService.ERROR_MESSAGE);

                if (!isError) {
                    switch (sendType) {
                        case NewOrder:
                            try {
                                SentOrderModel model = RequestBusiness.getOrderedRequest(getContext(), orderNo);
                                if (model.OrderNo != 0) {
                                    boolean isFind = false;
                                    for (int i = 0; i < models.size(); i++) {
                                        if (model.OrderNo == models.get(i).OrderNo) {
                                            models.set(i, model);
                                            sentOrderAdapter.notifyItemChanged(i);
                                            layoutManager.scrollToPosition(i);
                                            isFind = true;
                                        }
                                    }
                                    if (!isFind) {
                                        models.add(model);
                                        sentOrderAdapter.notifyItemInserted(models.size());
                                        layoutManager.scrollToPosition(models.size());
                                    }

                                }
                            } catch (Exception e) {
                                HandleException.ShowException(getContext(), e);
                            }
                            break;
                        case DeleteOrder:
                            try {

                                for (int i = 0; i < models.size(); i++) {
                                    if (orderNo == models.get(i).OrderNo) {
                                        models.remove(i);
                                        layoutManager.scrollToPosition(i);
                                        sentOrderAdapter.notifyItemRemoved(i);
                                    }
                                }
                            } catch (Exception e) {
                                HandleException.ShowException(getContext(), e);
                            }

                            break;
                    }
                    Common.toast(getContext(), response);
                    sharedOrderViewModel.isRefresh.setValue(true);
                } else {
                    HandleException.ShowException(getContext(), new Exception(errorMessage));
                }
                Notification notification = new Notification(context, personID);
                notification.cancel();
                //TODO: show orderDetail
            }

        }
    };
    private RecyclerView recyclerView;
    private SentOrderAdapter.OnRequestActionListener onRequestActionListener =
            new SentOrderAdapter.OnRequestActionListener() {

                @Override
                public void onClick(View v, SentOrderModel sentOrderModel) {
                    Intent intent = OrderDetailActivity.newInstance(getContext(), sentOrderModel.OrderNo);
                    startActivity(intent);
                }

                @Override
                public void onLongClick(View v, final SentOrderModel sentOrderModel) {
                    if (sentOrderModel.IsIssued) return;
                    showMenu(v, sentOrderModel);
                }

                public void showMenu(View v, final SentOrderModel sentOrderModel) {
                    PopupMenu popup = new PopupMenu(getContext(), v);

                    // This getContext() implements OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int id = item.getItemId();

                            if (id == R.id.action_order_details) {
//                                YesNoDialog.showDialog((AppCompatActivity) getActivity(), "سوال", "آیا می خواهید این درخواست ویرایش شود؟",
//                                        new Runnable() {
//                                            @Override
//                                            public void run() {
                                try {
                                    cardIndexService.makeOrderReadyToEdit(sentOrderModel.OrderNo);
//                                    if(settingRepository.getUnchangedOrdersNoInCardindeForEdit() != 0
//                                            && settingRepository.getUnchangedOrdersNoInCardindeForEdit() != sentOrderModel.OrderNo)
//                                        cardIndexRepository.removeUnchangedCardindexForEdit(orderRepository,
//                                                settingRepository, cardIndexRepository, commonPackage);
//
//                                    cardIndexRepository.makeOrderReadyToEdit(getContext(), sentOrderModel.OrderNo);
//                                    settingRepository.setUnchangedOrdersNoInCardindeForEdit(sentOrderModel.OrderNo);
                                    CustomerActivity.startActivity(getActivity(), sentOrderModel.PersonID);
                                    sharedOrderViewModel.isRefresh.setValue(true);
                                } catch (Exception e) {
                                    UncaughtException exception = new UncaughtException(commonPackage, e);
                                    ExceptionHandler.handle(exception, getActivity());
//                                    HandleException.ShowException(getContext(), e);
                                }
//
//                                            }
//                                        }
// , null);
                            } else if (id == R.id.action_delete_order) {
                                try {
                                    YesNoDialog.showDialog((AppCompatActivity) getActivity(), "سوال", "آیا می خواهید این درخواست حذف شود؟",
                                            new Runnable() {
                                                @Override
                                                public void run() {
                                                    SendRequestModel model = new SendRequestModel(
                                                            sentOrderModel.PersonID,
                                                            ActionSendType.DeleteOrder,
                                                            sentOrderModel.OrderNo);
                                                    SendOrderService.send(getContext(), model);
                                                }
                                            }, null);
                                } catch (Exception e) {
                                    HandleException systemException = new HandleException(getContext(), e);
                                    ErrorDialog.showDialog((AppCompatActivity) getActivity(),
                                            systemException.getUserTitle(), systemException.getUserMessage(),
                                            systemException.getSystemMessage());
                                }
                            }


                            return false;
                        }
                    });
                    popup.inflate(R.menu.order_list_menu);
                    popup.show();
                }
            };

    public SentOrdersFragment() {
    }


    private boolean isEqualOrderAndCardindex(long orderNo) {

        return false;
    }
}
