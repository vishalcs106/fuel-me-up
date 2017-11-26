package com.android.fuelmeup.view;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.fuelmeup.R;
import com.android.fuelmeup.model.DatabaseHandler;

public class MainActivity extends AppCompatActivity {

    Fragment mainFragment;
    private final String TAG_MAIN_FRAGMENT = "MainFragment";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainFragment = getSupportFragmentManager().findFragmentByTag(TAG_MAIN_FRAGMENT);
        if(mainFragment == null){
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mainFragment, TAG_MAIN_FRAGMENT).commit();
        }
    }

}
