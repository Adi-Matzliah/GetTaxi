package com.exercise.here.util.di.component;

import com.exercise.here.application.App;
import com.exercise.here.ui.main.MainActivity;
import com.exercise.here.ui.service.EtaUpdaterService;
import com.exercise.here.util.di.module.AppModule;
import com.exercise.here.util.di.module.ImageLoaderModule;
import com.exercise.here.util.di.module.NetworkModule;
import com.exercise.here.util.di.module.StorageModule;
import com.exercise.here.util.di.scope.AppScope;

import dagger.Component;

@AppScope
@Component(modules = {AppModule.class, NetworkModule.class, ImageLoaderModule.class, StorageModule.class})
public interface AppComponent {
    void inject(App app);

    void inject(MainActivity activity);

    void inject(EtaUpdaterService service);
}
