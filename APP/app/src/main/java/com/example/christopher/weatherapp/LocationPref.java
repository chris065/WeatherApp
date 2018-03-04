package com.example.christopher.weatherapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class LocationPref
{

    SharedPreferences prefs;

    public LocationPref(Activity activity)
    {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }
    String getCity()
    {
        return prefs.getString("city", "London, GB");
    }
    void setCity(String city)
    {
        prefs.edit().putString("city", city).apply();
    }
}
