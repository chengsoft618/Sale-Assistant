package com.shoniz.saledistributemobility.view.base.progress;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.view.base.BaseDialog;

public class ProgressDialog extends BaseDialog {

    TextView txtTitle;
    TextView txtMessage;
    ProgressBar progressBar;
    int currentStageIndex = 0;
    int progressSize = 1;
    View dialogView;
    boolean isProgressIndeterminate = false;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_progress, null);

        txtTitle = dialogView.findViewById(R.id.txt_title);
        txtMessage = dialogView.findViewById(R.id.txt_message);
        progressBar = dialogView.findViewById(R.id.progress_bar);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);

        progressBar.setIndeterminate(isProgressIndeterminate);
        if (!isProgressIndeterminate)
            progressBar.setMax(progressSize);
        txtTitle.setText(R.string.wait);

        return builder.create();
    }

    public static ProgressDialog newInstance() {
        ProgressDialog dialog = new ProgressDialog();
        return dialog;
    }

    public void setProgressSize(int progressSize) {
        this.progressSize = progressSize;
        isProgressIndeterminate = progressSize == 1;

    }

    public void showInProgress(String message) {
        if (progressSize < currentStageIndex)
            return;

        currentStageIndex++;
        progressBar.setProgress(currentStageIndex);
        txtMessage.setText(message);
    }

    public void showSimpleMassage(String message) {
        txtMessage.setText(message);
    }

    public void showInProgress(@StringRes int stringRes) {
        if (progressSize < currentStageIndex)
            return;

        currentStageIndex++;
        progressBar.setProgress(currentStageIndex);
        txtMessage.setText(stringRes);
    }
}
