package com.shoniz.saledistributemobility.data.api.retrofit;

import com.shoniz.saledistributemobility.framework.InOutError;

import retrofit2.Retrofit;

/**
 * Created by 921235 on 5/7/2018.
 */

public interface IRetrofitConfig {
    Retrofit getRetrofit() throws InOutError;
}
