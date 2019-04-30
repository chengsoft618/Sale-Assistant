package com.shoniz.saledistributemobility.infrastructure.connectivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.support.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ConnectionStateMonitor    {

    @SuppressLint("NewApi")
    public static void registerConnectivityNetworkMonitor(final Context context) {


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkRequest.Builder builder = new NetworkRequest.Builder();

        connectivityManager.registerNetworkCallback(
                builder.build(),
                new ConnectivityManager.NetworkCallback() {

                    @Override
                    public void onAvailable(Network network) {
                        context.sendBroadcast(
                                getConnectivityIntent(false)
                        );
                    }

                    @Override
                    public void onLost(Network network) {
                        context.sendBroadcast(
                                getConnectivityIntent(true)
                        );
                    }
                }

        );

    }


    private static Intent getConnectivityIntent(boolean noConnection) {
        Intent intent = new Intent();
        intent.setAction("com.shoniz.saledistributemobility.CONNECTIVITY_CHANGE");
        intent.putExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, noConnection);
        return intent;
    }
}
