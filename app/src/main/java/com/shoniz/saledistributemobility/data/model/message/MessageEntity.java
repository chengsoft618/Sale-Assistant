package com.shoniz.saledistributemobility.data.model.message;

import android.arch.persistence.room.Entity;

import com.shoniz.saledistributemobility.data.BaseEntity;

@Entity(tableName = "Message",primaryKeys = "MessageId")
public class MessageEntity extends BaseEntity {

    public int MessageId;
    public int MessageTypeId;
    public int SendID;
    public String SendDateTime;
    public String ReceiveDateTime;
    public String MsgBody;
    public int SenderUserId;
    public int ReceiverUserId;
    public boolean IsSeen;
    public boolean IsRemoved;

}
