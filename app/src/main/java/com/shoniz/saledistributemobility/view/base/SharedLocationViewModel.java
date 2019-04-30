package com.shoniz.saledistributemobility.view.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;

public class SharedLocationViewModel extends ViewModel {

    MutableLiveData<Location> location=new MutableLiveData<>();

}
