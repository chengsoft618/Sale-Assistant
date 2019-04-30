package com.shoniz.saledistributemobility.view.ordering.operation;

import android.arch.lifecycle.MutableLiveData;

import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.view.base.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class VerificationSharedViewModel extends BaseViewModel {

    public MutableLiveData<List<OrderCompleteData>> verifyViewModel = new MutableLiveData<>();
    public MutableLiveData<List<OrderCompleteData>> verifyCancelViewModel = new MutableLiveData<>();

    public MutableLiveData<Boolean> isDataAllChange = new MutableLiveData<>();

    @Inject
    IOrderRepository repository;

    @Inject
    CommonPackage commonPackage;

    @Inject
    public VerificationSharedViewModel() {
    }

    public void fetchOrderData() throws BaseException {
        List<OrderCompleteData> verify = new ArrayList<>();
        List<OrderCompleteData> verifyCancel = new ArrayList<>();
        for (OrderCompleteData od : repository.fetchOrderAllFromApi()) {
            if ((od.ActionId == 0 && od.UserId == commonPackage.getSettingPref().getEmployeeInfoEntity().EmployeeId) ||
                    (od.ActionId == 1 && od.UserId != commonPackage.getSettingPref().getEmployeeInfoEntity().EmployeeId)) {
                verify.add(od);
            }else {
                verifyCancel.add(od);
            }
        }
        verifyViewModel.postValue(verify);
        verifyCancelViewModel.postValue(verifyCancel);
    }



}
