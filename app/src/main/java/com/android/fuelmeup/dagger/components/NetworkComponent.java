package com.android.fuelmeup.dagger.components;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.fuelmeup.dagger.modules.NetworkModule;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by Vishal on 21-11-2017.
 */

@Singleton
@Component(modules = NetworkModule.class)
public interface NetworkComponent {
    Retrofit retrofit();
}
