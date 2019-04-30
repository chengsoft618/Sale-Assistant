package com.shoniz.saledistributemobility.framework.exception.log;


import android.content.Context;

import com.shoniz.saledistributemobility.data.model.log.ILogRepository;
import com.shoniz.saledistributemobility.data.model.log.LogRepository;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.ArrayList;
import java.util.List;

public final class LoggerCommandFactory {
   //private ILogRepository logRepository;
    private  Context context;

    public LoggerCommandFactory(Context context) {
        this.context = context;
    }

    public final List<LoggerCommand> create(ILoggable loggable) {
        List<LoggerCommand> loggers = new ArrayList();
        if (loggable instanceof ISendToServerExceptionLogger) {
            SendToServerExceptionLogger logger = new SendToServerExceptionLogger(context,
                    (BaseException)loggable);
            LoggerCommand loggerCommand = new LoggerCommand(logger);

            loggers.add(loggerCommand);
        }

        return loggers;
    }
}