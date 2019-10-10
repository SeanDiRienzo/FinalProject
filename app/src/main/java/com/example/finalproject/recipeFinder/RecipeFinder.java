package com.example.finalproject.recipeFinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.finalproject.R;

public class RecipeFinder extends AppCompatActivity {

    public static final String ACTIVITY_NAME = "RECIPE_FINDER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_finder);
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
