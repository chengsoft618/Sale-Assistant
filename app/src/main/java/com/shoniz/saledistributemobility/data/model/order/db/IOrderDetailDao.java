package com.shoniz.saledistributemobility.data.model.order.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.shoniz.saledistributemobility.data.model.order.OrderDetailEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderEntity;

import java.util.List;

import retrofit2.http.DELETE;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface IOrderDetailDao {
    @Insert
    public void insert(OrderDetailEntity entity);

    @Insert
    public void insert(OrderDetailEntity... entities);

    @Insert(onConflict = REPLACE)
    public void insert(List<OrderDetailEntity> entities);

    @Query("DELETE FROM orderdetail where OrderNo in (SELECT OrderNo From 'order' WHERE actionId <> 0)")
    public void deleteNotIssued();



    @Query("DELETE FROM orderdetail where OrderNo in (:ordersNo)")
    public void delete(List<Long> ordersNo);

    @Query("DELETE FROM orderdetail where OrderNo =:ordersNo")
    public void delete(Long ordersNo);

}
