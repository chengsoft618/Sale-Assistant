package com.shoniz.saledistributemobility.data.model.update.api;

import com.shoniz.saledistributemobility.base.FileContentModel;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

public interface IDatabaseUpdateApi {
    FileContentModel getSaleDb() throws BaseException;
}
