package com.example.christopher.weatherapp;

import android.content.Context;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class fetchWeather
{
    private static final String OWM_API =  "http://api.openweathermap.org/data/2.5/forecast/daily?q=%s&units=metric";

    public static JSONObject getJSON(String city)
    {
        try
        {
            URL url = new URL(String.format(OWM_API, city));
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            connection.addRequestProperty("x-api-key", "INSERT_KEY_HERE");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp ="";

            while((tmp = reader.readLine()) != null)
            {
                json.append(tmp).append("\n");
            }
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            if(data.getInt("cod") != 200)
            {
                return null;
            }
            return data;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
