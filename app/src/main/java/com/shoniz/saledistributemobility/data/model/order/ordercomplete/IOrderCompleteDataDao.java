package com.shoniz.saledistributemobility.data.model.order.ordercomplete;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.shoniz.saledistributemobility.data.model.order.OrderData;

import java.util.List;

@Dao
public interface IOrderCompleteDataDao {

    @Query("SELECT  DISTINCT  " +
            "  0 RowNumber,  " +
            " cb.PersonID,  " +
            " o.OrderNo,  " +
            " o.RegDate,  " +
            " o.OrderTypeID,  " +
            " o.TotalAmount,  " +
            " o.IsIssued,  " +
            " o.AccDesc,  " +
            " o.SalesDesc,  " +
            " o.Varity ,  " +
            " o.ChequeDuration,  " +
            " o.InvoiceRemains,  " +
            " o.OrderWeight,  " +
            " o.OrderStatus,  " +
            " o.OrderNetWeight,  " +
            " o.OrderWeight,  " +
            " cb.CustomerID,  " +
            " cb.PersonName CustomerName,  " +
            " cb.Address,  " +
            " cb.TelNo,  " +
            " cb.CellNo,  " +
            " cb.ContactName,  " +
            " cb.PathCode,  " +
            " cb.PathName,  " +
            " o.Varity,  " +
            " o.SenderId,  " +
            " o.ActionId,  " +
            " o.ActionDate,  " +
            " o.Comment,  " +
            " o.UserID,  " +
            " o.IssuePrintedTime, " +
            " o.ResponseDoc, " +
            " ifnull(p.IsActive, 0) InPath,  " +
            " (  " +
            "  SELECT  " +
            "   sum(Price)  " +
            "  FROM  " +
            "   OrderDetail od  " +
            "  WHERE  " +
            "   od.OrderNo =:orderNo  " +
            "  AND od.IsBonus = 1  " +
            " ) BonusAmount  " +
            "FROM  " +
            " CustomerBase AS cb  " +
            "LEFT JOIN Path p ON cb.PathCode = p.PathCode  " +
            "LEFT JOIN `order` o ON o.PersonID = cb.PersonID  " +
            "  WHERE o.OrderNo =:orderNo Limit 1")
    public OrderCompleteData getOrderComplete(long orderNo);

    @Query("SELECT  " +
            "   B.OrderNo,    " +
            "   B.IsBonus,    " +
            "   B.`Row` RowNumber,    " +
            "   B.Price TotalAmount ,    " +
            "   QTY,    " +
            "   Price,    " +
            "   UnitID UnitID,    " +
            "   B.Shortcut,    " +
            "   ProductName,    " +
            "   CASE    " +
            " WHEN UnitID = 1 THEN    " +
            "   'کارتن'    " +
            " ELSE    " +
            "   'بسته'    " +
            " END UnitName    " +
            " FROM    " +
            "   OrderDetail B    " +
            " INNER JOIN Coding C ON B.Shortcut = C.Shortcut    " +
            " WHERE OrderNo =:orderNo")
    public List<OrderDetailCompleteData> getOrderDetailComplete(long orderNo);
}
