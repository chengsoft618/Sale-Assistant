package com.shoniz.saledistributemobility.framework.serialization;

import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.log.ISendToServerExceptionLogger;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.SysException;
import com.shoniz.saledistributemobility.utility.Common;

/**
 * Created by 921235 on 5/9/2018.
 */

public class SerializationError extends SysException implements ILogSerializable {
    public SerializationError(CommonPackage commonPackage, Exception exception, String message){
        super(commonPackage, exception);
        userMessage = message;
    }

    public SerializationError(CommonPackage commonPackage, Exception exception, BaseException parent, String message){
        super(commonPackage, exception, parent);
        userMessage = message;
    }

}
