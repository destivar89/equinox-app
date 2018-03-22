package com.telefonica.movistarhome.templateapp.infraestructure.di.commons;

import android.app.Application;
import android.content.Context;

import com.telefonica.movistarhome.templateapp.App;
import com.telefonica.movistarhome.templateapp.presentation.commons.Navigator;
import com.telefonica.movistarhome.templateapp.domain.service.PreferenceService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    Context provideContext(App application) {
        return application.getApplicationContext();
    }

    @Provides
    Application provideApplication(App application) {
        return application;
    }

    @Provides
    Navigator provideNavigator() {
        return new Navigator();
    }

    @Singleton
    @Provides
    PreferenceService providePreferenceService(App application) {
        return new PreferenceService(application);
    }

}
