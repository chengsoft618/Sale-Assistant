package com.shoniz.saledistributemobility.data.model.customer;

import android.arch.persistence.db.SimpleSQLiteQuery;

import com.shoniz.saledistributemobility.data.model.customer.api.ICustomerApi;
import com.shoniz.saledistributemobility.data.model.customer.db.ICustomerDao;
import com.shoniz.saledistributemobility.data.model.order.UnvisitedCustomerReasonEntity;
import com.shoniz.saledistributemobility.data.model.order.db.IUnvisitedCustomerReasonDao;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.List;

public class CustomerRepository implements ICustomerRepository {
    private ICustomerApi customerApi;
    private ICustomerDao customerDao;
    IUnvisitedCustomerReasonDao unvisitedCustomerReasonDao;
    CommonPackage commonPackage;

    public CustomerRepository(ICustomerApi customerApi, ICustomerDao customerDao, CommonPackage commonPackage, IUnvisitedCustomerReasonDao unvisitedCustomerReasonDao){
        this.customerApi = customerApi;
        this.customerDao = customerDao;
        this.unvisitedCustomerReasonDao = unvisitedCustomerReasonDao;
        this.commonPackage = commonPackage;
    }

    @Override
    public List<CustomerAddressEntity> getCustomerAddress(int personID) {
        return customerDao.getCustomerAddress(personID);
    }



//    @Override
//    public void syncCustomerWholeInfoById(int personId) throws BaseException {
//        List<CustomerBasicEntity> listCustomerBasicEntity = null;
//        try {
//            listCustomerBasicEntity = customerApi.getCustomerBaseInfoById(personId);
//            List<CustomerChequeEntity> listCustomerChequeEntity = customerApi.getEmployeeCustomerChequeById(personId);
//            List<CustomerBoughtEntity> listCustomerBoughtEntity = customerApi.getEmployeeCustomerBoughtById(personId);
//            List<CustomerCreditEntity> listCustomerCreditEntity = customerApi.getEmployeeCustomerCreditById(personId);
//            List<OrderEntity> listOrderEntity = customerApi.getOrderByPersonId(personId);
//            List<OrderDetailEntity> listOrderDetailEntity = customerApi.getOrderDetailByPersonId(personId);
//            customerDao.insertEmployeeCustomerBaseInfoByPath(listCustomerBasicEntity);
//            customerDao.insertEmployeeCustomerChequeByPath(listCustomerChequeEntity);
//            customerDao.insertEmployeeCustomerBoughtByPath(listCustomerBoughtEntity);
//            customerDao.insertEmployeeCustomerCreditByPath(listCustomerCreditEntity);
//            customerDao.insertOrderByPath(listOrderEntity);
//            customerDao.insertOrderDetailByPath(listOrderDetailEntity);
//        } catch (InOutError inOutError) {
//            ApiNetworkException apiNetworkException = new ApiNetworkException(commonPackage, inOutError);
//            throw apiNetworkException;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public boolean isPathSync(int pathId) {
        return customerDao.pathCustomerCount(pathId) > 0;
    }

//    @Override
//    public void syncEmployeeWholeInfoByPath(int pathId) throws BaseException {
//        List<CustomerBasicEntity> listCustomerBasicEntity = null;
//        try {
//            listCustomerBasicEntity = customerApi.getEmployeeCustomerBaseInfoByPath(pathId);
//            List<CustomerChequeEntity> listCustomerChequeEntity = customerApi.getEmployeeCustomerChequeByPath(pathId);
//            List<CustomerBoughtEntity> listCustomerBoughtEntity = customerApi.getEmployeeCustomerBoughtByPath(pathId);
//            List<CustomerCreditEntity> listCustomerCreditEntity = customerApi.getEmployeeCustomerCreditByPath(pathId);
//            List<OrderEntity> listOrderEntity = customerApi.getOrderByPath(pathId);
//            List<OrderDetailEntity> listOrderDetailEntity = customerApi.getOrderDetailByPath(pathId);
//            clearPathInfo(pathId);
//            customerDao.insertEmployeeCustomerBaseInfoByPath(listCustomerBasicEntity);
//            customerDao.insertEmployeeCustomerChequeByPath(listCustomerChequeEntity);
//            customerDao.insertEmployeeCustomerBoughtByPath(listCustomerBoughtEntity);
//            customerDao.insertEmployeeCustomerCreditByPath(listCustomerCreditEntity);
//            customerDao.insertOrderByPath(listOrderEntity);
//            customerDao.insertOrderDetailByPath(listOrderDetailEntity);
//        } catch (InOutError inOutError) {
//            ApiNetworkException apiNetworkException = new ApiNetworkException(commonPackage, inOutError);
//            throw apiNetworkException;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public List<CustomerData> getCustomerBaseInfoByPath(int pathCode, boolean containInactiveCustomers, boolean containClassB){
            String isActive = " ";
            String classNames = " ";
            if (!containClassB) {
                classNames = " AND ClassNames='A'";
            }

            if (!containInactiveCustomers) {
                isActive = " AND IsActive=1 ";
            }

        String query = "SELECT DISTINCT \n" +
                "  cb.*, ifnull(p.OrderNO, 0) AS UnIssuedOrderNo,\n" +
                "  NeededCreditAmount,\n" +
                "  ifnull(ucr.NotSallReasonID, 0) NotSellReasonID,\n " +
                " (\n" +
                "    SELECT\n" +
                "        MAX(o1.RegDate)\n" +
                "     FROM\n" +
                "          \"Order\" o1 "+
                "       WHERE  o1.PersonID = cb.PersonID" +
                "  ) LastRegDate\n" +
                " FROM\n" +
                "  CustomerBase cb\n" +
                " LEFT JOIN UnvisitedCustomerReason ucr ON ucr.PersonId = cb.PersonID AND ucr.IsSent = 1 " +
                " LEFT JOIN (\n" +
                "  SELECT\n" +
                "    OrderNo,\n" +
                "    PersonID,\n" +
                "    NeededCreditAmount,\n" +
                "    IsIssued\n" +
                "  FROM\n" +
                "    \"Order\" o1\n" +
                " ) p ON p.PersonID = cb.PersonID\n" +

                " AND p.IsIssued = 0\n" +
                " WHERE\n" +
                "  PathCode =" +
                    pathCode + isActive + classNames +
                " ORDER BY cb.VisitOrder";
            SimpleSQLiteQuery simpleSQLiteQuery = new SimpleSQLiteQuery(query);
             List<CustomerData> customerBasicModels = customerDao.getCustomerBaseInfoByPath(simpleSQLiteQuery);
             return customerBasicModels;
    }

    @Override
    public CustomerBasicEntity getCustomerBase(int personID) {
        return customerDao.getCustomerBase(personID);
    }

    @Override
    public CustomerCreditEntity getCustomerCredit(int personID) {
        return customerDao.getCustomerCredit(personID);
    }

    @Override
    public void sendUnvisitedReason(UnvisitedReasonData unvisitedReasonData) throws BaseException {
        customerApi.sendUnvisitedCustomerReason(unvisitedReasonData);
    }

//    private void clearPathInfo(int pathId){
//        List<Integer> paths = new ArrayList<>(1);
//        paths.add(pathId);
//        List<Integer> pathsCustomerIds = customerDao.getCustomersIdsByPath(paths);
//        List<Long> pathsOrders = customerDao.getOrderNoByPersonsIds(pathsCustomerIds);
//        customerDao.deleteEmployeeCustomerBaseInfoByPath(paths);
//        customerDao.deleteEmployeeCustomerBoughtByPath(pathsCustomerIds);
//        customerDao.deleteEmployeeCustomerCreditByPath(pathsCustomerIds);
//        customerDao.deleteEmployeeCustomerChequeByPath(pathsCustomerIds);
//        customerDao.deleteOrderByPath(pathsCustomerIds);
//        customerDao.deleteOrderDetailByPath(pathsOrders);
//   }

    public void resetCustomerLastVisitingDays(int personID){
        customerDao.resetCustomerLastVisitingDays(personID);
    }

    @Override
    public void saveUnvisitingReason(UnvisitedCustomerReasonEntity unvisitedCustomerReasonEntity) {
        if(unvisitedCustomerReasonEntity != null)
            unvisitedCustomerReasonDao.insert(unvisitedCustomerReasonEntity);
    }

}
