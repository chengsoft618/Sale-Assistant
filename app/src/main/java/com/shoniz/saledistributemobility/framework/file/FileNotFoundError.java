package com.shoniz.saledistributemobility.framework.file;

import android.content.Context;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.SysException;

import java.io.FileNotFoundException;

import javax.inject.Inject;

public class FileNotFoundError extends SysException{

    public FileNotFoundError(CommonPackage commonPackage, FileNotFoundException exception, String fileName) {
        super(commonPackage, exception);
        init(fileName);
    }

    public FileNotFoundError(CommonPackage commonPackage, FileNotFoundException exception, String fileName, BaseException parent) {
        super(commonPackage, exception ,parent);
        init(fileName);
    }

    public void init(String fileName){
        userMessage = commonPackage.getContext().getResources().getString(R.string.ex_file_not_found_message, fileName);
        userTitle = commonPackage.getContext().getResources().getString(R.string.file_error_title);
    }
}
