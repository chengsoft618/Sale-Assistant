package com.shoniz.saledistributemobility.di;

import android.app.Application;


import com.shoniz.saledistributemobility.data.database.SaleDatabaseModule;
import com.shoniz.saledistributemobility.data.database.UserDatabaseModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        BaseModule.class,
        ApiModule.class,
        UserDatabaseModule.class,
        SaleDatabaseModule.class,
        ActivityBuilderModule.class,
        ServiceModule.class,
        RepositoryModule.class


})
public interface AppComponent extends AndroidInjector<DaggerApplication> {
    void inject(BaseApp app);

    @Override
    void inject(DaggerApplication application);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder getApp(Application application);

        AppComponent build();
    }

}
