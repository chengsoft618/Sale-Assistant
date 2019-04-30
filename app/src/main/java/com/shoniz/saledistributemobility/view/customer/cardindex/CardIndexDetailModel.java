package com.shoniz.saledistributemobility.view.customer.cardindex;

/**
 * Created by ferdos.s on 12/28/2017.
 */

public class CardIndexDetailModel {
    public boolean isNew() {
        return (CardIndexHistory.QtyCarton1 + CardIndexHistory.QtyCarton2 + CardIndexHistory.QtyCarton3 +
                CardIndexHistory.QtyPackge1 + CardIndexHistory.QtyPackge2 + CardIndexHistory.QtyPackge3) == 0;
    }

    public static class Column {
        public static final String Order_NO = "OrderNo";
        public static final String Shortcut = "Shortcut";
        public static final String PersonId = "PersonId";
        public static final String Request_Carton = "RequestCarton";
        public static final String Request_Package = "RequestPackage";
        public static final String Existence_Carton = "ExistenceCarton";
        public static final String Existence_Package = "ExistencePackage";
        public static final String Product_Name = "ProductName";
        public static final String Carton_Price = "CartonPrice";
        public static final String Package_Price = "PackagePrice";

    }

    public Long OrderNo;
    public String Shortcut;
    public int PersonId;
    public int RequestCarton;
    public int RequestPackage;
    public int ExistenceCarton;
    public int ExistencePackage;
    public Boolean IsSelected = false;
    public String ProductName;
    public int CartonPrice;
    public int PackagePrice;

    public History CardIndexHistory = new History();

    public class History {
        public String Shortcut;
        public int QtyCarton1;
        public int QtyPackge1;
        public int QtyCarton2;
        public int QtyPackge2;
        public int QtyCarton3;
        public int QtyPackge3;
        public Long OrderNo1;
        public Long OrderNo2;
        public Long OrderNo3;
    }

    @Override
    protected CardIndexDetailModel clone() throws CloneNotSupportedException {
        CardIndexDetailModel model = new CardIndexDetailModel();
        model.OrderNo = this.OrderNo;
        model.Shortcut = this.Shortcut;
        model.PersonId = this.PersonId;
        model.RequestCarton = this.RequestCarton;
        model.RequestPackage = this.RequestPackage;
        model.ExistenceCarton = this.ExistenceCarton;
        model.ExistencePackage = this.ExistencePackage;
        model.IsSelected = this.IsSelected;
        model.ProductName = this.ProductName;
        model.CartonPrice = this.CartonPrice;
        model.PackagePrice = this.PackagePrice;
        model.CardIndexHistory = this.CardIndexHistory;

        return model;
    }

    public static class HistoryColumn {
        public static final String Shortcut = "Shortcut";
        public static final String QtyCarton1 = "QtyCarton1";
        public static final String QtyPackge1 = "QtyPackge1";
        public static final String QtyCarton2 = "QtyCarton2";
        public static final String QtyPackge2 = "QtyPackge2";
        public static final String QtyCarton3 = "QtyCarton3";
        public static final String QtyPackge3 = "QtyPackge3";
        public static final String OrderNo1 = "OrderNo1";
        public static final String OrderNo2 = "OrderNo2";
        public static final String OrderNo3 = "OrderNo3";
    }
}
