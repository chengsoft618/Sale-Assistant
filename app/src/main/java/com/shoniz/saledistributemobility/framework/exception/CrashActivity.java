package com.shoniz.saledistributemobility.framework.exception;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.utility.Consts;
import com.shoniz.saledistributemobility.utility.dialog.ErrorDialog.ErrorDialog;

public class CrashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        Bundle bundle=getIntent().getExtras();
        String error=bundle.getString(Consts.ERROR_MESSAGE);

        HandleException systemException = new HandleException(this,new Exception(error));
        ErrorDialog.showDialog(this,
                systemException.getUserTitle(), systemException.getUserMessage(),
                systemException.getSystemMessage());
    }
}
