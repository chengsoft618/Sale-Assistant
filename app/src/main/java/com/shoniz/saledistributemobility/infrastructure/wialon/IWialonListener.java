package com.shoniz.saledistributemobility.infrastructure.wialon;

import android.content.Context;

public interface IWialonListener {
    void onSocketClosed(Context context);
    void onLogin(boolean status);
    void onRun();
}
