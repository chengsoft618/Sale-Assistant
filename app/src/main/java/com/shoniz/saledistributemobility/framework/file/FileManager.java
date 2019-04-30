package com.shoniz.saledistributemobility.framework.file;

import android.content.Context;
import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

/**
 * Created by 921235 on 5/10/2018.
 */

public class FileManager {

    public static boolean isDirectoryExist(String folderFullPath){
        File file = new File(folderFullPath);
        return file.isDirectory();
    }

    public static void createDirectory(String folderFullPath){
        File file = new File(folderFullPath);
        file.mkdirs();
    }


}
