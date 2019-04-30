package com.shoniz.saledistributemobility.view.path.customerlist;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.base.FileContentModel;
import com.shoniz.saledistributemobility.data.model.cardindex.ICardIndexRepository;
import com.shoniz.saledistributemobility.data.model.customer.ICustomerRepository;
import com.shoniz.saledistributemobility.data.model.log.ILogRepository;
import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.model.order.UnvisitedCustomerReasonEntity;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BusinessException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.ExceptionHandler;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;
import com.shoniz.saledistributemobility.order.RequestBusiness;
import com.shoniz.saledistributemobility.order.unvisited.ReasonDto;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.utility.StringHelper;
import com.shoniz.saledistributemobility.utility.data.api.OfficeApi;
import com.shoniz.saledistributemobility.utility.data.pref.AppPref;
import com.shoniz.saledistributemobility.utility.dialog.AsyncTaskDialog;
import com.shoniz.saledistributemobility.utility.dialog.ErrorDialog.ErrorDialog;
import com.shoniz.saledistributemobility.utility.dialog.OnProgressUpdate;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;
import com.shoniz.saledistributemobility.utility.dialog.RunnableModel;
import com.shoniz.saledistributemobility.view.customer.CustomerBusiness;
import com.shoniz.saledistributemobility.view.customer.CustomerData;
import com.shoniz.saledistributemobility.view.customer.activity.CustomerActivity;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexBusiness;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBasicModel;
import com.shoniz.saledistributemobility.view.dialog.unvisitedCustomerDialog.UnvisitingDialog;
import com.shoniz.saledistributemobility.view.entity.ReasonEntity;
import com.shoniz.saledistributemobility.view.path.PathModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.shoniz.saledistributemobility.view.path.pathlist.PathListFragment.IS_ACTIVE;
import static com.shoniz.saledistributemobility.view.path.pathlist.PathListFragment.PERSIAN_DATE;


public class CustomerListFragment extends dagger.android.support.DaggerFragment {

    @Inject
    CommonPackage commonPackage;
    @Inject
    ILogRepository logRepository;
    @Inject
    ICustomerRepository customerRepository;
    @Inject
    IOrderRepository orderRepository;
    @Inject
    ISettingRepository settingRepository;
    @Inject
    ICardIndexRepository cardIndexRepository;

    AppCompatActivity activity;
    private RecyclerView recyclerView;
    private int pathCode;
    private List<CustomerBasicModel> models;
    private List<CustomerBasicModel> searchModels;
    private ActionMode mActionMode;
    private CustomerAdapter adapter;
    private ToolbarCustomerActionModeCallback actionModeCallback;
    private SearchView mSearchView;

    private static String[] unvisitedReasonsString;
    private static List<ReasonEntity> reasonModels;

    private boolean isCustomersOfActivePath = false;
    private String activePathDate = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        isCustomersOfActivePath = getArguments().getBoolean(IS_ACTIVE, false);
        activePathDate = getArguments().getString(PERSIAN_DATE, "");

        activity = (AppCompatActivity) getActivity();
        if (getArguments() != null) {
            pathCode = getArguments().getInt(CustomerBasicModel.Column.PATH_CODE);
        }
        setHasOptionsMenu(true);
        searchModels = new ArrayList<>();
        reasonModels = orderRepository.getUnvisitingReasons();
        //unvisitedReasonsString = new String[reasonModels.size()];
//        RadioGroup r = new RadioGroup(commonPackage.getContext());
//                for (int i = 0; i < reasonModels.size(); i++) {
//            RadioButton rdbtn = new RadioButton(commonPackage.getContext());
//            rdbtn.setId(reasonModels.get(i).NotSallReasonID);
//            rdbtn.setText(reasonModels.get(i).NotSallReasonText);
//            r.addView(rdbtn);
//        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_list, container, false);

        if (view instanceof RecyclerView) {
            recyclerView = (RecyclerView) view;
        }
        setHasOptionsMenu(true);
        //refreshRecycle();
        return view;
    }

    private CustomerAdapter.OnItemSelectedListener onItemSelectedListener = new CustomerAdapter.OnItemSelectedListener() {
        @Override
        public void onClick(CustomerBasicModel customerBasicModel) {
            try {
                boolean isEmptyCardindex = false;
                isEmptyCardindex = CardIndexBusiness.isEmptyCardIndex(activity, customerBasicModel.PersonID);

                if (customerBasicModel.UnIssuedOrderNo > 0 && isEmptyCardindex)
                    showSentOrderOperationDialog(customerBasicModel);
                else
                    CustomerActivity.startActivity(getActivity(), customerBasicModel.PersonID);

            } catch (Exception e) {
                UncaughtException exception =
                        new UncaughtException(commonPackage, e);
                ExceptionHandler.handle(exception, getActivity());
            }
        }

        public void showSentOrderOperationDialog(CustomerBasicModel customerBasicModel) {
            final Dialog dialog = new Dialog(activity);
            dialog.setContentView(R.layout.dialog_sent_order_operation);
            Button btnEdit = dialog.findViewById(R.id.btn_edit_sent_order);
            Button btnNew = dialog.findViewById(R.id.btn_new_order);
            Button btnCancel = dialog.findViewById(R.id.btn_cancel);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (settingRepository.getUnchangedOrdersNoInCardindeForEdit() != 0
                                && settingRepository.getUnchangedOrdersNoInCardindeForEdit() != customerBasicModel.UnIssuedOrderNo)
                            try {
                                cardIndexRepository.removeUnchangedCardindexForEdit(orderRepository,
                                        settingRepository, cardIndexRepository, commonPackage);
                            } catch (IOException e) {
                                UncaughtException exception = new UncaughtException(commonPackage, e);
                                ExceptionHandler.handle(exception, getActivity());
                            }

                        RequestBusiness.makeOrderReadyToEdit(activity, customerBasicModel.UnIssuedOrderNo);
                        settingRepository.setUnchangedOrdersNoInCardindeForEdit(customerBasicModel.UnIssuedOrderNo);
                        dialog.dismiss();
                        CustomerActivity.startActivity(getActivity(), customerBasicModel.PersonID);
                    } catch (IOException e) {
                        BaseException ex = new BusinessException(commonPackage, "خطا در تبدیل درخواست به کارتکس");
                        ExceptionHandler.handle(ex, getActivity());
                    }

                }
            });
            btnNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    CustomerActivity.startActivity(getActivity(), customerBasicModel.PersonID);

                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

        @Override
        public void onUpdateClick(List<Integer> selectedIds) {
            if (selectedIds.size() == models.size()) {
                upDateCustomers();
            } else {
                //updateSelectedCustomers();
            }
        }


        @Override
        public void onItemCheck(CustomerBasicModel customerBasicModel) {
            if (adapter.getSelectedList().size() == adapter.getList().size()) {
                actionModeCallback.setCheckAll(true);
            } else {
                actionModeCallback.setCheckAll(false);
            }
        }

        private int single_choice_selected;

        @Override
        public void onLongClick(CustomerBasicModel customerBasicModel) {
            if (!isCustomersOfActivePath) return;
            if (customerBasicModel.UnIssuedOrderNo > 0) return;

            UnvisitingDialog dialog = new UnvisitingDialog();
            dialog.show(getActivity(), reasonModels, new UnvisitingDialog.IUnvisitListener() {
                @Override
                public void onClick(int reasonId, String desc) {
                    CommonAsyncTask asyncTask = new CommonAsyncTask(null,
                            new CommonAsyncTask.RunnableDo() {
                                @Override
                                public AsyncResult run(Object param, CommonAsyncTask.OnProgress onProgress) {
                                    OfficeApi shonizApi = new OfficeApi(getContext());
                                    ReasonDto reasonEntity = new ReasonDto();
                                    reasonEntity.NotSallReasonID = reasonId;
                                    reasonEntity.PersonID = customerBasicModel.PersonID;
                                    reasonEntity.PersianDate = activePathDate;
                                    reasonEntity.Description = desc;
                                    AsyncResult result = new AsyncResult();
                                    try {
                                        shonizApi.SetReasonAll(reasonEntity);
                                        dialog.closeDialog();
                                    } catch (Exception e) {
                                        result.exception = new UncaughtException(commonPackage, e);
                                    }
                                    result.model = reasonEntity;
                                    return result;
                                }
                            },
                            new CommonAsyncTask.RunnablePost() {
                                @Override
                                public void run(Object param) {
                                    AsyncResult result = (AsyncResult) param;
                                    if (result.exception == null) {

                                        UnvisitedCustomerReasonEntity reasonEntity =
                                                new UnvisitedCustomerReasonEntity();
                                        reasonEntity.PersonId = customerBasicModel.PersonID;
                                        reasonEntity.IsSent = true;
                                        reasonEntity.NotSallReasonID = reasonModels.get(single_choice_selected).NotSallReasonID;
                                        reasonEntity.Description = ((ReasonDto) result.model).Description;
                                        orderRepository.saveUnvisitingReason(reasonEntity);
                                        refreshRecycle();
                                    } else {
                                        Common.toast(commonPackage.getContext(), "اشکال در ارسال داده. لطفا مجددا امتحان کنید");
                                        ExceptionHandler.handle(result.exception, commonPackage.getContext());
                                    }
                                }
                            }, null);
                    asyncTask.run();
                }
            });
        }
    };

    public static CustomerListFragment newInstance(PathModel pathModel) {
        CustomerListFragment fragment = new CustomerListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CustomerBasicModel.Column.PATH_CODE, pathModel.PathCode);
        bundle.putBoolean(IS_ACTIVE, pathModel.IsActive);
        bundle.putString(PERSIAN_DATE, pathModel.PersianDate.trim());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        try {
            if (id == R.id.action_customer_class_name) {
                AppPref.setCustomerClassNameB(activity, !item.isChecked());
                refreshRecycle();
                activity.invalidateOptionsMenu();
                return true;
            } else if (id == R.id.action_customer_is_active) {
                AppPref.setActiveCustomerChecked(activity, !item.isChecked());
                refreshRecycle();
                activity.invalidateOptionsMenu();
                return true;
            }

        } catch (Exception e) {
            HandleException.ShowException(activity, e);
        }
        return super.onOptionsItemSelected(item);
    }

    private void upDateCustomers() {
        RunnableMethod<Object, Object> runDo = new RunnableMethod<Object, Object>() {
            @Override
            public Object run(Object object, OnProgressUpdate onProgressUpdate) {
                RunnableModel<FileContentModel> runnableModel = new RunnableModel<>();

                try {
                    int allProgress = 5, currentProgress = 1;

                    String preMessage = StringHelper.GenerateMessage(getString(R.string.progress_status), currentProgress, allProgress);
                    onProgressUpdate.onProgressUpdate("بروز رسانی نرم افزار");
                    CustomerData.createSaleDb(activity, CustomerBusiness.getSaleDb(activity).FileContents);
                    currentProgress++;
                } catch (Exception e) {
                    HandleException systemException = new HandleException(activity, e);
                    ErrorDialog.showDialog((AppCompatActivity) getActivity(),
                            systemException.getUserTitle(), systemException.getUserMessage(),
                            systemException.getSystemMessage());
                    runnableModel.HasError = true;
                    runnableModel.exception = systemException;
                }
                return runnableModel;
            }
        };

        RunnableMethod<RunnableModel<FileContentModel>, Object> runPost =
                new RunnableMethod<RunnableModel<FileContentModel>, Object>() {
                    @Override
                    public Object run(RunnableModel<FileContentModel> runnableModel, OnProgressUpdate onProgressUpdate) {
                        if (runnableModel.HasError) {
                            ErrorDialog.showDialog((AppCompatActivity) getActivity(),
                                    runnableModel.exception.getUserTitle(), runnableModel.exception.getUserMessage(),
                                    runnableModel.exception.getSystemMessage());
                        }
                        return null;
                    }
                };

        AsyncTaskDialog asyncTaskDialog = new AsyncTaskDialog(getActivity(),
                getString(R.string.wait),
                getString(R.string.get_visit_path), null, runDo, runPost);
        asyncTaskDialog.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshRecycle();
    }

    private void refreshRecycle() {
        try {
            Common.setRecycleViewLayoutManager(activity, recyclerView);
            if (searchModels.size() == 0)
                models = customerRepository.getCustomerBaseInfoByPath(pathCode);

            adapter = new CustomerAdapter(getActivity(),
                    searchModels.size() == 0 ? models : searchModels,
                    onItemSelectedListener, reasonModels);
            adapter.isCustomersOfActivePath = isCustomersOfActivePath;
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {
            HandleException systemException = new HandleException(activity, e);
            ErrorDialog.showDialog((AppCompatActivity) getActivity(),
                    systemException.getUserTitle(), systemException.getUserMessage(),
                    systemException.getSystemMessage());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.customer_list_update_menu, menu);
        menu.findItem(R.id.action_customer_is_active).setChecked(AppPref.isActiveCustomerChecked(activity));
        menu.findItem(R.id.action_customer_class_name).setChecked(AppPref.isCustomerClassNameB(activity));

        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String search) {
                searchModels.removeAll(searchModels);

                if (search.trim() != "")
                    for (CustomerBasicModel customer : models) {
                        if((customer.PersonID + "").contains(search) ||
                                customer.ContactName.contains(search) ||
                                (customer.CustomerID + "").contains(search) ||
                                customer.PersonName.contains(search))
                            searchModels.add(customer);
                    }
                refreshRecycle();
                return false;
            }
        });
        mSearchView.setQueryHint("جستجوی مشتری...");

        super.onCreateOptionsMenu(menu, inflater);
    }

}


