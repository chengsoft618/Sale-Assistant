package com.shoniz.saledistributemobility.utility.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoniz.saledistributemobility.R;


/**
 * Created by ferdos.s on 5/27/2017.
 */

@SuppressLint("ValidFragment")
public class YesNoDialog extends BaseDialog {

    private String title;
    private String yesButtonCaption;
    private String noButtonCaption;
    private String message;
    private String systemMessage;
    private Runnable yesRunnable;
    private Runnable noRunnable;
    private static YesNoDialog currentDialog;
    AlertDialog.Builder builder;


    @SuppressLint("ValidFragment")
    private YesNoDialog(AppCompatActivity dialogContext) {
        super(dialogContext);
        yesButtonCaption=dialogContext.getString(R.string.yes);
        noButtonCaption=dialogContext.getString(R.string.no);
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_yes_no, null);

        builder.setView(dialogView)
                .setPositiveButton(yesButtonCaption, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(yesRunnable!=null)
                        {
                            yesRunnable.run();
                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton(noButtonCaption, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(noRunnable!=null)
                {
                    noRunnable.run();
                }
                dialog.dismiss();
            }
        });
        TextView txtHeader = dialogView.findViewById(R.id.txt_header);
        final TextView txtMessage = dialogView.findViewById(R.id.txt_message);
        TextView txtUserMessage = dialogView.findViewById(R.id.txt_user_message);
        ImageView imgHeaderIcon = dialogView.findViewById(R.id.img_header_icon);



        txtHeader.setText(getString(R.string.error_dialog_title));
        txtMessage.setText(this.systemMessage);
        txtUserMessage.setText(this.message);
        imgHeaderIcon.setImageResource(R.drawable.ic_help_outline_black_24dp);

        return builder.create();

    }

    public static void showDialog(AppCompatActivity dialogContext, String title, String message,Runnable yesRunnable,Runnable noRunnable) {
        currentDialog = new YesNoDialog(dialogContext);
        currentDialog.message = message;
        currentDialog.title = title;
        currentDialog.yesRunnable=yesRunnable;
        currentDialog.noRunnable=noRunnable;
        currentDialog.show(dialogContext.getSupportFragmentManager(), "YesNoDialog");
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
}
