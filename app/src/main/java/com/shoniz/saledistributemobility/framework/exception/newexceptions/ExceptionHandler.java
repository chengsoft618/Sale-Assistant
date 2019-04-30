package com.shoniz.saledistributemobility.framework.exception.newexceptions;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.shoniz.saledistributemobility.data.model.log.ILogRepository;
import com.shoniz.saledistributemobility.framework.Utility;
import com.shoniz.saledistributemobility.framework.exception.log.DisplayExceptionLogger;
import com.shoniz.saledistributemobility.framework.exception.log.IDisplayExceptionLogger;
import com.shoniz.saledistributemobility.framework.exception.log.LoggerCommand;
import com.shoniz.saledistributemobility.framework.exception.log.LoggerCommandFactory;
import com.shoniz.saledistributemobility.framework.serialization.LogSerializer;

import java.util.List;

public class ExceptionHandler {
    public static void handle(BaseException exception, AppCompatActivity activity) {
        if (exception instanceof IDisplayExceptionLogger && activity != null)
            new DisplayExceptionLogger(exception, activity).log();

        while (!exception.exceptionStack.isEmpty()) {
            List<LoggerCommand> commands =
                    new LoggerCommandFactory(activity)
                            .create(exception.exceptionStack.pop());
            for (LoggerCommand command : commands) {
                command.execute(); //TODO complete exceptions
            }
        }
        Utility.writeFile(exception.getMessage());
    }

    public static void handle(BaseException exception, Context context) {
        try {
            while (!exception.exceptionStack.isEmpty()) {
                List<LoggerCommand> commands =
                        new LoggerCommandFactory(context)
                                .create(exception.exceptionStack.pop());
                for (LoggerCommand command : commands) {
                    command.execute(); //TODO complete exceptions
                }
            }
            Utility.writeFile(exception.getMessage());
        } catch (Exception e) {
            Utility.writeFile("خطا در سریال کردن خطا" + e.getMessage());
        }
    }
}
