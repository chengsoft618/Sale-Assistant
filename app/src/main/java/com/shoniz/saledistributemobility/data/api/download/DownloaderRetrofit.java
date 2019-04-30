package com.shoniz.saledistributemobility.data.api.download;

import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.data.api.retrofit.IRetroCommand;
import com.shoniz.saledistributemobility.data.api.retrofit.RetroManager;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.io.IOException;
import java.io.InputStream;

public class DownloaderRetrofit implements IFileDownloader {
    ApiParameter apiParameter;
    CommonPackage commonPackage;

    public DownloaderRetrofit(ApiParameter apiParameter, CommonPackage commonPackage) {
        this.apiParameter = apiParameter;
        this.commonPackage = commonPackage;
    }



    public InputStream download(final String fileUrl) throws BaseException {

        IRetroCommand<InputStream, IDownloaderRetrofitService> command = new IRetroCommand<InputStream, IDownloaderRetrofitService>() {
            @Override
            public InputStream exec(IDownloaderRetrofitService service) throws IOException {
                return  service.download(fileUrl).execute().body().byteStream();
            }
        };
        return new RetroManager<InputStream, IDownloaderRetrofitService>(commonPackage).callOfficeApi(command
                , IDownloaderRetrofitService.class);
    }

    @Override
    public void download(String url, String fileName, String description, String mimeType) throws InOutError {

    }
}
