package com.shoniz.saledistributemobility.infrastructure;

import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

public class AsyncResult<T> {
    public BaseException exception = null;
    public T model;
    public boolean hasError(){
        return exception!=null;
    }
}
