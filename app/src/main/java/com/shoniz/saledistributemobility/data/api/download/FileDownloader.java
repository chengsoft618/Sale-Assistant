package com.shoniz.saledistributemobility.data.api.download;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.api.core.ICoreApi;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.utility.SimpleAsyncTask;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;

public class FileDownloader implements IFileDownloader {

    private CommonPackage commonPackage;
    private ICoreApi coreApi;

    public FileDownloader(CommonPackage commonPackage, ICoreApi coreApi) {
        this.commonPackage = commonPackage;
        this.coreApi = coreApi;
    }


    @Override
    public void download(String url, String fileName, String description, String mimeType) {


        RunnableMethod runnableMethod = (param, onProgressUpdate) -> {

            try {
                String downloadPath = Environment.DIRECTORY_DOWNLOADS;
                if (!commonPackage.getUtility().canWritePath(downloadPath)) {
                    downloadPath=Environment.getExternalStorageDirectory().getAbsolutePath();
                }
                String base = coreApi.getActiveUrl();
                Uri downloadUri = Uri.parse(base + "/" + url);
                DownloadManager.Request request = new DownloadManager.Request(downloadUri);
                DownloadManager downloadManager = (DownloadManager) commonPackage.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                        DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setTitle(commonPackage.getContext().getString(R.string.common_downloading_file))
                        .setDescription(description)
                        .setDestinationInExternalPublicDir(downloadPath, fileName);
                long id = downloadManager.enqueue(request);
                commonPackage.getSettingPref().setDownloadId(id);
            } catch (Exception e) {
                e.getMessage();
            }

            return null;
        };
        SimpleAsyncTask simpleAsyncTask = new SimpleAsyncTask(null, runnableMethod, null, null);
        simpleAsyncTask.run();

    }


}
