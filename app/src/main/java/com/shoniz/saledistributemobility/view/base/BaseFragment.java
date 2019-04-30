package com.shoniz.saledistributemobility.view.base;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shoniz.saledistributemobility.data.model.log.ILogRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.ExceptionHandler;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerToolbar;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.view.base.progress.ProgressDialog;
import com.shoniz.saledistributemobility.view.base.viewmodel.BaseViewModel;
import com.shoniz.saledistributemobility.view.base.viewmodel.INavigator;

import javax.inject.Inject;

public abstract class BaseFragment<T extends ViewDataBinding, V extends BaseViewModel> extends dagger.android.support.DaggerFragment
        implements INavigator {

    private BaseActivity mActivity;

    protected T mViewDataBinding;
    protected V mViewModel;

    public boolean isActive(){
        return getViewModel().isActive();
    }
    public void setIsActive(boolean isActive){
        getViewModel().setIsActive(isActive);
    }

    private ProgressDialog progressDialog;

    String PROGRESS_DIALOG = "ProgressDialog";
    String ERROR_DIALOG = "ErrorDialog";

    public abstract V getViewModel();

    public abstract int getBindingVariable();

    public abstract void onChangeLocation(Location location);

    SharedLocationViewModel sharedLocationViewModel;

    @Inject
    ILogRepository logRepository;
    @Inject
    public CommonPackage commonPackage;

    @LayoutRes
    public abstract int getLayoutId();

    @Override
    public void onDestroy() {
        super.onDestroy();
        //bgc();
    }

    private void bgc() {
        mViewDataBinding = null;
        mViewModel = null;
        mActivity = null;
        progressDialog = null;
        Runtime.getRuntime().gc();
        System.gc();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = (BaseActivity) getActivity();
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.setLifecycleOwner(this);
        setLocationObserve();
        return mViewDataBinding.getRoot();
    }

    private void setLocationObserve() {
            sharedLocationViewModel = ViewModelProviders.of(getBaseActivity()).get(SharedLocationViewModel.class);
            sharedLocationViewModel.location.observe(this, new Observer<Location>() {
                @Override
                public void onChanged(@Nullable Location location) {
                    onChangeLocation(location);
                }
            });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            this.mActivity = (BaseActivity) context;
        }
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    @Override
    public void handleError(BaseException exceptions) {
        onEndProgress();
        ExceptionHandler.handle(exceptions, getBaseActivity());
    }

    @Override
    public void onBeginProgress() {
        progressDialog = ProgressDialog.newInstance();
        progressDialog.setCancelable(false);
        progressDialog.show(mActivity.getSupportFragmentManager(), PROGRESS_DIALOG);
    }

    @Override
    public void setProgressSize(int progressSize) {
        progressDialog.setProgressSize(progressSize);
    }

    @Override
    public void showInProgress(String message) {
        progressDialog.showInProgress(message);
    }

    @Override
    public void showSimpleProgress(String message) {
        progressDialog.showSimpleMassage(message);
    }

    @Override
    public void onEndProgress() {
        try {
            progressDialog.dismiss();
        }catch (Exception e){}

    }

    @Override
    public void showInProgress(int stringRes) {
        progressDialog.showInProgress(stringRes);
    }

    public abstract int getFragmentTitle();

    protected RecyclerToolbar toolbarListener;
    protected ActionMode mActionMode;




    public <M extends RecyclerToolbar> void setToolbarListener(M toolbarListener) {
        this.toolbarListener = toolbarListener;
    }

    public ActionMode getActionMode(){
        return mActionMode;
    }

    public void showActionMode(){
        if(mViewModel.hasActiveActionMode)// || mViewModel.isVisibleActionModel())
            mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(toolbarListener);
    }

    @Override
    public void showSnackBar(String title) {
        View parentLayout = getActivity().findViewById(android.R.id.content);
        Snackbar.make(parentLayout, title, Snackbar.LENGTH_LONG)
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
    }

    @Override
    public void showSnackBar(String title, String btnString, Runnable runnable) {
        View parentLayout = getActivity().findViewById(android.R.id.content);
        Snackbar.make(parentLayout, title, Snackbar.LENGTH_LONG)
                .setAction(btnString, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        runnable.run();
                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
    }
}
