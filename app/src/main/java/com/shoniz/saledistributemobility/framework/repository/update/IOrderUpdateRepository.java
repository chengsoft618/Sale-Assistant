package com.shoniz.saledistributemobility.framework.repository.update;

import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

public interface IOrderUpdateRepository extends IUpdateRepository {
    void updateOrder() throws BaseException;
}
