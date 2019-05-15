package com.shoniz.saledistributemobility.data.database;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.shoniz.saledistributemobility.data.model.path.db.IPathDao;
import com.shoniz.saledistributemobility.data.model.cardindex.ICardIndexDataDao;
import com.shoniz.saledistributemobility.data.model.coding.ICodingDao;
import com.shoniz.saledistributemobility.data.model.customer.db.ICustomerDao;
import com.shoniz.saledistributemobility.data.model.order.db.IOrderDao;
import com.shoniz.saledistributemobility.data.model.order.db.IOrderDetailDao;
import com.shoniz.saledistributemobility.data.model.order.db.IUnvisitedCustomerReasonDao;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.IOrderCompleteDataDao;
import com.shoniz.saledistributemobility.data.model.order.verifying.IOrderDataDao;
import com.shoniz.saledistributemobility.data.model.update.db.IUpdateDao;

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



    @Provides
    IUpdateDao provideUpdateDao(SaleDatabase database) {
        return database.getUpdateDao();
    }



}
