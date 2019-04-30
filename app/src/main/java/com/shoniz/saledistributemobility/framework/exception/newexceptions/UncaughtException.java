package com.shoniz.saledistributemobility.framework.exception.newexceptions;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.OldBaseException;
import com.shoniz.saledistributemobility.framework.exception.log.IDisplayExceptionLogger;
import com.shoniz.saledistributemobility.framework.exception.log.ISendToServerExceptionLogger;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by 921235 on 5/8/2018.
 */

public class UncaughtException extends BaseException implements IDisplayExceptionLogger, ISendToServerExceptionLogger {

    public UncaughtException(CommonPackage commonPackage, Exception exception, String message){
        super(commonPackage);
        init(exception, message);
    }



    public UncaughtException(CommonPackage commonPackage, Exception exception){
       super(commonPackage);
       init(exception);
   }

    public UncaughtException(CommonPackage commonPackage, OldBaseException exception){
        super(commonPackage);
        init(exception);
        userMessage=exception.getUserMessage();
        userTitle=exception.getUserTitle();
    }

    public UncaughtException(CommonPackage commonPackage, Exception exception, BaseException parent){
        super(commonPackage, parent);
        init(exception);

    }

    private void init(Exception exception) {
        String userMessage= exception.getMessage();
        init(exception, userMessage);
    }

    private void init(Exception exception, String userMessage) {
        StackTraceElement[] arr = exception.getStackTrace();
        StringBuilder report = new StringBuilder(getStackTrace().toString());
        String lineSeperator = "-------------------------------\n\n";
        report.append("--------- Stack trace ---------\n\n");
        for (StackTraceElement anArr : arr) {
            report.append("    ");
            report.append(anArr.toString());
            report.append(lineSeperator);
        }
        systemMessage = report.toString();
        this.userMessage= userMessage;
        userTitle= commonPackage.getContext().getString(R.string.ex_uncaught_exception_title);
    }

    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
