package com.android.fuelmeup;

import com.android.fuelmeup.model.DaoMaster;
import com.android.fuelmeup.model.DaoSession;
import com.facebook.stetho.Stetho;

/**
 * Created by Vishal on 21-11-2017.
 */

public class Application extends android.app.Application {
    DaoSession mDaoSession;
    @Override
    public void onCreate(){
        super.onCreate();
        mDaoSession = new DaoMaster(
                new DaoMaster.DevOpenHelper(this, "fuelMeUp.db").getWritableDb()).newSession();
        Stetho.initializeWithDefaults(this);
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
