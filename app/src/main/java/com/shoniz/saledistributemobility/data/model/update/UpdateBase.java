package com.shoniz.saledistributemobility.data.model.update;

public abstract class UpdateBase {

    protected IAppUpdateListener appUpdateListener;
    public void setAppUpdateListener(IAppUpdateListener appUpdateListener) {
        this.appUpdateListener = appUpdateListener;
    }

    protected void setUpdateMessage(String message){
        if (appUpdateListener != null) {
            appUpdateListener.onStageUpdate(message);
        }
    }
}
