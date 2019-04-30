package com.shoniz.saledistributemobility.framework;

import android.content.Context;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.framework.exception.log.IDisplayExceptionLogger;
import com.shoniz.saledistributemobility.framework.exception.log.ISendToServerExceptionLogger;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.NotLogToServerSysException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.SysException;

import java.io.IOException;

import javax.inject.Inject;

public class InOutError extends NotLogToServerSysException implements IDisplayExceptionLogger{
    public InOutError(CommonPackage commonPackage, IOException exception, String source) {
        super(commonPackage);
        init(source);
    }

    public InOutError(CommonPackage commonPackage, IOException exception, String source, BaseException parent) {
        super(commonPackage, parent);
        init(source);
    }

    public void init(String source){
        userMessage = "نرم افزار به اینترنت دسترسی ندارد. جهت تست اینترنت خود می توانید سایت www.yahoo.com را در مرورگر خود باز نمایید. همچنین از خاموش بودن نرم افزار های VPN مطمئن شوید. و در نهایت ممکن است این خطا به علت کندی سرعت اینترنت باشد که با تغییر مکان خود می توانید بعدا سعی نمایید.";
        userTitle = "خطا در دسترسی به اینترنت";
    }
}
