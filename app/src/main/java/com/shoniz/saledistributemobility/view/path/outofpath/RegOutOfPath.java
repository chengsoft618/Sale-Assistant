package com.shoniz.saledistributemobility.view.path.outofpath;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.shoniz.saledistributemobility.order.OrderDataOld;
import com.shoniz.saledistributemobility.utility.dialog.OnProgressUpdate;
import com.shoniz.saledistributemobility.utility.dialog.RunnableMethod;
import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.view.customer.activity.CustomerActivity;
import com.shoniz.saledistributemobility.view.customer.CustomerBusiness;
import com.shoniz.saledistributemobility.framework.exception.HandleException;
import com.shoniz.saledistributemobility.view.customer.CustomerData;
import com.shoniz.saledistributemobility.order.detail.OrderDetailData;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBasicModel;
import com.shoniz.saledistributemobility.view.customer.info.bought.CustomerBuyModel;
import com.shoniz.saledistributemobility.view.customer.info.cheque.CustomerChequeModel;
import com.shoniz.saledistributemobility.view.customer.info.credit.CustomerCreditModel;
import com.shoniz.saledistributemobility.order.detail.OrderDetailModel;
import com.shoniz.saledistributemobility.order.OrderModel;
import com.shoniz.saledistributemobility.view.path.PathModel;
import com.shoniz.saledistributemobility.utility.dialog.RunnableModel;
import com.shoniz.saledistributemobility.utility.dialog.AsyncTaskDialog;
import com.shoniz.saledistributemobility.utility.dialog.ErrorDialog.ErrorDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegOutOfPath {

    int personId;
    Activity context;

    public RegOutOfPath(Activity context, int personId) {
        this.personId = personId;
        this.context = context;
    }

//    public void manageOutOfPath() throws IOException {
//        if (CustomerData.isCustomerExist(context, personId)) {
//            goToCustomerPage(personId);
//            return;
//        }
//        syncCustomer(personId);
//    }
//
//    private void syncCustomer(final int personId) {
//        final AppCompatActivity activity = (AppCompatActivity) context;
//
//        RunnableMethod<Object, Object> runDo = new RunnableMethod<Object, Object>() {
//            @Override
//            public Object run(Object object, OnProgressUpdate onProgressUpdate) {
//                RunnableModel<CustomerBasicModel> runnableModel = new RunnableModel<>();
//                try {
//                    String preMessage = " در حال گرفتن اطلاعات مشتری خارج مسیر. لطفا منتظر بمانید...";
//                    onProgressUpdate.onProgressUpdate(preMessage);
//
//                    getCustomerInfo(activity);
//                    runnableModel.context = activity;
//                } catch (Exception e) {
//                    HandleException systemException = new HandleException(activity, e);
//                    ErrorDialog.showDialog(activity,
//                            systemException.getUserTitle(), systemException.getUserMessage(),
//                            systemException.getSystemMessage());
//                    runnableModel.HasError = true;
//                }
//                return runnableModel;
//            }
//        };
//
//        RunnableMethod<RunnableModel<List<PathModel>>, Object> runPost =
//                new RunnableMethod<RunnableModel<List<PathModel>>, Object>() {
//                    @Override
//                    public Object run(RunnableModel<List<PathModel>> runnableModel, OnProgressUpdate onProgressUpdate) {
//                        if (!runnableModel.HasError) {
//                            try {
//                                goToCustomerPage(personId);
//                            } catch (Exception e) {
//                                HandleException.ShowException(activity, e);
//                            }
//                        } else {
//                            syncCustomerInfoById(activity, personId);
//                        }
//                        return null;
//                    }
//                };
//
//        AsyncTaskDialog asyncTaskDialog = new AsyncTaskDialog(context,
//                context.getString(R.string.wait),
//                context.getString(R.string.get_visit_path), null, runDo, runPost);
//        asyncTaskDialog.execute();
//    }
//
//    private void syncCustomerInfoById(AppCompatActivity context, int personId) {
//        Intent intent = new Intent(context, CustomerActivity.class);
//        intent.putExtra(CustomerPreference.IsActivityForJustGettingRequest, true);
//        intent.putExtra(CustomerPreference.PersonId, personId);
//        context.startActivity(intent);
////        android.support.v4.app.FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
////        CardIndexFragment fragment = CardIndexFragment.newInstance(personId);
////        transaction.replace(R.id.fragment_container, fragment);
////        transaction.addToBackStack("cardIndex");
////        transaction.commit();
//    }
//
//    private void goToCustomerPage(int personId) {
//        try {
//            Intent intent = new Intent(context, CustomerActivity.class);
//            intent.putExtra("PersonId", personId);
//            context.startActivity(intent);
//        } catch (Exception e) {
//            HandleException.ShowException(context, e);
//        }
//    }

//    private void getCustomerInfo(Context context) throws Exception {
//        final List<Integer> personIds = new ArrayList<>();
//        personIds.add(personId);
//
//        List<CustomerBasicModel> customers = CustomerBusiness.getBaseInfoByPersonIds(context, personIds);
//        if (customers.size() < 1)
//            throw new Exception("");
//        List<CustomerChequeModel> chequeModel = CustomerBusiness.getChequeByPersonId(context, personId);
//        List<CustomerCreditModel> customerCreditModels = CustomerBusiness.getCreditByPersonIds(context, personIds);
//        List<CustomerBuyModel> customerBuyModels = CustomerBusiness.getBoughtSummaryByPersonIds(context, personIds);
//        List<OrderModel> orderModels = CustomerBusiness.getOrderByPersonIds(context, personIds);
//        List<OrderDetailModel> orderDetailModels = CustomerBusiness.getOrderDetailByPersonIds(context, personIds);
//
//        CustomerData.insertBaseInfo(context, customers);
//        OrderDataOld.insert(context, orderModels);
//        OrderDetailData.insert(context, orderDetailModels);
//        CustomerData.insertCustomersCheque(context, chequeModel);
//        CustomerData.insertCustomersBuy(context, customerBuyModels);
//        CustomerData.insertCustomersCredit(context, customerCreditModels);
//    }
}
