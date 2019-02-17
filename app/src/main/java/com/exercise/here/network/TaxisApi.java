package com.exercise.here.network;


import com.exercise.here.network.response.TaxisResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface TaxisApi {

   @GET("bins/ndqle")
    Single<TaxisResponse> getTaxis();
}