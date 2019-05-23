package com.shoniz.saledistributemobility.data.model.order.api;

import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.api.retrofit.IRetroCommand;
import com.shoniz.saledistributemobility.data.api.retrofit.RetroManager;
import com.shoniz.saledistributemobility.data.model.ShortcutAvailability;
import com.shoniz.saledistributemobility.data.model.order.OrderDetailEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderEntity;
import com.shoniz.saledistributemobility.data.model.customer.UnvisitedCustomerReasonEntity;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderDetailCompleteData;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.io.IOException;
import java.util.List;

public class OrderRetrofit implements IOrderApi {

    ApiParameter apiParameter;
    CommonPackage commonPackage;


    public OrderRetrofit(ApiParameter apiParameter, CommonPackage commonPackage) {
        this.apiParameter = apiParameter;

        this.commonPackage = commonPackage;
    }

    @Override
    public List<OrderEntity> getOrderVerification() throws BaseException {
        IRetroCommand<List<OrderEntity>, IOrderRetrofitService> command = new IRetroCommand<List<OrderEntity>, IOrderRetrofitService>() {
            @Override
            public List<OrderEntity> exec(IOrderRetrofitService service) throws IOException {
                apiParameter.RoleId = commonPackage.getSettingPref().getCurrentRoleId();
                return service.getNotIssuedOrders(apiParameter).execute().body();
            }
        };

        return new RetroManager<List<OrderEntity>, IOrderRetrofitService>(commonPackage).callOfficeApi(command
                , IOrderRetrofitService.class);
    }


    @Override
    public OrderCompleteData fetchOrder(long orderNo) throws BaseException {
        IRetroCommand<OrderCompleteData, IOrderRetrofitService> command = service -> {
            apiParameter.OrderNo = orderNo;
            return service.getOrder(apiParameter).execute().body();
        };
        return new RetroManager<OrderCompleteData, IOrderRetrofitService>(commonPackage).callOfficeApi(command
                , IOrderRetrofitService.class);
    }

    @Override
    public List<OrderCompleteData> fetchOrderAll() throws BaseException {
        IRetroCommand<List<OrderCompleteData>, IOrderRetrofitService> command = service ->
        {
            apiParameter.RoleId = commonPackage.getSettingPref().getCurrentRoleId();
            return service.getOrderAll(apiParameter).execute().body();
        };
        return new RetroManager<List<OrderCompleteData>, IOrderRetrofitService>(commonPackage).callOfficeApi(command
                , IOrderRetrofitService.class);
    }

    @Override
    public List<OrderDetailEntity> getOrderDetailVerification() throws BaseException {

        IRetroCommand<List<OrderDetailEntity>, IOrderRetrofitService> command = new IRetroCommand<List<OrderDetailEntity>, IOrderRetrofitService>() {
            @Override
            public List<OrderDetailEntity> exec(IOrderRetrofitService service) throws IOException {
                apiParameter.RoleId = commonPackage.getSettingPref().getCurrentRoleId();
                return service.getNotIssuedOrdersDetail(apiParameter).execute().body();
            }

        };

        return new RetroManager<List<OrderDetailEntity>, IOrderRetrofitService>(commonPackage).callOfficeApi(command
                , IOrderRetrofitService.class);
    }


    @Override
    public List<UnvisitedCustomerReasonEntity> getSaleNotReason(String persianDate) throws BaseException {

        apiParameter.FromDate = persianDate;
        apiParameter.RoleId = commonPackage.getSettingPref().getCurrentRoleId();
        IRetroCommand<List<UnvisitedCustomerReasonEntity>, IOrderRetrofitService> command = service -> service.getSaleNotReason(apiParameter).execute().body();
        return new RetroManager<List<UnvisitedCustomerReasonEntity>, IOrderRetrofitService>(commonPackage).callOfficeApi(command
                , IOrderRetrofitService.class);

    }


    @Override
    public void verify(final List<Long> ids, int roleId) throws BaseException {
        IRetroCommand<Object, IOrderRetrofitService> command = new IRetroCommand<Object, IOrderRetrofitService>() {
            @Override
            public Object exec(IOrderRetrofitService service) throws IOException {
                apiParameter.Ids = ids;
                apiParameter.RoleId = roleId;
                apiParameter.ActionId = 1;
                service.verify(apiParameter).execute().body();
                return null;
            }
        };

        new RetroManager<Object, IOrderRetrofitService>(commonPackage).callOfficeApi(command
                , IOrderRetrofitService.class);
    }

    @Override
    public void reject(final long id, final String comment) throws BaseException {
        IRetroCommand<Object, IOrderRetrofitService> command = new IRetroCommand<Object, IOrderRetrofitService>() {
            @Override
            public Object exec(IOrderRetrofitService service) throws IOException {
                apiParameter.OrderNo = id;
                apiParameter.Message = comment;
                apiParameter.RoleId = commonPackage.getSettingPref().getCurrentRoleId();
                service.reject(apiParameter).execute().body();
                return null;
            }
        };

        new RetroManager<Object, IOrderRetrofitService>(commonPackage).callOfficeApi(command
                , IOrderRetrofitService.class);
    }

    @Override
    public void sentTo(long id, int userId, String comment, int roleId) throws BaseException {
        IRetroCommand<Object, IOrderRetrofitService> command = new IRetroCommand<Object, IOrderRetrofitService>() {
            @Override
            public Object exec(IOrderRetrofitService service) throws IOException {
                apiParameter.OrderNo = id;
                apiParameter.UserID = userId;
                apiParameter.Message = comment;
                apiParameter.RoleId = roleId;
                service.sendTo(apiParameter).execute().body();
                return null;
            }
        };

        new RetroManager<Object, IOrderRetrofitService>(commonPackage).callOfficeApi(command
                , IOrderRetrofitService.class);
    }

    @Override
    public void cancelVerification(final Long id, String message) throws BaseException {
        IRetroCommand<Object, IOrderRetrofitService> command = new IRetroCommand<Object, IOrderRetrofitService>() {
            @Override
            public Object exec(IOrderRetrofitService service) throws IOException {
                apiParameter.OrderNo = id;
                apiParameter.Message = message;
                apiParameter.RoleId = commonPackage.getSettingPref().getCurrentRoleId();
                service.cancelVerification(apiParameter).execute().body();
                return null;
            }
        };

        new RetroManager<Object, IOrderRetrofitService>(commonPackage).callOfficeApi(command
                , IOrderRetrofitService.class);
    }


    @Override
    public List<OrderDetailCompleteData> fetchOrderDetail(long orderNo) throws BaseException {
        IRetroCommand<List<OrderDetailCompleteData>, IOrderRetrofitService> command = new IRetroCommand<List<OrderDetailCompleteData>, IOrderRetrofitService>() {
            @Override
            public List<OrderDetailCompleteData> exec(IOrderRetrofitService service) throws IOException {
                apiParameter.OrderNo = orderNo;
                apiParameter.RoleId = commonPackage.getSettingPref().getCurrentRoleId();
                return service.fetchOrderDetail(apiParameter).execute().body();
            }
        };

        return new RetroManager<List<OrderDetailCompleteData>, IOrderRetrofitService>(commonPackage).callOfficeApi(command
                , IOrderRetrofitService.class);
    }

    @Override
    public List<ShortcutAvailability> syncShortcutsAvailability() throws BaseException {
        IRetroCommand<List<ShortcutAvailability>, IOrderRetrofitService> command = service -> service.syncShortcutsAvailability(apiParameter).execute().body();

       return new RetroManager<List<ShortcutAvailability>, IOrderRetrofitService>(commonPackage).callOfficeApi(command
                , IOrderRetrofitService.class);
    }


}
