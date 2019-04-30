package com.shoniz.saledistributemobility.utility.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shoniz.saledistributemobility.R;


@SuppressLint("ValidFragment")
public class SimpleDialog extends BaseDialog {

    private String title;
    private String yesButtonCaption;
    private String message;
    private Runnable yesRunnable;
    private static SimpleDialog currentDialog;
    AlertDialog.Builder builder;


    private SimpleDialog(AppCompatActivity dialogContext) {
        super(dialogContext);
        yesButtonCaption=dialogContext.getString(R.string.yes);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder= new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(yesButtonCaption, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(yesRunnable!=null)
                        {
                            yesRunnable.run();
                        }
                        dialog.dismiss();
                    }
                }).setTitle(this.title).setMessage(this.message).setIcon(R.drawable.ic_help_outline_black_24dp);
        return builder.create();

    }

    public static void showDialog(AppCompatActivity dialogContext, String title, String message,Runnable yesRunnable) {
        currentDialog = new SimpleDialog(dialogContext);
        currentDialog.message = message;
        currentDialog.title = title;
        currentDialog.yesRunnable=yesRunnable;
        currentDialog.show(dialogContext.getSupportFragmentManager(), "SimpleDialog");
    }

    @Override
    public void dismissDialog() {

    }

    public void setYesButtonCaption(String yesButtonCaption) {
        this.yesButtonCaption = yesButtonCaption;
    }


}
