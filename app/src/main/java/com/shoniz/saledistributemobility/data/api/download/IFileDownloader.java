package com.shoniz.saledistributemobility.data.api.download;

import com.shoniz.saledistributemobility.framework.InOutError;

import java.io.InputStream;

public interface IFileDownloader {
    void download(String url, String fileName, String description,String mimeType) throws InOutError;
}
