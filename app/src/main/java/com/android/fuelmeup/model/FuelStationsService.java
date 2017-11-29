package com.android.fuelmeup.model;

import com.google.gson.JsonObject;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;



/**
 * Created by Vishal on 21-11-2017.
 */

public interface FuelStationsService {
    @GET
    Observable<JsonObject> getGasStations(@Url String url);
}
