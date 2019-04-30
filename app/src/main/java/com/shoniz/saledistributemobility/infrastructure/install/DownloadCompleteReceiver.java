package com.shoniz.saledistributemobility.infrastructure.install;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.shoniz.saledistributemobility.data.api.ApiNetworkException;
import com.shoniz.saledistributemobility.data.model.log.ILogRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.utility.SimpleAsyncTask;
import com.shoniz.saledistributemobility.utility.dialog.OnProgressUpdate;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;

import javax.inject.Inject;

import dagger.android.DaggerBroadcastReceiver;


public class DownloadCompleteReceiver extends DaggerBroadcastReceiver {

    @Inject
    ILogRepository logRepository;
    @Inject
    CommonPackage commonPackage;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        try {
            String action = intent.getAction();
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Query query = new DownloadManager.Query();
                if (intent.getExtras() != null) {
                    Long id = intent.getExtras().getLong(DownloadManager.EXTRA_DOWNLOAD_ID);
                    if (commonPackage.getSettingPref().getDownloadId() == id) {
                        query.setFilterById(id);
                        Cursor cursor = null;
                        if (downloadManager != null) {
                            cursor = downloadManager.query(query);
                            if (cursor.moveToFirst()) {
                                int statusColumnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                                int status = cursor.getInt(statusColumnIndex);
                                if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                    String downloadPathFile = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                                    commonPackage.getUtility().installApk(Uri.parse(downloadPathFile).getPath());
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception ex){
            commonPackage.getUtility().toast(ex.getMessage());
        }


    }
}
