package com.shoniz.saledistributemobility.view.dialog.unvisitedCustomerDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.view.entity.ReasonEntity;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class UnvisitingDialog {
    private RadioGroup radioGroup;
    Button dialogButton;
    Button cancelDialog;
    TextView txtDesc;
    IUnvisitListener unvisitListener;
    Dialog dialog;

    public void show(Activity context, List<ReasonEntity> reasonEntities, IUnvisitListener listener){
        unvisitListener = listener;
        dialog= new Dialog(context);
        dialog.setContentView(R.layout.dialog_unvisit_reason);
        dialog.setTitle("تعیین علت عدم ویزیت");

        // set the custom dialog components - text, image and button
        txtDesc = (TextView) dialog.findViewById(R.id.txtUnvisitDesc);

        radioGroup =  (RadioGroup)dialog.findViewById(R.id.rdoGrpReasons);
        int checkedRadioId = 0;
        HashMap<Integer, Integer> reasonMap = new HashMap<>();
        for(ReasonEntity reason : reasonEntities){
            int id= generateViewId();
            if(checkedRadioId == 0)
                checkedRadioId = id;
            reasonMap.put(id, reason.NotSallReasonID);
            RadioButton rdbtn = new RadioButton(context);
            rdbtn.setId(id);
            rdbtn.setText(reason.NotSallReasonText);
            radioGroup.addView(rdbtn);
        }
        radioGroup.check(checkedRadioId);

        Button dialogButton = (Button) dialog.findViewById(R.id.btnYes);
        cancelDialog = (Button) dialog.findViewById(R.id.btnNo);

        dialogButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int reasonId = radioGroup.getCheckedRadioButtonId();
                String desc =  txtDesc.getText().toString();
                unvisitListener.onClick(reasonId, desc);
            }
        });

        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void closeDialog(){
        dialog.dismiss();
    }

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    private static int generateViewId() {
        for (;;) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }



    public interface IUnvisitListener {
        void onClick(int reasonId, String desc);
    }
}
