package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.finalproject.carChargingStation.CarChargingStation;
import com.example.finalproject.currencyConverter.CurrencyConverter;
import com.example.finalproject.news.NewsModule;
import com.example.finalproject.recipeFinder.RecipeFinder;

public class MainActivity extends AppCompatActivity {
    public static final String ACTIVITY_NAME = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton btn_charge = (ImageButton)findViewById(R.id.btn_charge);
        btn_charge.setOnClickListener(bt -> {
            Intent nextPage = new Intent(MainActivity.this, CarChargingStation.class);
            startActivity(nextPage);
        });

        ImageButton btn_news = (ImageButton)findViewById(R.id.btn_news);
        btn_news.setOnClickListener(bt -> {
            Intent nextPage = new Intent(MainActivity.this, NewsModule.class);
            startActivity(nextPage);
        });

        ImageButton btn_recipe = (ImageButton)findViewById(R.id.btn_recipe);
        btn_recipe.setOnClickListener(bt -> {
            Intent nextPage = new Intent(MainActivity.this, RecipeFinder.class);
            startActivity(nextPage);
        });

        ImageButton btn_currency = (ImageButton)findViewById(R.id.btn_currency);
        btn_currency.setOnClickListener(bt -> {
            Intent nextPage = new Intent(MainActivity.this, CurrencyConverter.class);
            startActivity(nextPage);
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME, "In Function onResume()");
    }

    @Override
    protected void onDestroy() {
        Log.e(ACTIVITY_NAME, "In Function onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Log.e(ACTIVITY_NAME, "In Function onStart()");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.e(ACTIVITY_NAME, "In Function onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(ACTIVITY_NAME, "In Function onStop()");
        super.onStop();
    }
}

