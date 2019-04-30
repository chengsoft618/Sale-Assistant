package com.shoniz.saledistributemobility.order.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.order.OrderBusiness;
import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.order.SendOrderService;
import com.shoniz.saledistributemobility.utility.Common;

import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {

    long orderNo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = getLayoutInflater().inflate(R.layout.activity_order_details, null);
//        RecyclerView recyclerView ;
//        try {
//            OrderCompleteModel orderCompleteModel ;
//            orderNo = getIntent().getExtras().getLong(SendOrderService.ORDER_NO);
//            orderCompleteModel = OrderBusiness.getOrderCompleteHeader(this, orderNo);
//            List<OrderCompleteDetailModel> orderCompleteDetailModels = OrderBusiness.getOrderCompleteDetails(this, orderNo);
//            recyclerView = v.findViewById(R.id.order_details_recycler);
//            OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(orderCompleteDetailModels, null);
//            recyclerView.setAdapter(orderDetailAdapter);
//            setHeaderViews(v, orderCompleteModel);
//
//            setContentView(v);
//            Toolbar toolbar = findViewById(R.id.toolbarListener);
//            setSupportActionBar(toolbar);
//
//        }
//        catch (Exception ex){
//            HandleException.ShowException(this, ex);
//        }
    }


}
