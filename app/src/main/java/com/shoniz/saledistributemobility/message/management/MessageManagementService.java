package com.shoniz.saledistributemobility.message.management;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.log.ILogRepository;
import com.shoniz.saledistributemobility.data.model.message.IMessageRepository;
import com.shoniz.saledistributemobility.data.model.message.MessageEntity;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.ExceptionHandler;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.infrastructure.wialon.WialonWorker;
import com.shoniz.saledistributemobility.message.MessageTypeEnum;
import com.shoniz.saledistributemobility.view.ordering.order.ResultModel;
import com.shoniz.saledistributemobility.utility.Notification;
import com.shoniz.saledistributemobility.utility.SimpleAsyncTask;
import com.shoniz.saledistributemobility.utility.dialog.OnProgressUpdate;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;
import com.shoniz.saledistributemobility.view.customer.SendRequestModel;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexBusiness;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.DaggerIntentService;

public class MessageManagementService extends DaggerIntentService {

    @Inject
    Context context;

    @Inject

    IMessageRepository messageRepository;

    @Inject
    CommonPackage commonPackage;

    @Inject
    ILogRepository logRepository;



    public MessageManagementService() {
        super("MessageManagementService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        RunnableMethod runDo=new RunnableMethod() {
            @Override
            public Object run(Object param, OnProgressUpdate onProgressUpdate) {
                BaseException exception=null;
                try {
                    List<MessageEntity> models = messageRepository.getMessageFromApi();
                    List<Long> ids=new ArrayList<>();
                    for (MessageEntity messageEntity : models) {
                        getMessage(messageEntity).manageMessages();
                        ids.add((long) messageEntity.MessageId);
                    }
                    messageRepository.setMessageDelivered(ids);
                } catch (BaseException ex) {
                    exception = ex;
                } catch (Exception ex) {
                    exception = new UncaughtException(commonPackage, ex);
                }
                if (exception!=null)
                    ExceptionHandler.handle(exception, getApplicationContext());
                else
                    BroadcastMessage.broadcast(context, new Bundle());
                return null;
            }
        };


        SimpleAsyncTask asyncTask=new SimpleAsyncTask(null,runDo,null,null);
        asyncTask.runSerial();


    }

    private IMessageManger getMessage(MessageEntity messageEntity) {
        switch (MessageTypeEnum.values()[messageEntity.MessageTypeId]) {
            case Personal:
                return new PersonalMessageManager(messageRepository, messageEntity);
            case Issued:
                return new IssuedMessageManager(messageRepository, messageEntity);
            case trackingOff:
            case trackingOn:
                return new TrackingMessageManager(context, messageEntity);

            case updateData:
                return new UpdateDataMessageManager(context, messageEntity);
            default:
                return new PersonalMessageManager(messageRepository, messageEntity);
        }

    }

}



