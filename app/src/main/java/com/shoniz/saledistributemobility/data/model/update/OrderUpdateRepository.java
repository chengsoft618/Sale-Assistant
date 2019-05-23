package com.shoniz.saledistributemobility.data.model.update;

import com.shoniz.saledistributemobility.R;
import com.shoniz.saledistributemobility.framework.repository.update.IOrderUpdateRepository;
import com.shoniz.saledistributemobility.data.model.path.db.IPathDao;
import com.shoniz.saledistributemobility.data.model.order.OrderDetailEntity;
import com.shoniz.saledistributemobility.data.model.order.OrderEntity;
import com.shoniz.saledistributemobility.data.model.customer.UnvisitedCustomerReasonEntity;
import com.shoniz.saledistributemobility.data.model.order.api.IOrderApi;
import com.shoniz.saledistributemobility.data.model.order.db.IOrderDao;
import com.shoniz.saledistributemobility.data.model.order.db.IOrderDetailDao;
import com.shoniz.saledistributemobility.data.model.customer.db.IUnvisitedCustomerReasonDao;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;
import com.shoniz.saledistributemobility.data.model.path.db.PathEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class OrderUpdateRepository extends UpdateBase implements IOrderUpdateRepository {




    @Inject
    public OrderUpdateRepository(CommonPackage commonPackage,
                                 IOrderApi orderApi,
                                 IOrderDao orderDao,
                                 IOrderDetailDao orderDetailOrder,
                                 IPathDao pathDao,
                                 IUnvisitedCustomerReasonDao unvisitedCustomerReasonDao) {
        this.commonPackage = commonPackage;

        this.orderApi = orderApi;
        this.orderDao = orderDao;
        this.orderDetailDao = orderDetailOrder;
        this.pathDao = pathDao;
        this.unvisitedCustomerReasonDao = unvisitedCustomerReasonDao;
    }

    @Override
    public void updateOrder() throws BaseException {
        setUpdateMessage(commonPackage.getContext().getString(R.string.get_order_list));
        List<OrderEntity> orders = orderApi.getOrderVerification();
        List<OrderDetailEntity> ordersDetail = orderApi.getOrderDetailVerification();

        List<Long> orderNos = new ArrayList<>();
        for (OrderEntity or : orders) {
            orderNos.add(or.OrderNo);
        }
        orderDao.setVerifiable();

        orderDao.delete(orderNos);
        orderDetailDao.delete(orderNos);

        orderDao.insert(orders);
        orderDetailDao.insert(ordersDetail);

        PathEntity pathEntity= pathDao.getActivePath();
        List<UnvisitedCustomerReasonEntity> saleNotReason;
        if(pathEntity != null) {
            saleNotReason = orderApi.getSaleNotReason(pathEntity.PersianDate);
            for (UnvisitedCustomerReasonEntity item : saleNotReason) {
                if (item.IsSent) {
                    unvisitedCustomerReasonDao.insert(item);
                } else {
                    item.NotSallReasonID = 1;
                    item.IsSent = false;
                    unvisitedCustomerReasonDao.insert(item);
                }
            }
        }
    }



    private CommonPackage commonPackage;
    private IOrderApi orderApi;
    private IOrderDao orderDao;
    private IOrderDetailDao orderDetailDao;
    private IPathDao pathDao;
    private IUnvisitedCustomerReasonDao unvisitedCustomerReasonDao;
}
