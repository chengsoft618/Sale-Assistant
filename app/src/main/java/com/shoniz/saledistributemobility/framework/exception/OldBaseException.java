package com.shoniz.saledistributemobility.framework.exception;

import android.util.Log;

import com.shoniz.saledistributemobility.framework.exception.model.ErrorModel;

/**
 * Created by ferdos.s on 5/18/2017.
 */

public abstract class OldBaseException extends Exception {
    protected ErrorModel errorModel;
    public OldBaseException(String message){
        errorModel = new ErrorModel();
        errorModel.userMessage = message;
    }
    public OldBaseException() {
        errorModel = new ErrorModel();
    }

    public final String getUserMessage(){
        return errorModel.userMessage;
    }
    public final String getSystemMessage(){
        return errorModel.systemMessage;
    }
    public final String getUserTitle(){
        return errorModel.userTitle;
    }

    public void log(){
        Log.e(errorModel.userMessage, errorModel.systemMessage);
    }

}
