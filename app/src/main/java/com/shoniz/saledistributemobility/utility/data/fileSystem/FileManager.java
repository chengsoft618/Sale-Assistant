package com.shoniz.saledistributemobility.utility.data.fileSystem;

import android.content.Context;
import android.os.Environment;
import android.util.Base64;

import com.shoniz.saledistributemobility.utility.data.sqlite.DBHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.shoniz.saledistributemobility.utility.data.sqlite.SqliteConsts.VERSION_DATABASE;

/**
 * Created by ferdos.s on 12/17/2017.
 */

public class FileManager {

    public static boolean replace(Context context, File desFile) throws IOException {
        desFile.delete();
        String path = DBHelper.getDatabasePath(context) + "/";
        DBHelper.createFromAssets(context, path + desFile.getName(),
                VERSION_DATABASE, "db/" + desFile.getName());
        return true;
    }

    public static boolean deleteDatabase(Context context, String dbName) {
        File file = new File(DBHelper.getDatabasePath(context) + "/" + dbName + ".db");
        file.delete();
        return true;
    }

    public static boolean deleteFolderContent(Context context, String desFolder) {
        File dir = new File(desFolder);
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (String aChildren : children) {
                new File(dir, aChildren).delete();
            }
        }
        return true;
    }

    public static void saveBase64File(String base64,
                                      String filePath) throws IOException {

        OutputStream osf = null;
        try {
            byte[] imgBytes = Base64.decode(base64, Base64.DEFAULT);
            osf = new FileOutputStream(new File(filePath));
            osf.write(imgBytes);
            osf.flush();
        }
        finally {
            if (osf != null)
                osf.close();
        }
    }

    public static boolean isFileExist(String path){
        File f = new File(path);
        return f.exists();
    }

    public static boolean deleteFile(String path){
        File f = new File(path);
        return f.exists();
    }

    public static String getImagePath(Context context) {
        String path= context.getFilesDir().getAbsolutePath();
        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return path;
    }
}
