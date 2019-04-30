package com.shoniz.saledistributemobility.order.unsent;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.databinding.FragmentRequestListBinding;
import com.shoniz.saledistributemobility.order.SharedOrderViewModel;
import com.shoniz.saledistributemobility.view.base.BaseFragment;
import com.shoniz.saledistributemobility.view.customer.ActionSendType;
import com.shoniz.saledistributemobility.view.customer.activity.CustomerActivity;
import com.shoniz.saledistributemobility.view.customer.ILocationChange;
import com.shoniz.saledistributemobility.view.customer.SendRequestModel;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexBusiness;
import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.location.LocationHelper;
import com.shoniz.saledistributemobility.order.RequestBusiness;
import com.shoniz.saledistributemobility.order.SendOrderService;
import com.shoniz.saledistributemobility.view.path.outofpath.CustomerPreference;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.Notification;
import com.shoniz.saledistributemobility.utility.dialog.ErrorDialog.ErrorDialog;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;


public class UnsentOrdersFragment extends BaseFragment<FragmentRequestListBinding, UnSentOrderViewModel> {

    @Inject
    @Named("UnSentOrderViewModelFactory")
    ViewModelProvider.Factory factory;

    UnsentOrderAdapter unsentOrderAdapter;
    ToolbarUnsentOrderActionModeCallback toolbarUnsentOrderActionModeCallback;
    List<UnsentOrderModel> models;

    @Override
    public UnSentOrderViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(UnSentOrderViewModel.class);
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
        return R.layout.fragment_request_list;
    }

    BroadcastReceiver SendOrderReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                int personID = bundle.getInt(SendOrderService.PERSON_ID);
                ActionSendType sendType = ActionSendType.values()[bundle.getInt(SendOrderService.ACTION_SEND_TYPE)];
                boolean isError = bundle.getBoolean(SendOrderService.IS_ERROR);
                String errorMessage = bundle.getString(SendOrderService.ERROR_MESSAGE);
                switch (sendType) {
                    case NewOrder:
                    case UpdateOrder:
                        upDateRecycle(personID, isError, errorMessage);
                        break;
                }
                Notification notification = new Notification(context, personID);
                notification.cancel();
                sharedOrderViewModel.isRefresh.setValue(true);
                //TODO: show orderDetail
            }

        }
    };
    Location lastLocation;
    private RecyclerView recyclerView;
    private ActionMode mActionMode;
    private UnsentOrderAdapter.UnsentRequestListener unsentRequestListener = new UnsentOrderAdapter.UnsentRequestListener() {
        @Override
        public void onClick(UnsentOrderModel unsentOrderModel) {
            Intent intent = new Intent(getActivity(), CustomerActivity.class);
            intent.putExtra(CustomerPreference.PersonId, unsentOrderModel.PersonID);
            startActivity(intent);
        }

        @Override
        public void onItemCheck(UnsentOrderModel unsentOrderModel) {
            if (unsentOrderAdapter.getSelectedList().size() == unsentOrderAdapter.getList().size()) {
                toolbarUnsentOrderActionModeCallback.setCheckAll(true);
            } else {
                toolbarUnsentOrderActionModeCallback.setCheckAll(false);
            }
        }

        @Override
        public void onLongClick() {
//            Toolbar toolbarListener = activity.findViewById(R.id.toolbarListener);
//            toolbarListener.setVisibility(View.GONE);

            toolbarUnsentOrderActionModeCallback = new ToolbarUnsentOrderActionModeCallback(unsentOrderAdapter);
            mActionMode = ((AppCompatActivity) getBaseActivity()).startSupportActionMode(toolbarUnsentOrderActionModeCallback);
        }

        @Override
        public void onUpdateClick(List<Integer> selectedIds) {
            if (selectedIds.size() < 1) {
                Common.toast(getActivity(), "آیتمی برای ارسال انتخاب نشده است");
                return;
            } else {
                List<SendRequestModel> lst = new LinkedList<>();
                for (int personID : unsentOrderAdapter.getSelectedList()) {
                    try {
                        SendRequestModel sendRequestModel;
                        if (CardIndexBusiness.getCardIndexDto(getContext(), personID).OrderNo > 0)
                            sendRequestModel = new SendRequestModel(personID, ActionSendType.UpdateOrder);
                        else
                            sendRequestModel = new SendRequestModel(personID, ActionSendType.NewOrder);
                        lst.add(sendRequestModel);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                SendOrderService.send(getActivity(), lst);
            }
        }

        @Override
        public void onDeleteClick(List<Integer> selectedIds) {
            if (selectedIds.size() < 1) {
                Common.toast(getActivity(), "آیتمی برای حذف انتخاب نشده است");
                return;
            } else {
                for (int personID : unsentOrderAdapter.getSelectedList()) {
                    try {
                        CardIndexBusiness.DeleteCardIndex(getActivity(), personID);
//                        for (int i = 0; i < models.size(); i++) {
//                            if (models.get(i).PersonID == personID) {
//                                models.remove(i);
//                            }
//                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                sharedOrderViewModel.isRefresh.setValue(true);
                mActionMode.finish();

            }
        }
    };

    public UnsentOrdersFragment() {
    }

    public static UnsentOrdersFragment newInstance() {
        UnsentOrdersFragment fragment = new UnsentOrdersFragment();
        return fragment;
    }

    SharedOrderViewModel sharedOrderViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            sharedOrderViewModel = ViewModelProviders.of(getBaseActivity()).get(SharedOrderViewModel.class);
            View  view =   inflater.inflate(R.layout.fragment_request_list, container, false);
            RecyclerView recyclerView=view.findViewById(R.id.recyclerCustomer);
            models = RequestBusiness.getUnsentRequestList(getActivity());
            unsentOrderAdapter = new UnsentOrderAdapter(models, unsentRequestListener);
            Common.setRecycleViewLayoutManager(getActivity(), recyclerView);
            recyclerView.setAdapter(unsentOrderAdapter);


            return view;
        } catch (Exception e) {

            HandleException systemException = new HandleException(getActivity(), e);
            ErrorDialog.showDialog((AppCompatActivity) getActivity(),
                    systemException.getUserTitle(), systemException.getUserMessage(),
                    systemException.getSystemMessage());
        }
        return null;
    }

    @Override
    public int getFragmentTitle() {
        return 0;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.unsent_order_list_action_mode, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        List<SendRequestModel> lst = new LinkedList<>();
        for (int personID : unsentOrderAdapter.getSelectedList()) {
            SendRequestModel sendRequestModel = new SendRequestModel(personID, ActionSendType.NewOrder);
            if (lastLocation != null)
                sendRequestModel.location =
                        LocationHelper.convertLocationToLocationEntity(lastLocation);
            lst.add(sendRequestModel);
        }
        SendOrderService.send(getActivity(), lst);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(SendOrderReceiver, new IntentFilter(SendOrderService.SEND_ORDER_COMPLETE));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(SendOrderReceiver);
    }

    private void upDateRecycle(int personId, Boolean isError, String errorMessage) {
        for (int i = 0; i < models.size(); i++) {
            if (models.get(i).PersonID == personId) {
                models.get(i).isSelectedToUpdate = false;

                if (!isError) {
                    models.remove(i);
                    unsentOrderAdapter.notifyItemRemoved(i);
                } else {
                    models.get(i).ErrorMessage = errorMessage;
                    unsentOrderAdapter.notifyItemChanged(i);
                }
                unsentOrderAdapter.removeSelectedList(personId);
                if (unsentOrderAdapter.getSelectedList().size() == 0) {
                    unsentOrderAdapter.setAllItemSelected(false);
                    unsentOrderAdapter.setSelectableMode(false);
                    unsentOrderAdapter.notifyDataSetChanged();
                    unsentOrderAdapter.setSelectableMode(true);
                }
                break;
            }
        }
    }
}
