package com.shoniz.saledistributemobility.view.base.viewpager;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

public class PagerViewModel extends ViewModel{

    @Inject
    public PagerViewModel() {
        isDataAllChange.setValue(false);
    }

    public MutableLiveData<Boolean> isDataAllChange = new MutableLiveData<>();
}
