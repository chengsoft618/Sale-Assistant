package com.shoniz.saledistributemobility.di.provider;

import com.shoniz.saledistributemobility.data.model.path.db.IPathDao;
import com.shoniz.saledistributemobility.data.model.customer.db.ICustomerDao;
import com.shoniz.saledistributemobility.data.model.message.db.IMessageDao;
import com.shoniz.saledistributemobility.data.model.order.db.IOrderDao;
import com.shoniz.saledistributemobility.data.model.order.db.IOrderDetailDao;
import com.shoniz.saledistributemobility.data.model.customer.db.IUnvisitedCustomerReasonDao;
import com.shoniz.saledistributemobility.data.model.order.ordercomplete.IOrderCompleteDataDao;
import com.shoniz.saledistributemobility.data.model.order.verifying.IOrderDataDao;
import com.shoniz.saledistributemobility.data.model.update.db.IUpdateDao;

public class DaoProvider__ {

    public IOrderDataDao getIOrderDataDao(){return orderDataDao;}
    public IOrderDao getIOrderDao(){return orderDao;}
    public IOrderDetailDao getIOrderDetailDao(){return orderDetailDao;}
    public IUnvisitedCustomerReasonDao getIUnvisitedCustomerReasonDao(){return unvisitedCustomerReasonDao;}
    public IPathDao getIPathDao(){return pathDao;}
    public IOrderCompleteDataDao getIOrderCompleteDataDao(){return orderCompleteDataDao;}
    public IUpdateDao getIUpdateDao(){return updateDao;}
    public ICustomerDao getICustomerDao(){return customerDao;}
    public IMessageDao getIMessageDao(){return messageDao;}

    //@Inject
    public DaoProvider__(IOrderDataDao orderDataDao, IOrderDao orderDao, IOrderDetailDao orderDetailDao, IUnvisitedCustomerReasonDao unvisitedCustomerReasonDao, IPathDao pathDao, IOrderCompleteDataDao orderCompleteDataDao, IUpdateDao updateDao, ICustomerDao customerDao, IMessageDao messageDao) {
        this.orderDataDao = orderDataDao;
        this.orderDao = orderDao;
        this.orderDetailDao = orderDetailDao;
        this.unvisitedCustomerReasonDao = unvisitedCustomerReasonDao;
        this.pathDao = pathDao;
        this.orderCompleteDataDao = orderCompleteDataDao;
        this.updateDao = updateDao;
        this.customerDao = customerDao;
        this.messageDao = messageDao;
    }

    private IOrderDataDao orderDataDao;
    private IOrderDao orderDao;
    private IOrderDetailDao orderDetailDao;
    private IUnvisitedCustomerReasonDao unvisitedCustomerReasonDao;
    private IPathDao pathDao;
    private IOrderCompleteDataDao orderCompleteDataDao;
    private IUpdateDao updateDao;
    private ICustomerDao customerDao;
    private IMessageDao messageDao;
}
