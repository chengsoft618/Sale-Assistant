package com.shoniz.saledistributemobility.app.repository.update;

import com.shoniz.saledistributemobility.base.FileContentModel;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

public interface IDatabaseUpdateRepository extends IUpdateRepository {

    void syncSaleDb() throws BaseException;

    FileContentModel getSaleDb() throws BaseException;
}
