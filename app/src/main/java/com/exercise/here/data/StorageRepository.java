package com.exercise.here.data;

import java.util.List;
import javax.inject.Inject;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import io.reactivex.Single;

public class StorageRepository {

    @NonNull
    private final TaxiDao dao;

    @Inject
    StorageRepository(@NonNull TaxiDao dao) {
        this.dao = dao;
    }

    public void saveOrUpdate(Taxi... taxis){
        dao.insertOrUpdateAll(taxis);
    }

    public LiveData<List<Taxi>> loadAllByOrder(){
        return dao.loadAllByOrder();
    }

    public Single<List<Taxi>> loadAllByOrderRX() {
        return dao.loadAllByOrderRX();
    }
}
