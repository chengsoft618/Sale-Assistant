package com.shoniz.saledistributemobility.data.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomDatabase;

import com.shoniz.saledistributemobility.data.model.location.db.ILocationDao;
import com.shoniz.saledistributemobility.data.model.app.BranchEntity;
import com.shoniz.saledistributemobility.data.model.message.db.IMessageDao;
import com.shoniz.saledistributemobility.view.entity.EmployeeInfoEntity;
import com.shoniz.saledistributemobility.view.entity.FileResourceEntity;
import com.shoniz.saledistributemobility.view.entity.ImageVersionEntity;
import com.shoniz.saledistributemobility.data.model.location.LocationEntity;
import com.shoniz.saledistributemobility.data.model.message.MessageEntity;
import com.shoniz.saledistributemobility.data.model.user.db.IUserDataDao;
import com.shoniz.saledistributemobility.data.model.user.RoleEntity;
import com.shoniz.saledistributemobility.data.model.user.UserEntity;
import com.shoniz.saledistributemobility.data.model.user.db.IRoleDao;
import com.shoniz.saledistributemobility.data.model.user.db.IUserDao;

@Database(entities = {EmployeeInfoEntity.class, FileResourceEntity.class
        , LocationEntity.class, MessageEntity.class, BranchEntity.class
        , ImageVersionEntity.class, UserEntity.class, RoleEntity.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase{

    public abstract IUserDao getUserDao();
    public abstract IRoleDao getRoleDao();
    public abstract IUserDataDao getUserDataDao();
    public abstract ILocationDao getLocationDao();
    public abstract IMessageDao getMessageDao();


    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }
}
