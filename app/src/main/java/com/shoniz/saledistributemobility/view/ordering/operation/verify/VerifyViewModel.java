package com.shoniz.saledistributemobility.view.ordering.operation.verify;

import android.arch.lifecycle.MutableLiveData;
import android.view.View;

import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.data.model.user.IUserRepository;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerViewModel;
import com.shoniz.saledistributemobility.view.entity.EmployeeInfoEntity;
import com.shoniz.saledistributemobility.view.ordering.operation.VerificationSharedViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class VerifyViewModel
        extends RecyclerViewModel<OrderCompleteData,
        Long,
        IVerifyNavigator> implements IVerifyRecyclerListener {

    IOrderRepository orderRepository;

    IUserRepository userRepository;

    ISettingRepository settingRepository;

    CommonPackage commonPackage;


    EmployeeInfoEntity employeeInfoEntity;

    VerificationSharedViewModel verificationSharedViewModel;

    @Inject
    public VerifyViewModel(IOrderRepository orderRepository, IUserRepository userRepository, ISettingRepository settingRepository, CommonPackage commonPackage) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.settingRepository = settingRepository;
        this.commonPackage = commonPackage;
        employeeInfoEntity = commonPackage.getSettingPref().getEmployeeInfoEntity();
        hasActiveActionMode = true;
    }

    @Override
    public MutableLiveData<List<OrderCompleteData>> getMutableList() {
        return verificationSharedViewModel.verifyViewModel;
    }

    public void verifyOrders() {
        verify(getSelectedKeys());
    }

    public void verifyOrders(long orderNo) {
        List<Long> list = new ArrayList<>();
        list.add(orderNo);
        verify(list);
    }

    private void verify(List<Long> orderNos) {

        CommonAsyncTask.RunnableDo<Object, AsyncResult> runnableDo = (param, onProgress) -> {
            AsyncResult result = new AsyncResult();
            try {
                orderRepository.verifyOrder(orderNos);
                verificationSharedViewModel.fetchOrderData();
            } catch (InOutError inOutError) {
                result.exception = inOutError;
            } catch (Exception e) {
                result.exception = new UncaughtException(commonPackage, e);
            }
            return result;
        };
        CommonAsyncTask.RunnablePost<AsyncResult> postRunnable = param -> {
            if (param.hasError())
                getNavigator().handleError(param.exception);
            else {
                verificationSharedViewModel.isDataAllChange.setValue(true);
            }
        };

        asyncCall(runnableDo, postRunnable, 1);
    }

    public void rejectVerify(long orderNo, String comment) {
        CommonAsyncTask.RunnableDo<Object, AsyncResult> runnableDo = (param, onProgress) -> {
            AsyncResult result = new AsyncResult();
            try {
                orderRepository.rejectVerify(orderNo, comment);
                verificationSharedViewModel.fetchOrderData();
            } catch (InOutError inOutError) {
                result.exception = inOutError;
            } catch (Exception e) {
                result.exception = new UncaughtException(commonPackage, e);
            }
            return result;
        };
        CommonAsyncTask.RunnablePost<AsyncResult> postRunnable = param -> {
            if (param.hasError())
                getNavigator().handleError(param.exception);
            else
                verificationSharedViewModel.isDataAllChange.setValue(true);
        };
        asyncCall(runnableDo, postRunnable, 1);
    }

    public void sendVerifyTo(long orderNo, int userId, String comment) {
        CommonAsyncTask.RunnableDo<Object, AsyncResult> runnableDo = (param, onProgress) -> {
            AsyncResult result = new AsyncResult();
            try {
                orderRepository.sendTo(orderNo, userId, comment, settingRepository.getCurrentRoleId());
                verificationSharedViewModel.fetchOrderData();
            } catch (InOutError inOutError) {
                result.exception = inOutError;
            } catch (Exception e) {
                result.exception = new UncaughtException(commonPackage, e);
            }
            return result;
        };
        CommonAsyncTask.RunnablePost<AsyncResult> postRunnable = param -> {
            if (param.hasError())
                getNavigator().handleError(param.exception);
            else
                verificationSharedViewModel.isDataAllChange.setValue(true);
        };
        asyncCall(runnableDo, postRunnable, 1);
    }



    public void load() {
        if (getRecyclerModelSize() == 0) {
            reload();
        }


    }

    @Override
    public void onOrderItemClick(View view, long orderNo) {
        getNavigator().showPopupMenu(view, orderNo);
    }

    public void reload() {
            setRecyclerModels(verificationSharedViewModel.verifyViewModel.getValue());
    }

    public void setSharedModel(VerificationSharedViewModel sharedModel) {
        this.verificationSharedViewModel = sharedModel;
    }
}
