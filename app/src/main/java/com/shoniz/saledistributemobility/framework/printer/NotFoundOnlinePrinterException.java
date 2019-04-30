package com.shoniz.saledistributemobility.framework.printer;

import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.log.IDisplayExceptionLogger;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

class NotFoundOnlinePrinterException extends BaseException implements IDisplayExceptionLogger {
    public NotFoundOnlinePrinterException(CommonPackage commonPackage) {
        super(commonPackage);

        userMessage = "چاپگری به دستگاه شما متصل نشده است. لطفا پس از بررسی، مجددا تلاش کنید.";
    }
}
