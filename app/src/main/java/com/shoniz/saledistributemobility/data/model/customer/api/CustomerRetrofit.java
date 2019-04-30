package com.shoniz.saledistributemobility.data.model.customer.api;

import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.api.retrofit.ApiException;
import com.shoniz.saledistributemobility.data.api.retrofit.IRetroCommand;
import com.shoniz.saledistributemobility.data.api.retrofit.RetroManager;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBasicEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBoughtEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerChequeEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerCreditEntity;
import com.shoniz.saledistributemobility.data.model.location.LocationEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderDetailEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderEntity;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRetrofit implements ICustomerApi {


    private ApiParameter apiParameter;
    private CommonPackage commonPackage;

    public CustomerRetrofit(ApiParameter apiParameter, CommonPackage commonPackage) {
        this.apiParameter = apiParameter;
        this.commonPackage = commonPackage;

    }

    @Override
    public void sendCustomerLocation(int customerId, LocationEntity locationEntity) throws BaseException {
        IRetroCommand<Void, ICustomerRetrofitService> command = new IRetroCommand<Void, ICustomerRetrofitService>() {
            @Override
            public Void exec(ICustomerRetrofitService service) throws IOException, ApiException {
                apiParameter.Accuracy = locationEntity.accuracy;
                apiParameter.Latitude = locationEntity.latitude;
                apiParameter.Longitude = locationEntity.longitude;
                apiParameter.PersonId = customerId;
                RetroManager.execCaller(service.setLocation(apiParameter));
                return null;
            }
        };
        new RetroManager<Void, ICustomerRetrofitService>(commonPackage).callOfficeApi(command
                , ICustomerRetrofitService.class);
    }

    @Override
    public List<CustomerBasicEntity> getEmployeeCustomerBaseInfoByPath(int pathId) throws BaseException {
        IRetroCommand<List<CustomerBasicEntity>, ICustomerRetrofitService> command = new IRetroCommand<List<CustomerBasicEntity>, ICustomerRetrofitService>() {
            @Override
            public List<CustomerBasicEntity> exec(ICustomerRetrofitService service) throws IOException, ApiException {
                List<Long> ids = new ArrayList<>(1);
                ids.add((long)pathId);
                apiParameter.Ids = ids;
                return RetroManager.execCaller(service.getEmployeeCustomerBaseInfoByPath(apiParameter)).body();
            }
        };
        return new RetroManager<List<CustomerBasicEntity>, ICustomerRetrofitService>(commonPackage).callOfficeApi(command
                , ICustomerRetrofitService.class);
    }

    @Override
    public List<CustomerChequeEntity> getEmployeeCustomerChequeByPath(int pathId) throws BaseException {
        IRetroCommand<List<CustomerChequeEntity>, ICustomerRetrofitService> command = new IRetroCommand<List<CustomerChequeEntity>, ICustomerRetrofitService>() {
            @Override
            public List<CustomerChequeEntity> exec(ICustomerRetrofitService service) throws IOException {
                List<Long> ids = new ArrayList<>(1);
                ids.add((long)pathId);
                apiParameter.Ids = ids;
                return service.getEmployeeCustomerChequeByPath(apiParameter).execute().body();
            }
        };
        return new RetroManager<List<CustomerChequeEntity>, ICustomerRetrofitService>(commonPackage).callOfficeApi(command
                , ICustomerRetrofitService.class);
    }

    @Override
    public List<CustomerBoughtEntity> getEmployeeCustomerBoughtByPath(int pathId) throws BaseException {
        IRetroCommand<List<CustomerBoughtEntity>, ICustomerRetrofitService> command = new IRetroCommand<List<CustomerBoughtEntity>, ICustomerRetrofitService>() {
            @Override
            public List<CustomerBoughtEntity> exec(ICustomerRetrofitService service) throws IOException, ApiException {
                List<Long> ids = new ArrayList<>(1);
                ids.add((long)pathId);
                apiParameter.Ids = ids;
                return  RetroManager.execCaller(service.getEmployeeCustomerBoughtByPath(apiParameter)).body();
            }
        };
        return new RetroManager<List<CustomerBoughtEntity>, ICustomerRetrofitService>(commonPackage).callOfficeApi(command
                , ICustomerRetrofitService.class);
    }

    @Override
    public List<CustomerCreditEntity> getEmployeeCustomerCreditByPath(int pathId) throws BaseException {
        IRetroCommand<List<CustomerCreditEntity>, ICustomerRetrofitService> command = new IRetroCommand<List<CustomerCreditEntity>, ICustomerRetrofitService>() {
            @Override
            public List<CustomerCreditEntity> exec(ICustomerRetrofitService service) throws IOException, ApiException {
                List<Long> ids = new ArrayList<>(1);
                ids.add((long)pathId);
                apiParameter.Ids = ids;
                return  RetroManager.execCaller(service.getEmployeeCustomerCreditByPath(apiParameter)).body() ;
            }
        };
        return new RetroManager<List<CustomerCreditEntity>, ICustomerRetrofitService>(commonPackage).callOfficeApi(command
                , ICustomerRetrofitService.class);
    }

    @Override
    public List<OrderEntity> getOrderByPath(int pathId) throws BaseException {
        IRetroCommand<List<OrderEntity>, ICustomerRetrofitService> command = new IRetroCommand<List<OrderEntity>, ICustomerRetrofitService>() {
            @Override
            public List<OrderEntity> exec(ICustomerRetrofitService service) throws IOException, ApiException {
                List<Long> ids = new ArrayList<>(1);
                ids.add((long)pathId);
                apiParameter.Ids = ids;
                return  RetroManager.execCaller(service.getOrderByPath(apiParameter)).body();
            }
        };
        return new RetroManager<List<OrderEntity>, ICustomerRetrofitService>(commonPackage).callOfficeApi(command
                , ICustomerRetrofitService.class);
    }

    @Override
    public List<OrderDetailEntity> getOrderDetailByPath(int pathId) throws BaseException {
        IRetroCommand<List<OrderDetailEntity>, ICustomerRetrofitService> command = new IRetroCommand<List<OrderDetailEntity>, ICustomerRetrofitService>() {
            @Override
            public List<OrderDetailEntity> exec(ICustomerRetrofitService service) throws IOException, ApiException {
                List<Long> ids = new ArrayList<>(1);
                ids.add((long)pathId);
                apiParameter.Ids = ids;
                return  RetroManager.execCaller(service.getOrderDetailByPath(apiParameter)).body();
            }
        };
        return new RetroManager<List<OrderDetailEntity>, ICustomerRetrofitService>(commonPackage).callOfficeApi(command
                , ICustomerRetrofitService.class);
    }


    @Override
    public List<CustomerBasicEntity> getCustomerBaseInfoById(int personId) throws BaseException {
        IRetroCommand<List<CustomerBasicEntity>, ICustomerRetrofitService> command = new IRetroCommand<List<CustomerBasicEntity>, ICustomerRetrofitService>() {
            @Override
            public List<CustomerBasicEntity> exec(ICustomerRetrofitService service) throws IOException, ApiException {
                List<Long> ids = new ArrayList<>(1);
                ids.add((long)personId);
                apiParameter.Ids = ids;
                return  RetroManager.execCaller(service.getCustomerBaseInfoById(apiParameter)).body();
            }
        };
        return new RetroManager<List<CustomerBasicEntity>, ICustomerRetrofitService>(commonPackage).callOfficeApi(command
                , ICustomerRetrofitService.class);
    }

    @Override
    public List<CustomerChequeEntity> getEmployeeCustomerChequeById(int personId) throws BaseException {
        IRetroCommand<List<CustomerChequeEntity>, ICustomerRetrofitService> command = new IRetroCommand<List<CustomerChequeEntity>, ICustomerRetrofitService>() {
            @Override
            public List<CustomerChequeEntity> exec(ICustomerRetrofitService service) throws IOException {
                List<Long> ids = new ArrayList<>(1);
                ids.add((long)personId);
                apiParameter.Ids = ids;
                return service.getEmployeeCustomerChequeById(apiParameter).execute().body();
            }
        };
        return new RetroManager<List<CustomerChequeEntity>, ICustomerRetrofitService>(commonPackage).callOfficeApi(command
                , ICustomerRetrofitService.class);
    }

    @Override
    public List<CustomerBoughtEntity> getEmployeeCustomerBoughtById(int personId) throws BaseException {
        IRetroCommand<List<CustomerBoughtEntity>, ICustomerRetrofitService> command = new IRetroCommand<List<CustomerBoughtEntity>, ICustomerRetrofitService>() {
            @Override
            public List<CustomerBoughtEntity> exec(ICustomerRetrofitService service) throws IOException, ApiException {
                List<Long> ids = new ArrayList<>(1);
                ids.add((long)personId);
                apiParameter.Ids = ids;
                return  RetroManager.execCaller(service.getEmployeeCustomerBoughtById(apiParameter)).body();
            }
        };
        return new RetroManager<List<CustomerBoughtEntity>, ICustomerRetrofitService>(commonPackage).callOfficeApi(command
                , ICustomerRetrofitService.class);
    }

    @Override
    public List<CustomerCreditEntity> getEmployeeCustomerCreditById(int personId) throws BaseException {
        IRetroCommand<List<CustomerCreditEntity>, ICustomerRetrofitService> command = new IRetroCommand<List<CustomerCreditEntity>, ICustomerRetrofitService>() {
            @Override
            public List<CustomerCreditEntity> exec(ICustomerRetrofitService service) throws IOException, ApiException {
                List<Long> ids = new ArrayList<>(1);
                ids.add((long)personId);
                apiParameter.Ids = ids;
                return  RetroManager.execCaller(service.getEmployeeCustomerCreditById(apiParameter)).body();
            }
        };
        return new RetroManager<List<CustomerCreditEntity>, ICustomerRetrofitService>(commonPackage).callOfficeApi(command
                , ICustomerRetrofitService.class);
    }

    @Override
    public List<OrderEntity> getOrderByPersonId(int personId) throws BaseException {
        IRetroCommand<List<OrderEntity>, ICustomerRetrofitService> command = new IRetroCommand<List<OrderEntity>, ICustomerRetrofitService>() {
            @Override
            public List<OrderEntity> exec(ICustomerRetrofitService service) throws IOException, ApiException {
                List<Long> ids = new ArrayList<>(1);
                ids.add((long)personId);
                apiParameter.Ids = ids;
                return  RetroManager.execCaller(service.getOrderByPersonId(apiParameter)).body();
            }
        };
        return new RetroManager<List<OrderEntity>, ICustomerRetrofitService>(commonPackage).callOfficeApi(command
                , ICustomerRetrofitService.class);
    }

    @Override
    public List<OrderDetailEntity> getOrderDetailByPersonId(int personId) throws BaseException {
        IRetroCommand<List<OrderDetailEntity>, ICustomerRetrofitService> command = new IRetroCommand<List<OrderDetailEntity>, ICustomerRetrofitService>() {
            @Override
            public List<OrderDetailEntity> exec(ICustomerRetrofitService service) throws IOException, ApiException {
                List<Long> ids = new ArrayList<>(1);
                ids.add((long)personId);
                apiParameter.Ids = ids;
                return  RetroManager.execCaller(service.getOrderDetailByPersonId(apiParameter)).body();
            }
        };
        return new RetroManager<List<OrderDetailEntity>, ICustomerRetrofitService>(commonPackage).callOfficeApi(command
                , ICustomerRetrofitService.class);
    }
}
