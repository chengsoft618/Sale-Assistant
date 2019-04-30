package com.shoniz.saledistributemobility.data.api;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.OldApiException;
import com.shoniz.saledistributemobility.framework.exception.log.IDisplayExceptionLogger;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.SysException;

public class ApiError extends SysException implements IDisplayExceptionLogger{
    public ApiError(CommonPackage commonPackage, ApiException exception) {
        super(commonPackage, exception);
        init(exception.getMessage());
    }

    public ApiError(CommonPackage commonPackage, ApiException exception, BaseException parent) {
        super(commonPackage, exception ,parent);
        init(exception.getMessage());
    }

    public void init(String source){
        userMessage =source;
        userTitle = commonPackage.getContext().getResources().getString(R.string.error_message_api_server);
    }
}
