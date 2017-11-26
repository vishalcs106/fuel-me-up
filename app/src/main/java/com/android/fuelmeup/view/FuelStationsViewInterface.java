package com.android.fuelmeup.view;

import com.android.fuelmeup.model.FuelStation;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Vishal on 21-11-2017.
 */

public interface FuelStationsViewInterface {
    void onFuelStationsComplete();
    void onFuelStationsError(String message);
    void onFuelStationsSuccess(ArrayList<FuelStation> result);
}
