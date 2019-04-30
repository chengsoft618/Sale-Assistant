package com.shoniz.saledistributemobility.data.model.update.api;

import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

public interface IOrderUpdateApi {
    void orderSync() throws BaseException;
}
