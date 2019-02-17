package com.exercise.here.viewmodel;

import com.exercise.here.data.StorageRepository;
import com.exercise.here.network.RemoteRepository;
import com.exercise.here.util.NetworkUtils;
import com.exercise.here.util.di.scope.AppScope;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/*
@AppScope
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;

    @Inject
    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
        this.creators = creators;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Provider<? extends ViewModel> creator = creators.get(modelClass);
        if (creator == null) {
            for (Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : creators.entrySet()) {
                if (modelClass.isAssignableFrom(entry.getKey())) {
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if (creator == null) {
            throw new IllegalArgumentException("unknown model class " + modelClass);
        }
        try {
            return (T) creator.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}*/



@AppScope
public class ViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    private final RemoteRepository remoteRepo;

    @NonNull
    private final StorageRepository storageRepo;

    @NonNull
    private final NetworkUtils networkUtils;

    @Inject
    public ViewModelFactory(@NonNull RemoteRepository remoteRepo,
                               @NonNull StorageRepository storageRepo,
                               @NonNull NetworkUtils networkUtils) {
        this.remoteRepo = remoteRepo;
        this.storageRepo = storageRepo;
        this.networkUtils = networkUtils;
    }


    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainScreenViewModel.class)) {
            return (T) new MainScreenViewModel(remoteRepo, storageRepo, networkUtils);
        } else {
            throw new IllegalArgumentException("unknown view model class " + modelClass);
        }
    }
}
