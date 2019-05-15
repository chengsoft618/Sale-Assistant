package com.shoniz.saledistributemobility.framework.repository.update;

import com.shoniz.saledistributemobility.catalog.ProductImageModel;
import com.shoniz.saledistributemobility.catalog.ResourceModel;
import com.shoniz.saledistributemobility.data.api.ApiNetworkException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.List;

public interface ICustomerUpdateRepository extends IUpdateRepository {
    void updateWholeInfoOfPath(int pathId) throws BaseException;
    void updateWholeInfoOfPerson(int personId) throws BaseException;
}
