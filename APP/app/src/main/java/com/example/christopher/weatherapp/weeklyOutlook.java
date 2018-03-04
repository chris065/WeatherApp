package com.example.christopher.weatherapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Calendar;

public class weeklyOutlook extends AppCompatActivity
{
    ImageView backgroundWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_weekly_outlook);

        backgroundWeek = findViewById(R.id.backgroundWeek);

        changeBackground();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.weekly_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home)
        {
            Intent i = new Intent(this, current_outlook.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeBackground()
    {
        Calendar c = Calendar.getInstance();
        int time = c.get(Calendar.HOUR_OF_DAY);

        if(time >=0 && time < 12)
        {
            backgroundWeek.setImageResource(R.drawable.sunrise_blur);
        }
        if(time >=12 && time < 16)
        {
            backgroundWeek.setImageResource(R.drawable.daytime_blur);
        }
        if(time >=16 && time < 21)
        {
            backgroundWeek.setImageResource(R.drawable.sunset_blur);
        }
        if(time >=21 && time < 24)
        {
            backgroundWeek.setImageResource(R.drawable.star_two_blur);
            //currentTemp.setTextColor(Color.parseColor("#ffffff"));
            //Have to use the white weather icons instead of the black ones
        }
    }
}
