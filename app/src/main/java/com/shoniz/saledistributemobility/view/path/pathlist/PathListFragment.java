package com.shoniz.saledistributemobility.view.path.pathlist;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.api.ApiNetworkException;
import com.shoniz.saledistributemobility.data.model.path.db.PathEntity;
import com.shoniz.saledistributemobility.databinding.FragmentPathListBinding;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.ExceptionHandler;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.framework.service.update.IAppUpdater;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerHelper;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerViewModel;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.SimpleAsyncTask;
import com.shoniz.saledistributemobility.utility.dialog.InputDialog;
import com.shoniz.saledistributemobility.utility.dialog.OnProgressUpdate;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;
import com.shoniz.saledistributemobility.utility.dialog.YesNoDialog;
import com.shoniz.saledistributemobility.view.base.BaseFragment;
import com.shoniz.saledistributemobility.view.customer.activity.CustomerActivity;
import com.shoniz.saledistributemobility.view.main.MainActivity;
import com.shoniz.saledistributemobility.view.path.PathModel__;
import com.shoniz.saledistributemobility.view.path.ToolbarPathActionModeCallback;
import com.shoniz.saledistributemobility.view.path.customerlist.CustomerListFragment;
import com.shoniz.saledistributemobility.view.path.outofpath.CustomerPreference;

import javax.inject.Inject;

public class PathListFragment extends BaseFragment<FragmentPathListBinding, PathListViewModel> implements IPathListNavigator {

    @Inject
    CommonPackage commonPackage;
    @Inject
    IAppUpdater appUpdater;

    public static String IS_ACTIVE = "ISACTIVE";
    public static String PERSIAN_DATE = "PERSIAN_DATE";


    @Inject
    ViewModelProvider.Factory factory;

    public static PathListFragment newInstance() {
        PathListFragment fragment = new PathListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mViewModel.init();
        refreshPathsRecycle();
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.path_list_update_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void refreshPathsRecycle() {
        RecyclerHelper.build(
                mViewModel,
                getBaseActivity(),
                this,
                mViewDataBinding.pathListRecycler,
                PathItemBindingBuilder.class)
                .setSelectingModel(
                        RecyclerViewModel.SelectingMode.None
                );
    }



    @Override
    public int getFragmentTitle() {
        return R.string.path_fragment_title;
    }

    @Override
    public PathListViewModel getViewModel() {
        PathListViewModel viewModel = ViewModelProviders.of(this, factory).get(PathListViewModel.class);
        viewModel.setNavigator(this);
        return viewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_path_list;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_update_all_paths_menu) {
            updatePaths();
            return true;
        } else if (id == R.id.action_out_of_path_menu) {
            InputDialog.InputDialogListener onYesClick = personIdText -> {
                int personId = Integer.parseInt(personIdText);
                if (personIdText.isEmpty()) {
                    return;
                }
                mViewModel.initOutOfPath(personId);
            };
            InputDialog.InputDialogListener onNoClick = new InputDialog.InputDialogListener() {
                @Override
                public void onClick(String input) {
                    InputDialog.closeDialog();
                }
            };
            InputDialog.showDialog((AppCompatActivity) getActivity(), getString(R.string.path_out_of_path_customer), getString(R.string.common_attention), 0, onYesClick, onNoClick, true);
        } else if (id == R.id.action_end_of_daily_visit) {
            InputDialog.InputDialogListener onYesClick = new InputDialog.InputDialogListener() {
                @Override
                public void onClick(String desc) {
                    mViewModel.sendEndOfDailyVisitReport(desc);
                }
            };
            InputDialog.showDialog((AppCompatActivity) getActivity(),
                    "",
                    getString(R.string.path_complete_path_visit_hint), mViewModel.getEndOfDailyVisitDesc(), 0,
                    onYesClick, null, InputType.TYPE_TEXT_FLAG_MULTI_LINE, 3);
        }
        return super.onOptionsItemSelected(item);
    }

    private void updatePaths() {
        YesNoDialog.showDialog(getBaseActivity(), getString(R.string.common_question), "توجه: در صورت بروزرسانی ، تمامی درخواست های ارسال نشده شما حذف خواهند شد. آیا بروز رسانی انجام شود؟",
                () -> {
                    Runnable runnablePre = () -> {
                        onBeginProgress();
                        setProgressSize(1);
                    };
                    CommonAsyncTask.RunnableDo<Object, AsyncResult> runnableDo = (param, onProgress) -> {
                        AsyncResult result = new AsyncResult();
                        try {
                            mViewModel.updateAllPaths(message -> onProgress.onUpdate(message));
                        } catch (BaseException ex) {
                            result.exception = ex;
                        }
                        return result;
                    };
                    CommonAsyncTask.RunnablePost<AsyncResult> postRunnable = param -> {
                        onEndProgress();
                        if (param.hasError()) {
                            handleError(param.exception);
                        } else {
                            MainActivity.createInstance(getBaseActivity());
                            System.exit(10);
                        }
                        //pathAdapter.notifyDataSetChanged();
                        //getBaseActivity().finish();

                    };

                    CommonAsyncTask.OnProgress onProgress = new CommonAsyncTask.OnProgress() {
                        @Override
                        public void onUpdate(Object... objects) {
                            showSimpleProgress(objects[0].toString());
                        }
                    };
                    CommonAsyncTask commonAsyncTask = new CommonAsyncTask(runnablePre, runnableDo, postRunnable, onProgress);
                    commonAsyncTask.run();
                }, null);
    }

    @Override
    public void goToCustomerList(PathEntity pathEntity) {
        CustomerListFragment newFragment = CustomerListFragment.newInstance(pathEntity);
        Common.addFragment(getActivity(), R.id.fragment_container, newFragment);
    }

    @Override
    public void goToCustomerPage(int personId, boolean isKnownCustomer) {
        try {
            Intent intent = new Intent(getActivity(), CustomerActivity.class);
            intent.putExtra("PersonId", personId);
            intent.putExtra(CustomerPreference.IsCardindexForUnknownCustomer, !isKnownCustomer);
            getActivity().startActivity(intent);
        } catch (Exception e) {
            HandleException.ShowException(getActivity(), e);
        }
    }

//    private void updatePath(int pathId, Runnable callback) {
//        mViewModel.updatePath(pathId);
//        Runnable runnablePre = () -> {
//            onBeginProgress();
//            setProgressSize(1);
//        };
//
//        CommonAsyncTask.RunnableDo<Object, AsyncResult> runnableDo = (param, onProgress) -> {
//            AsyncResult result = new AsyncResult();
//            try {
//                mViewModel.updatePath(pathId);
//            } catch (BaseException ex) {
//                result.exception = ex;
//            } catch (Exception e) {
//                result.exception = new UncaughtException(commonPackage, e);
//            }
//            return result;
//
//
//        };
//        CommonAsyncTask.RunnablePost<AsyncResult> postRunnable = param -> {
//            onEndProgress();
//            if (param.hasError()) {
//                handleError(param.exception);
//                return;
//            }
//            if (callback != null)
//                callback.run();
//           // pathAdapter.notifyDataSetChanged();
//
//        };
//
//        CommonAsyncTask.OnProgress onProgress = new CommonAsyncTask.OnProgress() {
//            @Override
//            public void onUpdate(Object... objects) {
//                showSimpleProgress(objects[0].toString());
//            }
//        };
//        CommonAsyncTask commonAsyncTask = new CommonAsyncTask(runnablePre, runnableDo, postRunnable, onProgress);
//        commonAsyncTask.run();
//    }

//    private void updatePath(PathModel__ pathModel) {
//        SimpleAsyncTask asyncTask = new SimpleAsyncTask(null, new RunnableMethod() {
//            @Override
//            public Object run(Object param, OnProgressUpdate onProgressUpdate) {
//                updatePath(pathModel.PathCode, null);
//                return null;
//            }
//        }, new RunnableMethod() {
//            @Override
//            public Object run(Object param, OnProgressUpdate onProgressUpdate) {
//                if (param != null) {
//                    ExceptionHandler.handle((BaseException) param, getActivity());
//                }
//                return null;
//            }
//        }, null);
//        asyncTask.run();
//    }

//    @Override
//    public void syncCustomerInfoById(int personId) {
//        Runnable runnablePre = () -> {
//            onBeginProgress();
//            setProgressSize(1);
//        };
//
//        CommonAsyncTask.RunnableDo<Object, AsyncResult> runnableDo = (param, onProgress) -> {
//            AsyncResult result = new AsyncResult();
//            try {
//                mViewModel.updateOutOfPathCustomerInfo(personId);
//            } catch (ApiNetworkException ex) {
//                result.exception = ex;
//            } catch (Exception e) {
//                result.exception = new UncaughtException(commonPackage, e);
//            }
//            return result;
//        };
//
//        CommonAsyncTask.RunnablePost<AsyncResult> postRunnable = param -> {
//            onEndProgress();
//            if (!param.hasError()) {
//                goToCustomerPage(personId);
//            } else {
//                ExceptionHandler.handle(param.exception, getActivity());
//            }
//        };
//
//        CommonAsyncTask.OnProgress onProgress = new CommonAsyncTask.OnProgress() {
//            @Override
//            public void onUpdate(Object... objects) {
//                showSimpleProgress(objects[0].toString());
//            }
//        };
//
//        CommonAsyncTask commonAsyncTask = new CommonAsyncTask(runnablePre, runnableDo, postRunnable, onProgress);
//        commonAsyncTask.run();
//    }





}
