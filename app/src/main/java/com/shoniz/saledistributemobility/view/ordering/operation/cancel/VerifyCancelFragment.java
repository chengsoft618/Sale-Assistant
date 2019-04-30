package com.shoniz.saledistributemobility.view.ordering.operation.cancel;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.databinding.FragmentVerifyCancelBinding;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerHelper;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerViewModel;
import com.shoniz.saledistributemobility.order.SendOrderService;
import com.shoniz.saledistributemobility.utility.dialog.InputDialog;
import com.shoniz.saledistributemobility.view.base.BaseFragment;
import com.shoniz.saledistributemobility.view.base.viewpager.PagerViewModel;
import com.shoniz.saledistributemobility.view.ordering.detail.OrderDetailActivity;
import com.shoniz.saledistributemobility.view.ordering.operation.VerificationSharedViewModel;

import javax.inject.Inject;
import javax.inject.Named;

public class VerifyCancelFragment extends BaseFragment<FragmentVerifyCancelBinding, VerifyCancelViewModel>
        implements IVerifyCancelNavigator {


    @Inject
    @Named("VerifyCancelViewModelFactory")
    ViewModelProvider.Factory factory;


    @Inject
    @Named("VerificationSharedViewModel")
    ViewModelProvider.Factory factoryShared;

    VerificationSharedViewModel verificationSharedViewModel;

    public static VerifyCancelFragment newInstance() {
        return new VerifyCancelFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        verificationSharedViewModel = ViewModelProviders.of(getBaseActivity(),factoryShared).get(VerificationSharedViewModel.class);

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
        RecyclerHelper.build(
                mViewModel,
                getBaseActivity(),
                this,
                mViewDataBinding.recycleOrderVerify,
                VerifyCancelItemBindingBuilder.class).setSelectingModel(
                RecyclerViewModel.SelectingMode.MultipleSelect
        );


        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public int getFragmentTitle() {
        return R.string.verify_verified_orders;
    }

    @Override
    public VerifyCancelViewModel getViewModel() {
        VerifyCancelViewModel viewModel = ViewModelProviders.of(this, factory).get(VerifyCancelViewModel.class);
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
        return R.layout.fragment_verify_cancel;
    }

    public void showPopupMenu(View v, final long orderNo) {
        PopupMenu popup = new PopupMenu(getBaseActivity(), v);

        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.verify_back) {

                    InputDialog.InputDialogListener onYesClick = new InputDialog.InputDialogListener() {
                        @Override
                        public void onClick(String desc) {
                            getViewModel().cancelVerification(orderNo,desc);
                        }
                    };

                    InputDialog.showDialog(getBaseActivity(),
                            getString(R.string.common_desc),
                            getString(R.string.verify_cancel_selected_order),
                            0, onYesClick, null);
//
                } else if (id == R.id.verify_show_order_details) {
                    openOrderDetailActivity(orderNo );

                }
                return false;
            }
        });
        popup.inflate(R.menu.item_verify_cancel_popup);
        popup.show();
    }

    @Override
    public void openOrderDetailActivity(long orderNo) {
        Intent order =OrderDetailActivity.newInstance(getActivity(),orderNo) ;
        startActivity(order);
    }


}
