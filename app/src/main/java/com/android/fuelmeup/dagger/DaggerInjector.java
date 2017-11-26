package com.android.fuelmeup.dagger;

import com.android.fuelmeup.dagger.components.DaggerGenericComponent;
import com.android.fuelmeup.dagger.components.GenericComponent;
import com.android.fuelmeup.dagger.components.DaggerNetworkComponent;
import com.android.fuelmeup.dagger.components.NetworkComponent;
import com.android.fuelmeup.dagger.modules.NetworkModule;

/**
 * Created by Vishal on 21-11-2017.
 */

public class DaggerInjector {
    private static GenericComponent appComponent = DaggerGenericComponent.builder().networkComponent(
            getNetworkComponent()).build();
    public static GenericComponent getApiComponent() {
        return appComponent;
    }

    public static NetworkComponent getNetworkComponent(){
        return DaggerNetworkComponent.builder().networkModule(new NetworkModule()).build();
    }
}