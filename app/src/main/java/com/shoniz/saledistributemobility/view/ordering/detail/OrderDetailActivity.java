package com.shoniz.saledistributemobility.view.ordering.detail;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.databinding.ActivityOrderDetailsBinding;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerHelper;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerViewModel;
import com.shoniz.saledistributemobility.view.ordering.order.SendOrderService;
import com.shoniz.saledistributemobility.view.base.BaseActivity;
import com.shoniz.saledistributemobility.view.ordering.detail.printissue.PrintIssueActivity;
import com.shoniz.saledistributemobility.view.ordering.detail.recycler.OrderDetailItemBindingBuilder;

import javax.inject.Inject;

public class OrderDetailActivity extends BaseActivity<OrderDetailViewModel, ActivityOrderDetailsBinding>
        implements IOrderDetailNavigator {
    @Inject
    CommonPackage commonPackage;
    @Inject
    ViewModelProvider.Factory factory;
    long orderNo;



    public static Intent newInstance(Context context, long orderNo) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra(SendOrderService.ORDER_NO, orderNo);
        return intent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //gc();
    }

    private void gc() {
        Runtime.getRuntime().gc();
        System.gc();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            mViewModel.setNavigator(this);
            RecyclerHelper.build(
                    mViewModel,
                    this,
                    this,
                    mViewDataBinding.orderDetailsRecycler,
                    OrderDetailItemBindingBuilder.class)
                    .setSelectingModel(
                            RecyclerViewModel.SelectingMode.None
                    );
            orderNo = getIntent().getExtras().getLong(SendOrderService.ORDER_NO);
            mViewModel.load(orderNo);


        } catch (Exception ex) {
            handleError(new UncaughtException(commonPackage, ex));
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_details;
    }

    @Override
    public OrderDetailViewModel getViewModel() {
        return ViewModelProviders.of(this, factory).get(OrderDetailViewModel.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public boolean isEnableLocation() {
        return false;
    }

    @Override
    public void onChangeLocation(Location location) {
    }


    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    public void onPrintButtonClick(long v) {
        try {
            Intent act = new Intent(this, PrintIssueActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(PrintIssueActivity.PrintIssueJsonBundleName,
                    mViewModel.getIssueData());
            act.putExtras(bundle);
            startActivity(act);
        }
        catch(Exception e){
         Exception ee = e;
            }
    }
}
