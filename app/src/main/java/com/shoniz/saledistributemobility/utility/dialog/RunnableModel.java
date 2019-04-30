package com.shoniz.saledistributemobility.utility.dialog;

import android.content.Context;

import com.shoniz.saledistributemobility.framework.exception.HandleException;

/**
 * Created by ferdos.s on 6/8/2017.
 */

public class RunnableModel<T> {
    public T Model;
    public boolean HasError=false;
    public HandleException exception;
    public Context context;
}
