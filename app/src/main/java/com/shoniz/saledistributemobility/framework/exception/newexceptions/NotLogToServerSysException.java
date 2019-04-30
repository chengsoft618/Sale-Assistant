package com.shoniz.saledistributemobility.framework.exception.newexceptions;

import com.shoniz.saledistributemobility.framework.CommonPackage;

/**
 * Created by ferdos.s on 5/18/2017.
 */

public class NotLogToServerSysException extends BaseException  {


    public NotLogToServerSysException(CommonPackage commonPackage) {
        super(commonPackage);
    }

    public NotLogToServerSysException(CommonPackage commonPackage, BaseException parentException) {
        super(commonPackage, parentException);
    }
}
