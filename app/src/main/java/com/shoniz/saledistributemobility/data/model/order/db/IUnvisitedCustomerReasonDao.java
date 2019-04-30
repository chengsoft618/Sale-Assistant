package com.shoniz.saledistributemobility.data.model.order.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.shoniz.saledistributemobility.data.model.order.OrderEntity;
import com.shoniz.saledistributemobility.data.model.order.UnvisitedCustomerReasonEntity;
import com.shoniz.saledistributemobility.order.unvisited.ReasonDto;
import com.shoniz.saledistributemobility.view.entity.ReasonEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface IUnvisitedCustomerReasonDao {



    @Insert(onConflict = REPLACE)
    public void insert(UnvisitedCustomerReasonEntity entity);

    @Insert(onConflict = REPLACE)
    public void insert(UnvisitedCustomerReasonEntity... entities);

    @Insert(onConflict = REPLACE)
    public void insert(List<UnvisitedCustomerReasonEntity> entities);


    @Query("SELECT count(*) FROM\n" +
            "\t\t\tCustomerBase AS cb\n" +
            "\t\tLEFT JOIN UnvisitedCustomerReason uc ON cb.PersonID = uc.PersonId\n" +
            "\t\tLEFT JOIN 'Reason' r ON r.NotSallReasonID = ifnull(uc.NotSallReasonID, 1)\n" +
            "\t\tINNER JOIN path p ON cb.PathCode = p.PathCode\n" +
            "\t\tLEFT JOIN 'Order' o ON o.PersonID = cb.PersonID\n" +
            "\t\tAND o.IsIssued = 0\n" +
            "\t\tWHERE\n" +
            "\t\t\tp.IsActive = 1\n" +
            "\t\tAND cb.IsActive = 1\n" +
            "\t\tAND Ifnull(uc.IsSent, 0) = 0\n" +
            "\t\tAND cb.ClassNames = 'A' \n" +
            "\t\tAND O.OrderNo Is NULL OR O.OrderNo = 0")
    public int isReasonSendAll();

    @Query("SELECT * from Reason where NotSallReasonID > 0 AND IsActive = 1")
    List<ReasonEntity> getUnvisitingReasons();
}
