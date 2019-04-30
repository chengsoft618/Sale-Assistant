package com.shoniz.saledistributemobility.framework.serialization;

import com.shoniz.saledistributemobility.framework.file.FileNotFoundError;
import com.shoniz.saledistributemobility.framework.InOutError;

import java.io.IOException;

/**
 * Created by 921235 on 5/10/2018.
 */

public interface ISerializer<T extends ILogSerializable> {
    void serialize(T object) throws SerializationError, FileNotFoundError, IOException, InOutError;

    T deserialize(String filePath) throws SerializationError, InOutError;
}
