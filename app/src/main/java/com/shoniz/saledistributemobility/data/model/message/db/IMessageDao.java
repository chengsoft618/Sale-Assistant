package com.shoniz.saledistributemobility.data.model.message.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.shoniz.saledistributemobility.data.model.message.MessageEntity;

import java.util.List;

import retrofit2.http.DELETE;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface IMessageDao {

    @Insert(onConflict = REPLACE)
    void insert(MessageEntity... entities);

    @Insert(onConflict = REPLACE)
    void insert(List<MessageEntity> messageEntities);

    @Query("DELETE FROM Message where SendID= :SendId")
    void delete(int SendId);

    @Query("SELECT * FROM Message ORDER BY SendID DESC")
    List<MessageEntity> getMessages();

    @Query("SELECT IFNULL(MAX(SendID),"+Integer.MAX_VALUE+") FROM Message")
    int getMaxId();

    @Query("SELECT COUNT(1) FROM Message WHERE IsSeen=0")
    int unreadMessage();

    @Query("UPDATE  Message SET IsSeen=1 WHERE IsSeen=0")
    void readAll();
}
