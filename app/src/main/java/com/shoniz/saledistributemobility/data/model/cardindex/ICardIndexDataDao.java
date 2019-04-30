package com.shoniz.saledistributemobility.data.model.cardindex;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;



import java.util.List;

@Dao
public interface ICardIndexDataDao {

    @Query("SELECT cid.*,c.NetWeight c_NetWeight,c.ProductDescription c_ProductDescription,c.ProductName c_ProductName,\n" +
            "c.SalePrice c_SalePrice,c.Shortcut c_Shortcut  FROM CardIndexDetail cid INNER JOIN Coding c on c.Shortcut=cid.Shortcut\n" +
            "WHERE cid.PersonId=:personId")
    public List<CardIndexDetailData> getCardIndexDetail(int personId);

    @Query("SELECT ci.*,cb.CustomerID c_CustomerID,\n" +
            "cb.PersonID c_PersonID,\n" +
            "cb.PersonName c_PersonName,\n" +
            "cb.ContactName c_ContactName,\n" +
            "cb.Address c_Address,\n" +
            "cb.TelNo c_TelNo,\n" +
            "cb.CellNo c_CellNo,\n" +
            "cb.OwnerType c_OwnerType,\n" +
            "cb.CustomerType c_CustomerType,\n" +
            "cb.CustomerTypeID c_CustomerTypeID,\n" +
            "cb.ActiveSeason c_ActiveSeason,\n" +
            "cb.MaxCredit c_MaxCredit,\n" +
            "cb.NotSaleReasonDate c_NotSaleReasonDate,\n" +
            "cb.NotSaleReasonDesc c_NotSaleReasonDesc,\n" +
            "cb.PathCode c_PathCode,\n" +
            "cb.PathName c_PathName,\n" +
            "cb.IsActive c_IsActive,\n" +
            "cb.CreditRemain c_CreditRemain,\n" +
            "cb.AccountRemain c_AccountRemain,\n" +
            "cb.ClassNames c_ClassNames,\n" +
            "cb.Latitude c_Latitude,\n" +
            "cb.Longitude c_Longitude,\n" +
            "cb.accuracy c_accuracy " +
            " FROM CardIndex ci INNER JOIN CustomerBase cb on ci.PersonID=cb.PersonID \n")
     List<CardIndexData> getAllCardIndices();
}




