package com.shoniz.saledistributemobility.di;

import android.content.Context;

import com.shoniz.saledistributemobility.app.repository.update.IBasicUpdateRepository;
import com.shoniz.saledistributemobility.app.repository.update.ICategoryUpdateRepository;
import com.shoniz.saledistributemobility.app.repository.update.IDatabaseUpdateRepository;
import com.shoniz.saledistributemobility.app.repository.update.IOrderUpdateRepository;
import com.shoniz.saledistributemobility.app.service.update.IAppUpdater;
import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.database.dao.IPathDao;
import com.shoniz.saledistributemobility.data.model.app.AppRepository;
import com.shoniz.saledistributemobility.data.model.app.IAppRepository;
import com.shoniz.saledistributemobility.data.model.app.IShonizRepository;
import com.shoniz.saledistributemobility.data.model.app.ShonizRepository;
import com.shoniz.saledistributemobility.data.model.app.api.IAppApi;
import com.shoniz.saledistributemobility.data.model.app.api.IShonizApi;
import com.shoniz.saledistributemobility.data.model.app.api.ShonizApi;
import com.shoniz.saledistributemobility.data.model.coding.CodingRepository;
import com.shoniz.saledistributemobility.data.model.coding.ICodingDao;
import com.shoniz.saledistributemobility.data.model.coding.ICodingRepository;
import com.shoniz.saledistributemobility.data.model.customer.CustomerRepository;
import com.shoniz.saledistributemobility.data.model.customer.ICustomerRepository;
import com.shoniz.saledistributemobility.data.model.customer.api.ICustomerApi;
import com.shoniz.saledistributemobility.data.model.customer.db.ICustomerDao;
import com.shoniz.saledistributemobility.data.model.location.ILocationRepository;
import com.shoniz.saledistributemobility.data.model.location.LocationRepository;
import com.shoniz.saledistributemobility.data.model.location.api.ILocationApi;
import com.shoniz.saledistributemobility.data.model.location.api.LocationRetrofit;
import com.shoniz.saledistributemobility.data.model.location.db.ILocationDao;
import com.shoniz.saledistributemobility.data.model.log.ILogRepository;
import com.shoniz.saledistributemobility.data.model.log.LogRepository;
import com.shoniz.saledistributemobility.data.model.log.api.ILogApi;
import com.shoniz.saledistributemobility.data.model.log.api.LogRetrofit;
import com.shoniz.saledistributemobility.data.model.message.IMessageRepository;
import com.shoniz.saledistributemobility.data.model.message.MessageRepository;
import com.shoniz.saledistributemobility.data.model.message.api.IMessageApi;
import com.shoniz.saledistributemobility.data.model.message.api.MessageRetrofit;
import com.shoniz.saledistributemobility.data.model.message.db.IMessageDao;
import com.shoniz.saledistributemobility.data.model.order.api.IOrderApi;
import com.shoniz.saledistributemobility.data.model.order.db.IOrderDao;
import com.shoniz.saledistributemobility.data.model.order.db.IOrderDetailDao;
import com.shoniz.saledistributemobility.data.model.order.db.IUnvisitedCustomerReasonDao;
import com.shoniz.saledistributemobility.data.model.update.BasicUpdateRepository;
import com.shoniz.saledistributemobility.data.model.update.CategoryUpdateRepository;
import com.shoniz.saledistributemobility.data.model.update.DatabaseUpdateRepository;
import com.shoniz.saledistributemobility.data.model.update.OrderUpdateRepository;
import com.shoniz.saledistributemobility.data.model.update.api.BasicUpdateRetrofit;
import com.shoniz.saledistributemobility.data.model.update.api.CategoryUpdateRetrofit;
import com.shoniz.saledistributemobility.data.model.update.api.DatabaseUpdateRetrofit;
import com.shoniz.saledistributemobility.data.model.update.api.IBasicUpdateApi;
import com.shoniz.saledistributemobility.data.model.update.api.ICategoryUpdateApi;
import com.shoniz.saledistributemobility.data.model.update.api.IDatabaseUpdateApi;
import com.shoniz.saledistributemobility.data.model.update.api.IOrderUpdateApi;
import com.shoniz.saledistributemobility.data.model.update.api.OrderUpdateRetrofit;
import com.shoniz.saledistributemobility.data.model.update.db.IUpdateDao;
import com.shoniz.saledistributemobility.data.model.user.IUserRepository;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingPref;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.data.sharedpref.SettingPref;
import com.shoniz.saledistributemobility.data.sharedpref.SettingRepository;
import com.shoniz.saledistributemobility.data.sharedpref.api.ISettingApi;
import com.shoniz.saledistributemobility.data.sharedpref.api.SettingRetrofit;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.printer.BluetoothPrinter;
import com.shoniz.saledistributemobility.framework.service.update.AppUpdater;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 921235 on 5/6/2018.
 */

@Module
public  class AppModule {

    @Singleton
    @Provides
    ILogApi providesLogApi(CommonPackage commonPackage){
        return  new LogRetrofit(commonPackage);
    }

    @Singleton
    @Provides
    ILogRepository providesLogRepository(Context context){
        return  new LogRepository(context);
    }


    @Singleton
    @Provides
    ILocationApi providesLocationApi(ApiParameter apiParameter, CommonPackage commonPackage){
        return  new LocationRetrofit(apiParameter,commonPackage);
    }

    @Singleton
    @Provides
    ILocationRepository providesLocationRepository(ILocationDao locationDao,ILocationApi locationApi){
        return  new LocationRepository(locationDao,locationApi);
    }

    @Singleton
    @Provides
    ISettingApi providesSettingApi(ApiParameter apiParameter, CommonPackage commonPackage){
        return  new SettingRetrofit(apiParameter,commonPackage);
    }

    @Singleton
    @Provides
    ISettingPref providesSettingPref(Context context){
        return  new SettingPref(context);
    }

    @Singleton
    @Provides
    ISettingRepository providesSettingRepository(ISettingApi settingApi, ISettingPref settingPref){
        return  new SettingRepository( settingApi, settingPref);
    }

    @Provides
    @Singleton
    public static IShonizApi providesShonizApi(ApiParameter apiParameter, CommonPackage commonPackage){
        return new ShonizApi(apiParameter,commonPackage);
    }

    @Provides
    @Singleton
    public static IShonizRepository providesShonizRepository(IShonizApi shonizApi){
        return new ShonizRepository(shonizApi);
    }

    @Provides
    @Singleton
    IAppRepository providesAppRepository(ISettingRepository settingRepository, IAppApi appApi){
        return new AppRepository(settingRepository, appApi);
    }



    @Singleton
    @Provides
    IMessageApi providesMessageRetrofit(ApiParameter apiParameter, CommonPackage commonPackage){
        return  new MessageRetrofit(apiParameter,commonPackage);
    }

    @Singleton
    @Provides
    IMessageRepository providesMessageRepository(IMessageApi messageApi, IMessageDao messageDao){
        return  new MessageRepository(messageApi,messageDao);
    }


    @Singleton
    @Provides
    ICustomerRepository providesCustomerRepository(ICustomerApi customerApi, ICustomerDao customerDao, CommonPackage commonPackage){
        return  new CustomerRepository(customerApi, customerDao, commonPackage);
    }

    @Singleton
    @Provides
    ICodingRepository providesCodingRepository(ICodingDao codingDao, CommonPackage commonPackage){
        return  new CodingRepository(codingDao, commonPackage);
    }

    @Singleton
    @Provides
    BluetoothPrinter providesBluetoothPrinter(CommonPackage commonPackage){
        return  new BluetoothPrinter(commonPackage);
    }

    @Singleton
    @Provides
    IDatabaseUpdateApi providesDatabaseUpdateApi(ApiParameter param, CommonPackage commonPackage){
        return  new DatabaseUpdateRetrofit(param, commonPackage);
    }

    @Singleton
    @Provides
    IDatabaseUpdateRepository providesDatabaseUpdateRepository(CommonPackage commonPackage, IDatabaseUpdateApi api){
        return  new DatabaseUpdateRepository(commonPackage, api);
    }

    @Singleton
    @Provides
    ICategoryUpdateApi providesICategoryUpdateApi(ApiParameter param, CommonPackage commonPackage){
        return  new CategoryUpdateRetrofit(param, commonPackage);
    }

    @Singleton
    @Provides
    ICategoryUpdateRepository providesCategoryUpdateRepository(CommonPackage commonPackage, ICategoryUpdateApi api, IUpdateDao updateDao){
        return  new CategoryUpdateRepository(commonPackage, api, updateDao);
    }

    @Singleton
    @Provides
    IOrderUpdateApi providesIOrderUpdateApi(ApiParameter param, CommonPackage commonPackage){
        return  new OrderUpdateRetrofit(param, commonPackage);
    }

    @Singleton
    @Provides
    IOrderUpdateRepository providesOrderUpdateRepository(CommonPackage commonPackage,
                                                         IOrderApi orderApi,
                                                         IOrderDao orderDao,
                                                         IOrderDetailDao orderDetailDao,
                                                         IPathDao pathDao,
                                                         IUnvisitedCustomerReasonDao unvisitedCustomerReasonDao){
        return  new OrderUpdateRepository(commonPackage, orderApi, orderDao, orderDetailDao,
                pathDao, unvisitedCustomerReasonDao);
    }

    @Singleton
    @Provides
    IBasicUpdateApi providesIBasicUpdateApi(ApiParameter param, CommonPackage commonPackage){
        return  new BasicUpdateRetrofit(param, commonPackage);
    }

    @Singleton
    @Provides
    IBasicUpdateRepository providesBasicUpdateRepository(IUserRepository userRepository){
        return  new BasicUpdateRepository(userRepository);
    }

    @Singleton
    @Provides
    IAppUpdater providesAppUpdater(ICategoryUpdateRepository categoryUpdateRepository,
                                      IDatabaseUpdateRepository databaseUpdateRepository,
                                      IBasicUpdateRepository basicUpdateRepository,
                                      IOrderUpdateRepository orderUpdateRepository,
                                      IAppRepository appRepository,
                                      ISettingRepository settingRepository){
        return  new AppUpdater(categoryUpdateRepository,
                databaseUpdateRepository,
                basicUpdateRepository,
                orderUpdateRepository,
                appRepository,
                settingRepository);
    }


}
