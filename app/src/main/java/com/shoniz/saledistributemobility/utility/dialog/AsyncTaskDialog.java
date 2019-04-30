package com.shoniz.saledistributemobility.utility.dialog;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.AsyncTask;


public class AsyncTaskDialog extends AsyncTask<Object, Object, Object> {


    private Activity context;
    private ProgressDialog progressDialog;
    private CharSequence title;
    private CharSequence message;
    private RunnableMethod runDo;
    private RunnableMethod runPre;
    private RunnableMethod runPost;
    private boolean hasError;

    public AsyncTaskDialog(Activity context, CharSequence title,
                           CharSequence message, RunnableMethod runPre,
                           RunnableMethod runDo, RunnableMethod runPost) {
        this.context = context;
        this.title = title;
        this.message = message;
        this.runDo =runDo;
        this.runPost =runPost;
        this.runPre =runPre;
        this.hasError=false;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            (  context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else
            (  context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        progressDialog.show();

        if(runPre!=null)
        {
            runPre.run(null,null);
        }

    }

    @Override
    protected Object doInBackground(Object... obj) {
        Object objReturn = new Object();
        if(runDo !=null)
        {
            OnProgressUpdate onProgressUpdate=new OnProgressUpdate() {

                @Override
                public void onProgressUpdate(Object... values) {
                    publishProgress(values);
                }
            };
            objReturn= runDo.run(null,onProgressUpdate);
        }
        return objReturn;
    }

    @Override
    protected void onPostExecute(Object obj) {
        super.onPostExecute(obj);
        progressDialog.dismiss();
        if(runPost!=null)
        {

            runPost.run(obj,null);
        }
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        super.onProgressUpdate(values);
        if(values.length==1)
        {
            progressDialog.setMessage(values[0].toString());
        }else {
            progressDialog.setTitle(values[0].toString());
            progressDialog.setMessage(values[1].toString());
        }

    }


    public void run(){
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
