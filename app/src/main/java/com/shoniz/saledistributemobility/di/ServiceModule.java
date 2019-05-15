package com.shoniz.saledistributemobility.di;

import com.shoniz.saledistributemobility.data.model.app.IAppRepository;
import com.shoniz.saledistributemobility.data.model.cardindex.ICardIndexRepository;
import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.repository.update.IBasicUpdateRepository;
import com.shoniz.saledistributemobility.framework.repository.update.ICategoryUpdateRepository;
import com.shoniz.saledistributemobility.framework.repository.update.ICustomerUpdateRepository;
import com.shoniz.saledistributemobility.framework.repository.update.IDatabaseUpdateRepository;
import com.shoniz.saledistributemobility.framework.repository.update.IOrderUpdateRepository;
import com.shoniz.saledistributemobility.framework.service.order.ICardIndexService;
import com.shoniz.saledistributemobility.framework.service.update.IAppUpdater;
import com.shoniz.saledistributemobility.service.order.CardIndexService;
import com.shoniz.saledistributemobility.service.update.AppUpdater;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {
    @Singleton
    @Provides
    ICardIndexService provideICardIndexService(ICardIndexRepository cardIndexRepository, CommonPackage commonPackage, ISettingRepository settingRepository, IOrderRepository orderRepository){
        return new CardIndexService(cardIndexRepository, commonPackage, settingRepository, orderRepository);
    }

    @Singleton
    @Provides
    IAppUpdater providesAppUpdater(CommonPackage commonPackage, ICategoryUpdateRepository categoryUpdateRepository, IDatabaseUpdateRepository databaseUpdateRepository, IBasicUpdateRepository basicUpdateRepository,
                                   IOrderUpdateRepository orderUpdateRepository, IAppRepository appRepository,
                                   ISettingRepository settingRepository, ICustomerUpdateRepository customerUpdateRepository){
        return  new AppUpdater(commonPackage, categoryUpdateRepository, databaseUpdateRepository,
                basicUpdateRepository, orderUpdateRepository, appRepository, settingRepository, customerUpdateRepository);
    }
}
