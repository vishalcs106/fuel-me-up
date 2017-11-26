package com.android.fuelmeup.model;

/**
 * Created by Vishal on 25-11-2017.
 */

public class CityCode {
    public  String code, cityName, latLng;
    public CityCode(String code, String cityName, String latLng){
        this.cityName = cityName;
        this.code = code;
        this.latLng = latLng;
    }
}
