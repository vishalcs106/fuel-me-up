package com.android.fuelmeup.presenter;

/**
 * Created by Vishal on 21-11-2017.
 */

public interface Presenter {
    void onCreate();

    void onPause();

    void onResume();

    void onDestroy();
}
