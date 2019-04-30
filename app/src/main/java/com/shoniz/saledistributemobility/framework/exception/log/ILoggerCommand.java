package com.shoniz.saledistributemobility.framework.exception.log;

import com.shoniz.saledistributemobility.framework.file.FileNotFoundError;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.serialization.SerializationError;

/**
 * Created by 921235 on 5/10/2018.
 */

public interface ILoggerCommand {
    void execute() throws FileNotFoundError, InOutError, SerializationError;
}
