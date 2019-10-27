package com.example.finalproject.news;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

import java.util.ArrayList;


public class NewsModule extends AppCompatActivity {
    public static final String ACTIVITY_NAME = "NEWS";
    private NewsArticleAdapter adapter;
    private ArrayList<NewsArticleObject> newsArticleList;
    private ListView newsArticleListView;
    private Button searchButton;
    private Button favouritesButton;
    private EditText searchEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        newsArticleList = new ArrayList<>();
        searchEditText = findViewById(R.id.search_editText);
        searchButton = findViewById(R.id.searchButton);
        favouritesButton = findViewById(R.id.goToFavourites);
        newsArticleListView = findViewById(R.id.articlesListView);


    }

    public void saveSearch(View view) {
        SharedPreferences sharedPref = getSharedPreferences("searchField",  Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("searchField", searchEditText.getText().toString());
        editor.apply();
    }
    public void displayLastSearch(View view) {
        SharedPreferences sharedPref = getSharedPreferences("searchField",  Context.MODE_PRIVATE);
        String searchField = sharedPref.getString("SearchField", "");
        searchEditText.setText(searchField);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

