package com.shoniz.saledistributemobility.data.database;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.shoniz.saledistributemobility.data.database.dao.IPathDao;
import com.shoniz.saledistributemobility.data.model.cardindex.CardIndexRepository;
import com.shoniz.saledistributemobility.data.model.cardindex.ICardIndexDataDao;
import com.shoniz.saledistributemobility.data.model.cardindex.ICardIndexRepository;
import com.shoniz.saledistributemobility.data.model.coding.ICodingDao;
import com.shoniz.saledistributemobility.data.model.customer.db.ICustomerDao;
import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.model.order.OrderRepository;
import com.shoniz.saledistributemobility.data.model.order.api.IOrderApi;
import com.shoniz.saledistributemobility.data.model.order.db.IOrderDao;
import com.shoniz.saledistributemobility.data.model.order.db.IOrderDetailDao;
import com.shoniz.saledistributemobility.data.model.order.db.IUnvisitedCustomerReasonDao;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.IOrderCompleteDataDao;
import com.shoniz.saledistributemobility.data.model.order.verifying.IOrderDataDao;
import com.shoniz.saledistributemobility.data.model.update.db.IUpdateDao;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SaleDatabaseModule {


    @Provides
    SaleDatabase providesSaleDatabase(Application application) {
        return Room.databaseBuilder(application, SaleDatabase.class, "SaleDatabase.db").allowMainThreadQueries().build();
    }


    @Provides
    IOrderDataDao provideOrderDataDao(SaleDatabase database) {
        return database.getOrderDataDao();
    }


    @Provides
    IOrderDao provideOrderDao(SaleDatabase database) {
        return database.getOrderDao();
    }


    @Provides
    IOrderDetailDao provideOrderDetailDao(SaleDatabase database) {
        return database.getOrderDetailDao();
    }


    @Provides
    ICardIndexDataDao provideCardIndexDataDao(SaleDatabase database) {
        return database.getCardIndexDataDao();
    }


    @Provides
    IUnvisitedCustomerReasonDao provideUnvisitedCustomerReasonDao(SaleDatabase database) {
        return database.getUnvisitedCustomerReasonDao();
    }


    @Provides
    ICustomerDao provideCustomerDao(SaleDatabase database) {
        return database.getCustomerDao();
    }

    @Provides
    IOrderCompleteDataDao provideOrderCompleteDataDao(SaleDatabase database) {
        return database.getOrderCompleteDataDao();
    }


    @Provides
    IPathDao providePathDao(SaleDatabase database) {
        return database.getPathDao();
    }

    @Provides
    ICodingDao provideCodingDao(SaleDatabase database) {
        return database.getCodingDao();
    }

    @Singleton
    @Provides
    IOrderRepository provideOrderRepository(IOrderApi orderApi,
                                            IOrderDataDao orderDataDao,
                                            IOrderDao orderDao,
                                            IOrderDetailDao orderDetailDao,
                                            IUnvisitedCustomerReasonDao unvisitedCustomerReasonDao, IPathDao pathDao,
                                            ISettingRepository settingRepository,  IOrderCompleteDataDao orderCompleteDataDao) {

        return new OrderRepository(orderApi,
                orderDataDao,
                orderDao,
                orderDetailDao,
                unvisitedCustomerReasonDao, pathDao,
                settingRepository,orderCompleteDataDao);
    }


    @Provides
    ICardIndexRepository provideCardIndexRepository(ICardIndexDataDao cardIndexDataDao) {

        return new CardIndexRepository(cardIndexDataDao);
    }

    @Provides
    IUpdateDao provideUpdateDao(SaleDatabase database) {
        return database.getUpdateDao();
    }

}
