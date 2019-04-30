package com.shoniz.saledistributemobility.view.dialog.orderdescription;

import android.arch.lifecycle.MutableLiveData;

import com.shoniz.saledistributemobility.data.model.customer.ICustomerRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.view.base.viewmodel.BaseViewModel;

import javax.inject.Inject;

public class DescriptionDialogViewModel extends BaseViewModel<IDescriptionNavigator> {



    public MutableLiveData<String> accDesc = new MutableLiveData<>();
    public MutableLiveData<String> saleDesc = new MutableLiveData<>();

    @Inject
    public DescriptionDialogViewModel() {

    }

    public void load(String accDesc, String saleDesc) {
        this.accDesc.setValue(accDesc);
        this.saleDesc.setValue(saleDesc);

    }
}
