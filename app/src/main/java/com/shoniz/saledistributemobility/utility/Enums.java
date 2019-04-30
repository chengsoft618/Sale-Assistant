package com.shoniz.saledistributemobility.utility;

public class Enums {


    public enum DBName {
        base,
        customer,
        sale,
        user,
        CustomerBase,
        CustomerCredit,
        CustomerBuy,
        CustomerCheque,
        path,
        Order,
        CardIndex,
        Location,
        SaleDatabase
    }

    public enum ConnectionType {
        mobile,
        wifi
    }



    public enum RequestEnum {
        RequestsListPage,
        UnsentRequestsPage,
        UnvisitedCustomerPage
    }

}
