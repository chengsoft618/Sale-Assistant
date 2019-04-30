package com.shoniz.saledistributemobility.data.model.log;

import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;


public interface ILogRepository {
    void sentLogToServer(LogEntity logEntities) throws BaseException, ApiException;
}
