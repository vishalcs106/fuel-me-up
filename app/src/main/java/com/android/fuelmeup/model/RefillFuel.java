package com.android.fuelmeup.model;


import org.greenrobot.greendao.annotation.Entity;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Keep;

/**
 * Created by Vishal on 26-11-2017.
 */
@Entity(nameInDb = "refill")
public class RefillFuel{

    public String type, location, amount, volume, latLng, userName, dateTime;
    public static final String TYPE_PETROL = "Petrol";
    public static final String TYPE_DIESEL = "Diesel";
    @Keep
    public RefillFuel(String type, String amount, String volume, String latLng, String location,
                      String userName, String dateTime){
        this.type = type;
        this.amount = amount;
        this.volume = volume;
        this.latLng = latLng;
        this.location = location;
        this.userName = userName;
        this.dateTime = dateTime;
    }


    @Generated(hash = 1258018079)
    public RefillFuel() {
    }


    public String getType(){
        return  type;
    }

    public  String getLocation(){
        return location;
    }

    public String getAmount(){
        return amount;
    }

    public String getVolume(){
        return volume;
    }

    public void setType(String type){
        this.type = type;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setAmount(String amount){
        this.amount = amount;
    }

    public void setVolume(String volume){
        this.volume = volume;
    }
    public String getLatLng() {
        return this.latLng;
    }
    public void setLatLng(String latLng) {
        this.latLng = latLng;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getDateTime() {
        return this.dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
