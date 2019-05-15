package com.shoniz.saledistributemobility.view.path.customerlist;

import android.arch.lifecycle.MutableLiveData;
import android.view.View;

import com.shoniz.saledistributemobility.data.model.customer.CustomerData;
import com.shoniz.saledistributemobility.data.model.customer.ICustomerRepository;
import com.shoniz.saledistributemobility.data.model.customer.UnvisitedReasonData;
import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.model.order.UnvisitedCustomerReasonEntity;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.ExceptionHandler;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.UncaughtException;
import com.shoniz.saledistributemobility.framework.service.order.ICardIndexService;
import com.shoniz.saledistributemobility.infrastructure.AsyncResult;
import com.shoniz.saledistributemobility.infrastructure.CommonAsyncTask;
import com.shoniz.saledistributemobility.infrastructure.recycler.RecyclerViewModel;
import com.shoniz.saledistributemobility.utility.Common;
import com.shoniz.saledistributemobility.view.entity.ReasonEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CustomerListViewModel extends RecyclerViewModel<CustomerData, Integer, ICustomerListNavigator>
        implements ICustomerRecyclerListener{

    ICardIndexService cardIndexService;
    CommonPackage commonPackage;
    ICustomerRepository customerRepository;
    IOrderRepository orderRepository;
    ISettingRepository settingRepository;
    int pathCode;
    boolean containClassB = false;
    boolean containInactiveCustomers = false;
    Boolean isActivePathOfPerson = false;
    String persianDate = "";
    MutableLiveData<List<CustomerData>> customersList = new MutableLiveData<>();
    private List<CustomerData> searchModels = new ArrayList<>();
    private List<ReasonEntity> reasonModels = new ArrayList<>();


    @Inject
    public CustomerListViewModel(CommonPackage commonPackage,
                                 ICardIndexService cardIndexService,
                                 ICustomerRepository customerRepository,
                                 IOrderRepository orderRepository,
                                 ISettingRepository settingRepository) {
        this.commonPackage = commonPackage;
        this.cardIndexService = cardIndexService;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.settingRepository = settingRepository;
    }

    public void startCustomerActivityToEdit(CustomerData customerBasicModel) {
        try {
            cardIndexService.makeOrderReadyToEdit(customerBasicModel.UnIssuedOrderNo);
        } catch (Exception e) {
            UncaughtException ex = new UncaughtException(commonPackage, e);
            getNavigator().handleError(ex);
        }

        getNavigator().startCustomerActivity(customerBasicModel.getId());
    }

    public void init(int pathCode, boolean isActivePathOfPerson, String persianDate){
        this.pathCode = pathCode;
        this.isActivePathOfPerson = isActivePathOfPerson;
        this.persianDate = persianDate;
        updateCustomerList();
        getNavigator().refreshRecycle();
    }

    void updateCustomerList(){
        customersList.setValue(
                customerRepository.getCustomerBaseInfoByPath(
                        pathCode,
                        containInactiveCustomers,
                        containClassB)
        );
    }

    @Override
    public void onCustomerClick(View view, CustomerData customerData) {
            if (customerData.UnIssuedOrderNo > 0 && cardIndexService.isEmptyCardIndex(customerData.getId()))
                getNavigator().showSentOrderOperationDialog(customerData);
            else
                getNavigator().startActivity(customerData.customerBasicEntity.PersonID);
    }

    @Override
    public void onCustomerLongClick(View view, CustomerData customerData) {
        if (!isActivePathOfPerson) return;
        if (customerData.UnIssuedOrderNo > 0) return;

        getNavigator().showUnvisitedCustomerDialog(customerData.getId());
    }

    @Override
    public MutableLiveData<List<CustomerData>> getMutableList() {
        if(searchModels.size() > 0){
            MutableLiveData<List<CustomerData>> searchList = new MutableLiveData<>();
            searchList.setValue(searchModels);
            return searchList;
        }
        return customersList;
    }

    public void onSearchTextChange(String search) {
        searchModels.removeAll(searchModels);

        if (search.trim() != ""){
            for (CustomerData customer : getMutableList().getValue()) {
                if ((customer.customerBasicEntity.PersonID + "").contains(search) ||
                        customer.customerBasicEntity.ContactName.contains(search) ||
                        (customer.customerBasicEntity.CustomerID + "").contains(search) ||
                        customer.customerBasicEntity.PersonName.contains(search))
                    searchModels.add(customer);
            }
        }
        getNavigator().refreshRecycle();
    }

    public void onCheckCustomerClass(boolean status){
        containClassB = status;
        settingRepository.setCustomerClassNameBCheckbox(status);
        updateCustomerList();
        getNavigator().refreshRecycle();
    }

    public void onCheckActiveCustomer(boolean status){
        containInactiveCustomers = status;
        settingRepository.setInactiveCustomerChecked(status);
        updateCustomerList();
        getNavigator().refreshRecycle();
    }

    public List<ReasonEntity> getCustomerNotvisitingReasons(){
        if(reasonModels.size() == 0)
            reasonModels = orderRepository.getUnvisitedReasons();
        return reasonModels;
    }

    public void submitUnvisitingReason(int personId, int reasonId, String desc){
        CommonAsyncTask asyncTask = new CommonAsyncTask(null,
                new CommonAsyncTask.RunnableDo() {
                    @Override
                    public AsyncResult run(Object param, CommonAsyncTask.OnProgress onProgress) {
                        UnvisitedReasonData unvisitedReasonData = new UnvisitedReasonData();
                        unvisitedReasonData.NotSallReasonID = reasonId;
                        unvisitedReasonData.PersonID = personId;
                        unvisitedReasonData.PersianDate = persianDate;
                        unvisitedReasonData.Description = desc;
                        AsyncResult result = new AsyncResult();
                        try {
                            customerRepository.sendUnvisitedReason(unvisitedReasonData);
                        } catch (BaseException e) {
                            result.exception = e;
                        }
                        result.model = unvisitedReasonData;
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
                            reasonEntity.PersonId = personId;
                            reasonEntity.IsSent = true;
                            reasonEntity.NotSallReasonID = reasonModels.get(0).NotSallReasonID;
                            reasonEntity.Description = ((UnvisitedReasonData) result.model).Description;
                            customerRepository.saveUnvisitingReason(reasonEntity);
                            getNavigator().refreshRecycle();
                        } else {
                            Common.toast(commonPackage.getContext(), "اشکال در ارسال داده. لطفا مجددا امتحان کنید");
                            ExceptionHandler.handle(result.exception, commonPackage.getContext());
                        }
                    }
                }, null);
        asyncTask.run();
    }

    public boolean getClassBCustomerCheckboxStatus(){
        return settingRepository.isClassNameBCustomerChecked();
    }
    public boolean getInactiveCustomerCheckboxStatus(){
        return settingRepository.isInactiveCustomerChecked();
    }
}
