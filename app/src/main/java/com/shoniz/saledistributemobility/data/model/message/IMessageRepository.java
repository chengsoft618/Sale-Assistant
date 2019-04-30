package com.shoniz.saledistributemobility.data.model.message;

import com.shoniz.saledistributemobility.data.model.location.LocationEntity;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.List;

public interface IMessageRepository {
    void insert(MessageEntity... entities);
    void insert(List<MessageEntity> messageEntities);
    List<MessageEntity> getMessages();
    void delete(int SendId);


    List<MessageEntity> getMessageFromApi() throws BaseException;
    void sycLatestMessage() throws BaseException;
    void setMessageDelivered(List<Long> ids) throws BaseException;
    int unreadMessage();
    void readAll();

    void sendCompletePathVisit(String desc) throws BaseException;
}
