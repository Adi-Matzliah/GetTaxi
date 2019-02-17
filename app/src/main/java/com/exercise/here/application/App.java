package com.exercise.here.application;

import android.app.Application;
import android.content.Context;

import com.exercise.here.R;
import com.exercise.here.util.di.component.AppComponent;

import com.exercise.here.util.di.component.DaggerAppComponent;
import com.exercise.here.util.di.module.AppModule;
import com.exercise.here.util.di.module.ContextModule;
import com.exercise.here.util.di.module.NetworkModule;
import com.exercise.here.util.di.module.StorageModule;

import androidx.annotation.NonNull;

public class App extends Application {

	@NonNull
	private AppComponent appComponent;

	@Override
	public void onCreate() {
		super.onCreate();

		appComponent = DaggerAppComponent.builder()
				.appModule(new AppModule(this))
				.contextModule(new ContextModule(getApplicationContext()))
				.storageModule(new StorageModule(getApplicationContext(), getString(R.string.db_name)))
				.networkModule(new NetworkModule(getString(R.string.base_url)))
				.build();
		appComponent.inject(this);
	}

	public static AppComponent getAppComponent(Context context) {
		return ((App) context.getApplicationContext()).appComponent;
	}

}