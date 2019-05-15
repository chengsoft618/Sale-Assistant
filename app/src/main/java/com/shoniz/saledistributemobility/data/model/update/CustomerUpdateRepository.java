package com.shoniz.saledistributemobility.data.model.update;

import com.shoniz.saledistributemobility.data.api.ApiNetworkException;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBasicEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerBoughtEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerChequeEntity;
import com.shoniz.saledistributemobility.data.model.customer.CustomerCreditEntity;
import com.shoniz.saledistributemobility.data.model.customer.api.ICustomerApi;
import com.shoniz.saledistributemobility.data.model.customer.db.ICustomerDao;
import com.shoniz.saledistributemobility.data.model.order.OrderDetailEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderEntity;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.framework.repository.update.ICustomerUpdateRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CustomerUpdateRepository extends UpdateBase implements ICustomerUpdateRepository {

    @Override
    public void updateWholeInfoOfPerson(int personId) throws BaseException {
        List<CustomerBasicEntity> listCustomerBasicEntity = null;
        listCustomerBasicEntity = customerApi.getCustomerBaseInfoById(personId);
        List<CustomerChequeEntity> listCustomerChequeEntity = customerApi.getEmployeeCustomerChequeById(personId);
        List<CustomerBoughtEntity> listCustomerBoughtEntity = customerApi.getEmployeeCustomerBoughtById(personId);
        List<CustomerCreditEntity> listCustomerCreditEntity = customerApi.getEmployeeCustomerCreditById(personId);
        List<OrderEntity> listOrderEntity = customerApi.getOrderByPersonId(personId);
        List<OrderDetailEntity> listOrderDetailEntity = customerApi.getOrderDetailByPersonId(personId);
        customerDao.insertEmployeeCustomerBaseInfoByPath(listCustomerBasicEntity);
        customerDao.insertEmployeeCustomerChequeByPath(listCustomerChequeEntity);
        customerDao.insertEmployeeCustomerBoughtByPath(listCustomerBoughtEntity);
        customerDao.insertEmployeeCustomerCreditByPath(listCustomerCreditEntity);
        customerDao.insertOrderByPath(listOrderEntity);
        customerDao.insertOrderDetailByPath(listOrderDetailEntity);
    }

    @Override
    public void updateWholeInfoOfPath(int pathId) throws BaseException {
        List<CustomerBasicEntity> listCustomerBasicEntity = null;

        listCustomerBasicEntity = customerApi.getEmployeeCustomerBaseInfoByPath(pathId);
        List<CustomerChequeEntity> listCustomerChequeEntity = customerApi.getEmployeeCustomerChequeByPath(pathId);
        List<CustomerBoughtEntity> listCustomerBoughtEntity = customerApi.getEmployeeCustomerBoughtByPath(pathId);
        List<CustomerCreditEntity> listCustomerCreditEntity = customerApi.getEmployeeCustomerCreditByPath(pathId);
        List<OrderEntity> listOrderEntity = customerApi.getOrderByPath(pathId);
        List<OrderDetailEntity> listOrderDetailEntity = customerApi.getOrderDetailByPath(pathId);
        clearPathInfo(pathId);
        customerDao.insertEmployeeCustomerBaseInfoByPath(listCustomerBasicEntity);
        customerDao.insertEmployeeCustomerChequeByPath(listCustomerChequeEntity);
        customerDao.insertEmployeeCustomerBoughtByPath(listCustomerBoughtEntity);
        customerDao.insertEmployeeCustomerCreditByPath(listCustomerCreditEntity);
        customerDao.insertOrderByPath(listOrderEntity);
        customerDao.insertOrderDetailByPath(listOrderDetailEntity);

    }

    private void clearPathInfo(int pathId) {
        List<Integer> paths = new ArrayList<>(1);
        paths.add(pathId);
        List<Integer> pathsCustomerIds = customerDao.getCustomersIdsByPath(paths);
        List<Long> pathsOrders = customerDao.getOrderNoByPersonsIds(pathsCustomerIds);
        customerDao.deleteEmployeeCustomerBaseInfoByPath(paths);
        customerDao.deleteEmployeeCustomerBoughtByPath(pathsCustomerIds);
        customerDao.deleteEmployeeCustomerCreditByPath(pathsCustomerIds);
        customerDao.deleteEmployeeCustomerChequeByPath(pathsCustomerIds);
        customerDao.deleteOrderByPath(pathsCustomerIds);
        customerDao.deleteOrderDetailByPath(pathsOrders);
    }

    @Inject
    public CustomerUpdateRepository(CommonPackage commonPackage, ICustomerDao customerDao, ICustomerApi customerApi) {
        this.commonPackage = commonPackage;
        this.customerDao = customerDao;
        this.customerApi = customerApi;
    }

    private CommonPackage commonPackage;
    private ICustomerDao customerDao;
    private ICustomerApi customerApi;

}
