package com.shoniz.saledistributemobility.framework.exception;

import android.content.Context;

import com.shoniz.saledistributemobility.R;

/**
 * Created by ferdos.s on 5/31/2017.
 */

public class ConnectionException extends OldBaseException {
    public ConnectionException(Context context,Exception exception) {
        super();
        errorModel.systemMessage = exception.getMessage();
        generateMessage(context);
    }
    private void generateMessage(Context context) {
        errorModel.userMessage = context.getString(R.string.error_message_connection);
        errorModel.userTitle = context.getString(R.string.error_dialog_title);
    }
}
