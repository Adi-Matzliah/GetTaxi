package com.exercise.here.util;

import android.net.NetworkInfo;

import javax.inject.Inject;

import androidx.annotation.NonNull;

public class NetworkUtils {

    @NonNull
    private final NetworkInfo networkInfo;

    @Inject
    public NetworkUtils(@NonNull NetworkInfo networkInfo) {
        this.networkInfo = networkInfo;
    }

    public boolean isNetworkAvailable() {
        return networkInfo != null && networkInfo.isConnected();
    }
}