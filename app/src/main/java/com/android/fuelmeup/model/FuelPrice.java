package com.android.fuelmeup.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishal on 25-11-2017.
 */

public class FuelPrice {
    public static final String TYPE_PETROL = "Petrol";
    public static final String TYPE_DIESEL = "Diesel";
    public String type,cityName, cityCode;
    public DatePrice today;
    private List<DatePrice> pastPrices = new ArrayList<>();

    public void setPastPrices(ArrayList<DatePrice> pastPrices) {
        this.pastPrices = pastPrices;
    }

    public List<DatePrice> getPastPrice(){
        return pastPrices;
    }

    public class DatePrice{
        public String  date, price;
        public DatePrice(String date, String price){
            this.date = date;
            this.price = price;
        }
    }
    public FuelPrice(String type,String cityName, String cityCode){
        this.type = type;
        this.cityCode = cityCode;
        this.cityName = cityName;
    }

    public void setTodayPrice(String date, String price){
        today = new DatePrice(date, price);
    }
    public DatePrice datePrice(String date, String price){
        return new DatePrice(date, price);
    }
}
