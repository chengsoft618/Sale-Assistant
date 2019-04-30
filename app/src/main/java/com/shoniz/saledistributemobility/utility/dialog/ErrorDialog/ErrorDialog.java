package com.shoniz.saledistributemobility.utility.dialog.ErrorDialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.utility.dialog.BaseDialog;

@SuppressLint("ValidFragment")
public class ErrorDialog extends BaseDialog {

    public ErrorDialog() {
        super();
        // empty to satisfy the compiler.
    }

    private String title;
    private boolean onlyUserMessage;
    private String message;
    private String systemMessage;
    private static ErrorDialog currentDialog;
    TextView txtMessage;
    ImageView btnShowDetails;


    private ErrorDialog(AppCompatActivity dialogContext) {
        super(dialogContext);
//        this.message=message;
//        this.title=title;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_error, null);

        builder.setView(dialogView)
                .setPositiveButton(R.string.button_caption_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        TextView txtHeader = dialogView.findViewById(R.id.txt_header);
        txtMessage = dialogView.findViewById(R.id.txt_message);
        TextView txtUserMessage = dialogView.findViewById(R.id.txt_user_message);
        ImageView imgHeaderIcon = dialogView.findViewById(R.id.img_header_icon);
        btnShowDetails = dialogView.findViewById(R.id.btn_show_details);


        txtHeader.setText(getString(R.string.error_dialog_title));
        txtMessage.setText(this.systemMessage);
        txtUserMessage.setText(this.message);
        imgHeaderIcon.setImageResource(R.drawable.ic_error_outline_black_24dp);
        btnShowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtMessage.getVisibility() == View.VISIBLE) {
                    txtMessage.setVisibility(View.GONE);
                    btnShowDetails.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
                } else {
                    btnShowDetails.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
                    txtMessage.setVisibility(View.VISIBLE);
                }

            }
        });
        if (onlyUserMessage) {
            txtMessage.setVisibility(View.GONE);
            btnShowDetails.setVisibility(View.GONE);
        }

        return builder.create();
    }

    public static void showDialog(AppCompatActivity dialogContext, String title, String message) {
        try {
            currentDialog = new ErrorDialog(dialogContext);
            currentDialog.message = message;
            currentDialog.title = title;
            currentDialog.onlyUserMessage = true;
            currentDialog.show(dialogContext.getSupportFragmentManager(), "ErrorDialog");
        } catch (Exception e) {
        }
    }

    public static void showDialog(AppCompatActivity dialogContext, String title, String message,
                                  String systemMessage) {
        try {
            currentDialog = new ErrorDialog(dialogContext);
            currentDialog.message = message;
            currentDialog.title = title;
            currentDialog.systemMessage = systemMessage;
            currentDialog.show(dialogContext.getSupportFragmentManager(), "ErrorDialog");
        } catch (Exception e) {
        }
    }

    @Override
    public void dismissDialog() {

    }


}
