package com.shoniz.saledistributemobility.framework.bluetooth;

import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.log.IDisplayExceptionLogger;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

public class BluetoothIsDisabledException extends BaseException implements IDisplayExceptionLogger {
    public BluetoothIsDisabledException(CommonPackage commonPackage)  {
        super(commonPackage);

        userMessage = "بلوتوث شما غیر فعال است. لطفا آنرا فعال کرده و مجددا تلاش نمایید";
    }
}
