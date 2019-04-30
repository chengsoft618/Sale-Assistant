package com.shoniz.saledistributemobility.utility.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoniz.saledistributemobility.R;


/**
 * Created by ferdos.s on 5/27/2017.
 */

@SuppressLint("ValidFragment")
public class InputDialog extends BaseDialog {

    private static InputDialog currentDialog;
    AlertDialog.Builder builder;
    private String yesButtonCaption;
    private String noButtonCaption;
    private String hint;
    private String title;
    private String text;
    private int icon;
    private InputDialogListener yesListener;
    private InputDialogListener noListener;
    private EditText txtInput;
    private boolean PREVENT_DISMISS_DIALOG = true;
    private int inputTypeId=-1;
    private int minLine=-1;


    @SuppressLint("ValidFragment")
    private InputDialog(AppCompatActivity dialogContext) {
        super(dialogContext);
        yesButtonCaption = dialogContext.getString(R.string.yes);
        noButtonCaption = dialogContext.getString(R.string.no);
    }

    public static void showDialog(AppCompatActivity dialogContext,
                                         String hint, String title, int icon,
                                         InputDialogListener yesListener, InputDialogListener noListener) {
        currentDialog = new InputDialog(dialogContext);
        currentDialog.hint = hint;
        currentDialog.title = title;
        currentDialog.icon = icon;
        currentDialog.yesListener = yesListener;
        currentDialog.noListener = noListener;
        currentDialog.show(dialogContext.getSupportFragmentManager(), "InputDialog");
    }
    public static void showDialog(AppCompatActivity dialogContext,
                                  String hint, String title,String text, int icon,
                                  InputDialogListener yesListener, InputDialogListener noListener,
                                  int inputTypeId,int minLine) {
        currentDialog = new InputDialog(dialogContext);
        currentDialog.hint = hint;
        currentDialog.text = text;
        currentDialog.title = title;
        currentDialog.icon = icon;
        currentDialog.yesListener = yesListener;
        currentDialog.noListener = noListener;
        currentDialog.show(dialogContext.getSupportFragmentManager(), "InputDialog");
        currentDialog.inputTypeId=inputTypeId;
        currentDialog.minLine=minLine;
    }


    public static void showDialog(AppCompatActivity dialogContext,
                                         String hint, String title, int icon,
                                         InputDialogListener yesListener, InputDialogListener noListener,
                                         boolean preventCloseDialog) {
        showDialog(dialogContext,
                hint, title, icon, yesListener, noListener);
        currentDialog.PREVENT_DISMISS_DIALOG = preventCloseDialog;
    }

    public static void closeDialog() {
        currentDialog.PREVENT_DISMISS_DIALOG = false;
        currentDialog.dismiss();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_input, null);


        TextView txtHeader = dialogView.findViewById(R.id.txt_header);
        txtInput = dialogView.findViewById(R.id.txt_input);
        ImageView imgHeaderIcon = dialogView.findViewById(R.id.img_header_icon);
        txtHeader.setText(title);
        txtInput.setHint(hint);
        txtInput.setText(text);
        imgHeaderIcon.setImageResource(icon == 0 ? R.drawable.ic_send : icon);
        builder.setView(dialogView)
                .setPositiveButton(yesButtonCaption, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (yesListener != null) {
                            yesListener.onClick(txtInput.getText().toString());
                        }
                        if (!PREVENT_DISMISS_DIALOG)
                            dialog.dismiss();
                        PREVENT_DISMISS_DIALOG = false;
                    }
                }).setNegativeButton(noButtonCaption, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (noListener != null) {
                    noListener.onClick(txtInput.getText().toString());
                }
                if (!PREVENT_DISMISS_DIALOG)
                    dialog.dismiss();
                PREVENT_DISMISS_DIALOG = false;
            }
        });

        if(inputTypeId!=-1){
            txtInput.setInputType(inputTypeId);
        }
        if(minLine!=-1){
            txtInput.setMinLines(minLine);
        }


        return builder.create();

    }



    @Override
    public void dismissDialog() {

    }

    public void setYesButtonCaption(String yesButtonCaption) {
        this.yesButtonCaption = yesButtonCaption;
    }

    public void setNoButtonCaption(String noButtonCaption) {
        this.noButtonCaption = noButtonCaption;
    }

    public interface InputDialogListener {
        void onClick(String input);
    }
}
