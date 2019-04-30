package com.shoniz.saledistributemobility.framework.exception.newexceptions;

import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.log.ISendToServerExceptionLogger;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by 921235 on 5/8/2018.
 */

public abstract class SysException extends BaseException implements ISendToServerExceptionLogger {


   public SysException(CommonPackage commonPackage, Exception exception){
       super(commonPackage);
       init(exception);
   }

    public SysException(CommonPackage commonPackage, Exception exception, BaseException parent){
        super(commonPackage, parent);
        init(exception);
    }

    private void init(Exception exception) {
        StackTraceElement[] arr = exception.getStackTrace();
        StringBuilder report = new StringBuilder( exception.getStackTrace().toString());
        String lineSeperator = "-------------------------------\n\n";
        report.append("--------- Stack trace ---------\n\n");
        for (StackTraceElement anArr : arr) {
            report.append("    ");
            report.append(anArr.toString());
            report.append(lineSeperator);
        }
        systemMessage = report.toString();
    }

    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
