package com.shoniz.saledistributemobility.view.customer;

import com.shoniz.saledistributemobility.data.model.location.LocationEntity;

public class SendRequestModel {
    public int PersonID;
    public int addressID;
    public long OrderNo;
    public ActionSendType actionSendType;
    public int NotSallReasonID;
    public LocationEntity location;
    public SendRequestModel(int personID,ActionSendType actionSendType){
        this.actionSendType =actionSendType;
        this.PersonID=personID;
        this.addressID=addressID;
    }


    public SendRequestModel(int personID,ActionSendType actionSendType,Long orderNo){
        this.actionSendType =actionSendType;
        this.PersonID=personID;
        this.OrderNo= orderNo;
        this.addressID= 1;
    }

}
