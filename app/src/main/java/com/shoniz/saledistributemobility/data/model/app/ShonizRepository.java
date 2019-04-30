package com.shoniz.saledistributemobility.data.model.app;

import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.data.model.app.api.IShonizApi;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.ArrayList;
import java.util.List;

public class ShonizRepository implements IShonizRepository {

    IShonizApi shonizApi;

    public ShonizRepository(IShonizApi shonizApi) {
        this.shonizApi = shonizApi;
    }

    @Override
    public List<BranchEntity> getShonizBranchEntities() throws BaseException, ApiException {
        return shonizApi.getBranchEntities();
    }

    @Override
    public List<BranchData> getShonizBranchData() throws BaseException, ApiException {
        List<BranchEntity> entities = shonizApi.getBranchEntities();
        List<BranchData> branchDataList = new ArrayList<>();
        for(BranchEntity entity : entities){
            BranchData branchData = new BranchData();
            branchData.branchEntity = entity;
            branchDataList.add(branchData);
        }
        return branchDataList;
    }
}
