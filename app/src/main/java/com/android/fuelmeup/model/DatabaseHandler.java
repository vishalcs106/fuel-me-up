package com.android.fuelmeup.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by Vishal on 25-11-2017.
 */

public class DatabaseHandler extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "city.db";
    private static final int DATABASE_VERSION = 1;
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public ArrayList<CityCode> getCityCodes(String cityName) {
        ArrayList<CityCode> codes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String rawQuery = "Select * from CodeCity where CityName like ?";
        Cursor cursor = db.rawQuery(rawQuery, new String[]{"%"+cityName+"%"});
        while (cursor.moveToNext()){
            CityCode cityCode = new CityCode(cursor.getString(0),
                    cursor.getString(1), cursor.getString(2));
            codes.add(cityCode);
        }
        return codes;
    }
}
