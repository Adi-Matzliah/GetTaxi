package com.exercise.here.network;

import com.exercise.here.data.Taxi;
import com.exercise.here.network.response.TaxisResponse;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import io.reactivex.Single;

public class RemoteRepository {

    @NonNull
    private final TaxisApi api;

    @Inject
    RemoteRepository(@NonNull TaxisApi api) {
        this.api = api;
    }

    public Single<List<Taxi>> getAllTaxis() {
        return api.getTaxis().map(TaxisResponse::getTaxis);
    }
}
