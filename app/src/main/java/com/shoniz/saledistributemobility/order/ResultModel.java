package com.shoniz.saledistributemobility.order;

import com.shoniz.saledistributemobility.order.detail.OrderDetailModel;

import java.util.List;

/**
 * Created by aghazadeh.a on 1/23/2018.
 */

public class ResultModel extends ResultBaseModel {

    public long OrderNo ;
    public long OrderSerial ;
    public boolean CanInvoiceIssue ;
    public String RegDate ;
    public int PersonID ;
    public int TotalAmount ;
    public boolean IsIssued ;
    public String AccDesc ;
    public String SalesDesc ;
    public int ChequeDuration ;
    public int InvoiceRemains ;
    public int OrderWeight ;
    public int OrderNetWeight ;
    public String OrderStatus ;
    public int OrderTypeID ;
    public int Varity ;
    public List<OrderDetailModel> Detail;
    public String Response ;
    public int SenderId ;
    public Long ActionDate;
    public int ActionId;
    public String Comment;
    public long NeededCreditAmount;
    public String ResponseDoc;
    public int IssuePrintedTime;



    public int UserId;
}
