package com.shoniz.saledistributemobility.view.ordering.order;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class SharedOrderViewModel extends ViewModel{

    public MutableLiveData<Boolean> isRefresh = new MutableLiveData<>();
}
