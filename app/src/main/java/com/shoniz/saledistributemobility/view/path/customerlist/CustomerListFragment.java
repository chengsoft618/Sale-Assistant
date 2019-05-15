package com.shoniz.saledistributemobility.view.path.customerlist;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.shoniz.saledistributemobility.BR;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.data.model.customer.CustomerData;
import com.shoniz.saledistributemobility.data.model.path.db.PathEntity;
import com.shoniz.saledistributemobility.databinding.FragmentCustomerListBinding;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerHelper;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerViewModel;
import com.shoniz.saledistributemobility.view.base.BaseFragment;
import com.shoniz.saledistributemobility.view.customer.activity.CustomerActivity;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBasicModel;
import com.shoniz.saledistributemobility.view.dialog.unvisitedCustomerDialog.UnvisitingDialog;

import java.util.List;

import javax.inject.Inject;

import static com.shoniz.saledistributemobility.view.path.pathlist.PathListFragment.IS_ACTIVE;
import static com.shoniz.saledistributemobility.view.path.pathlist.PathListFragment.PERSIAN_DATE;


public class CustomerListFragment extends BaseFragment<FragmentCustomerListBinding, CustomerListViewModel>
        implements ICustomerListNavigator {

    @Inject
    CommonPackage commonPackage;
//    @Inject
//    ILogRepository logRepository;
//    @Inject
//    ICustomerRepository customerRepository;
//    @Inject
//    IOrderRepository orderRepository;
//    @Inject
//    ISettingRepository settingRepository;
//    @Inject
//    ICardIndexRepository cardIndexRepository;
    @Inject
    ViewModelProvider.Factory factory;

    //AppCompatActivity activity;
    private RecyclerView recyclerView;
    //private int pathCode;
    private List<CustomerData> models;

    private CustomerAdapter adapter;
    private SearchView mSearchView;

    //private static List<ReasonEntity> reasonModels;

//    private boolean isCustomersOfActivePath = false;
//    private String activePathDate = "";

    public static CustomerListFragment newInstance(PathEntity pathEntity) {
        CustomerListFragment fragment = new CustomerListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CustomerBasicModel.Column.PATH_CODE, pathEntity.PathCode);
        bundle.putBoolean(IS_ACTIVE, pathEntity.IsActive);
        bundle.putString(PERSIAN_DATE, pathEntity.PersianDate.trim());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        activePathDate = getArguments().getString(PERSIAN_DATE, "");
//        reasonModels = orderRepository.getUnvisitedReasons();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mViewModel.init(bundle.getInt(CustomerBasicModel.Column.PATH_CODE),
                bundle.getBoolean(IS_ACTIVE, false),
                bundle.getString(PERSIAN_DATE, ""));
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public CustomerListViewModel getViewModel() {
        CustomerListViewModel model = ViewModelProviders.of(this, factory).get(CustomerListViewModel.class);
        return model;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_customer_list;
    }

    @Override
    public int getFragmentTitle() {
        return 0;
    }

    private CustomerAdapter.CustomerListListener customerListListener = new CustomerAdapter.CustomerListListener() {
        @Override
        public void onClick(CustomerBasicModel customerBasicModel) {

//            try {
//                boolean isCardIndexEmpty = CardIndexBusiness.isCardIndexEmpty(activity, customerBasicModel.PersonID);
//
//                if (customerBasicModel.UnIssuedOrderNo > 0 && isCardIndexEmpty)
//                    showSentOrderOperationDialog(customerBasicModel);
//                else
//                    CustomerActivity.startActivity(getActivity(), customerBasicModel.PersonID);
//
//            } catch (Exception e) {
//                UncaughtException exception =
//                        new UncaughtException(commonPackage, e);
//                ExceptionHandler.handle(exception, getActivity());
//            }
        }

        @Override
        public void onLongClick(CustomerBasicModel customerBasicModel) {
//            if (!isCustomersOfActivePath) return;
//            if (customerBasicModel.UnIssuedOrderNo > 0) return;
//
//            UnvisitingDialog dialog = new UnvisitingDialog();
//            dialog.show(getActivity(), reasonModels, new UnvisitingDialog.IUnvisitListener() {
//                @Override
//                public void onClick(int reasonId, String desc) {
//                    CommonAsyncTask asyncTask = new CommonAsyncTask(null,
//                            new CommonAsyncTask.RunnableDo() {
//                                @Override
//                                public AsyncResult run(Object param, CommonAsyncTask.OnProgress onProgress) {
//                                    OfficeApi shonizApi = new OfficeApi(getContext());
//                                    UnvisitedReasonData reasonEntity = new UnvisitedReasonData();
//                                    reasonEntity.NotSallReasonID = reasonId;
//                                    reasonEntity.PersonID = customerBasicModel.PersonID;
//                                    reasonEntity.PersianDate = activePathDate;
//                                    reasonEntity.Description = desc;
//                                    AsyncResult result = new AsyncResult();
//                                    try {
//                                        shonizApi.SetReasonAll(reasonEntity);
//                                        dialog.closeDialog();
//                                    } catch (Exception e) {
//                                        result.exception = new UncaughtException(commonPackage, e);
//                                    }
//                                    result.model = reasonEntity;
//                                    return result;
//                                }
//                            },
//                            new CommonAsyncTask.RunnablePost() {
//                                @Override
//                                public void run(Object param) {
//                                    AsyncResult result = (AsyncResult) param;
//                                    if (result.exception == null) {
//
//                                        UnvisitedCustomerReasonEntity reasonEntity =
//                                                new UnvisitedCustomerReasonEntity();
//                                        reasonEntity.PersonId = customerBasicModel.PersonID;
//                                        reasonEntity.IsSent = true;
//                                        reasonEntity.NotSallReasonID = reasonModels.get(single_choice_selected).NotSallReasonID;
//                                        reasonEntity.Description = ((UnvisitedReasonData) result.model).Description;
//                                        orderRepository.saveUnvisitingReason(reasonEntity);
//                                        refreshRecycle();
//                                    } else {
//                                        Common.toast(commonPackage.getContext(), "اشکال در ارسال داده. لطفا مجددا امتحان کنید");
//                                        ExceptionHandler.handle(result.exception, commonPackage.getContext());
//                                    }
//                                }
//                            }, null);
//                    asyncTask.run();
//                }
//            });
        }
    };

    @Override
    public void showUnvisitedCustomerDialog(int personId){
    UnvisitingDialog dialog = new UnvisitingDialog();
    dialog.show(getActivity(), mViewModel.getCustomerNotvisitingReasons(), new UnvisitingDialog.IUnvisitListener() {
        @Override
        public void onClick(int reasonId, String desc) {
            dialog.closeDialog();
            mViewModel.submitUnvisitingReason(personId, reasonId, desc);
//            CommonAsyncTask asyncTask = new CommonAsyncTask(null,
//                    new CommonAsyncTask.RunnableDo() {
//                        @Override
//                        public AsyncResult run(Object param, CommonAsyncTask.OnProgress onProgress) {
//                            OfficeApi shonizApi = new OfficeApi(getContext());
//                            UnvisitedReasonData reasonEntity = new UnvisitedReasonData();
//                            reasonEntity.NotSallReasonID = reasonId;
//                            reasonEntity.PersonID = customerBasicModel.PersonID;
//                            reasonEntity.PersianDate = activePathDate;
//                            reasonEntity.Description = desc;
//                            AsyncResult result = new AsyncResult();
//                            try {
//                                shonizApi.SetReasonAll(reasonEntity);
//                                dialog.closeDialog();
//                            } catch (Exception e) {
//                                result.exception = new UncaughtException(commonPackage, e);
//                            }
//                            result.model = reasonEntity;
//                            return result;
//                        }
//                    },
//                    new CommonAsyncTask.RunnablePost() {
//                        @Override
//                        public void run(Object param) {
//                            AsyncResult result = (AsyncResult) param;
//                            if (result.exception == null) {
//
//                                UnvisitedCustomerReasonEntity reasonEntity =
//                                        new UnvisitedCustomerReasonEntity();
//                                reasonEntity.PersonId = customerBasicModel.PersonID;
//                                reasonEntity.IsSent = true;
//                                reasonEntity.NotSallReasonID = reasonModels.get(single_choice_selected).NotSallReasonID;
//                                reasonEntity.Description = ((UnvisitedReasonData) result.model).Description;
//                                orderRepository.saveUnvisitingReason(reasonEntity);
//                                refreshRecycle();
//                            } else {
//                                Common.toast(commonPackage.getContext(), "اشکال در ارسال داده. لطفا مجددا امتحان کنید");
//                                ExceptionHandler.handle(result.exception, commonPackage.getContext());
//                            }
//                        }
//                    }, null);
//            asyncTask.run();
        }
    });
}

    @Override
    public void showSentOrderOperationDialog(CustomerData customerData) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_sent_order_operation);
        Button btnEdit = dialog.findViewById(R.id.btn_edit_sent_order);
        Button btnNew = dialog.findViewById(R.id.btn_new_order);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                getViewModel().startCustomerActivityToEdit(customerData);
            }
        });
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(customerData.getId());

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
    public void startActivity(int personId) {
        CustomerActivity.startActivity(getActivity(), personId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_customer_class_name :
                mViewModel.onCheckCustomerClass(item.isChecked()); break;
            case R.id.action_customer_is_active:
                mViewModel.onCheckActiveCustomer(item.isChecked()); break;
            default: return super.onOptionsItemSelected(item);
        }
        getActivity().invalidateOptionsMenu();
        return true;
    }

    public void refreshRecycle() {
        RecyclerHelper.build(
                mViewModel,
                getBaseActivity(),
                this,
                mViewDataBinding.customerListRecycler,
                CustomerItemBindingBuilder.class)
                .setSelectingModel(
                        RecyclerViewModel.SelectingMode.None
                );
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.customer_list_update_menu, menu);
        menu.findItem(R.id.action_customer_is_active).setChecked(mViewModel.getInactiveCustomerCheckboxStatus());
        menu.findItem(R.id.action_customer_class_name).setChecked(mViewModel.getClassBCustomerCheckboxStatus());

        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        mSearchView.setIconifiedByDefault(true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String search) {
                mViewModel.onSearchTextChange(search);
//                searchModels.removeAll(searchModels);
//
//                if (search.trim() != "")
//                    for (CustomerData__ customer : models) {
//                        if ((customer.customerBasicEntity.PersonID + "").contains(search) ||
//                                customer.customerBasicEntity.ContactName.contains(search) ||
//                                (customer.customerBasicEntity.CustomerID + "").contains(search) ||
//                                customer.customerBasicEntity.PersonName.contains(search))
//                            searchModels.add(customer);
//                    }
//                refreshRecycle();
                return false;
            }
        });
        mSearchView.setQueryHint("جستجوی مشتری...");

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void startCustomerActivity(int personId) {
        CustomerActivity.startActivity(getActivity(), personId);
    }
}


