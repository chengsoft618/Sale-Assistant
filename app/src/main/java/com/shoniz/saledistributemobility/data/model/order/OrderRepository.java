package com.shoniz.saledistributemobility.data.model.order;

//import com.shoniz.saledistributemobility.app.order.OrderModel;

import com.shoniz.saledistributemobility.data.model.path.db.IPathDao;
import com.shoniz.saledistributemobility.data.model.ShortcutAvailability;
import com.shoniz.saledistributemobility.data.model.order.api.IOrderApi;
import com.shoniz.saledistributemobility.data.model.order.db.IOrderDao;
import com.shoniz.saledistributemobility.data.model.order.db.IOrderDetailDao;
import com.shoniz.saledistributemobility.data.model.customer.db.IUnvisitedCustomerReasonDao;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.IOrderCompleteDataDao;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderCompleteData;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.OrderDetailCompleteData;
import com.shoniz.saledistributemobility.data.model.order.verifying.IOrderDataDao;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.view.entity.ReasonEntity;

import java.util.Hashtable;
import java.util.List;


public class OrderRepository implements IOrderRepository {


    private  IOrderApi orderApi;
    private   IOrderDataDao orderDataDao;
    private   IOrderDao orderDao;
    private  IOrderDetailDao orderDetailDao;
    private IUnvisitedCustomerReasonDao unvisitedCustomerReasonDao;
    private IPathDao pathDao;
    private ISettingRepository settingRepository;
    private IOrderCompleteDataDao orderCompleteDataDao;
    private Hashtable<Integer, Integer> shortcutsAvailability=new Hashtable<>();


    public OrderRepository(IOrderApi orderApi,
                           IOrderDataDao orderDataDao,
                           IOrderDao orderDao,
                           IOrderDetailDao orderDetailDao,
                           IUnvisitedCustomerReasonDao unvisitedCustomerReasonDao, IPathDao pathDao,
                           ISettingRepository settingRepository,
                           IOrderCompleteDataDao orderCompleteDataDao) {
        this.orderApi = orderApi;
        this.orderDataDao = orderDataDao;
        this.orderDao = orderDao;
        this.orderDetailDao = orderDetailDao;
        this.pathDao = pathDao;
        this.unvisitedCustomerReasonDao = unvisitedCustomerReasonDao;
        this.settingRepository = settingRepository;
        this.orderCompleteDataDao = orderCompleteDataDao;
    }


//    @Override
//    public List<OrderData> getOrdersToVerify(int userId) {
//        return orderDataDao.getVerifiableOrders(userId);
//    }
//
//    @Override
//    public List<OrderData> getVerifiedOrdersToCancel(int userId) {
//        return orderDataDao.getVerifiedOrdersToCancel(settingRepository.getEmployeeInfoEntity().EmployeeId);
//    }

//    @Override
//    public List<OrderEntity> getOrderNotIssued() throws InOutError {
//        return orderApi.getOrderVerification();
//    }

    @Override
    public List<OrderDetailEntity> getOrderDetailNotIssued() throws BaseException {
        return orderApi.getOrderDetailVerification();
    }

//    @Override
//    public List<OrderEntity> getOrderVerification() throws InOutError {
//        return orderApi.getOrderVerification();
//    }

    @Override
    public OrderCompleteData fetchOrderFromApi(long orderNo) throws BaseException {
        return orderApi.fetchOrder(orderNo);
    }

    @Override
    public List<OrderCompleteData> fetchOrderAllFromApi() throws BaseException {
        return orderApi.fetchOrderAll();
    }


//    @Override
//    public void sync() throws BaseException {
//        List<OrderEntity> orders = orderApi.getOrderVerification();
//        List<OrderDetailEntity> ordersDetail = orderApi.getOrderDetailVerification();
//
//        List<Long> orderNos = new ArrayList<>();
//        for (OrderEntity or : orders) {
//            orderNos.add(or.OrderNo);
//        }
//        orderDao.setVerifiable();
//
//        orderDao.delete(orderNos);
//        orderDetailDao.delete(orderNos);
//
//        orderDao.insert(orders);
//        orderDetailDao.insert(ordersDetail);
//
//        PathEntity pathEntity= pathDao.getActivePath();
//        List<UnvisitedCustomerReasonEntity> saleNotReason;
//        if(pathEntity != null) {
//            saleNotReason = orderApi.getSaleNotReason(pathEntity.PersianDate);
//            for (UnvisitedCustomerReasonEntity item : saleNotReason) {
//                if (item.IsSent) {
//                    unvisitedCustomerReasonDao.insert(item);
//                } else {
//                    item.NotSallReasonID = 1;
//                    item.IsSent = false;
//                    unvisitedCustomerReasonDao.insert(item);
//                }
//            }
//        }
//    }



    @Override
    public int isReasonSendAll() {
        return unvisitedCustomerReasonDao.isReasonSendAll();
    }

    @Override
    public void verifyOrder(List<Long> orderIds) throws BaseException {
        int roleId = settingRepository.getCurrentRoleId();
        orderApi.verify(orderIds, roleId);
        //sync();
    }

    @Override
    public void cancelVerify(Long orderId, String message) throws BaseException {

    }

    @Override
    public void rejectVerify(long orderId, String comment) throws BaseException {

    }
//
//    @Override
//    public void cancelVerify(Long orderIds,String message) throws BaseException {
//        orderApi.cancelVerification(orderIds,message);
//        //sync();
//    }
//
//    @Override
//    public void rejectVerify(long orderId, String comment) throws BaseException {
//        orderApi.reject(orderId, comment);
//    }

    @Override
    public void sendTo(long orderId, int userId, String comment,int roleId) throws BaseException {
        orderApi.sentTo(orderId, userId, comment,roleId);
    }




    @Override
    public List<OrderDetailCompleteData> fetchOrderDetailFromApi(long orderNo) throws BaseException {
        return orderApi.fetchOrderDetail(orderNo);
    }

    @Override
    public OrderCompleteData getOrderComplete(long orderNo) {
        return orderCompleteDataDao.getOrderComplete(orderNo);
    }



    @Override
    public List<OrderDetailCompleteData> getOrderDetailComplete(long orderNo) {
        return orderCompleteDataDao.getOrderDetailComplete(orderNo);
    }

    @Override
    public Hashtable<Integer, Integer> getShortcutsAvailability() {
        return shortcutsAvailability;
    }

    @Override
    public void syncShortcutsAvailability() throws BaseException {
        List<ShortcutAvailability> temp=orderApi.syncShortcutsAvailability();
        if(temp!=null && temp.size()>0){
            shortcutsAvailability.clear();
            for (ShortcutAvailability item:temp) {
                shortcutsAvailability.put(item.Shortcut,item.AvailabilityStatus);
            }
        }
    }




}
