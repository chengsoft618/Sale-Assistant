package com.shoniz.saledistributemobility.data.model.order.ordercomplete;


import com.google.gson.annotations.SerializedName;
import com.shoniz.saledistributemobility.data.MetaData;

public class OrderDetailCompleteData extends MetaData<Long> implements Comparable<OrderDetailCompleteData>{

    public Long OrderNo;
    public boolean IsBonus;
    public int TotalAmount;
    public int Qty;
    public int Price;
    public int UnitID;
    public int Shortcut;
    public String ProductName;
    public String UnitName;




    @Override
    public Long getId() {
        return OrderNo;
    }

    @Override
    public int compareTo(OrderDetailCompleteData orderDetailCompleteData) {

            if (orderDetailCompleteData == null) return 0;
            if (orderDetailCompleteData.Shortcut == Shortcut) return 0;
            if (Shortcut > orderDetailCompleteData.Shortcut) return 1;
            return -1;
        }

}
