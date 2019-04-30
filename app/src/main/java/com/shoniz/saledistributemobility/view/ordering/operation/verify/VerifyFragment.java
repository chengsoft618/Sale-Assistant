package com.shoniz.saledistributemobility.view.ordering.operation.verify;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.user.UserEntity;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.databinding.FragmentVerifyBinding;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerHelper;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerToolbar;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerViewModel;
import com.shoniz.saledistributemobility.order.SendOrderService;
import com.shoniz.saledistributemobility.utility.dialog.InputDialog;
import com.shoniz.saledistributemobility.utility.dialog.YesNoDialog;
import com.shoniz.saledistributemobility.view.base.BaseFragment;
import com.shoniz.saledistributemobility.view.dialog.userselect.UserSelectDialog;
import com.shoniz.saledistributemobility.view.ordering.detail.OrderDetailActivity;
import com.shoniz.saledistributemobility.view.ordering.operation.VerificationSharedViewModel;

import javax.inject.Inject;
import javax.inject.Named;

public class VerifyFragment extends BaseFragment<FragmentVerifyBinding, VerifyViewModel>
        implements IVerifyNavigator {

    @Inject
    @Named("VerifyViewModelFactory")
    ViewModelProvider.Factory factory;


    @Inject
    @Named("VerificationSharedViewModel")
    ViewModelProvider.Factory factoryShared;

    @Inject
    UserSelectDialog userSelectDialog;

    @Inject
    ISettingRepository settingRepository;

    VerificationSharedViewModel verificationSharedViewModel;


    public static VerifyFragment newInstance() {
        return new VerifyFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        verificationSharedViewModel = ViewModelProviders.of(getBaseActivity(), factoryShared).get(VerificationSharedViewModel.class);
        verificationSharedViewModel.isDataAllChange.observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isChanged) {
                if (isChanged)
                    mViewModel.reload();
            }
        });
        mViewModel.setSharedModel(verificationSharedViewModel);
        mViewModel.setNavigator(this);
        mViewModel.load();
        setHasOptionsMenu(true);

        RecyclerHelper.build(
                mViewModel,
                getBaseActivity(),
                this,
                mViewDataBinding.recycleOrderVerify,
                VerifyItemBindingBuilder.class) .setSelectingModel(
                RecyclerViewModel.SelectingMode.MultipleSelect
        );

        setToolbarListener(new VerifyToolbar());

        return view;
    }

    @Override
    public int getFragmentTitle() {
        return R.string.verify_verifiable_orders;
    }


    @Override
    public VerifyViewModel getViewModel() {
        VerifyViewModel viewModel = ViewModelProviders.of(this, factory).get(VerifyViewModel.class);
        return viewModel;
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
        return R.layout.fragment_verify;
    }

    public void showPopupMenu(View v, final long orderNo) {
        PopupMenu popup = new PopupMenu(getBaseActivity(), v);

        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.verify_verify) {
                    YesNoDialog.showDialog(getBaseActivity(), getString(R.string.common_question), getString(R.string.verify_verify_question),
                            new Runnable() {
                                @Override
                                public void run() {

                                    mViewModel.verifyOrders(orderNo);
                                }
                            }, null);
                } else if (id == R.id.verify_reject) {
                    InputDialog.InputDialogListener onYesClick = new InputDialog.InputDialogListener() {
                        @Override
                        public void onClick(String desc) {
                            getViewModel().rejectVerify(orderNo, desc);
                        }
                    };
                    InputDialog.showDialog(getBaseActivity(), getString(R.string.verify_desc_reject), getString(R.string.common_desc), 0, onYesClick, null);
                } else if (id == R.id.verify_sent_to) {
                    sendVerifyTo(orderNo);
                } else if (id == R.id.verify_show_order_details) {
                    openOrderDetailActivity(orderNo);
                }
                return false;
            }
        });
        popup.inflate(R.menu.item_verify_popup);
        if (settingRepository.getCurrentRoleId() == 1) {
            popup.getMenu().findItem(R.id.verify_reject).setVisible(false);
        }
        popup.show();
    }

    private void sendVerifyTo(long orderNo) {
        userSelectDialog.show(getBaseActivity(), new UserSelectDialog.ChooseUser() {
            @Override
            public void onChooseUser(UserEntity userEntity) {
                InputDialog.InputDialogListener onYesClick = new InputDialog.InputDialogListener() {
                    @Override
                    public void onClick(String desc) {
                        getViewModel().sendVerifyTo(orderNo, userEntity.userId, desc);
                    }
                };
                InputDialog.showDialog(getBaseActivity(),
                        getString(R.string.verify_desc_sent),
                        getString(R.string.verify_sent_to_user, getString(R.string.common_employee_id_name, userEntity.userId, userEntity.fullName)),
                        0, onYesClick, null);
            }
        });
    }

    @Override
    public void openOrderDetailActivity(long orderNo) {
        Intent order =OrderDetailActivity.newInstance(getActivity(),orderNo) ;
        startActivity(order);
    }

    private class VerifyToolbar extends RecyclerToolbar {

        public VerifyToolbar() {
            super(R.menu.verify_fragment_menu, mViewModel);
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            super.onActionItemClicked(mode, item);
            switch (item.getItemId()) {
                case R.id.action_verify_selected_orders:
                    getViewModel().verifyOrders();
                    break;
            }
            return false;
        }
    }

}
