package com.shoniz.saledistributemobility.data.model.message.api;

import com.shoniz.saledistributemobility.data.model.message.MessageEntity;
import com.shoniz.saledistributemobility.framework.InOutError;
import com.shoniz.saledistributemobility.framework.exception.newexceptions.BaseException;

import java.util.List;

/**
 * Created by 921235 on 5/12/2018.
 */

public interface IMessageApi {
    List<MessageEntity> catchMessage() throws BaseException;
    List<MessageEntity>  catchLatestMessage(int sendId) throws BaseException;
    void SetMessageDelivered(List<Long> ids) throws BaseException;

    void sendCompletePathVisit(String desc) throws BaseException;;
}
