package com.shoniz.saledistributemobility.view.path.pathlist;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.app.service.update.IAppUpdater;
import com.shoniz.saledistributemobility.data.api.ApiNetworkException;
import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.data.model.customer.ICustomerRepository;
import com.shoniz.saledistributemobility.data.model.message.IMessageRepository;
import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.model.update.AppDataUpdate;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.databinding.FragmentPathListBinding;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.ConnectionException;
import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.ExceptionHandler;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.framework.service.update.AppUpdater;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.SimpleAsyncTask;
import com.shoniz.saledistributemobility.utility.dialog.ErrorDialog.ErrorDialog;
import com.shoniz.saledistributemobility.utility.dialog.InputDialog;
import com.shoniz.saledistributemobility.utility.dialog.OnProgressUpdate;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;
import com.shoniz.saledistributemobility.utility.dialog.YesNoDialog;
import com.shoniz.saledistributemobility.view.base.BaseFragment;
import com.shoniz.saledistributemobility.view.customer.activity.CustomerActivity;
import com.shoniz.saledistributemobility.view.main.MainActivity;
import com.shoniz.saledistributemobility.view.path.PathAdapter;
import com.shoniz.saledistributemobility.view.path.PathData;
import com.shoniz.saledistributemobility.view.path.PathModel;
import com.shoniz.saledistributemobility.view.path.ToolbarPathActionModeCallback;
import com.shoniz.saledistributemobility.view.path.customerlist.CustomerListFragment;
import com.shoniz.saledistributemobility.view.path.outofpath.CustomerPreference;

import java.util.List;

import javax.inject.Inject;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link PathAdapter.OnPathListener}
 * interface.
 */
public class PathListFragment extends BaseFragment<FragmentPathListBinding, PathListViewModel> implements IPathListNavigator {

    ToolbarPathActionModeCallback toolbarPathListActionModeCallback;
    RecyclerView recyclerView;
    AppCompatActivity activity;
    List<PathModel> pathModels;
    PathAdapter pathAdapter;
    @Inject
    ISettingRepository settingRepository;
//    @Inject
//    AppDataUpdate appDataUpdate;
    @Inject
    IOrderRepository orderRepository;
    @Inject
    IMessageRepository messageRepository;
    @Inject
    CommonPackage commonPackage;
    @Inject
    IAppUpdater appUpdater;

    public static String IS_ACTIVE = "ISACTIVE";
    public static String PERSIAN_DATE = "PERSIAN_DATE";

    View imgDownload;


    @Inject
    ViewModelProvider.Factory factory;
    @Inject
    ICustomerRepository customerRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        activity = (AppCompatActivity) getActivity();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.path_list_update_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);


    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
//        View view = inflater.inflate(R.layout.fragment_path_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;

            try {
                Common.setRecycleViewLayoutManager(context, recyclerView);
                pathModels = PathData.getAllPaths(getBaseActivity());
                pathAdapter = new PathAdapter(pathModels, onRecyclerListener);
                recyclerView.setAdapter(pathAdapter);
            } catch (Exception e) {
                HandleException systemException = new HandleException(getBaseActivity(), e);
                ErrorDialog.showDialog((AppCompatActivity) getActivity(),
                        systemException.getUserTitle(), systemException.getUserMessage(),
                        systemException.getSystemMessage());
            }

        }


        return view;
    }


    private PathAdapter.OnPathListener onRecyclerListener = new PathAdapter.OnPathListener() {
        @Override
        public void onClick(PathModel pathModel) {
            try {
                Runnable callFragment = new Runnable() {
                    @Override
                    public void run() {
                        CustomerListFragment newFragment = CustomerListFragment.newInstance(pathModel);

                        Common.addFragment(activity, R.id.fragment_container, newFragment);
                    }
                };
                if (!customerRepository.isPathSync(pathModel.PathCode)) {
                    updatePath(pathModel.PathCode, callFragment);
                    return;
                }
                callFragment.run();
            } catch (Exception e) {
                HandleException systemException = new HandleException(getContext(), e);
                ErrorDialog.showDialog((AppCompatActivity) getActivity(),
                        systemException.getUserTitle(), systemException.getUserMessage(),
                        systemException.getSystemMessage());
            }

        }

        @Override
        public void onUpdateClick(List<Integer> selectedIds) {
            updatePaths();
        }

        @Override
        public void onItemCheck(PathModel pathModel) {
            if (pathAdapter.getSelectedList().size() == pathAdapter.getList().size()) {
                toolbarPathListActionModeCallback.setCheckAll(true);
            } else {
                toolbarPathListActionModeCallback.setCheckAll(false);
            }

        }


        @Override
        public void onLongClick(PathModel pathModel) {
//            toolbarPathListActionModeCallback = new ToolbarPathActionModeCallback(pathAdapter);
//            ActionMode mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(toolbarPathListActionModeCallback);
            YesNoDialog.showDialog(getBaseActivity(), getString(R.string.common_question), "آیا مسیر انتخاب شده بروز رسانی شود؟",
                    new Runnable() {
                        @Override
                        public void run() {
                            updatePath(pathModel);
                        }
                    }, null);
        }

        @Override
        public void onUpdateClick(PathModel pathModel) {
            updatePath(pathModel);
        }
    };

    private void updatePath(PathModel pathModel) {
        SimpleAsyncTask asyncTask = new SimpleAsyncTask(null, new RunnableMethod() {
            @Override
            public Object run(Object param, OnProgressUpdate onProgressUpdate) {
                updatePath(pathModel.PathCode, null);
                return null;
            }
        }, new RunnableMethod() {
            @Override
            public Object run(Object param, OnProgressUpdate onProgressUpdate) {
                if (param != null) {
                    ExceptionHandler.handle((BaseException) param, getActivity());
                }
                return null;
            }
        }, null);
        asyncTask.run();
    }


    public static PathListFragment newInstance() {
        PathListFragment fragment = new PathListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getFragmentTitle() {
        return R.string.path_fragment_title;
    }

    @Override
    public PathListViewModel getViewModel() {
        PathListViewModel model = ViewModelProviders.of(this, factory).get(PathListViewModel.class);
        model.setNavigator(this);
        return model;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void onChangeLocation(Location location) {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_path_list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PathAdapter.OnPathListener) {
            onRecyclerListener = (PathAdapter.OnPathListener) context;
        } else {
            //Common.toast("must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onRecyclerListener = null;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_update_path_menu) {
            updatePaths();
            return true;
        } else if (id == R.id.action_reg_out_of_path) {
            InputDialog.InputDialogListener onYesClick = new InputDialog.InputDialogListener() {

                @Override
                public void onClick(String personIdText) {
                    try {
                        if (personIdText.isEmpty()) {
                            return;
                        }
                        int branchcode = settingRepository.getBranchEntity().BranchCode;
                        int branchCapacity = 1000000;

                        int personId = Integer.parseInt(personIdText);
                        if (personId < branchCapacity)
                            personId = branchcode * branchCapacity + personId;
                        else if (personId < branchcode * branchCapacity || personId > ((branchcode + 1) * branchCapacity - 1)) {
                            commonPackage.getUtility().toast("کد مشتری شما در محدوده مشتریان دفتر نمی باشد");
                            return;
                        }

                        //RegOutOfPath regOutOfPath = new RegOutOfPath(getActivity(), personId);

                        mViewModel.manageOutOfPath(personId); //manageOutOfPath(personId);

                    } catch (Exception e) {
                        HandleException.ShowException(getContext(), e);
                    }
                    //   InputDialog.closeDialog();
                }
            };

            InputDialog.InputDialogListener onNoClick = new InputDialog.InputDialogListener() {

                @Override
                public void onClick(String input) {
                    InputDialog.closeDialog();
                }
            };

            InputDialog.showDialog((AppCompatActivity) getActivity(), getString(R.string.path_out_of_path_customer), getString(R.string.common_attention), 0, onYesClick, onNoClick, true);
        } else if (id == R.id.action_path_complete_path_visit) {

            InputDialog.InputDialogListener onYesClick = new InputDialog.InputDialogListener() {
                @Override
                public void onClick(String desc) {
                    mViewModel.sendCompletePathVisit(desc);

                }
            };
            String desc = settingRepository.getLastCompletePathVisitDesc();
            InputDialog.showDialog((AppCompatActivity) getActivity(),
                    "",
                    getString(R.string.path_complete_path_visit_hint), desc, 0,
                    onYesClick, null, InputType.TYPE_TEXT_FLAG_MULTI_LINE, 3);

        }

        return super.onOptionsItemSelected(item);
    }

    private void updatePaths() {

        YesNoDialog.showDialog(getBaseActivity(), getString(R.string.common_question), "توجه: در صورت بروزرسانی ، تمامی درخواست های ارسال نشده شما حذف خواهند شد. آیا بروز رسانی انجام شود؟",
                new Runnable() {
                    @Override
                    public void run() {
                        Runnable runnablePre = () -> {
                            onBeginProgress();
                            setProgressSize(1);
                        };

                        CommonAsyncTask.RunnableDo<Object, AsyncResult> runnableDo = (param, onProgress) -> {
                            AsyncResult result = new AsyncResult();
                            try {

                                appUpdater.setAppUpdateListener(message -> onProgress.onUpdate(message));
                                appUpdater.updateWholeData();
                                settingRepository.setUnchangedOrdersNoInCardindeForEdit(0L);
                                // pathModels = PathData.getAllPaths(getContext());
                                // pathAdapter = new PathAdapter(pathModels, onRecyclerListener);

                            } catch (BaseException ex) {
                                result.exception = ex;
                            } catch (Exception e) {
                                result.exception = new UncaughtException(commonPackage, e);
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
                    }
                }, null);
    }

    private void updatePath(int pathId, Runnable callback) {
        Runnable runnablePre = () -> {
            onBeginProgress();
            setProgressSize(1);
        };

        CommonAsyncTask.RunnableDo<Object, AsyncResult> runnableDo = (param, onProgress) -> {
            AsyncResult result = new AsyncResult();
            try {
                customerRepository.syncEmployeeWholeInfoByPath(pathId);
            } catch (BaseException ex) {
                result.exception = ex;
            } catch (Exception e) {
                result.exception = new UncaughtException(commonPackage, e);
            }
            return result;


        };
        CommonAsyncTask.RunnablePost<AsyncResult> postRunnable = param -> {
            onEndProgress();
            if (param.hasError()) {
                handleError(param.exception);
                return;
            }
            if (callback != null)
                callback.run();
            pathAdapter.notifyDataSetChanged();

        };

        CommonAsyncTask.OnProgress onProgress = new CommonAsyncTask.OnProgress() {
            @Override
            public void onUpdate(Object... objects) {
                showSimpleProgress(objects[0].toString());
            }
        };
        CommonAsyncTask commonAsyncTask = new CommonAsyncTask(runnablePre, runnableDo, postRunnable, onProgress);
        commonAsyncTask.run();
    }

    @Override
    public void syncCustomerInfoById(int personId) {
        Runnable runnablePre = () -> {
            onBeginProgress();
            setProgressSize(1);
        };

        CommonAsyncTask.RunnableDo<Object, AsyncResult> runnableDo = (param, onProgress) -> {
            AsyncResult result = new AsyncResult();
            try {
                mViewModel.syncCustomerWholeInfoById(personId);
            } catch (ApiNetworkException ex) {
                result.exception = ex;
            } catch (Exception e) {
                result.exception = new UncaughtException(commonPackage, e);
            }
            return result;
        };

        CommonAsyncTask.RunnablePost<AsyncResult> postRunnable = param -> {
            onEndProgress();
            if (!param.hasError()) {
                goToCustomerPage(personId);
            } else {
                ExceptionHandler.handle(param.exception, getActivity());
            }
        };

        CommonAsyncTask.OnProgress onProgress = new CommonAsyncTask.OnProgress() {
            @Override
            public void onUpdate(Object... objects) {
                showSimpleProgress(objects[0].toString());
            }
        };

        CommonAsyncTask commonAsyncTask = new CommonAsyncTask(runnablePre, runnableDo, postRunnable, onProgress);
        commonAsyncTask.run();
    }

    public void goToCardIndexPage(int personId) {
        Intent intent = new Intent(getActivity(), CustomerActivity.class);
        intent.putExtra(CustomerPreference.IsActivityForJustGettingRequest, true);
        intent.putExtra(CustomerPreference.PersonId, personId);
        getBaseActivity().startActivity(intent);
    }

    @Override
    public void goToCustomerPage(int personId) {
        try {
            Intent intent = new Intent(getActivity(), CustomerActivity.class);
            intent.putExtra("PersonId", personId);
            getActivity().startActivity(intent);
        } catch (Exception e) {
            HandleException.ShowException(getActivity(), e);
        }
    }
}
