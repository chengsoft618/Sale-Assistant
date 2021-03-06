package com.shoniz.saledistributemobility.data.database;

import com.shoniz.saledistributemobility.data.model.message.db.IMessageDao;
import com.shoniz.saledistributemobility.data.model.order.IOrderRepository;
import com.shoniz.saledistributemobility.data.model.update.CategoryUpdateRepository;
import com.shoniz.saledistributemobility.data.model.update.IAppUpdateRepository;
import com.shoniz.saledistributemobility.data.model.location.db.ILocationDao;
import com.shoniz.saledistributemobility.data.model.update.api.ICategoryUpdateApi;
import com.shoniz.saledistributemobility.data.model.update.db.IUpdateDao;
import com.shoniz.saledistributemobility.data.model.update.db.IUpdateUserDbDao;
import com.shoniz.saledistributemobility.data.model.user.db.IUserDataDao;
import com.shoniz.saledistributemobility.data.model.user.IUserRepository;
import com.shoniz.saledistributemobility.data.model.user.UserRepository;
import com.shoniz.saledistributemobility.data.model.user.api.IUserApi;
import com.shoniz.saledistributemobility.data.model.user.db.IRoleDao;
import com.shoniz.saledistributemobility.data.model.user.db.IUserDao;
import com.shoniz.saledistributemobility.framework.CommonPackage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UserDatabaseModule {



    @Singleton
    @Provides
    IUserDao providesUserDao(UserDatabase userDatabase) {
        return userDatabase.getUserDao();
    }

    @Provides
    IRoleDao providesRoleDao(UserDatabase userDatabase) {
        return userDatabase.getRoleDao();
    }

    @Provides
    IUserDataDao providesUserDataDao(UserDatabase userDatabase) {
        return userDatabase.getUserDataDao();
    }

    @Provides
    IUpdateUserDbDao providesIUpdateUserDbDao(UserDatabase userDatabase) {
        return userDatabase.getIUpdateUserDbDao();
    }








    @Provides
    ILocationDao providesLocationDao(UserDatabase userDatabase) {
        return userDatabase.getLocationDao();
    }





    @Provides
    IMessageDao providesMessageDao(UserDatabase userDatabase) {
        return userDatabase.getMessageDao();
    }


}
