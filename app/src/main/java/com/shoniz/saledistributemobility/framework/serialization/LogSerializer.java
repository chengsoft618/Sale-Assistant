package com.shoniz.saledistributemobility.framework.serialization;

import android.content.Context;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.log.LogEntity;
import com.shoniz.saledistributemobility.data.sharedpref.SettingPref;
import com.shoniz.saledistributemobility.di.BaseApp;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.Device;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.Utility;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.framework.file.FileNotFoundError;
import com.shoniz.saledistributemobility.framework.file.LogFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;


public class LogSerializer implements ISerializer<ILogSerializable> {

    private LogFile logFile;
    private CommonPackage commonPackage;

    public LogSerializer(Context context) {
        this.commonPackage = new CommonPackage(
                new Device(context),
                new Utility(context),
                context,
                new SettingPref(context)
        );
        this.logFile = new LogFile();
    }

    @Override
    public void serialize(ILogSerializable serializableLog) throws SerializationError, FileNotFoundError, InOutError {
        FileOutputStream fos = null;
        ObjectOutputStream os = null;
        try {
            fos = logFile.getFileOutputStream(commonPackage.getContext());
            os = new ObjectOutputStream(fos);
            os.writeObject(serializableLog);
        } catch (FileNotFoundException e) {
            FileNotFoundError foundError = new FileNotFoundError(commonPackage, e, logFile.LOG_FOLDER);
            throw foundError;
        } catch (IOException e) {
            InOutError inOutError = new InOutError(commonPackage, e, logFile.LOG_FOLDER);
            throw inOutError;
        } finally {
            if (os != null) try {
                os.close();
            } catch (IOException e) {
                InOutError inOutError = new InOutError(commonPackage, e, "ObjectOutputStream");
                throw inOutError;
            }
            if (fos != null) try {
                fos.close();
            } catch (IOException e) {
                InOutError inOutError = new InOutError(commonPackage, new IOException(""), "FileOutputStream");
                throw inOutError;
            }
        }
    }

    public void serialize(List<LogEntity> logEntities) throws SerializationError, FileNotFoundError, InOutError {
        for (ILogSerializable logSerializable : logEntities) {
            serialize(logSerializable);
        }
    }


    @Override
    public ILogSerializable deserialize(String filePath) throws SerializationError, InOutError {
        FileInputStream fis = null;
        FileInputStream fos;
        try {
            //fis = openFileInput(getFilesDir() + "/MySimpleClassFile.dat");
            fos = new FileInputStream(new File(filePath));
            ObjectInputStream is = new ObjectInputStream(fos);
            ILogSerializable logSerializable = (ILogSerializable) is.readObject();
            is.close();
            fos.close();
            return logSerializable;
        } catch (IOException e) {
            throw new InOutError(commonPackage, e, filePath);
        } catch (ClassNotFoundException e) {
            throw new SerializationError(commonPackage, e, commonPackage.getContext().getString(R.string.error_find_serialized_object));
        }
    }
}
