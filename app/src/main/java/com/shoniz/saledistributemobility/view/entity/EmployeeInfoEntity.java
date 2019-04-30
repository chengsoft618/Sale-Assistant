package com.shoniz.saledistributemobility.view.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

import com.shoniz.saledistributemobility.data.BaseEntity;
import com.shoniz.saledistributemobility.utility.StringHelper;

@Entity(tableName = "EmployeeInfo", primaryKeys = "EmployeeId")
public class EmployeeInfoEntity extends BaseEntity {

    public int EmployeeId;
    public String EmployeeName;
    public String Tel;
    public String Photo;

    @Ignore
    public byte[] getPhotoBytes() {
        return StringHelper.getByteFromBase64(Photo);
    }
}
