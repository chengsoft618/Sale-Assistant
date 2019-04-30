package com.shoniz.saledistributemobility.view.customer;

import android.content.Context;

import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexOldDb;
import com.shoniz.saledistributemobility.data.model.location.LocationEntity;
import com.shoniz.saledistributemobility.utility.dialog.StageListener;
import com.shoniz.saledistributemobility.base.download.ProgressStage;
import com.shoniz.saledistributemobility.framework.exception.OldApiException;
import com.shoniz.saledistributemobility.framework.exception.ConnectionException;
import com.shoniz.saledistributemobility.utility.data.api.OfficeApi;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexDetailModel;
import com.shoniz.saledistributemobility.order.detail.OrderDetailModel;
import com.shoniz.saledistributemobility.order.OrderModel;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBasicModel;
import com.shoniz.saledistributemobility.view.customer.info.bought.CustomerBuyModel;
import com.shoniz.saledistributemobility.view.customer.info.cheque.CustomerChequeModel;
import com.shoniz.saledistributemobility.view.customer.info.credit.CustomerCreditModel;
import com.shoniz.saledistributemobility.base.FileContentModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class CustomerBusiness {
    public static FileContentModel getSaleDb(Context context) throws IOException, OldApiException, ConnectionException {
        OfficeApi officeApi=new OfficeApi(context);
        return officeApi.getSaleDb();
    }

//    public static FileContentModel getBaseInfoDb(Context context) throws IOException, OldApiException, ConnectionException {
//        OfficeApi officeApi=new OfficeApi(context);
//        return officeApi.getEmployeeCustomerBaseInfoDb();
//    }
//    public static List<CustomerBasicModel> getBaseInfoByPersonIds(Context context, List<Integer> personIds) throws IOException, OldApiException, ConnectionException {
//        OfficeApi officeApi=new OfficeApi(context);
//        return officeApi.getEmployeeCustomerBaseInfoByPersonIds(personIds);
//    }
//    public static List<CustomerBasicModel> getBaseInfoByPath(Context context, int pathId) throws IOException, OldApiException, ConnectionException {
//        List<Integer> pathIds=new ArrayList<>();
//        pathIds.add(pathId);
//        return getBaseInfoByPaths(context,pathIds);
//    }
    public static List<CustomerBasicModel> getBaseInfoByPaths(Context context, List<Integer> pathIds) throws IOException, OldApiException, ConnectionException {
        OfficeApi officeApi=new OfficeApi(context);
        return officeApi.getEmployeeCustomerBaseInfoByPath(pathIds);
    }

//    public static FileContentModel getCreditDb(Context context) throws IOException, OldApiException, ConnectionException {
//        OfficeApi officeApi=new OfficeApi(context);
//        return officeApi.getEmployeeCustomerCreditDb();
//    }
//    public static List<CustomerCreditModel> getCreditByPersonIds(Context context, List<Integer> personIds) throws IOException, OldApiException, ConnectionException {
//        OfficeApi officeApi=new OfficeApi(context);
//        return officeApi.getEmployeeCustomerCreditByPersonIds(personIds);
//    }
    public static List<CustomerCreditModel> getCreditByPath(Context context, List<Integer> pathIds) throws IOException, OldApiException, ConnectionException {
        OfficeApi officeApi=new OfficeApi(context);
        return officeApi.getEmployeeCustomerCreditByPath(pathIds);
    }

//    public static FileContentModel getBoughtSummaryDb(Context context) throws IOException, OldApiException, ConnectionException {
//        OfficeApi officeApi=new OfficeApi(context);
//        return officeApi.getEmployeeCustomerSumBuyAndBuyReturnedDb();
//    }
//    public static List<CustomerBuyModel> getBoughtSummaryByPersonIds(Context context, List<Integer> personIds) throws IOException, OldApiException, ConnectionException {
//        OfficeApi officeApi=new OfficeApi(context);
//        return officeApi.getEmployeeCustomerSumBuyAndBuyReturnedByPersonIds(personIds);
//    }
    public static List<CustomerBuyModel> getBoughtSummaryByPath(Context context, List<Integer> pathIds) throws IOException, OldApiException, ConnectionException {
        OfficeApi officeApi=new OfficeApi(context);
        return officeApi.getEmployeeCustomerSumBuyAndBuyReturnedByPath(pathIds);
    }

//    public static FileContentModel getChequeDb(Context context) throws IOException, OldApiException, ConnectionException {
//        OfficeApi officeApi=new OfficeApi(context);
//        return officeApi.getEmployeeCustomerChequeDb();
//    }
//    public static List<CustomerChequeModel> getEmployeeCustomerChequeByPersonIds(Context context,List<Integer> personIds) throws IOException, OldApiException, ConnectionException {
//        OfficeApi officeApi=new OfficeApi(context);
//        return officeApi.getEmployeeCustomerChequeByPersonIds(personIds);
//    }
//
//    public static List<CustomerChequeModel> getChequeByPersonId(Context context, int personId) throws IOException, OldApiException, ConnectionException {
//        OfficeApi officeApi=new OfficeApi(context);
//        List<Integer> ids=new ArrayList<>();
//        ids.add(personId);
//        return officeApi.getEmployeeCustomerChequeByPersonIds(ids);
//    }
    public static List<CustomerChequeModel> getChequeByPath(Context context, List<Integer> pathIds) throws IOException, OldApiException, ConnectionException {
        OfficeApi officeApi=new OfficeApi(context);
        return officeApi.getEmployeeCustomerChequeByPath(pathIds);
    }

    public void sendCustomerLocation(Context context, int customerId,LocationEntity locationEntity) throws ConnectionException, OldApiException, IOException {
        OfficeApi officeApi=new OfficeApi(context);
        officeApi.sendCustomerLocation(customerId, locationEntity);
    }

    //region BusinessDoc
    public static FileContentModel getOrderDb(Context context) throws IOException, OldApiException, ConnectionException {
        OfficeApi officeApi=new OfficeApi(context);
        return officeApi.GetOrderDb();
    }

    public static List<OrderModel> getOrderByPaths(Context context, List<Integer> pathIds) throws IOException, OldApiException, ConnectionException {
        OfficeApi officeApi=new OfficeApi(context);
        return officeApi.getBusinessDocByPaths(pathIds);
    }

    public static List<OrderModel> getOrderByPersonIds(Context context, List<Integer> personIds) throws IOException, OldApiException, ConnectionException {
        OfficeApi officeApi=new OfficeApi(context);
        return officeApi.getBusinessDocByPersonIds(personIds);
    }
    public static List<OrderDetailModel> getOrderDetailByPersonIds(Context context, List<Integer> personIds) throws IOException, OldApiException, ConnectionException {
        OfficeApi officeApi=new OfficeApi(context);
        return officeApi.getBusinessDocDetailByPersonIds(personIds);
    }

    public static List<OrderDetailModel> getOrderDetailByPaths(Context context, List<Integer> pathIds) throws IOException, OldApiException, ConnectionException {
        OfficeApi officeApi=new OfficeApi(context);
        return officeApi.getBusinessDocDetailByPaths(pathIds);
    }

    public static List<CardIndexDetailModel> getOrderByPersonId(Context context, Integer PersonId) throws IOException {
        return CardIndexOldDb.getNewCardIndexByPersonId(context,PersonId);    }

    public static List<String> getOrderDatesByPersonId(Context context, Integer PersonId) throws IOException {
        //List<CardIndexDetailModel> cardIndexDetailModels = CardIndexOldDb.getAllCardIndises(context);
        return CardIndexOldDb.getCardIndexDatesByPersonId(context,PersonId);
    }

    public static void updateCustomersInfo(Context context,List<Integer> SelectedListIds, StageListener onStageListener) throws ConnectionException, OldApiException, IOException {
        int allProgress = 4, currentTaskIndex = 1;

        onStageListener.OnStageGoing(ProgressStage.UpdateCustomersBaseInfo, currentTaskIndex, allProgress);
        CustomerData.updateCustomersBaseInfo(context,CustomerBusiness.getBaseInfoByPaths(context,SelectedListIds));
        currentTaskIndex++;

        onStageListener.OnStageGoing(ProgressStage.UpdateCustomersBuy, currentTaskIndex, allProgress);
        CustomerData.updateCustomersBuy(context,CustomerBusiness.getBoughtSummaryByPath(context,SelectedListIds));
        currentTaskIndex++;

        onStageListener.OnStageGoing(ProgressStage.UpdateCustomersCheque, currentTaskIndex, allProgress);
        CustomerData.updateCustomersCheque(context,CustomerBusiness.getChequeByPath(context,SelectedListIds));
        currentTaskIndex++;

        onStageListener.OnStageGoing(ProgressStage.UpdateCustomersCredit, currentTaskIndex, allProgress);
        CustomerData.updateCustomersCredit(context,CustomerBusiness.getCreditByPath(context,SelectedListIds));
        currentTaskIndex++;
    }



}
