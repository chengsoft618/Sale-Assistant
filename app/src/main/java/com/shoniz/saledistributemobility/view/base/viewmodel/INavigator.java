package com.shoniz.saledistributemobility.view.base.viewmodel;

import com.shoniz.saledistributemobility.framework.IProgress;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.List;

public interface INavigator extends IProgress {
    void handleError(BaseException exceptions);
    void showSnackBar(String title);
    void showSnackBar(String title, String btnString, Runnable runnable);
}
