package com.shoniz.saledistributemobility.view.message;

import android.arch.lifecycle.MutableLiveData;
import android.view.View;

import com.shoniz.saledistributemobility.data.model.message.IMessageRepository;
import com.shoniz.saledistributemobility.data.model.message.MessageData;
import com.shoniz.saledistributemobility.data.model.message.MessageEntity;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MessageViewModel extends RecyclerViewModel<MessageData,
        Integer,
        IMessageNavigator> implements IMessageRecyclerListener {

    MutableLiveData<List<MessageData>> lst = new MutableLiveData<>();
    private CommonPackage commonPackage;
    private IMessageRepository messageRepository;

    @Inject
    public MessageViewModel(CommonPackage commonPackage, IMessageRepository messageRepository) {
        this.commonPackage = commonPackage;
        this.messageRepository = messageRepository;
    }

    public void load() {
           CommonAsyncTask.RunnableDo<Object, AsyncResult<List<MessageData>>> runnableDo = (param, onProgress) -> {
                AsyncResult<List<MessageData>> asyncResult = new AsyncResult<>();

            try {
                List<MessageData> messageData = new ArrayList<>();
                messageRepository.sycLatestMessage();
                List<MessageEntity> list = messageRepository.getMessages();
                for (MessageEntity messageEntity : list) {
                    messageData.add(new MessageData(messageEntity));
                }
                messageRepository.readAll();
                asyncResult.model = messageData;
            } catch (BaseException ex) {
                asyncResult.exception = ex;
            } catch (Exception ex) {
                asyncResult.exception = new UncaughtException(commonPackage, ex);
            }
            return asyncResult;
        };
        CommonAsyncTask.RunnablePost<AsyncResult<List<MessageData>>> postRunnable = result -> {
            int x = 0;
            if (!result.hasError() && result.model != null) {
                lst.setValue(result.model);
                refreshRecycler();
            } else {
                getNavigator().handleError(result.exception);
            }
        };
        asyncCall(runnableDo, postRunnable);

    }

    public void loadLatestMessage() {
        CommonAsyncTask.RunnableDo<Object, AsyncResult<List<MessageData>>> runnableDo = (param, onProgress) -> {
            AsyncResult<List<MessageData>> asyncResult = new AsyncResult<>();

            try {
                messageRepository.sycLatestMessage();
                List<MessageData> messageData = new ArrayList<>();
                for (MessageEntity messageEntity : messageRepository.getMessages()) {
                    messageData.add(new MessageData(messageEntity));
                }
                asyncResult.model = messageData;
            } catch (BaseException ex) {
                asyncResult.exception = ex;
            } catch (Exception ex) {
                asyncResult.exception = new UncaughtException(commonPackage, ex);
            }
            return asyncResult;
        };
        CommonAsyncTask.RunnablePost<AsyncResult<List<MessageData>>> postRunnable = result -> {
            int x = 0;
            if (!result.hasError() && result.model != null) {
                lst.setValue(result.model);
                refreshRecycler();
            } else {
                getNavigator().handleError(result.exception);
            }
        };
        asyncCall(runnableDo, postRunnable);
    }

    @Override
    public MutableLiveData<List<MessageData>> getMutableList() {
        return lst;
    }

    @Override
    public void onClick(View v, Integer id) {
        super.onClick(v, id);
    }

    @Override
    public void onDeleteItemClick(View view, int SendId) {

        CommonAsyncTask.RunnableDo<Object, AsyncResult<List<MessageData>>> runnableDo = (param, onProgress) -> {
            AsyncResult<List<MessageData>> asyncResult = new AsyncResult<>();

            messageRepository.delete(SendId);
            List<MessageData> messageData = new ArrayList<>();
            for (MessageEntity messageEntity : messageRepository.getMessages()) {
                messageData.add(new MessageData(messageEntity));
            }
            asyncResult.model = messageData;
            return asyncResult;
        };
        CommonAsyncTask.RunnablePost<AsyncResult<List<MessageData>>> postRunnable = result -> {
            int x = 0;
            if (!result.hasError() && result.model != null) {
                lst.setValue(result.model);
                refreshRecycler();
            } else {
                getNavigator().handleError(result.exception);
            }
        };
        asyncCall(runnableDo, postRunnable);

    }
}
