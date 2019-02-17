package com.exercise.here.util.di.module;

import android.content.Context;
import com.exercise.here.util.di.scope.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    public final Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @AppScope
    Context provideContext() {
        return context;
    }
}
