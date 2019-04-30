package com.shoniz.saledistributemobility.data.model.coding;

import android.arch.persistence.db.SupportSQLiteQuery;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.SkipQueryVerification;

import com.shoniz.saledistributemobility.data.model.customer.CustomerAddressEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBasicEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBoughtEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerChequeEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerCreditEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderDetailEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderEntity;
import com.shoniz.saledistributemobility.view.customer.info.basic.CustomerBasicModel;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ICodingDao {

    @Query("SELECT * FROM Coding WHERE Shortcut=:shortcut")
    CodingEntity getProduct(String shortcut);


}

