package com.example.finalproject.recipeFinder;

import android.os.Bundle;

import com.example.finalproject.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * EMPTY ACTIVITY IS LAYOUT FOR PHONE
 */

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_fragment);

        Bundle dataToPass = getIntent().getExtras(); //get the data that was passed from FragmentExample
        // So far the screen is blank

        ReciperFinder_DetailFragment dFragment = new ReciperFinder_DetailFragment();
        dFragment.setArguments(dataToPass); //pass data to the the fragment
        dFragment.setTablet(false); //tell the Fragment that it's on a phone.
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentLocation, dFragment)
                .addToBackStack("AnyName")
                .commit();
    }


}

