package com.exercise.here.viewmodel;

import com.exercise.here.data.StorageRepository;
import com.exercise.here.data.Taxi;
import com.exercise.here.network.RemoteRepository;
import com.exercise.here.util.NetworkUtils;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainScreenViewModel extends ViewModel {

    @NonNull
    private final RemoteRepository remoteRepo;

    @NonNull
    private final StorageRepository storageRepo;

    @NonNull
    private final NetworkUtils networkUtils;

    @Nullable
    private final MutableLiveData<List<Taxi>> taxis = new MutableLiveData<>();

    @Nullable
    private final MutableLiveData<Boolean> isLoadingError = new MutableLiveData<>();

    @Nullable
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    @NonNull
    private CompositeDisposable apiDisposable = new CompositeDisposable();

    @Inject
    public MainScreenViewModel(@NonNull RemoteRepository remoteRepo,
                               @NonNull StorageRepository storageRepo,
                               @NonNull NetworkUtils networkUtils) {
        this.remoteRepo = remoteRepo;
        this.storageRepo = storageRepo;
        this.networkUtils = networkUtils;
    }

    public void loadTaxis() {
        if (networkUtils.isNetworkAvailable()) {
            fetchTaxis();
        }
    }

    public void fetchTaxis() {
        isLoading.setValue(true);
        apiDisposable.add(remoteRepo.getAllTaxis()
                .flatMap(this::storeTaxis)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Taxi>>() {

                    @Override
                    public void onSuccess(List<Taxi> taxisItems) {
                        isLoadingError.setValue(false);
                        isLoading.setValue(false);
                        taxis.setValue(taxisItems);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isLoadingError.setValue(true);
                        isLoading.setValue(false);
                    }}));
    }

    private Single<List<Taxi>> storeTaxis(List<Taxi> taxis) {
        storageRepo.saveOrUpdate(taxis.toArray(new Taxi[taxis.size()]));
        return Single.just(taxis);
    }

    @Nullable
    public LiveData<List<Taxi>> getTaxis() {
        return storageRepo.loadAllByOrder();
    }

    @Nullable
    public LiveData<Boolean> getIsLoadingError() {
        return isLoadingError;
    }

    @Nullable
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        apiDisposable.clear();
    }
}
