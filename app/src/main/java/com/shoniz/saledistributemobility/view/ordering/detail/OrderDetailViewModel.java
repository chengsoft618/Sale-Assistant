package com.shoniz.saledistributemobility.view.ordering.detail;

import android.arch.lifecycle.MutableLiveData;
import android.view.View;

import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderDetailCompleteData;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerViewModel;
import com.shoniz.saledistributemobility.view.ordering.detail.recycler.IOrderDetailRecyclerListener;

import java.util.List;

import javax.inject.Inject;

public class OrderDetailViewModel extends RecyclerViewModel<OrderDetailCompleteData,
        Integer,
        IOrderDetailNavigator> implements IOrderDetailRecyclerListener {

    public MutableLiveData<OrderCompleteData> orderData = new MutableLiveData<>();
    public MutableLiveData<Boolean> isPrintButtonVisible = new MutableLiveData<>();

    public MutableLiveData<List<OrderDetailCompleteData>> orderDetailComplete = new MutableLiveData<>();

    @Inject
    IOrderRepository orderRepository;

    @Inject
    ISettingRepository settingRepository;

    @Inject
    CommonPackage commonPackage;

    @Inject
    public OrderDetailViewModel() {
    }

    @Override
    public MutableLiveData<List<OrderDetailCompleteData>> getMutableList() {
        return orderDetailComplete;
    }

    public void onPrintButtonClick(long orderNo) {
        getNavigator().onPrintButtonClick(orderNo);
    }

    public void load(long orderNo) {
        OrderCompleteData orderCompleteData = orderRepository.getOrderComplete(orderNo);
        orderData.setValue(orderCompleteData);
        isPrintButtonVisible.setValue(
                orderCompleteData.ResponseDoc !=null && !orderCompleteData.ResponseDoc.isEmpty() && (
                        settingRepository.getAllowanceIssuePrintTime() == 0 ||
                                orderCompleteData.IssuePrintedTime < settingRepository.getAllowanceIssuePrintTime()
                )
        );
        List<OrderDetailCompleteData> orderCompleteDetailModels = orderRepository.getOrderDetailComplete(orderNo);
        orderDetailComplete.postValue(orderCompleteDetailModels);
        //isPrintButtonVisible.postValue(orderCompleteDetailModels);
        setRecyclerModels(orderCompleteDetailModels);
    }

    public String getIssueData()   {
        if (orderData.getValue() != null)
            return orderData.getValue().ResponseDoc;
        return "";
    }

//    public void loadOnLine(long orderNo) {
//
//        CommonAsyncTask.RunnableDo<Object, AsyncResult<List<OrderDetailCompleteData>>> runnableDo = (param, onProgress) -> {
//            AsyncResult<List<OrderDetailCompleteData>> asyncResult = new AsyncResult<>();
//
//            try {
//                OrderCompleteData order = orderRepository.fetchOrderFromApi(orderNo);
//                orderData.postValue(order);
//                asyncResult.model=  orderRepository.fetchOrderDetailFromApi(orderNo);
//            } catch (BaseException ex) {
//                asyncResult.exception = ex;
//            } catch (Exception ex) {
//                asyncResult.exception = new UncaughtException(commonPackage, ex);
//            }
//            return asyncResult;
//        };
//        CommonAsyncTask.RunnablePost<AsyncResult<List<OrderDetailCompleteData>>> postRunnable = result -> {
//            int x = 0;
//            if (!result.hasError() && result.model != null) {
//                orderDetailComplete.postValue(result.model);
//                setRecyclerModels(result.model);
//            } else {
//                getNavigator().handleError(result.exception);
//            }
//        };
//        asyncCall(runnableDo, postRunnable);
//    }


    @Override
    public void onOrderItemClick(View view, long orderNo) {

    }
}
