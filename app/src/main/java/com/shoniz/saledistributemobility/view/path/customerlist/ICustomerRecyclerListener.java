package com.shoniz.saledistributemobility.view.path.customerlist;

import android.view.View;

import com.shoniz.saledistributemobility.data.model.customer.CustomerData;
import com.shoniz.saledistributemobility.infrastructure.recycler.IRecyclerListener;

public interface ICustomerRecyclerListener extends IRecyclerListener {
    void onCustomerClick(View view, CustomerData paCustomerData);
    void onCustomerLongClick(View view, CustomerData paCustomerData);
}
