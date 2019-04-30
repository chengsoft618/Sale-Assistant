package com.shoniz.saledistributemobility.order;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.shoniz.saledistributemobility.data.model.log.ILogRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.ExceptionHandler;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.infrastructure.wialon.WialonWorker;
import com.shoniz.saledistributemobility.utility.dialog.OnProgressUpdate;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexBusiness;
import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.view.customer.CustomerData;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBasicModel;
import com.shoniz.saledistributemobility.view.customer.SendRequestModel;
import com.shoniz.saledistributemobility.utility.Notification;
import com.shoniz.saledistributemobility.utility.SimpleAsyncTask;
import com.shoniz.saledistributemobility.utility.StringHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.inject.Inject;

import dagger.android.DaggerService;


public class SendOrderService extends DaggerService {
    @Inject
    CommonPackage commonPackage;
    @Inject
    ILogRepository logRepository;
    public static String SEND_ORDER_COMPLETE = "SendOrderComplete";
    public static String ERROR_MESSAGE = "ErrorMessage";
    public static String PERSON_ID = "PersonID";
    public static String ORDER_NO = "OrderNo";
    public static String IS_ERROR = "IsError";
    public static String RESPONSE = "RESPONSE";
    public static String ACTION_SEND_TYPE = "actionSendType";
    public static int NOTIFICATION_ID = 10001;
    Notification notification;
    static ConcurrentLinkedQueue<SendRequestModel> queue = new ConcurrentLinkedQueue<>();
    public static Boolean serviceRunning = false;


    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        RunnableMethod runDo = new RunnableMethod() {
            @Override
            public Object run(Object param, OnProgressUpdate onProgressUpdate) {
                final Context context = getBaseContext();
                serviceRunning = true;
                boolean isError;
                String errorMessage;
                Notification notificationAll = null;
                ResultModel resultModel = new ResultModel();
                if (queue.size() > 0) {
                    notificationAll = new Notification(context, NOTIFICATION_ID);
                }
                if (intent != null) {
                    while (queue.size() > 0) {
                        notificationAll.set(R.drawable.ic_launcher_round, "در حال پردازش درخواست ها",
                                "درخواست های باقی مانده :" + queue.size());
                        SendRequestModel sendRequestModel = null;
//                        int orderSentSuccessfullyTimes = 1;
//                        while(orderSentSuccessfullyTimes < 4) {
                            try {
                                notificationAll.push();
                                isError = false;
                                errorMessage = "";
                                sendRequestModel = queue.remove();
                                notification = new Notification(context, sendRequestModel.PersonID);
                                pushNotification(context, sendRequestModel);
                                resultModel = sendOrder(context, sendRequestModel);
                            } catch (Exception e) {
//                                if(orderSentSuccessfullyTimes < 3) {
//                                    orderSentSuccessfullyTimes++;
//                                    continue;
//                                }
                                HandleException exceptions = new HandleException(context, e);
                                UncaughtException uncaughtException = new UncaughtException(commonPackage, e);
                                uncaughtException.userMessage="sendOrder";
                                //ExceptionHandler.handle(uncaughtException,commonPackage.getContext());
                                try {
                                    CardIndexBusiness.UpdateErrorMessage(context, sendRequestModel.PersonID, exceptions.getUserMessage());
                                } catch (Exception e1) {
                                    UncaughtException uncaughtException1 = new UncaughtException(commonPackage, e);
                                    uncaughtException.userMessage = "UpdateErrorMessage";
                                    exceptions.AddException(e1);
                                    ExceptionHandler.handle(uncaughtException1, commonPackage.getContext());
                                }
                                isError = true;
                                errorMessage = exceptions.getUserMessage();
                                notification.setContext(exceptions.getUserMessage() + "\n" +
                                        exceptions.getSystemMessage());
                                notificationAll.push();
                            }
                        //}
                        notification.setProgress(0, 0, false);
                        notificationAll.push();
                        if (sendRequestModel.OrderNo != 0) {
                            resultModel.OrderNo = sendRequestModel.OrderNo;
                        }
                        sendBroadcast(sendRequestModel, isError, errorMessage, resultModel);
                        sendRequestModel.OrderNo = resultModel.OrderNo;
                        try {
                            WialonWorker.setWorker(sendRequestModel);
                        } catch (Exception e) {
                            UncaughtException uncaughtException1=new UncaughtException(commonPackage, e);
                            uncaughtException1.userMessage="UpdateErrorMessage";
                            ExceptionHandler.handle(uncaughtException1, commonPackage.getContext());
                        }
                    }
                }
                serviceRunning = false;
                if (notificationAll != null)
                    notificationAll.cancel();
                return null;
            }
        };


        SimpleAsyncTask asyncTask = new SimpleAsyncTask(null, runDo, null, null);
        asyncTask.run();
        return Service.START_NOT_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    public static void send(Context context, List<SendRequestModel> model) {
        for (int i = 0; i < model.size(); i++) {
            if (!queue.contains(model.get(i)))
                queue.add(model.get(i));
        }
        if (!SendOrderService.serviceRunning) {
            Intent intent = new Intent(context, SendOrderService.class);
            context.startService(intent);
        }

    }

    public static void send(Context context, SendRequestModel model) {
        if (!queue.contains(model))
            queue.add(model);
        if (!SendOrderService.serviceRunning) {
            Intent intent = new Intent(context, SendOrderService.class);
            context.startService(intent);
        }

    }


    private void pushNotification(Context context, SendRequestModel sendRequestModel) throws IOException {
        CustomerBasicModel customerBasicModel = CustomerData.getCustomerBaseInfo(context, sendRequestModel.PersonID);
        notification.setContext(StringHelper.GenerateMessage(context, R.string.noti_send_order, sendRequestModel.PersonID + "", customerBasicModel.PersonName));
        notification.setTitle("ارسال درخواست");
        notification.setProgress(0, 0, true);
        notification.push();
    }

    private ResultModel sendOrder(Context context, SendRequestModel sendRequestModel) throws Exception {
        ResultModel resultModel = new ResultModel();
        switch (sendRequestModel.actionSendType) {
            case NewOrder:
            case UpdateOrder:
                resultModel = OrderBusiness.SendOrder(context, sendRequestModel);
                RequestBusiness.deleteOrder(context, resultModel.OrderNo);
                OrderBusiness.SaveOrderResult(context, resultModel);
                CardIndexBusiness.DeleteCardIndex(context, sendRequestModel.PersonID);
                break;

            case DeleteOrder:
                resultModel = OrderBusiness.DeleteOrder(context, sendRequestModel);
                break;
            case SaleNotReason:
                OrderBusiness.sendReasonAll(context, sendRequestModel.PersonID);
                break;
        }
        return resultModel;
    }


    private void sendBroadcast(SendRequestModel sendRequestModel, boolean isError, String errorMessage, ResultModel resultModel) {
        Intent in = new Intent(SEND_ORDER_COMPLETE);
        in.putExtra(PERSON_ID, sendRequestModel.PersonID);
        in.putExtra(ORDER_NO, resultModel.OrderNo);
        in.putExtra(ERROR_MESSAGE, errorMessage);
        in.putExtra(ACTION_SEND_TYPE, sendRequestModel.actionSendType.ordinal());
        in.putExtra(IS_ERROR, isError);
        in.putExtra(RESPONSE, resultModel.Response);
        sendBroadcast(in);
    }


}
