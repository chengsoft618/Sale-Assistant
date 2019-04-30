package com.shoniz.saledistributemobility.utility;
import android.os.AsyncTask;
import android.os.Build;

import com.shoniz.saledistributemobility.utility.dialog.OnProgressUpdate;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;


public class SimpleAsyncTask extends AsyncTask<Object, Object, Object> {
    private RunnableMethod runDo;
    private RunnableMethod runPre;
    private RunnableMethod runPost;
    private RunnableMethod runUpdate;
    private boolean hasError;

    public SimpleAsyncTask(RunnableMethod runPre,
                           RunnableMethod runDo, RunnableMethod runPost,RunnableMethod runUpdate) {
        this.runPre =runPre;
        this.runDo =runDo;
        this.runPost =runPost;
        this.runUpdate =runUpdate;
        this.hasError=false;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

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
        if(runPost!=null)
        {
            runPost.run(obj,null);
        }
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        if(runUpdate!=null){
            runUpdate.run(values,null);
        }
        super.onProgressUpdate(values);
    }

    public void run(){
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void runSerial(){
        executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }


}
