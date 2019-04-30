package com.shoniz.saledistributemobility.utility.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseDialog extends DialogFragment {

    protected BaseDialog(AppCompatActivity dialogContext){
        DialogContext = dialogContext;

    }
    public BaseDialog(){

    }
    public String Title;
    public String Message;


    public AppCompatActivity DialogContext;


    //public abstract void showDialog(AppCompatActivity dialogContext, String title, String message);
    //public abstract void showDialog(AppCompatActivity dialogContext, String title, String message, String systemMessage);
    public abstract void dismissDialog();
}
