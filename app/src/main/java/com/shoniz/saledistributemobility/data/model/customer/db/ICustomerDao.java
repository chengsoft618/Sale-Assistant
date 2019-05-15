package com.shoniz.saledistributemobility.data.model.customer.db;

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
import com.shoniz.saledistributemobility.data.model.customer.CustomerData;
import com.shoniz.saledistributemobility.data.model.customer.UnvisitedReasonData;
import com.shoniz.saledistributemobility.data.model.order.OrderDetailEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderEntity;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ICustomerDao {

    @Query("SELECT * FROM CustomerAddress WHERE PersonID=:personID")
    List<CustomerAddressEntity> getCustomerAddress(int personID);

    @Insert(onConflict = REPLACE)
    public void insert(List<OrderEntity> entities);

    @Query("DELETE FROM 'order' WHERE OrderNo in (:ordersNos)")
    public void deleteNotIssued(List<Long> ordersNos);

    @Query("Select PersonID FROM CustomerBase WHERE PathCode in (:pathsIds) ")
    public List<Integer> getCustomersIdsByPath(List<Integer> pathsIds);

    @Query("Select OrderNo FROM `Order` WHERE PersonID in (:personsIds)")
    public List<Long> getOrderNoByPersonsIds(List<Integer> personsIds);

    @Insert(onConflict = REPLACE)
    public void insertEmployeeCustomerBaseInfoByPath(List<CustomerBasicEntity> employeeCustomerBaseInfos);

    @Insert(onConflict = REPLACE)
    public void insertEmployeeCustomerChequeByPath(List<CustomerChequeEntity> employeeCustomerCheques);

    @Insert(onConflict = REPLACE)
    public void insertEmployeeCustomerBoughtByPath(List<CustomerBoughtEntity> employeeCustomerBoughts);

    @Insert(onConflict = REPLACE)
    public void insertEmployeeCustomerCreditByPath(List<CustomerCreditEntity> employeeCustomerCredits);

    @Insert(onConflict = REPLACE)
    public void insertOrderByPath(List<OrderEntity> orders);

    @Insert(onConflict = REPLACE)
    public void insertOrderDetailByPath(List<OrderDetailEntity> orderDetails);

    @Query("DELETE FROM CustomerBase WHERE PathCode in (:pathsIds) ")
    public void deleteEmployeeCustomerBaseInfoByPath(List<Integer> pathsIds);

    @Query("DELETE FROM CustomerCheque WHERE PersonID in (:personsIds) ")
    public void deleteEmployeeCustomerChequeByPath(List<Integer> personsIds);

    @Query("DELETE FROM CustomerBuy WHERE PersonID in (:personsIds)")
    public void deleteEmployeeCustomerBoughtByPath(List<Integer> personsIds);

    @Query("DELETE FROM CustomerCredit WHERE PersonID in (:personsIds)")
    public void deleteEmployeeCustomerCreditByPath(List<Integer> personsIds);

    @Query("DELETE FROM `Order` WHERE PersonID in (:personsIds)")
    public void deleteOrderByPath(List<Integer> personsIds);

    @Query("DELETE FROM OrderDetail WHERE  OrderNo in (:orderNos)")
    public void deleteOrderDetailByPath(List<Long> orderNos);

    @Query("SELECT COUNT(1) FROM CustomerBase WHERE PathCode = :pathId")
    public int pathCustomerCount(int pathId);

    @Query("SELECT * FROM CustomerBase Where personID =:personID")
    public CustomerBasicEntity getCustomerBase(int personID);

    @SkipQueryVerification
    @RawQuery
    List<CustomerData> getCustomerBaseInfoByPath(SupportSQLiteQuery quert);

    @Query("SELECT * FROM CustomerCredit Where personID =:personID")
    CustomerCreditEntity getCustomerCredit(int personID);

    @Query("Update CustomerBase SET LastVisitDays = 0 WHERE PersonId = :personID")
    void resetCustomerLastVisitingDays(int personID);

//    @Query("SELECT cb.PersonID, p.PersianDate, ifnull(uc.NotSallReasonID, 1) NotSallReasonID " +
//            " FROM CustomerBase cb" +
//            " INNER JOIN Path p ON p.PathCode = cb.PathCode" +
//            " LEFT JOIN UnvisitedCustomerReason uc ON uc.PersonId = cb.PersonID" +
//            " WHERE p.IsActive = 1 AND cb.PersonId = :personID")
//    UnvisitedReasonData getUnvisitedCustomerReasons(int personID);



}

