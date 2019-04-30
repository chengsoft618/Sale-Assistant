package com.shoniz.saledistributemobility.data.model.message;

import com.shoniz.saledistributemobility.data.model.message.api.IMessageApi;
import com.shoniz.saledistributemobility.data.model.message.db.IMessageDao;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.List;

public class MessageRepository implements IMessageRepository {
    private IMessageApi messageApi;
    private IMessageDao messageDao;

    public MessageRepository(IMessageApi messageApi, IMessageDao messageDao) {
        this.messageApi = messageApi;
        this.messageDao = messageDao;
    }

    @Override
    public void insert(MessageEntity... entities) {
        messageDao.insert(entities);
    }

    @Override
    public void insert(List<MessageEntity> messageEntities) {
        messageDao.insert(messageEntities);
    }

    @Override
    public List<MessageEntity> getMessages() {
       return messageDao.getMessages();
    }

    @Override
    public void delete(int SendId) {
        messageDao.delete(SendId);
    }

    @Override
    public List<MessageEntity> getMessageFromApi() throws BaseException {
         return messageApi.catchMessage();
    }

    @Override
    public void sycLatestMessage() throws BaseException {
        int maxId=messageDao.getMaxId();
        messageDao.insert(messageApi.catchLatestMessage(maxId));
    }

    @Override
    public void setMessageDelivered(List<Long> ids) throws BaseException {
        messageApi.SetMessageDelivered(ids);
    }

    @Override
    public int unreadMessage() {
        return messageDao.unreadMessage();
    }

    @Override
    public void readAll() {
         messageDao.readAll();
    }

    @Override
    public void sendCompletePathVisit(String desc) throws BaseException {
        messageApi.sendCompletePathVisit(desc);
    }
}
