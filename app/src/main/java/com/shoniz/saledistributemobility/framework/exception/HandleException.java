package com.shoniz.saledistributemobility.framework.exception;

import android.content.Context;
import android.database.SQLException;
import android.os.NetworkOnMainThreadException;
import android.support.v7.app.AppCompatActivity;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.utility.dialog.ErrorDialog.ErrorDialog;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;
public class HandleException extends OldBaseException {
    private List<Exception> list;
    private Context context;

    public static void ShowException(Context context, Exception exception) {
        HandleException systemException= new HandleException(context,exception) ;
        ErrorDialog.showDialog((AppCompatActivity) context,
                systemException.getUserTitle(), systemException.getUserMessage(),
                systemException.getSystemMessage());
    }

    public void Show() {
        ErrorDialog.showDialog((AppCompatActivity) context,
                getUserTitle(), getUserMessage());
    }
    public void ShowWithDetail() {
        ErrorDialog.showDialog((AppCompatActivity) context,
                getUserTitle(), getUserMessage(),
                getSystemMessage());
    }

    public void AddException(Exception exception) {
        this.list.add(exception);
        generateMessage(context, this.list.size()-1);
    }

    public HandleException(Context context,Exception exception) {
        super();
        // TODO: change this Class
        this.list = new LinkedList<>();
        this.list.add(exception);
        this.context=context;
        generateMessage(context, this.list.size()-1);
    }
    public HandleException(Context context) {
        super();
        // TODO: change this Class
        this.list = new LinkedList<>();
        this.context=context;
    }

    private  void generateMessage(Context context,int index){
        Exception exception=list.get(index);
        if(exception instanceof OldApiException){
            errorModel = ((OldApiException) exception).errorModel;
        }
        else if(exception instanceof ConnectionException){
            errorModel.userTitle += context.getString(R.string.error_dialog_title)+" \n";
            errorModel.userMessage += context.getString(R.string.error_message_connection)+" \n";
            errorModel.systemMessage += "ConnectException"+" \n";
        }
        else if(exception instanceof SocketTimeoutException){
            errorModel.userTitle += context.getString(R.string.error_dialog_title)+" \n";
            errorModel.userMessage += context.getString(R.string.error_message_timeout)+" \n";
            errorModel.systemMessage += "SocketTimeoutException"+" \n";
        }
        else if(exception instanceof MalformedURLException){
            errorModel.userTitle += context.getString(R.string.error_dialog_title)+" \n";
            errorModel.userMessage += context.getString(R.string.error_message_url)+" \n";
            errorModel.systemMessage += "MalformedURLException"+" \n";
        }
        else if(exception instanceof FileNotFoundException){
            errorModel.userTitle += context.getString(R.string.error_dialog_title)+" \n";
            errorModel.userMessage += context.getString(R.string.file_not_found_error)+" \n";
            errorModel.systemMessage += "FileNotFoundException"+" \n";
        }
        else if(exception instanceof IOException){
            errorModel.userTitle += context.getString(R.string.error_dialog_title)+" \n";
            errorModel.userMessage += context.getString(R.string.error_message_io)+" \n";
            errorModel.systemMessage += "IOException"+" \n";
        }
        else if(exception instanceof SQLException){
            DatabaseException ex = new DatabaseException(context,(SQLException) exception);
            errorModel.userTitle += ex.errorModel.userTitle+" \n";
            errorModel.userMessage += ex.errorModel.userMessage+" \n";
            errorModel.systemMessage += ex.errorModel.systemMessage+" \n";
        }
        else if(exception instanceof NetworkOnMainThreadException){
            errorModel.userTitle += context.getString(R.string.error_dialog_title)+" \n";
            errorModel.userMessage += context.getString(R.string.main_thread_error)+" \n";
            errorModel.systemMessage += "NetworkOnMainThreadException"+" \n";
        }
         else {
            errorModel.userTitle += context.getString(R.string.error_dialog_title)+" \n";
            errorModel.userMessage += exception.getMessage()+" \n";
            //errorModel.systemMessage += " \n";
        }
    }
}
