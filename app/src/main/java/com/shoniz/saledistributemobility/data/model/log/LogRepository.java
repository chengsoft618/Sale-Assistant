package com.shoniz.saledistributemobility.data.model.log;

import android.content.Context;

import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.data.model.log.api.ILogApi;
import com.shoniz.saledistributemobility.data.model.log.api.LogRetrofit;
import com.shoniz.saledistributemobility.data.sharedpref.SettingPref;
import com.shoniz.saledistributemobility.data.sharedpref.SettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.Device;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.Utility;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.List;

//import javax.inject.Inject;

public class LogRepository implements ILogRepository {
    Context context;

    LogRetrofit logApi;

    public LogRepository(Context context) {
        this.context = context;
        CommonPackage commonPackage =
                new CommonPackage(new Device(context), new Utility(context), context, new SettingPref(context));
        logApi = new LogRetrofit(commonPackage);
    }


    public void sentLogToServer(LogEntity log) throws BaseException {
        logApi.sendLog(log);
    }

    public void sentLogsToServer(List<LogEntity> logs) throws BaseException {
        logApi.sendLogs(logs);
    }
}
