package com.shoniz.saledistributemobility.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.shoniz.saledistributemobility.data.api.ApiParameter;
import com.shoniz.saledistributemobility.data.api.core.CoreApi;
import com.shoniz.saledistributemobility.data.api.core.ICoreApi;
import com.shoniz.saledistributemobility.data.api.download.FileDownloader;
import com.shoniz.saledistributemobility.data.api.download.IFileDownloader;
import com.shoniz.saledistributemobility.data.database.UserDatabase;
import com.shoniz.saledistributemobility.data.model.app.api.AppApi;
import com.shoniz.saledistributemobility.data.model.app.api.IAppApi;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingPref;
import com.shoniz.saledistributemobility.data.sharedpref.ISettingRepository;
import com.shoniz.saledistributemobility.framework.CommonPackage;
import com.shoniz.saledistributemobility.framework.Device;
import com.shoniz.saledistributemobility.framework.Utility;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class BaseModule {

    @Binds
    @Singleton
    abstract Context getContext(Application application);

    @Singleton
    @Provides
    public static Utility getUtility(Context context) {
        return new Utility(context);
    }

    @Singleton
    @Provides
    public static Device getDevice(Context context) {
        return new Device(context);
    }

    @Singleton
    @Provides
    public static ApiParameter providesApiParameter(CommonPackage commonPackage) {
        return new ApiParameter(commonPackage);
    }

    @Singleton
    @Provides
    public static UserDatabase providesUserDatabase(Application application) {
        return Room.databaseBuilder(application, UserDatabase.class, "UserDatabase.db").allowMainThreadQueries().build();
    }



    @Singleton
    @Provides
    public static IAppApi provideAppApi(ApiParameter apiParameter, CommonPackage commonPackage){
        return new AppApi(apiParameter,commonPackage);
    }

    @Singleton
    @Provides
    public static ICoreApi provideCoreApi(ApiParameter apiParameter, CommonPackage commonPackage, ISettingRepository settingRepository){
        return new CoreApi(apiParameter,commonPackage, settingRepository);
    }


    @Provides
    @Singleton
    public static CommonPackage providesCommonPackage(Device device, Utility utility, Context context,ISettingPref settingPref){
        return new CommonPackage(device, utility, context,settingPref);
    }

    @Provides
    @Singleton
    public static IFileDownloader providesFileDownloader(CommonPackage commonPackage,ICoreApi  coreApi){
        return new FileDownloader(commonPackage,coreApi);
    }



}
