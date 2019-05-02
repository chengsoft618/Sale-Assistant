package com.shoniz.saledistributemobility.di;

import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.model.app.api.IShonizApi;
import com.shoniz.saledistributemobility.data.model.app.api.ShonizApi;
import com.shoniz.saledistributemobility.data.model.location.api.ILocationApi;
import com.shoniz.saledistributemobility.data.model.location.api.LocationRetrofit;
import com.shoniz.saledistributemobility.data.model.log.api.ILogApi;
import com.shoniz.saledistributemobility.data.model.log.api.LogRetrofit;
import com.shoniz.saledistributemobility.data.model.message.api.IMessageApi;
import com.shoniz.saledistributemobility.data.model.message.api.MessageRetrofit;
import com.shoniz.saledistributemobility.data.model.update.api.BasicUpdateRetrofit;
import com.shoniz.saledistributemobility.data.model.update.api.CategoryUpdateRetrofit;
import com.shoniz.saledistributemobility.data.model.update.api.DatabaseUpdateRetrofit;
import com.shoniz.saledistributemobility.data.model.update.api.IBasicUpdateApi;
import com.shoniz.saledistributemobility.data.model.update.api.ICategoryUpdateApi;
import com.shoniz.saledistributemobility.data.model.update.api.IDatabaseUpdateApi;
import com.shoniz.saledistributemobility.data.model.update.api.IOrderUpdateApi;
import com.shoniz.saledistributemobility.data.model.update.api.OrderUpdateRetrofit;
import com.shoniz.saledistributemobility.data.sharedpref.api.ISettingApi;
import com.shoniz.saledistributemobility.data.sharedpref.api.SettingRetrofit;
import com.shoniz.saledistributemobility.framework.CommonPackage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiModules {

    @Singleton
    @Provides
    IMessageApi providesMessageRetrofit(ApiParameter apiParameter, CommonPackage commonPackage){
        return  new MessageRetrofit(apiParameter,commonPackage);
    }

    @Singleton
    @Provides
    IDatabaseUpdateApi providesDatabaseUpdateApi(ApiParameter param, CommonPackage commonPackage){
        return  new DatabaseUpdateRetrofit(param, commonPackage);
    }



    @Singleton
    @Provides
    ICategoryUpdateApi providesICategoryUpdateApi(ApiParameter param, CommonPackage commonPackage){
        return  new CategoryUpdateRetrofit(param, commonPackage);
    }



    @Singleton
    @Provides
    IOrderUpdateApi providesIOrderUpdateApi(ApiParameter param, CommonPackage commonPackage){
        return  new OrderUpdateRetrofit(param, commonPackage);
    }



    @Singleton
    @Provides
    IBasicUpdateApi providesIBasicUpdateApi(ApiParameter param, CommonPackage commonPackage){
        return  new BasicUpdateRetrofit(param, commonPackage);
    }

    @Singleton
    @Provides
    ILogApi providesLogApi(CommonPackage commonPackage){
        return  new LogRetrofit(commonPackage);
    }




    @Singleton
    @Provides
    ILocationApi providesLocationApi(ApiParameter apiParameter, CommonPackage commonPackage){
        return  new LocationRetrofit(apiParameter,commonPackage);
    }


    @Singleton
    @Provides
    ISettingApi providesSettingApi(ApiParameter apiParameter, CommonPackage commonPackage){
        return  new SettingRetrofit(apiParameter,commonPackage);
    }



    @Provides
    @Singleton
    public static IShonizApi providesShonizApi(ApiParameter apiParameter, CommonPackage commonPackage){
        return new ShonizApi(apiParameter,commonPackage);
    }
}
