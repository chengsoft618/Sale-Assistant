package com.shoniz.saledistributemobility.view.path.pathlist;

import android.content.Context;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.api.ApiNetworkException;
import com.shoniz.saledistributemobility.data.model.customer.ICustomerRepository;
import com.shoniz.saledistributemobility.data.model.message.IMessageRepository;
import com.shoniz.saledistributemobility.data.model.message.MessageData;
import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;
import com.shoniz.saledistributemobility.view.base.viewmodel.BaseViewModel;
import com.shoniz.saledistributemobility.view.customer.CustomerData;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

public class PathListViewModel extends BaseViewModel<IPathListNavigator> {

    ICustomerRepository customerRepository;
    CommonPackage commonPackage;

    @Inject
    IMessageRepository messageRepository;
    @Inject
    ISettingRepository settingRepository;

    Context context;

    @Inject
    public PathListViewModel(CommonPackage commonPackage, ICustomerRepository customerRepository) {
        this.commonPackage = commonPackage;
        this.customerRepository = customerRepository;
        context = commonPackage.getContext();
    }

    public void manageOutOfPath(int personId) throws IOException {
        if (CustomerData.isCustomerExist(context, personId)) {
            getNavigator().goToCustomerPage(personId);
            return;
        }
        getNavigator().syncCustomerInfoById(personId);
    }

    public void syncCustomerWholeInfoById(int personId) throws BaseException {
        customerRepository.syncCustomerWholeInfoById(personId);
    }


    public void sendCompletePathVisit(String desc){
        settingRepository.setLastCompletePathVisitDesc(desc);

        CommonAsyncTask.RunnableDo<Object, AsyncResult<List<MessageData>>> runnableDo = (param, onProgress) -> {
            AsyncResult<List<MessageData>> asyncResult = new AsyncResult<>();
            try {
                messageRepository.sendCompletePathVisit(desc);
            } catch (BaseException ex) {
                asyncResult.exception = ex;
            } catch (Exception ex) {
                asyncResult.exception = new UncaughtException(commonPackage, ex);
            }
            return asyncResult;
        };
        CommonAsyncTask.RunnablePost<AsyncResult<List<MessageData>>> postRunnable = result -> {

            if (result.hasError()) {
               getNavigator(). handleError(result.exception);
            }else{
                getNavigator().showSnackBar(context.getString(R.string.path_send_message_successfully));
            }
        };
        asyncCall(runnableDo, postRunnable);
    }

}
