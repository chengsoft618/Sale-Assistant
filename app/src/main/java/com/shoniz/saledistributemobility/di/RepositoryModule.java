package com.shoniz.saledistributemobility.di;

import android.content.Context;

import com.shoniz.saledistributemobility.data.model.path.IPathRepository;
import com.shoniz.saledistributemobility.data.model.path.PathRepository;
import com.shoniz.saledistributemobility.data.model.path.db.IPathDao;
import com.shoniz.saledistributemobility.data.model.app.AppRepository;
import com.shoniz.saledistributemobility.data.model.app.IAppRepository;
import com.shoniz.saledistributemobility.data.model.app.IShonizRepository;
import com.shoniz.saledistributemobility.data.model.app.ShonizRepository;
import com.shoniz.saledistributemobility.data.model.app.api.IAppApi;
import com.shoniz.saledistributemobility.data.model.app.api.IShonizApi;
import com.shoniz.saledistributemobility.data.model.cardindex.CardIndexRepository;
import com.shoniz.saledistributemobility.data.model.cardindex.ICardIndexDataDao;
import com.shoniz.saledistributemobility.data.model.cardindex.ICardIndexRepository;
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
import com.shoniz.saledistributemobility.data.model.location.db.ILocationDao;
import com.shoniz.saledistributemobility.data.model.log.ILogRepository;
import com.shoniz.saledistributemobility.data.model.log.LogRepository;
import com.shoniz.saledistributemobility.data.model.message.IMessageRepository;
import com.shoniz.saledistributemobility.data.model.message.MessageRepository;
import com.shoniz.saledistributemobility.data.model.message.api.IMessageApi;
import com.shoniz.saledistributemobility.data.model.message.db.IMessageDao;
import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.model.order.OrderRepository;
import com.shoniz.saledistributemobility.data.model.order.api.IOrderApi;
import com.shoniz.saledistributemobility.data.model.order.db.IOrderDao;
import com.shoniz.saledistributemobility.data.model.order.db.IOrderDetailDao;
import com.shoniz.saledistributemobility.data.model.customer.db.IUnvisitedCustomerReasonDao;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.IOrderCompleteDataDao;
import com.shoniz.saledistributemobility.data.model.order.verifying.IOrderDataDao;
import com.shoniz.saledistributemobility.data.model.update.BasicUpdateRepository;
import com.shoniz.saledistributemobility.data.model.update.CategoryUpdateRepository;
import com.shoniz.saledistributemobility.data.model.update.CustomerUpdateRepository;
import com.shoniz.saledistributemobility.data.model.update.DatabaseUpdateRepository;
import com.shoniz.saledistributemobility.data.model.update.OrderUpdateRepository;
import com.shoniz.saledistributemobility.data.model.update.api.ICategoryUpdateApi;
import com.shoniz.saledistributemobility.data.model.update.api.IDatabaseUpdateApi;
import com.shoniz.saledistributemobility.data.model.update.db.IUpdateDao;
import com.shoniz.saledistributemobility.data.model.update.db.IUpdateUserDbDao;
import com.shoniz.saledistributemobility.data.model.user.IUserRepository;
import com.shoniz.saledistributemobility.data.model.user.UserRepository;
import com.shoniz.saledistributemobility.data.model.user.api.IUserApi;
import com.shoniz.saledistributemobility.data.model.user.db.IRoleDao;
import com.shoniz.saledistributemobility.data.model.user.db.IUserDao;
import com.shoniz.saledistributemobility.data.model.user.db.IUserDataDao;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingPref;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.data.sharedpref.SettingRepository;
import com.shoniz.saledistributemobility.data.sharedpref.api.ISettingApi;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.repository.update.IBasicUpdateRepository;
import com.shoniz.saledistributemobility.framework.repository.update.ICategoryUpdateRepository;
import com.shoniz.saledistributemobility.framework.repository.update.ICustomerUpdateRepository;
import com.shoniz.saledistributemobility.framework.repository.update.IDatabaseUpdateRepository;
import com.shoniz.saledistributemobility.framework.repository.update.IOrderUpdateRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    IUserRepository providesUserRepository(IUserDataDao userDataDao, IUserDao userDao, IRoleDao roleDao, IUserApi userApi) {
        return new UserRepository(userDataDao, userDao, roleDao, userApi);
    }

    @Singleton
    @Provides
    ILogRepository providesLogRepository(Context context){
        return  new LogRepository(context);
    }

    @Singleton
    @Provides
    ILocationRepository providesLocationRepository(ILocationDao locationDao, ILocationApi locationApi){
        return  new LocationRepository(locationDao,locationApi);
    }

    @Singleton
    @Provides
    ISettingRepository providesSettingRepository(ISettingApi settingApi, ISettingPref settingPref){
        return  new SettingRepository( settingApi, settingPref);
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
    IMessageRepository providesMessageRepository(IMessageApi messageApi, IMessageDao messageDao){
        return  new MessageRepository(messageApi,messageDao);
    }


    @Singleton
    @Provides
    ICustomerRepository providesCustomerRepository(ICustomerApi customerApi, ICustomerDao customerDao,
                                                   CommonPackage commonPackage, IUnvisitedCustomerReasonDao unvisitedCustomerReasonDao){
        return  new CustomerRepository(customerApi, customerDao, commonPackage, unvisitedCustomerReasonDao);
    }

    @Singleton
    @Provides
    ICodingRepository providesCodingRepository(ICodingDao codingDao, CommonPackage commonPackage){
        return  new CodingRepository(codingDao, commonPackage);
    }

    @Singleton
    @Provides
    IDatabaseUpdateRepository providesDatabaseUpdateRepository(CommonPackage commonPackage, IDatabaseUpdateApi api){
        return  new DatabaseUpdateRepository(commonPackage, api);
    }

    @Singleton
    @Provides
    ICategoryUpdateRepository providesCategoryUpdateRepository(CommonPackage commonPackage, ICategoryUpdateApi api,
                                                               IUpdateDao updateDao, IUpdateUserDbDao updateUserDbDao){
        return  new CategoryUpdateRepository(commonPackage, api, updateDao, updateUserDbDao);
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
    IBasicUpdateRepository providesBasicUpdateRepository(IUserRepository userRepository, ISettingRepository settingRepository, IAppApi appApi){
        return  new BasicUpdateRepository(userRepository, settingRepository, appApi);
    }

    @Singleton
    @Provides
    IOrderRepository provideOrderRepository(IOrderApi orderApi,
                                            IOrderDataDao orderDataDao,
                                            IOrderDao orderDao,
                                            IOrderDetailDao orderDetailDao,
                                            IUnvisitedCustomerReasonDao unvisitedCustomerReasonDao, IPathDao pathDao,
                                            ISettingRepository settingRepository, IOrderCompleteDataDao orderCompleteDataDao) {

        return new OrderRepository(orderApi,
                orderDataDao,
                orderDao,
                orderDetailDao,
                unvisitedCustomerReasonDao, pathDao,
                settingRepository,orderCompleteDataDao);
    }

    @Singleton
    @Provides
    ICardIndexRepository provideCardIndexRepository(ICardIndexDataDao cardIndexDataDao, CommonPackage commonPackage) {

        return new CardIndexRepository(cardIndexDataDao);
    }

    @Singleton
    @Provides
    ICustomerUpdateRepository provideCustomerUpdateRepository(CommonPackage commonPackage, ICustomerDao customerDao, ICustomerApi customerApi) {
        return new CustomerUpdateRepository(commonPackage, customerDao, customerApi);
    }

    @Singleton
    @Provides
    IPathRepository providePathRepository(IPathDao pathDao) {
        return new PathRepository(pathDao);
    }
}
