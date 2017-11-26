package com.android.fuelmeup.dagger.modules;

import com.android.fuelmeup.dagger.CustomScope;
import com.android.fuelmeup.model.DieselPriceService;
import com.android.fuelmeup.model.PetrolPriceService;
import com.android.fuelmeup.model.FuelStationsService;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Vishal on 21-11-2017.
 */
@Module
public class GenericModule {
    @Provides
    @CustomScope
    FuelStationsService providesFuelStationsService(Retrofit retrofit){
        return retrofit.create(FuelStationsService.class);
    }

    @Provides
    @CustomScope
    PetrolPriceService providesPetrolPriceService(Retrofit retrofit){
        return retrofit.create(PetrolPriceService.class);
    }

    @Provides
    @CustomScope
    DieselPriceService providesDieselPriceService(Retrofit retrofit){
        return retrofit.create(DieselPriceService.class);
    }

    @Provides
    @CustomScope
    Gson providesGson(){
        return new Gson();
    }
}
