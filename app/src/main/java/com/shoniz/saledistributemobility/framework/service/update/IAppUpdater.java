package com.shoniz.saledistributemobility.framework.service.update;

import com.shoniz.saledistributemobility.data.api.ApiNetworkException;
import com.shoniz.saledistributemobility.data.model.update.IAppUpdateListener;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

public interface IAppUpdater {

    void updateWholeData() throws BaseException;

    void updatePrePath() throws BaseException;

    void updateCatalog() throws BaseException;

    void updateUser() throws BaseException;

    void updateOrder() throws BaseException;

    void setAppUpdateListener(IAppUpdateListener appUpdateListener);

    void setAppInitializationStatus();

    void updateWholeInfoOfPerson(int personId) throws BaseException;

    void updateWholeInfoOfPath(int pathId) throws BaseException;


}
