package com.android.fuelmeup.view;

import com.android.fuelmeup.model.FuelPrice;

/**
 * Created by Vishal on 25-11-2017.
 */

public interface FuelPriceViewInterface {
    void onPetrolPriceSuccess(FuelPrice fuelPrice);
    void onPetrolPriceError(String message);
    void onDieselPriceSuccess(FuelPrice fuelPrice);
    void onDieselPriceError(String message);
}
