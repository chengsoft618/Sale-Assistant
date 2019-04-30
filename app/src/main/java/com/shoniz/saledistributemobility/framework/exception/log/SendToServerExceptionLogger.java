package com.shoniz.saledistributemobility.framework.exception.log;


import android.content.Context;

import com.shoniz.saledistributemobility.data.model.log.LogRepository;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.serialization.ILogSerializable;
import com.shoniz.saledistributemobility.framework.serialization.LogSerializer;
import com.shoniz.saledistributemobility.utility.SimpleAsyncTask;
import com.shoniz.saledistributemobility.utility.dialog.OnProgressUpdate;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;

public class SendToServerExceptionLogger implements ILogger<BaseException>, ILogSerializable {
    private BaseException baseException;
    private Context context;


    public SendToServerExceptionLogger(Context context, BaseException baseException) {
        this.baseException = baseException;
        this.context = context;
    }

    @Override
    public void log() {
        UncoughtExceptionIntentService.start(context, baseException.getLogEntity());
    }
}
