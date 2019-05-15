package com.shoniz.saledistributemobility.order;

import android.content.Context;
import android.database.SQLException;

import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.framework.exception.OldApiException;
import com.shoniz.saledistributemobility.framework.exception.ConnectionException;
import com.shoniz.saledistributemobility.order.sent.SentOrderModel;
import com.shoniz.saledistributemobility.order.unsent.UnsentOrderModel;
import com.shoniz.saledistributemobility.order.unvisited__.UnvisitedCustomerModel__;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexBusiness;
import com.shoniz.saledistributemobility.utility.data.api.OfficeApi;
import com.shoniz.saledistributemobility.order.detail.OrderDetailData;
import com.shoniz.saledistributemobility.view.customer.SendRequestModel;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexDto;
import com.shoniz.saledistributemobility.order.detail.OrderCompleteDetailModel;
import com.shoniz.saledistributemobility.order.unvisited__.ReasonModel__;

import java.io.IOException;
import java.util.List;

public class OrderBusiness {
    public static ResultModel SendOrder(Context context, SendRequestModel sendRequestModel) throws IOException, ConnectionException, OldApiException {
        OfficeApi shonizApi = new OfficeApi(context);
        CardIndexDto cardIndexDto = CardIndexBusiness.getCardIndexDto(context, sendRequestModel.PersonID);
        return shonizApi.sendOrder(cardIndexDto, sendRequestModel);
    }

    public static ResultModel DeleteOrder(Context context, SendRequestModel sendRequestModel) throws Exception {

        OfficeApi shonizApi = new OfficeApi(context);
        ResultModel res;
        try {
            res = shonizApi.deleteOrder(sendRequestModel);
            deleteOrder(context, sendRequestModel.OrderNo);
            return res;
        } catch (Exception e) {
            throw e;
        }
    }

//    public static void sendReasonAll(Context context, int personId) throws IOException, ConnectionException, OldApiException {
//        OfficeApi shonizApi = new OfficeApi(context);
//        UnvisitedReasonData reasonDto = CardIndexBusiness.getReasonDto(context, personId);
//        shonizApi.SetReasonAll(reasonDto);
//    }

//    public static List<UnvisitedCustomerReasonEntity> sendReasonAll(Context context, List<Integer> personIds) throws IOException, ConnectionException, OldApiException {
//        OfficeApi shonizApi = new OfficeApi(context);
//        List<UnvisitedReasonData> unvisitedReasonData = CardIndexBusiness.getReasonDto(context, personIds);
//        return shonizApi.SetReasonAll(unvisitedReasonData);
//    }

    public static void SaveOrderResult(Context context, ResultModel model) throws IOException {
        OrderModel orderModel = new OrderModel();
        orderModel.AccDesc = model.AccDesc;
        orderModel.SalesDesc = model.SalesDesc;
        orderModel.ChequeDuration = model.ChequeDuration;
        orderModel.InvoiceRemains = model.InvoiceRemains;
        orderModel.OrderTypeID = model.OrderTypeID;
        orderModel.PersonID = model.PersonID;
        orderModel.OrderWeight = model.OrderWeight;
        orderModel.OrderNetWeight = model.OrderNetWeight;
        orderModel.OrderStatus = model.OrderStatus;
        orderModel.TotalAmount = model.TotalAmount;
        orderModel.Varity = model.Varity;
        orderModel.OrderNo = model.OrderNo;
        orderModel.OrderSerial = model.OrderSerial;
        orderModel.RegDate = model.RegDate;
        orderModel.IsIssued = model.IsIssued;
        orderModel.SenderId=model.SenderId;
        orderModel.ActionId=model.ActionId;
        orderModel.ActionDate=model.ActionDate;
        orderModel.Comment=model.Comment;
        orderModel.UserId=model.UserId;
        orderModel.Verifiable=1;
        orderModel.NeededCreditAmount = model.NeededCreditAmount;
        orderModel.ResponseDoc = model.ResponseDoc;
        orderModel.IssuePrintedTime = model.IssuePrintedTime;
        OrderDataOld.insert(context, orderModel);
        OrderDetailData.insert(context, model.Detail);
    }

    public static List<OrderCompleteDetailModel> getOrderCompleteDetails(Context context, long orderNo) throws IOException {
        return OrderDetailData.getOrderCompleteDetails(context, orderNo);
    }

    public static OrderCompleteData getOrderCompleteHeader(Context context, long orderNo) throws IOException {
        return OrderDataOld.getOrderCompleteHeader(context, orderNo);
    }

    public static List<ReasonModel__> getUnvisitingReason(Context context) throws IOException, SQLException {
        return OrderDataOld.getUnvisitingReason(context);
    }

    public static SentOrderModel getOrderedRequest(Context context, long orderNo) throws Exception{
        return RequestListData.getOrderedRequest(context,orderNo);
    }
    public static void deleteOrder(Context context,long orderNo) throws Exception{
        RequestListData.deleteOrder(context,orderNo);
    }

    public static List<UnsentOrderModel> getUnsentRequestList(Context context) throws Exception{
        return RequestListData.getUnsentRequestList(context);
    }

    public static List<UnvisitedCustomerModel__> getUnvisitedCustomerList(Context context) throws Exception{
        return RequestListData.getUnvisitedCustomerList(context);
    }
}
