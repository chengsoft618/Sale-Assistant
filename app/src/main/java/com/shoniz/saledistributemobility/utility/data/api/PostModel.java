package com.shoniz.saledistributemobility.utility.data.api;

import com.shoniz.saledistributemobility.catalog.ResourceModel;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexDto;
import com.shoniz.saledistributemobility.order.unvisited.ReasonDto;

import java.util.List;

public class PostModel {
    public int GpsStatus;
    public int ApplicationVersion;
    public long Imei;
    public long OrderNo;
    public String GoogleId;
    public String DataBaseVersion;
    public String FromDate;
    public String ToDate;
    public int Shortcut;
    public int VersionNo;
    public List<Integer> Ids;
    public int PersonId;
    public String ReasonId;
    public int NotSaleReasonId;
    public double Latitude;
    public double Longitude;
    public float Accuracy;
    public String GoogleAddress;
    public String ErrorTitle;
    public String ErrorTrace;
    public int ResourceFileId;
    public int AddressID;
    public List<ResourceModel> ResourceFiles;
    public List<CardIndexDto.AmountModel> RequiresList;
    public List<CardIndexDto.AmountModel> ExistenceList;
    public List<ReasonDto> ReasonList;
    public boolean IsDelete ;
    public boolean IsNew ;
    public int ChequeDuration ;
    public int PaymentTypeID ;
    public String NeedDate ;
    public String SaleDesc ;
    public String AccDesc ;
    public float BatteryStatus ;
    public String Provider ;

}
