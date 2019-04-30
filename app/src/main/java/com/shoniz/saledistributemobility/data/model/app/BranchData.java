package com.shoniz.saledistributemobility.data.model.app;

import android.arch.persistence.room.Embedded;

import com.shoniz.saledistributemobility.data.MetaData;

public class BranchData extends MetaData<Integer> {

    @Embedded
    public BranchEntity branchEntity;


    @Override
    public Integer getId() {
        return branchEntity.BranchCode;
    }
}
