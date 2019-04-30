package com.shoniz.saledistributemobility.message.firebase;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.shoniz.saledistributemobility.framework.exception.ConnectionException;
import com.shoniz.saledistributemobility.framework.exception.OldApiException;
import com.shoniz.saledistributemobility.message.gcm.GcmApi;
import com.shoniz.saledistributemobility.message.gcm.GcmPreferences;
import com.shoniz.saledistributemobility.utility.Common;

import java.io.IOException;

import static android.content.ContentValues.TAG;



public class ShonizFireBase extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();


//        String token = FirebaseInstanceId.getInstance().getToken();
//        if (!token.isEmpty()) {
//            try {
//                new GcmApi().saveGcmToken(this, token);
//                Common.getPref(this).set(GcmPreferences.SENT_TOKEN_TO_SERVER, token);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }





        //Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken);
    }
}
