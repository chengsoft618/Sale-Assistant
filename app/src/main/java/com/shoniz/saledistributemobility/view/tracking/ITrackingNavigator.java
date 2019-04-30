package com.shoniz.saledistributemobility.view.tracking;

import com.shoniz.saledistributemobility.view.base.viewmodel.INavigator;

public interface ITrackingNavigator extends INavigator {

    void onStartExactTracking();
    void onStopExactTracking();
}
