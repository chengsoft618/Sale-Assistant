package com.shoniz.saledistributemobility.data.model.update;

import android.support.annotation.StringRes;

import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.log.IDisplayExceptionLogger;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BusinessException;

public class AppUpdateError extends BusinessException implements IDisplayExceptionLogger {
    public AppUpdateError(CommonPackage commonPackage, String userMessage) {
        super(commonPackage, userMessage);
    }

    public AppUpdateError(CommonPackage commonPackage, @StringRes int resId) {
        super(commonPackage, commonPackage.getContext().getString(resId));
    }

}
