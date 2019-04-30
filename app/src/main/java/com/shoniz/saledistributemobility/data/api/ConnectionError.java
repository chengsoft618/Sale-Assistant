package com.shoniz.saledistributemobility.data.api;

import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.SysException;

/**
 * Created by 921235 on 5/12/2018.
 */

public class ConnectionError extends SysException {
    public ConnectionError(CommonPackage commonPackage, Exception exception){
        super(commonPackage, exception);
    }

    public ConnectionError(CommonPackage commonPackage, Exception exception, BaseException parent){
        super(commonPackage, exception, parent);
    }
}
