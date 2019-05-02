package com.shoniz.saledistributemobility.data.model.order.verifying;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.shoniz.saledistributemobility.data.model.order.OrderData;

import java.util.List;

@Dao
public interface IOrderDataDao {

//    @Query("SELECT o.*,p.*,cb.PersonName cb_PersonName,cb.PersonId cb_PersonId, 0 RowNumber FROM `order` o " +
//            "INNER JOIN customerbase cb on o.PersonId = cb.PersonId " +
//            " INNER JOIN path p on p.PathCode = cb.PathCode " +
//            "WHERE o.IsIssued = 0 AND Verifiable=1 AND ((o.ActionId = 0 AND o.UserId = :userId) OR (o.ActionId = 1 AND o.UserId <> :userId) OR " +
//            "(o.ActionId = 3 AND o.UserId = :userId))")
//    public List<OrderData> getVerifiableOrders(int userId);
//
//    @Query("SELECT o.*,p.*,cb.PersonName cb_PersonName, cb.PersonId cb_PersonID, 0 RowNumber" +
//            " FROM `order` o INNER JOIN customerbase cb on o.PersonId = cb.PersonId " +
//            " INNER JOIN path p on p.PathCode = cb.PathCode" +
//            " WHERE Verifiable=1 AND (ActionId = 1 OR ActionId = 2 OR ActionId = 3) AND o.UserId = :userId")
//    public List<OrderData> getVerifiedOrdersToCancel(int userId);
}
