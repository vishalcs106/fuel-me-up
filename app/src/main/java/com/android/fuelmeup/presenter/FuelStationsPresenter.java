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

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Vishal on 21-11-2017.
 */

public class FuelStationsPresenter{
    @Inject
    FuelStationsService apiService;
    @Inject Gson gson;
    FuelStationsViewInterface viewInterface;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public FuelStationsPresenter(FuelStationsViewInterface viewInterface){
        this.viewInterface = viewInterface;
        DaggerInjector.getApiComponent().inject(this);
    }

    public void getLocations(String url){
        compositeDisposable.add(apiService.getGasStations(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableObserver<JsonObject>() {
                    @Override
                    public void onNext(JsonObject jsonObject) {
                        JsonArray resultsArray = jsonObject.getAsJsonArray("results");
                        ArrayList<FuelStation> stations = new ArrayList<>();
                        for(int i=0;i<resultsArray.size();i++){
                            JsonObject stationJson = resultsArray.get(i).getAsJsonObject();
                            try {
                                FuelStation fuelStation = gson.fromJson(stationJson, FuelStation.class);
                                stations.add(fuelStation);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        viewInterface.onFuelStationsSuccess(stations);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }
}
