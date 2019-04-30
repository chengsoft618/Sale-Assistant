package com.shoniz.saledistributemobility.location;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.log.IDisplayExceptionLogger;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.SysException;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LocationConnectionError extends SysException implements IDisplayExceptionLogger {
    public String systemMessage = "3333";
    public String stackTrace = "";

    public LocationConnectionError(CommonPackage commonPackage, Exception exception, BaseException parent){
        super(commonPackage, parent);
    }

    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        return sw.getBuffer().toString();
    }
}
