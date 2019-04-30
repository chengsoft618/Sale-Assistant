package com.shoniz.saledistributemobility.view.customer.cardindex;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ferdos.s on 1/16/2018.
 */

public class CardIndexDto {
    public int ChequeDuration;
    public int AddressID;
    public String NeedDate;
    public String AccDesc;
    public String SaleDesc;
    public int PersonId;
    public long OrderNo;
    public List<AmountModel> RequiresList = new ArrayList<>();
    public List<AmountModel> ExistenceList = new ArrayList<>();


    public void addToRequireList(String shortcut, int amount){
         RequiresList.add(new AmountModel(shortcut, amount));
    }
    public void addToExistence(String shortcut, int amount){
        ExistenceList.add(new AmountModel(shortcut, amount));
    }

   public class AmountModel{
       public AmountModel(String shortcut, int amount){
           Shortcut = shortcut;
           Amount = amount;
       }
       public String Shortcut;
       public int Amount;
   }

}
