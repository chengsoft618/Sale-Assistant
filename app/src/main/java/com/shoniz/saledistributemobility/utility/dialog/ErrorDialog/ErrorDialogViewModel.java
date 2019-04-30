package com.shoniz.saledistributemobility.utility.dialog.ErrorDialog;

import android.arch.lifecycle.ViewModel;

public class ErrorDialogViewModel extends ViewModel {

    private String title;
    private String message;


    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }


    public ErrorDialogViewModel(String title, String message) {
        this.title=title;
        this.message=message;
    }


}
