package com.shoniz.saledistributemobility.app.repository.update;

import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

public interface IOrderUpdateRepository extends IUpdateRepository {
    void updateOrder() throws BaseException;
}
