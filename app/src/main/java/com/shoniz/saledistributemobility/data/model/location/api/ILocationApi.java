package com.shoniz.saledistributemobility.data.model.location.api;

import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

/**
 * Created by 921235 on 5/12/2018.
 */

public interface ILocationApi {
    String startExactTracking(int userId) throws BaseException;
    String stopExactTracking(int userId) throws BaseException;
}
