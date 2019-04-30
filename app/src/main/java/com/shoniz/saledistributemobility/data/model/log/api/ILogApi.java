package com.shoniz.saledistributemobility.data.model.log.api;

import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.data.model.location.LocationEntity;
import com.shoniz.saledistributemobility.data.model.log.LogEntity;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.List;

public interface ILogApi {
    void sendLog(LogEntity log) throws BaseException, ApiException;
    void sendLogs(List<LogEntity> logs) throws BaseException, ApiException;
}
