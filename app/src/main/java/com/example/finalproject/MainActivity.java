package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.finalproject.carChargingStation.CarChargingStation;
import com.example.finalproject.currencyConverter.CurrencyConverter;
import com.example.finalproject.news.NewsModule;
import com.example.finalproject.recipeFinder.RecipeFinder;

public class MainActivity extends AppCompatActivity {
    public static final String ACTIVITY_NAME = "MAIN_ACTIVITY";
    Toolbar main_menu;
    String description = "Final Project Milestone #2 \n Sean Di Rienzo \n Svitlana Tsushka \n Jason something";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_menu = findViewById(R.id.main_menu);
        setSupportActionBar(main_menu);


    }

    public void startRecipeActivity() {
        Intent recipeActivity = new Intent(this, RecipeFinder.class);
        startActivity(recipeActivity);
    }

    public void startCarChargingActivity() {
        Intent chargingActivity = new Intent(this, CarChargingStation.class);
        startActivity(chargingActivity);
    }

    public void startCurrencyActivity() {
        Intent currencyActivity = new Intent(this, CurrencyConverter.class);
        startActivity(currencyActivity);
    }

    public void startNewsActivity() {
        Intent newsActivity = new Intent(this, NewsModule.class);
        startActivity(newsActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.currency_selection:
                startCurrencyActivity();
                break;
            case R.id.carCharginStation_Selection:
                startCarChargingActivity();
                break;

            case R.id.recipe_selection:
                startRecipeActivity();
                break;

            case R.id.news_selection:
                startNewsActivity();


                break;

            case R.id.overflow_help:
                AlertDialog.Builder helpAlertBuilder = new AlertDialog.Builder(MainActivity.this);
                helpAlertBuilder.setTitle("Help");
                helpAlertBuilder.setMessage(description);
                helpAlertBuilder.show();
                break;


        }


        return true;
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

