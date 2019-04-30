package com.shoniz.saledistributemobility.framework.printer;

import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.log.IDisplayExceptionLogger;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

class NotFoundPairedPrinterException extends BaseException implements IDisplayExceptionLogger {
    public NotFoundPairedPrinterException(CommonPackage commonPackage) {
        super(commonPackage);
        userMessage = "چاپگر جفت شده به دستگاه یافت نشد";
    }
}
