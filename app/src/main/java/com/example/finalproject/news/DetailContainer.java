package com.example.finalproject.news;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

/**
 * Container Activity class that has a frame layout to inflate the details fragment into
 */
public class DetailContainer extends AppCompatActivity {
    /**
     * NewsArticleObject variable to store the news article object sent through fragment transaction manager
     */
    private NewsArticleObject articleObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_container);


        Bundle bundle = getIntent().getExtras();


        DetailFragment dFragment = new DetailFragment();
        dFragment.setArguments(bundle); //pass data to the the fragment
        dFragment.setTablet(false); //tell the Fragment that it's on a phone.

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentLocation, dFragment)

                .commit();
    }
}