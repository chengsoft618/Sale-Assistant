package com.shoniz.saledistributemobility.data.model.message;

import android.arch.persistence.room.Embedded;

import com.shoniz.saledistributemobility.data.MetaData;
import com.shoniz.saledistributemobility.data.model.app.BranchEntity;

public class MessageData extends MetaData<Integer> {

    public MessageData(MessageEntity messageEntity) {
        this.messageEntity = messageEntity;
    }

    @Embedded
    public MessageEntity messageEntity;



    @Override
    public Integer getId() {
        return messageEntity.SendID;
    }
}
