package com.android.fuelmeup.utils;

import android.content.Context;

/**
 * Created by Vishal on 26-11-2017.
 */

public class UserUtils {
    public static String getUdid(Context context) {
        String id = android.provider.Settings.System.getString(context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        return id;
    }
}
