package com.shoniz.saledistributemobility.view.main;

import android.net.Uri;

import com.shoniz.saledistributemobility.view.base.viewmodel.INavigator;

import java.io.File;

public interface IMainNavigator extends INavigator {
    void updateData(boolean isForNewVersion);
    void showNewApkNotification();

    void startChangeLogs();
}
