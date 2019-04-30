package com.shoniz.saledistributemobility.framework.file;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by 921235 on 5/10/2018.
 */

public class LogFile {

    //static Context context;
    static public String LOG_FOLDER = "";

//    public LogFile(){
//        LOG_FOLDER = context.getFilesDir() + "/logs/";
//    }

    public static FileOutputStream getFileOutputStream(Context context) throws FileNotFoundException {
        LOG_FOLDER = context.getFilesDir() + "/logs/";
        if(!FileManager.isDirectoryExist(LOG_FOLDER))
            FileManager.createDirectory(LOG_FOLDER);
        return new FileOutputStream (new File( LOG_FOLDER + new Date().getTime() + UUID.randomUUID() + ".dat"), true);
    }

    public static File[] getLogFilesPaths(Context context){
        LOG_FOLDER = context.getFilesDir() + "/logs/";
        if(FileManager.isDirectoryExist(LOG_FOLDER)){
            return new File(LOG_FOLDER).listFiles();
        }
        return new File[]{};
    }

}
