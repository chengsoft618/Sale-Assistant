package com.shoniz.saledistributemobility.data.model.order.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.shoniz.saledistributemobility.data.model.order.OrderEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface IOrderDao {
    @Insert(onConflict = REPLACE)
    public void insert(OrderEntity entity);

    @Insert(onConflict = REPLACE)
    public void insert(OrderEntity... entities);




    @Insert(onConflict = REPLACE)
    public void insert(List<OrderEntity> entities);

    @Query("DELETE FROM 'order' WHERE OrderNo in (:ordersNos)")
    public void delete(List<Long> ordersNos );


    @Query("DELETE FROM 'order' WHERE OrderNo =:ordersNos")
    public void delete(Long ordersNos );


    @Query("UPDATE  'order'  SET Verifiable=0 where Verifiable=1 ")
    public void setVerifiable();


}
