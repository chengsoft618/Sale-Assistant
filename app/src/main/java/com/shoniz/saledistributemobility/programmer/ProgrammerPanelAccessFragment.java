package com.shoniz.saledistributemobility.programmer;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.shoniz.saledistributemobility.R;

public class ProgrammerPanelAccessFragment extends DialogFragment {

    public ProgrammerPanelAccessFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_programmer_password_panel, null);
        final TextView txtPassword = view.findViewById(R.id.txt_password);
        builder.setView(view)

                .setPositiveButton("ورود", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if(txtPassword.getText().toString().equals("AhmadSiamak")){
                            Intent intent = new Intent(getContext(), ProgrammerActivity.class);
                            startActivity(intent);
                        }
                    }
                })
        .setNegativeButton("انصراف", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        return builder.create();
    }

}
