package com.shoniz.saledistributemobility.data.api;

import com.shoniz.saledistributemobility.data.model.customer.api.CustomerRetrofit;
import com.shoniz.saledistributemobility.data.model.customer.api.ICustomerApi;
import com.shoniz.saledistributemobility.data.model.order.api.IOrderApi;
import com.shoniz.saledistributemobility.data.model.order.api.OrderRetrofit;
import com.shoniz.saledistributemobility.data.model.user.api.IUserApi;
import com.shoniz.saledistributemobility.data.model.user.api.UserRetrofit;
import com.shoniz.saledistributemobility.framework.CommonPackage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModule {

    @Singleton
    @Provides
    IUserApi provideUserApi(ApiParameter parameter, CommonPackage commonPackage){
        return new UserRetrofit(parameter, commonPackage);
    }

    @Singleton
    @Provides
    IOrderApi provideOrderApi(ApiParameter parameter, CommonPackage commonPackage){
        return new OrderRetrofit(parameter, commonPackage);
    }

    @Singleton
    @Provides
    ICustomerApi provideCustomerApi(ApiParameter parameter, CommonPackage commonPackage){
        return new CustomerRetrofit(parameter, commonPackage);
    }

}
