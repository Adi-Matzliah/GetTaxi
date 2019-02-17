package com.exercise.here.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import com.exercise.here.application.App;
import com.exercise.here.data.StorageRepository;
import com.exercise.here.data.Taxi;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

public class EtaUpdaterService extends Service {

    @Inject
    StorageRepository storageRepo;

    private Binder binder = new EtaUpdaterService.LocalBinder();

    private static final int UPDATE_TIME_INTERVAL = 5;

    private static final int MIN_RAND_ETA = 60;

    private static final int MAX_RAND_ETA = 6300;

    @NonNull
    private CompositeDisposable disposable = new CompositeDisposable();

    public EtaUpdaterService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        App.getAppComponent(this).inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public Observable<List<Taxi>> getDBObservableCallable() {
        return Observable.fromCallable(() -> storageRepo.loadAllByOrder().getValue());
    }

    public void onUpdateEta() {
        disposable.add(Observable.interval(UPDATE_TIME_INTERVAL, TimeUnit.SECONDS)
                .flatMap(d -> storageRepo.loadAllByOrderRX().toObservable())
                .flatMapIterable(list->list)
                .map(taxi -> {
                    taxi.setEta((int) (MIN_RAND_ETA + Math.random() * ( MAX_RAND_ETA - MIN_RAND_ETA )));
                    storageRepo.saveOrUpdate(taxi);
                    return taxi;
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Taxi>>() {
                    @Override
                    public void onSuccess(List<Taxi> taxis) {
                        Log.e(taxis.toString(), taxis.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("error", e.toString());
                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class LocalBinder extends Binder {
        public EtaUpdaterService getService() {
            return EtaUpdaterService.this;
        }
    }
}
