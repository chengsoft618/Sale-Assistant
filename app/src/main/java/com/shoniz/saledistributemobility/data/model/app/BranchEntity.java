package com.shoniz.saledistributemobility.data.model.app;


import android.arch.persistence.room.Entity;

import com.shoniz.saledistributemobility.data.BaseEntity;

@Entity(tableName = "Branch", primaryKeys = "BranchCode")
public class BranchEntity extends BaseEntity {
    public int BranchCode;
    public String BranchName;
    public String BranchPersianName;
    public String LanIp;
    public String WanIp1;//TODO Gateway
    public String WanIp2;//TODO Gateway
    public String ServicePort;
    public String ReportsPort;
    public int IsActive;
}
