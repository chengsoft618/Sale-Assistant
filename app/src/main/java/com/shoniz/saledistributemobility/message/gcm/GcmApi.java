package com.shoniz.saledistributemobility.message.gcm;

import android.content.Context;

import com.shoniz.saledistributemobility.framework.Device;
import com.shoniz.saledistributemobility.framework.exception.OldApiException;
import com.shoniz.saledistributemobility.framework.exception.ConnectionException;
import com.shoniz.saledistributemobility.utility.data.api.ApiConsts;
import com.shoniz.saledistributemobility.utility.data.api.CallApi;
import com.shoniz.saledistributemobility.utility.data.api.PostModel;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.device.Connection;

import java.io.IOException;

import okhttp3.Response;


public class GcmApi {
    public void saveGcmToken(Context context, String googleId) throws IOException, ConnectionException, OldApiException {

        PostModel postModel = new PostModel();
        Device device=new Device(context);
        postModel.Imei =device.getIMEI();
        if(device.getIMEI()==device.getActualIMEI() && googleId.length()>1){
            postModel.ApplicationVersion = Common.getVersionCode(context);
            postModel.GoogleId = googleId;
            // TODO : set Latitude,Longitude,Accuracy,BatteryStatus,GPSStatus
            Response response = new CallApi(Connection.getApiUrl(context))
                    .Post(ApiConsts.Api.REGISTER_CLOUD_MESSAGE, postModel);

            if (response.code() != 200) {
                throw
                        new OldApiException(context,response);
            }
        }
    }
}
