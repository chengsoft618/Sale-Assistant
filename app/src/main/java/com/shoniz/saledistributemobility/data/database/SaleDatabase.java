package com.shoniz.saledistributemobility.data.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;

import com.shoniz.saledistributemobility.data.model.path.db.IPathDao;
import com.shoniz.saledistributemobility.data.model.cardindex.ICardIndexDataDao;
import com.shoniz.saledistributemobility.data.model.coding.CodingEntity;
import com.shoniz.saledistributemobility.data.model.coding.ICodingDao;
import com.shoniz.saledistributemobility.data.model.customer.CustomerAddressEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBasicEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBoughtEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerChequeEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerCreditEntity;
import com.shoniz.saledistributemobility.data.model.customer.db.ICustomerDao;
import com.shoniz.saledistributemobility.data.model.order.OrderDetailEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderEntity;
import com.shoniz.saledistributemobility.data.model.order.UnvisitedCustomerReasonEntity;
import com.shoniz.saledistributemobility.data.model.order.db.IOrderDao;
import com.shoniz.saledistributemobility.data.model.order.db.IOrderDetailDao;
import com.shoniz.saledistributemobility.data.model.order.db.IUnvisitedCustomerReasonDao;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.IOrderCompleteDataDao;
import com.shoniz.saledistributemobility.data.model.order.verifying.IOrderDataDao;
import com.shoniz.saledistributemobility.data.model.update.db.IUpdateDao;
import com.shoniz.saledistributemobility.view.entity.CardIndexDetailEntity;
import com.shoniz.saledistributemobility.view.entity.CardIndexEntity;
import com.shoniz.saledistributemobility.view.entity.CategoryEntity;
import com.shoniz.saledistributemobility.view.entity.FileResourceEntity;
import com.shoniz.saledistributemobility.view.entity.ImageVersionEntity;
import com.shoniz.saledistributemobility.data.model.path.db.PathEntity;
import com.shoniz.saledistributemobility.view.entity.ProfileCategoryEntity;
import com.shoniz.saledistributemobility.view.entity.ReasonEntity;
import com.shoniz.saledistributemobility.view.entity.SubCategoryDetailEntity;
import com.shoniz.saledistributemobility.view.entity.SubCategoryEntity;

@Database(entities = {OrderEntity.class, OrderDetailEntity.class
        , CardIndexDetailEntity.class, CardIndexEntity.class
        , CategoryEntity.class, CodingEntity.class
        , CustomerBasicEntity.class, CustomerBoughtEntity.class
        , CustomerChequeEntity.class, CustomerCreditEntity.class
        , PathEntity.class, UnvisitedCustomerReasonEntity.class
        , ProfileCategoryEntity.class, SubCategoryDetailEntity.class
        , SubCategoryEntity.class, ReasonEntity.class
        , CustomerAddressEntity.class}, version = 1, exportSchema = false)

public abstract class SaleDatabase extends RoomDatabase{


    public abstract IPathDao getPathDao();
    public abstract IOrderDataDao getOrderDataDao();
    public abstract ICardIndexDataDao getCardIndexDataDao();
    public abstract IOrderDao getOrderDao();
    public abstract IOrderDetailDao getOrderDetailDao();
    public abstract IUnvisitedCustomerReasonDao getUnvisitedCustomerReasonDao();
    public abstract ICustomerDao getCustomerDao();
    public abstract IOrderCompleteDataDao getOrderCompleteDataDao();
    public abstract ICodingDao getCodingDao();
    public abstract IUpdateDao getUpdateDao();


    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
