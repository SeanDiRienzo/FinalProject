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
    /**
     * Holds the name of the current activity
     */
    public static final String ACTIVITY_NAME = "MAIN_ACTIVITY";
    /**
     * Toolbar instance
     */
    Toolbar main_menu;
    /**
     * Description of the final project
     */
    String description = "Final Project \n Sean Di Rienzo \n Svitlana Tsushka \n Jason Tomkins \n Evan Legault";

    /**
     * Method sets layout
     * @param savedInstanceState saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_menu = findViewById(R.id.main_menu);
        setSupportActionBar(main_menu);
    }
    /**
     * Methods goes to the next activity (Recipe)
     */
    public void startRecipeActivity() {
        Intent recipeActivity = new Intent(this, RecipeFinder.class);
        startActivity(recipeActivity);
    }
    /**
     * Methods goes to the next activity (CarChargingStation)
     */
    public void startCarChargingActivity() {
        Intent chargingActivity = new Intent(this, CarChargingStation.class);
        startActivity(chargingActivity);
    }
    /**
     * Methods goes to the next activity (Currency)
     */
    public void startCurrencyActivity() {
        Intent currencyActivity = new Intent(this, CurrencyConverter.class);
        startActivity(currencyActivity);
    }
    /**
     * Methods goes to the next activity (News)
     */
    public void startNewsActivity() {
        Intent newsActivity = new Intent(this, NewsModule.class);
        startActivity(newsActivity);
    }
    /**
     * Method inflates menu, adds items to the action bar
     * @param menu menu
     * @return boolean if menu is inflated
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    /**
     * Method responses to the click on menu item
     * @param menuItem menu item
     * @return
     */
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

