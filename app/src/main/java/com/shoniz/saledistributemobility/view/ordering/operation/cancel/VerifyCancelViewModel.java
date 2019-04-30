package com.shoniz.saledistributemobility.view.ordering.operation.cancel;

import android.arch.lifecycle.MutableLiveData;
import android.view.View;

import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerViewModel;
import com.shoniz.saledistributemobility.view.entity.EmployeeInfoEntity;
import com.shoniz.saledistributemobility.view.ordering.operation.VerificationSharedViewModel;

import java.util.List;

import javax.inject.Inject;

public class VerifyCancelViewModel
        extends RecyclerViewModel<OrderCompleteData,
        Long,
        IVerifyCancelNavigator>
        implements IVerifyCancelRecyclerListener {

    @Inject
    IOrderRepository repository;

    @Inject
    CommonPackage commonPackage;


    EmployeeInfoEntity employeeInfoEntity;


    VerificationSharedViewModel verificationSharedViewModel;

    @Override
    public MutableLiveData<List<OrderCompleteData>> getMutableList() {
        return verificationSharedViewModel.verifyCancelViewModel;

    }

    @Inject
    public VerifyCancelViewModel() {
        
    }



    public void load() {
        employeeInfoEntity = commonPackage.getSettingPref().getEmployeeInfoEntity();
        if (getRecyclerModelSize() == 0) {
            reload();
        }
    }





    public void cancelVerification(Long orderNos,String message) {
        CommonAsyncTask.RunnableDo<List<Integer>, AsyncResult> runnableDo = (param, onProgress) -> {
            AsyncResult result = new AsyncResult();
            try {
                repository.cancelVerify(orderNos,message);
                verificationSharedViewModel.fetchOrderData();
            } catch (InOutError inOutError) {
                result.exception = inOutError;
            } catch (Exception e) {
                e.getMessage();
            }
            return result;
        };
        CommonAsyncTask.RunnablePost<AsyncResult> postRunnable = param -> {
            BaseException exception;
            if (param.hasError())
                getNavigator().handleError(param.exception);
            else {
                verificationSharedViewModel.isDataAllChange.setValue(true);
            }

        };

        asyncCall(runnableDo, postRunnable, 1);
    }

    public void reload() {
            setRecyclerModels(verificationSharedViewModel.verifyCancelViewModel.getValue());
    }

    @Override
    public void onOrderItemClick(View view, long orderNo) {
        getNavigator().showPopupMenu(view, orderNo);
    }

    public void setSharedModel(VerificationSharedViewModel sharedModel) {
        this.verificationSharedViewModel = sharedModel;
    }


}
