package com.shoniz.saledistributemobility.data.model.app;

import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.List;

public interface IShonizRepository {
    List<BranchEntity> getShonizBranchEntities() throws BaseException, ApiException;
    List<BranchData> getShonizBranchData() throws BaseException, ApiException;
}
