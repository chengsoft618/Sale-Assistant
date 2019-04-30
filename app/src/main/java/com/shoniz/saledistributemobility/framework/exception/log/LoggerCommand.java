package com.shoniz.saledistributemobility.framework.exception.log;

import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.file.FileNotFoundError;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.serialization.ILogSerializable;
import com.shoniz.saledistributemobility.framework.serialization.LogSerializer;
import com.shoniz.saledistributemobility.framework.serialization.SerializationError;

import java.io.Serializable;


public class LoggerCommand implements ILoggerCommand, ILogSerializable {
    public ILogger logger;
    //CommonPackage commonPackage;


    public LoggerCommand(ILogger logger) {
        this.logger = logger;
    }

    @Override
    public void execute() {
        logger.log();

    }
}
