package com.shoniz.saledistributemobility.data.api.core;

import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.io.InputStream;

public interface ICoreApi {
    String getActiveUrl() throws BaseException, ApiException;

}
