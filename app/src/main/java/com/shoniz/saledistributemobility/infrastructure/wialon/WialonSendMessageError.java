package com.shoniz.saledistributemobility.infrastructure.wialon;

import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

public class WialonSendMessageError extends BaseException {
    public WialonSendMessageError(CommonPackage commonPackage) {
        super(commonPackage);
        userMessage = "خطا در ارسال اطلاعات به سیمرغ!";
    }
}
