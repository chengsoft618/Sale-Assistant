package com.shoniz.saledistributemobility.framework.exception.log;

import android.support.v7.app.AppCompatActivity;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.utility.dialog.ErrorDialog.ErrorDialogViewModel;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.utility.dialog.ErrorDialog.ErrorDialog;

import java.util.Stack;

public class DisplayExceptionLogger implements ILogger<BaseException> {

    private AppCompatActivity activity;
    private BaseException baseException;
    public DisplayExceptionLogger(BaseException baseException, AppCompatActivity activity){
        this.activity = activity;
        this.baseException = baseException;
    }

    @Override
    public void log() {
        StringBuilder message = new StringBuilder();

        Stack<BaseException> exceptions = (Stack<BaseException>)baseException.exceptionStack.clone();
        while (!exceptions.isEmpty()){
            BaseException t = exceptions.pop();
            if(t instanceof IDisplayExceptionLogger) {
                if (message.length() > 0)
                    message.append("\n");
                message.append(t.userTitle).append(": ").append(t.userMessage);
            }
        }
        if(activity!=null){
            //ErrorDialogViewModel errorDialogViewModel=new ErrorDialogViewModel(activity.getString(R.string.error_dialog_title),message.toString());
            ErrorDialog.showDialog(activity,activity.getString(R.string.error_dialog_title),message.toString());
        }

    }

}
