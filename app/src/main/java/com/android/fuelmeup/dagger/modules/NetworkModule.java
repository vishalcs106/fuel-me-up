package com.android.fuelmeup.dagger.modules;


import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vishal on 21-11-2017.
 */
@Module
public class NetworkModule {
    public NetworkModule(){

    }
    @Provides
    @Singleton
    RxJavaCallAdapterFactory providesRxJavaCallAdapterFactory(){
        return RxJavaCallAdapterFactory.create();
    }
    @Provides
    @Singleton
    GsonConverterFactory providesGsonConverterFactory(){
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(){
        return new OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory,
                              RxJavaCallAdapterFactory rxJavaCallAdapterFactory){
        return new Retrofit.Builder()
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .client(okHttpClient).baseUrl("https://www.mypetrolprice.com/").build();
    }
}
