package com.example.christopher.weatherapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Locale;

public class current_outlook extends AppCompatActivity
{
    Handler handler;
    TextView currentTemp, location;
    ImageView background;

    public current_outlook()
    {
        handler = new Handler();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_current_outlook);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        background = findViewById(R.id.background);
        currentTemp = findViewById(R.id.currentTemp);
        location = findViewById(R.id.location);

        updateWeather(new LocationPref(this).getCity());

        changeBackground();

    }

    public void updateWeather(final String city)
    {
        new Thread()
        {
            public void run()
            {
                final JSONObject json = fetchWeather.getJSON(city);
                if(json == null)
                {
                    handler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                        }
                    });
                }
                else
                {
                    handler.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            renderWeather(json);
                        }
                    });
                }
            }
        }
                .start();
    }

    public void renderWeather(JSONObject jsonObj)
    {
        try
        {
            location.setText(jsonObj.getJSONObject("city").getString("name").toUpperCase(Locale.ENGLISH) + ", " + jsonObj.getJSONObject("city").getString("country"));

            JSONObject temp = jsonObj.getJSONArray("list").getJSONObject(0).getJSONObject("temp");

            /*Setting the values*/

            currentTemp.setText(String.format("%.0f", temp.getDouble("day")) + "°C");


        }
        catch(Exception e)
        {
            Log.e("Error", "One or more of the fields are not found in the JSON data");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_current_outlook, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.refresh) {
            finish();
            startActivity(getIntent());
        }
        if(id == R.id.change_loc)
        {
            changeLocationDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickWeek(View view)
    {
        Intent i = new Intent(this, weeklyOutlook.class);
        startActivity(i);
    }

    public void changeBackground()
    {
        Calendar c = Calendar.getInstance();
        int time = c.get(Calendar.HOUR_OF_DAY);

        if(time >=0 && time < 12)
        {
            background.setImageResource(R.drawable.sunrise_blur);
        }
        if(time >=12 && time < 16)
        {
            background.setImageResource(R.drawable.daytime_blur);
        }
        if(time >=16 && time < 21)
        {
            background.setImageResource(R.drawable.sunset_blur);
        }
        if(time >=21 && time < 24)
        {
            background.setImageResource(R.drawable.star_two_blur);
            currentTemp.setTextColor(Color.parseColor("#ffffff"));
            //Have to use the white weather icons instead of the black ones
        }
    }
    public void changeLocationDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Location");
        final EditText input  = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Go!", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                changeLocation(input.getText().toString());
                finish();
                startActivity(getIntent());
            }
        });
        builder.show();
    }
    public void changeLocation(String city)
    {
        updateWeather(city);
        new LocationPref(this).setCity(city);
    }
}
