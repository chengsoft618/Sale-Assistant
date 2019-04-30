package com.shoniz.saledistributemobility.data.api;

import android.content.Context;

import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.SysException;

import javax.inject.Inject;

/**
 * Created by 921235 on 5/9/2018.
 */

public class ApiNetworkException extends SysException {

    public ApiNetworkException(CommonPackage commonPackage, Exception exception){
        super( commonPackage, exception);
    }

    public ApiNetworkException(CommonPackage commonPackage, Exception exception, BaseException parent){
        super(commonPackage, exception, parent);
    }


}
