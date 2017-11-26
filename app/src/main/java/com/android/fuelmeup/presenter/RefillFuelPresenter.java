package com.android.fuelmeup.presenter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.android.fuelmeup.Application;
import com.android.fuelmeup.model.DaoSession;
import com.android.fuelmeup.model.RefillFuel;
import com.android.fuelmeup.view.RefillFuelViewInterface;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vishal on 26-11-2017.
 */

public class RefillFuelPresenter extends BasePresenter {
    DaoSession daoSession;
    RefillFuelViewInterface viewInterface;
    Context mContext;
    public RefillFuelPresenter(Context context, RefillFuelViewInterface viewInterface) {
        mContext = context;
        daoSession = ((Application)context.getApplicationContext()).getDaoSession();
        this.viewInterface = viewInterface;
    }

    public void saveRefillData(RefillFuel refill) {
        daoSession.getRefillFuelDao().save(refill);
        viewInterface.onSaveSuccess();
    }

    public void  getFuelStationName(double latitude, double longitude){
        String fuelStationName = "";
        Geocoder gcd = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(latitude, longitude,10);
            for (Address adrs : addresses) {
                if (adrs != null) {
                    String location = adrs.getAddressLine(0);
                    if (location != null && !location.equals("")) {
                        fuelStationName = location;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewInterface.onLocationSuccess(fuelStationName);
    }
}
