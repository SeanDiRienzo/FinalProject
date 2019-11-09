package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.finalproject.recipeFinder.RecipeFinder;

public class MainActivity extends AppCompatActivity {
    public static final String ACTIVITY_NAME = "MAIN_ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ACTIONS FOR LOGIN BUTTON
        Button loginButton = findViewById(R.id.buttonRecipeFinder);
        loginButton.setOnClickListener(clk -> {
            // Next activity
            Intent recipeFinderActivity = new Intent(MainActivity.this, RecipeFinder.class);
            startActivity(recipeFinderActivity);
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

    public void goToNews() {

    }

    public void goToRecipeFinder() {

    }

    public void goToCurrencyConverter () {

    }

    public void goToCarChargingStation() {

    }
}

