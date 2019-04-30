package com.shoniz.saledistributemobility.infrastructure.wialon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.reflect.TypeToken;
import com.shoniz.saledistributemobility.data.model.log.LogEntity;
import com.shoniz.saledistributemobility.data.model.log.LogRepository;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.file.FileNotFoundError;
import com.shoniz.saledistributemobility.framework.file.LogFile;
import com.shoniz.saledistributemobility.framework.serialization.LogSerializer;
import com.shoniz.saledistributemobility.framework.serialization.SerializationError;
import com.shoniz.saledistributemobility.utility.SimpleAsyncTask;
import com.shoniz.saledistributemobility.utility.dialog.OnProgressUpdate;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;
import com.shoniz.saledistributemobility.view.customer.SendRequestModel;
import com.shoniz.saledistributemobility.data.model.location.ILocationRepository;
import com.shoniz.saledistributemobility.data.model.location.LocationEntity;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.Utility;
import com.shoniz.saledistributemobility.view.entity.EmployeeInfoEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import dagger.android.DaggerIntentService;

public class WialonService extends DaggerIntentService {

    static String SEND_REQUEST_MODEL_STRING = "SEND_REQUEST_MODEL_STRING";

    @Inject
    CommonPackage commonPackage;

    @Inject
    ISettingRepository settingRepository;

    @Inject
    ILocationRepository locationRepository;

    EmployeeInfoEntity employeeInfoEntity;

    public WialonService() {
        super("WialonService");
    }

    public static void startService(Context context) {
        Utility utility = new Utility(context);
        if (!utility.isRunningService(WialonService.class)) {
            Intent mIntent = new Intent(context, WialonService.class);
            context.startService(mIntent);
        }
    }

    public static void startService(Context context, SendRequestModel requestModel) {
        Utility utility = new Utility(context);

        if (!utility.isRunningService(WialonService.class)) {
            Intent mIntent = new Intent(context, WialonService.class);
            mIntent.putExtra(SEND_REQUEST_MODEL_STRING, Utility.getJson(requestModel));
            context.startService(mIntent);
        }
    }


    public void sendLocation(WialonClient client) {

        try {
            HashMap<String, String> messages = new HashMap<>();

            messages.put("Visitor" + employeeInfoEntity.EmployeeId, "Tracking" + " Battery " +
                    commonPackage.getDevice().getBatteryCurrentLevel());
            for (LocationEntity locationEntity : locationRepository.getLocations())
                client.sendMessage(Wialon.makeLocationMessage(locationEntity, messages));

        } catch (Exception wiolonSendMessageError) {
        }

    }

    public void sendLocation(WialonClient client,LocationEntity locationEntity, String customerId, String orderNo, String action) {

        try {
            HashMap<String, String> messages = new HashMap<>();
            if (orderNo.equals("0")) {
                messages.put("Visitor " + employeeInfoEntity.EmployeeId, " Customer - "
                        + customerId + "( Rollback From Server  )" + " Battery " +
                        commonPackage.getDevice().getBatteryCurrentLevel());
            } else {
                messages.put("Visitor " + employeeInfoEntity.EmployeeId, " Customer - "
                        + customerId + "( " + action + " - " + orderNo + "  )" + " Battery " +
                        commonPackage.getDevice().getBatteryCurrentLevel());
            }

            client.sendMessage(Wialon.makeLocationMessage(locationEntity, messages));

        } catch (Exception wiolonSendMessageError) {
        }

    }

    private void login(WialonClient client) {
        try {
            client.sendMessage(Wialon.makeLoginMessage(commonPackage.getDevice().getIMEI()));
        } catch (WialonSendMessageError wiolonSendMessageError) {
            wiolonSendMessageError.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        employeeInfoEntity = commonPackage.getSettingPref().getEmployeeInfoEntity();

        RunnableMethod runDo=new RunnableMethod() {
            @Override
            public Object run(Object param, OnProgressUpdate onProgressUpdate) {
                WialonClient client = new WialonClient(commonPackage, settingRepository.getWiolonIp(), settingRepository.getWiolonPort());
                client.setWiolonListener(new IWialonListener() {
                    @Override
                    public void onSocketClosed(Context context) {
                        client.stopClient();
                    }

                    @Override
                    public void onLogin(boolean status) {
                        Bundle bundle = intent.getExtras();
                        SendRequestModel sendRequestModel = null;
                        String personId = "";
                        String orderNo = "";
                        String action = "";
                        if (bundle != null && bundle.containsKey(SEND_REQUEST_MODEL_STRING)) {
                            sendRequestModel = Utility.getObject(bundle.getString(SEND_REQUEST_MODEL_STRING), new TypeToken<SendRequestModel>() {
                            });
                            personId = "" + sendRequestModel.PersonID;
                            orderNo = "" + sendRequestModel.OrderNo;
                            action = "" + sendRequestModel.actionSendType.name();
                        }

                        if (sendRequestModel == null)
                            sendLocation(client);
                        else {
                            sendLocation(client,sendRequestModel.location, personId, orderNo, action);
                        }
                        locationRepository.deleteAll();
                        client.stopClient();
                    }

                    @Override
                    public void onRun() {
                        login(client);
                    }
                });
                client.run();
                return null;
            }
        };

        if(settingRepository.isEnableLocationTracking()){
            SimpleAsyncTask asyncTask=new SimpleAsyncTask(null,runDo,null,null);
            asyncTask.runSerial();
        }
    }
}
