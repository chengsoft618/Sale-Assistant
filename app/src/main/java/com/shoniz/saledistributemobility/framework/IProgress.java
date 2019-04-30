package com.shoniz.saledistributemobility.framework;

import android.support.annotation.StringRes;

import java.util.List;

public interface IProgress {
    void onBeginProgress();
    void setProgressSize(int progressSize);
    void showInProgress(String message);
    void showSimpleProgress(String message);
    void showInProgress(@StringRes int stringRes);
    void onEndProgress();


}
