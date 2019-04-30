package com.shoniz.saledistributemobility.view.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.DialogFragment;

public class BaseDialog extends DialogFragment {
    Activity baseActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity)
            baseActivity = (BaseActivity) context;
    }

    @Override
    public void onDetach() {
        baseActivity = null;
        super.onDetach();
    }
}
