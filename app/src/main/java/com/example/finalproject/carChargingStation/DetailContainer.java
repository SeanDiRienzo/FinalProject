package com.example.finalproject.carChargingStation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.news.DetailFragment;
import com.example.finalproject.news.NewsArticleObject;

public class DetailContainer extends AppCompatActivity {
    /**
     * Method sets the layout, loads data to the fragment
     * @param savedInstanceState saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details_fragment);

        Bundle bundle = getIntent().getExtras();

        StationFragment dFragment = new StationFragment();
        dFragment.setArguments(bundle); //pass data to the the fragment
        dFragment.setTablet(false); //tell the Fragment that it's on a phone.

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentLocation, dFragment)
                .commit();
    }
}