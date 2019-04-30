package com.shoniz.saledistributemobility.app.repository.update;

import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

public interface IBasicUpdateRepository extends IUpdateRepository {
    void updateUsers() throws BaseException;

    void updateEmployee() throws BaseException;
}
