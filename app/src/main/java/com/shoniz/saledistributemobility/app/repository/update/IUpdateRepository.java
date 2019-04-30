package com.shoniz.saledistributemobility.app.repository.update;

import com.shoniz.saledistributemobility.data.model.update.IAppUpdateListener;

public interface IUpdateRepository {

    void setAppUpdateListener(IAppUpdateListener appUpdateListener);
}
