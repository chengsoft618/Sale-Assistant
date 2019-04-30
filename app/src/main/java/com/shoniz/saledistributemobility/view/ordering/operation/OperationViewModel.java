package com.shoniz.saledistributemobility.view.ordering.operation;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.view.base.viewmodel.BaseViewModel;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;

import java.util.List;

import javax.inject.Inject;

public class OperationViewModel extends BaseViewModel<IOperationNavigator> {
    private CommonPackage commonPackage;
    private IOrderRepository repository;

    VerificationSharedViewModel verificationSharedViewModel;

    @Inject
    public OperationViewModel(CommonPackage commonPackage, IOrderRepository repository) {
        this.commonPackage = commonPackage;
        this.repository = repository;
    }

    public void load() {

        CommonAsyncTask.RunnableDo<List<Integer>, AsyncResult> runnableDo = (param, onProgress) -> {
            AsyncResult result = new AsyncResult();
            try {
                verificationSharedViewModel.fetchOrderData();
            } catch (InOutError inOutError) {
                result.exception = inOutError;
            } catch (Exception e) {
                e.getMessage();
            }
            return result;
        };
        CommonAsyncTask.RunnablePost<AsyncResult> postRunnable = param -> {
            if (param.hasError())
                getNavigator().handleError(param.exception);
            else {
                getNavigator().updateFragments();
            }

        };

        asyncCall(runnableDo, postRunnable, 1);

    }

    public void setSharedModel(VerificationSharedViewModel sharedModel) {
        this.verificationSharedViewModel = sharedModel;
    }
}
