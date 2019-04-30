package com.shoniz.saledistributemobility.app.service.update;

import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.data.model.update.IAppUpdateListener;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

public interface IAppUpdater {

    void updateWholeData() throws BaseException;

    void updatePrePath() throws BaseException;

    void updateCatalog() throws BaseException;

    void updateUser() throws BaseException;

    void updateOrder() throws BaseException;

    void setAppUpdateListener(IAppUpdateListener appUpdateListener);
}
