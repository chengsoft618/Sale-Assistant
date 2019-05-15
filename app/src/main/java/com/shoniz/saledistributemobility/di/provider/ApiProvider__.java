package com.shoniz.saledistributemobility.di.provider;

import com.shoniz.saledistributemobility.data.model.app.api.IAppApi;
import com.shoniz.saledistributemobility.data.model.app.api.IShonizApi;
import com.shoniz.saledistributemobility.data.model.customer.api.ICustomerApi;
import com.shoniz.saledistributemobility.data.model.location.api.ILocationApi;
import com.shoniz.saledistributemobility.data.model.order.api.IOrderApi;
import com.shoniz.saledistributemobility.data.model.update.api.ICategoryUpdateApi;
import com.shoniz.saledistributemobility.data.model.update.api.IDatabaseUpdateApi;
import com.shoniz.saledistributemobility.data.sharedpref.api.ISettingApi;

import javax.inject.Inject;

public class ApiProvider__ {

    public IOrderApi getIOrderApi() {return orderApi;}
    public ICategoryUpdateApi getICategoryUpdateApi() {return categoryUpdateApi;}
    public IAppApi getIAppApi() {return appApi;}
    public IShonizApi getIShonizApi() {return shonizApi;}
    public ISettingApi getISettingApi() {return settingApi;}
    public ILocationApi getILocationApi() {return locationApi;}
    public ICustomerApi getICustomerApi() {return customerApi;}
    public IDatabaseUpdateApi getIDatabaseUpdateApi() {return databaseUpdateApi;}


    //@Inject
    public ApiProvider__(IOrderApi orderApi, ICategoryUpdateApi categoryUpdateApi, IAppApi appApi, IShonizApi shonizApi, ISettingApi settingApi, ILocationApi locationApi, ICustomerApi customerApi, IDatabaseUpdateApi databaseUpdateApi) {
        this.orderApi = orderApi;
        this.categoryUpdateApi = categoryUpdateApi;
        this.appApi = appApi;
        this.shonizApi = shonizApi;
        this.settingApi = settingApi;
        this.locationApi = locationApi;
        this.customerApi = customerApi;
        this.databaseUpdateApi = databaseUpdateApi;
    }

    private IOrderApi orderApi;
    private ICategoryUpdateApi categoryUpdateApi;
    private IAppApi appApi;
    private IShonizApi shonizApi;
    private ISettingApi settingApi;
    private ILocationApi locationApi;
    private ICustomerApi customerApi;
    private IDatabaseUpdateApi databaseUpdateApi;
}
