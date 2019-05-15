package com.shoniz.saledistributemobility.view.path.outofpath;


import android.app.Activity;

public class RegOutOfPath {

    int personId;
    Activity context;

    public RegOutOfPath(Activity context, int personId) {
        this.personId = personId;
        this.context = context;
    }

//    public void initOutOfPath() throws IOException {
//        if (CustomerData__.isCustomerExist(context, personId)) {
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
//        RunnableMethod<RunnableModel<List<PathModel__>>, Object> runPost =
//                new RunnableMethod<RunnableModel<List<PathModel__>>, Object>() {
//                    @Override
//                    public Object run(RunnableModel<List<PathModel__>> runnableModel, OnProgressUpdate onProgressUpdate) {
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
//        intent.putExtra(CustomerPreference.IsCardindexForUnknownCustomer, true);
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
//        CustomerData__.insertBaseInfo(context, customers);
//        OrderDataOld.insert(context, orderModels);
//        OrderDetailData.insert(context, orderDetailModels);
//        CustomerData__.insertCustomersCheque(context, chequeModel);
//        CustomerData__.insertCustomersBuy(context, customerBuyModels);
//        CustomerData__.insertCustomersCredit(context, customerCreditModels);
//    }
}
