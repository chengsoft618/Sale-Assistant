package com.shoniz.saledistributemobility.framework.exception;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;

import com.shoniz.saledistributemobility.R;

/**
 * Created by ferdos.s on 5/30/2017.
 */

public class DatabaseException extends OldBaseException {
    private SQLException exception;
    protected DatabaseException(Context context,SQLException exception){
        this.exception = exception;

        if(exception instanceof SQLiteConstraintException){
            errorModel.userTitle = context.getString(R.string.error_dialog_title);
            errorModel.userMessage = context.getString(R.string.error_message_constraint)+
                    exception.getMessage();
            errorModel.systemMessage = exception.getMessage();
        }
        else{
            errorModel.userTitle = context.getString(R.string.error_dialog_title);
            errorModel.userMessage = context.getString(R.string.error_message_sqlite) +
                    exception.getMessage();
            errorModel.systemMessage = exception.getMessage();
        }
    }
    protected DatabaseException(Context context,String exception){
        this.exception = new SQLException(exception);
        errorModel.userTitle = context.getString(R.string.error_dialog_title);
        errorModel.userMessage = context.getString(R.string.error_message_sqlite)+exception;
        errorModel.systemMessage = exception;

    }
}
