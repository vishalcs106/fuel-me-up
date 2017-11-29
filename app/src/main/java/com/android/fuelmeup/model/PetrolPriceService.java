package com.android.fuelmeup.model;



import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;


/**
 * Created by Vishal on 25-11-2017.
 */

public interface PetrolPriceService {
    @GET("/{code}/Petrol-price-in-Bengaluru")
    Observable<ResponseBody> getFuelPrice(@Path("code") String code);
}
