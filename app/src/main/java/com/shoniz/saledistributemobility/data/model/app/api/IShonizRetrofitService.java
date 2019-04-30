package com.shoniz.saledistributemobility.data.model.app.api;

import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.model.app.BranchEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IShonizRetrofitService {
    @POST("GetBranchList")
    Call<List<BranchEntity>> getBranches(@Body ApiParameter parameter);
}
