package com.exercise.here.util.di.module;

import android.app.Application;

import com.exercise.here.util.di.scope.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @AppScope
    Application provideApplication() {
        return application;
    }
}
