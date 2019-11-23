package com.example.finalproject.news;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

public class DetailContainer extends AppCompatActivity {
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