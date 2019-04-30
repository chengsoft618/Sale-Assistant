package com.shoniz.saledistributemobility.framework.exception.newexceptions;

import com.shoniz.saledistributemobility.framework.CommonPackage;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by ferdos.s on 5/18/2017.
 */

public class BusinessException extends BaseException  {

    public BusinessException(CommonPackage commonPackage){
        super(commonPackage);
        userTitle = "خطا در عملیات کاربر";
    }

    public BusinessException(CommonPackage commonPackage, String userMessage){
        super(commonPackage);
        this.userMessage = userMessage;
        userTitle = "خطا در عملیات کاربر";
    }

    public BusinessException(CommonPackage commonPackage, String userMessage, BaseException parent){
        super(commonPackage, parent);
        this.userMessage = userMessage;
        userTitle = "خطا در عملیات کاربر";
    }
}
