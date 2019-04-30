package com.shoniz.saledistributemobility.view.entrance;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.StringHelper;
import com.shoniz.saledistributemobility.view.branch.BranchActivity;

import java.util.ArrayList;
import java.util.List;

public class EntranceActivity extends AppCompatActivity {

    public static final int MULTIPLE_PERMISSIONS = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
        checkDeniedPermissions();
    }

    private void checkDeniedPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : AppPermissions.GetPermissions()) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(
                    this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    MULTIPLE_PERMISSIONS);
        }else{
            BranchActivity.createInstance(this);
            finish();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0) {
                    String permissionsDenied = "";
                    for (String per : permissionsList) {
                        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                            permissionsDenied += "\n" + per;
                        }
                    }
                    if (!permissionsDenied.isEmpty()) {
                        Common.toast(this, StringHelper.GenerateMessage(getString(R.string.access_denied_permissions), permissionsDenied));
                        checkDeniedPermissions();
                        return;
                    }else{
                        BranchActivity.createInstance(this);
                        finish();
                    }

                }
            }
        }
    }

}
