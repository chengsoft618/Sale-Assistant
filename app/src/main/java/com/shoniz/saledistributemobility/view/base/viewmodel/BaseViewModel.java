package com.shoniz.saledistributemobility.view.base.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;

public abstract class BaseViewModel<N extends INavigator> extends ViewModel {
    private boolean isActive;
    private N mNavigator;
    public boolean hasActiveActionMode=false;
    //protected boolean isVisibleActionModel = true;

    public N getNavigator() {
        return mNavigator;
    }

    public void setNavigator(N navigator) {
        this.mNavigator = navigator;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setHasActiveActionMode(boolean hasActiveActionMode) {
        this.hasActiveActionMode = hasActiveActionMode;
    }

    //public boolean isVisibleActionModel() {
//        return isVisibleActionModel;
//    }
//
//    public void setVisibleActionModel(boolean visibleActionModel) {
//        isVisibleActionModel = visibleActionModel;
//    }

    protected void asyncCall(CommonAsyncTask.RunnableDo runnableDo, CommonAsyncTask.RunnablePost post, int progressSize) {

        Runnable runnablePre = () -> {
            getNavigator().onBeginProgress();
            getNavigator().setProgressSize(progressSize);

        };

        CommonAsyncTask.RunnablePost post1 = new CommonAsyncTask.RunnablePost() {
            @Override
            public void run(Object param) {
                getNavigator().onEndProgress();
                if (post != null) post.run(param);
            }

        };

        CommonAsyncTask.OnProgress onProgress = new CommonAsyncTask.OnProgress() {

            @Override
            public void onUpdate(Object... objects) {
                handlePublisher(objects);
            }
        };

        CommonAsyncTask commonAsyncTask = new CommonAsyncTask(runnablePre, runnableDo, post1, onProgress);
        commonAsyncTask.run();
    }

    protected void asyncCall(CommonAsyncTask.RunnableDo runnableDo, CommonAsyncTask.RunnablePost post) {
        asyncCall(runnableDo, post, 1);
    }

    private void handlePublisher(Object... objects) {
        if (objects == null)
            return;
        Object o = objects[0];
        if (o instanceof String) {
            getNavigator().showInProgress(o.toString());
            return;
        }
        if (o instanceof Integer) {
            getNavigator().showInProgress((int) o);
            return;
        }
        if (o instanceof BaseException) {
            getNavigator().handleError((BaseException) o);
        }
    }
}
