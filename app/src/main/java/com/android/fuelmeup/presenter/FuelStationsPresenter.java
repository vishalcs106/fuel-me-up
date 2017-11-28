package com.android.fuelmeup.presenter;

import com.android.fuelmeup.dagger.DaggerInjector;
import com.android.fuelmeup.model.FuelStation;
import com.android.fuelmeup.model.FuelStationsService;
import com.android.fuelmeup.view.FuelStationsViewInterface;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;



import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Vishal on 21-11-2017.
 */

public class FuelStationsPresenter extends BasePresenter implements Observer<JsonObject> {
    @Inject
    FuelStationsService apiService;
    @Inject Gson gson;
    FuelStationsViewInterface viewInterface;
    public FuelStationsPresenter(FuelStationsViewInterface viewInterface){
        this.viewInterface = viewInterface;
        DaggerInjector.getApiComponent().inject(this);
    }


    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        viewInterface.onFuelStationsError(e.getMessage());
    }



    @Override
    public void onNext(JsonObject jsonObject) {
        JsonArray resultsArray = jsonObject.getAsJsonArray("results");
        ArrayList<FuelStation> stations = new ArrayList<>();
        for(int i=0;i<resultsArray.size();i++){
            JsonObject stationJson = resultsArray.get(i).getAsJsonObject();
            FuelStation fuelStation = gson.fromJson(stationJson, FuelStation.class);
            stations.add(fuelStation);
        }
        viewInterface.onFuelStationsSuccess(stations);
    }

    public void getLocations(String url){
        unSubscribeAll();
        Subscription subscription = apiService.getGasStations(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this);
        configureSubscription().add(subscription);
    }
}
