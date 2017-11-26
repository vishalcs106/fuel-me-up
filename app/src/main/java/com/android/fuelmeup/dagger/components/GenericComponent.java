package com.android.fuelmeup.dagger.components;

import com.android.fuelmeup.dagger.CustomScope;
import com.android.fuelmeup.dagger.modules.GenericModule;
import com.android.fuelmeup.presenter.FuelPricePresenter;
import com.android.fuelmeup.presenter.FuelStationsPresenter;

import dagger.Component;

/**
 * Created by Vishal on 21-11-2017.
 */
@Component(modules = GenericModule.class, dependencies = NetworkComponent.class)
@CustomScope
public interface GenericComponent {
    void inject(FuelStationsPresenter fuelStationsPresenter);

    void inject(FuelPricePresenter fuelPricePresenter);
}
