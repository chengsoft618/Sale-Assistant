package com.shoniz.saledistributemobility.infrastructure;

import android.os.AsyncTask;
import android.support.annotation.StringRes;

import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.utility.dialog.OnProgressUpdate;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;


public class CommonAsyncTask extends AsyncTask<Object, Object, Object> {
    private Runnable runPre;
    private RunnableDo runDo;
    private RunnablePost runPost;
    private OnProgress onProgress;

    public CommonAsyncTask(Runnable runPre,
                           RunnableDo runDo, RunnablePost runPost, OnProgress onProgress) {
        this.runPre = runPre;
        this.runDo = runDo;
        this.runPost = runPost;
        this.onProgress = onProgress;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (runPre != null) {
            runPre.run();
        }
    }

    @Override
    protected Object doInBackground(Object... obj) {
        Object objReturn = new Object();
        if (runDo != null) {
            OnProgress onProgressUpdate = new OnProgress() {
                @Override
                public void onUpdate(Object... objects) {
                    publishProgress(objects);
                }

            };
            objReturn = runDo.run(null, onProgressUpdate);
        }
        return objReturn;
    }

    @Override
    protected void onPostExecute(Object obj) {
        super.onPostExecute(obj);
        if (runPost != null) {
            runPost.run(obj);
        }
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        if (onProgress != null) {
            onProgress.onUpdate(values);
        }
    }
    public void run(){
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    public interface OnProgress {
        void onUpdate(Object... objects);
    }

    public interface RunnableDo<TIN, TOUT extends AsyncResult> {
        TOUT run(TIN param, OnProgress onProgress);
    }

    public interface RunnablePost<TIN> {
        void run(TIN param);
    }


}
