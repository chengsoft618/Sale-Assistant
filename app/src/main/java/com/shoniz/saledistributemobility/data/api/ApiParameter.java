package com.shoniz.saledistributemobility.data.api;

import com.shoniz.saledistributemobility.catalog.ResourceModel;
import com.shoniz.saledistributemobility.data.model.customer.UnvisitedReasonData;
import com.shoniz.saledistributemobility.view.customer.cardindex.CardIndexDto;
import com.shoniz.saledistributemobility.framework.CommonPackage;

import java.util.List;

public class ApiParameter {

    //Device device;
    //Utility utility;



    public ApiParameter(CommonPackage commonPackage) {
//        this.device = device;
//        this.utility = utility;

        Imei = commonPackage.getDevice().getIMEI();
        ApplicationVersion = commonPackage.getUtility().getVersionCode();
        VersionName=commonPackage.getUtility().getVersionName();
        DataBaseVersion="1.8.0.0";
    }


    public int GpsStatus;
    public int AddressID;
    public int ApplicationVersion;
    public long Imei;
    public long OrderNo;
    public String Message;
    public String VersionName;
    public String DataBaseVersion;
    public String GoogleId;
    public String FromDate;
    public String ToDate;
    public int Shortcut;
    public int MessageTypeId;
    public int VersionNo;
    public List<Long> Ids;
    public int PersonId;
    public int UserID;
    public String ReasonId;
    public int NotSaleReasonId;
    public double Latitude;
    public double Longitude;
    public float Accuracy;
    public String GoogleAddress;
    public String ErrorTitle;
    public String CPU;
    public String Release;
    public String ErrorTrace;
    public int ResourceFileId;
    public List<ResourceModel> ResourceFiles;
    public List<CardIndexDto.AmountModel> RequiresList;
    public List<CardIndexDto.AmountModel> ExistenceList;
    public List<UnvisitedReasonData> ReasonList;
    public boolean IsDelete ;
    public boolean IsNew ;
    public int ChequeDuration ;
    public int PaymentTypeID ;
    public String NeedDate ;
    public String SaleDesc ;
    public String AccDesc ;
    public String Description ;
    public int BatteryStatus ;
    public String Provider ;
    public int RoleId = 0;
    public int ActionId;
    public int SendId=Integer.MAX_VALUE;

}
