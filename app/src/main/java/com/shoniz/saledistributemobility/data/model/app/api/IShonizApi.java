package com.shoniz.saledistributemobility.data.model.app.api;

import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.data.model.app.BranchEntity;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.List;

public interface IShonizApi {
    List<BranchEntity> getBranchEntities() throws BaseException, ApiException;
}
