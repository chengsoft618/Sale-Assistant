package com.shoniz.saledistributemobility.view.path.customerlist;

import android.view.View;

import com.shoniz.saledistributemobility.data.model.customer.CustomerData;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerItemViewModel;

public class CustomerItemViewModel extends RecyclerItemViewModel<CustomerData,
        ICustomerRecyclerListener> {

    public boolean onCustomerClick(View v){
        getRecyclerListener().onCustomerClick(v, getModel());
        return false;
    }

    public boolean onCustomerLongClick(View v){
        getRecyclerListener().onCustomerLongClick(v, getModel());
        return false;
    }
}
