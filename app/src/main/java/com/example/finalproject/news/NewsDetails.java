package com.example.finalproject.news;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.finalproject.R;
import com.example.finalproject.carChargingStation.CarChargingStation;
import com.example.finalproject.currencyConverter.CurrencyConverter;
import com.example.finalproject.recipeFinder.RecipeFinder;
import com.squareup.picasso.Picasso;

public class NewsDetails extends AppCompatActivity {
    private Button goToUrlButton;
    private Button addToFavouritesButton;
    private Button openInBrowser;
    private TextView articleDescription;
    private TextView articleTitle;
    private ImageView articleImage;
    private TextView articleUrl;
    private Intent lastIntent;
    private NewsArticleObject articleObject;

    private Toolbar main_menu;

    /**
     * setup the ui
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        main_menu = findViewById(R.id.main_menu_details);
        setSupportActionBar(main_menu);
        getSupportActionBar().setTitle(R.string.details_header); //string is custom name you want

        articleTitle = findViewById(R.id.details_title);
        articleDescription = findViewById(R.id.details_description);
        articleImage = findViewById(R.id.details_image);
        articleUrl = findViewById(R.id.url_textview);
        lastIntent = getIntent();
        articleObject = (NewsArticleObject) lastIntent.getSerializableExtra("articleObject");
        Log.v("url", articleObject.getArticleUrl());
        Log.v("imageurl", articleObject.getImageUrl());
        /**
         * grab article object from intent
         * display article object information
         */
        articleTitle.setText(articleObject.getTitle());
        articleDescription.setText(articleObject.getDescription());
        Picasso.get().load(articleObject.getImageUrl()).into(articleImage);
        articleUrl.setText(articleObject.getArticleUrl());
        /** create button for the user to open the article in their default web browser*/
        addToFavouritesButton = findViewById(R.id.add_to_favourites_button);
        MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper(this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();
        addToFavouritesButton.setOnClickListener(fav -> {

            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyDatabaseOpenHelper.COL_TITLE, articleObject.getTitle());
            newRowValues.put(MyDatabaseOpenHelper.COL_DESCRIPTION, articleObject.getDescription());
            newRowValues.put(MyDatabaseOpenHelper.COL_ARTICLEURL, articleObject.getArticleUrl());
            newRowValues.put(MyDatabaseOpenHelper.COL_IMAGEURL, articleObject.getImageUrl());
            long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog dialog = builder.setMessage("Added Article to Favourites")
                    .setPositiveButton("OK", (d, w) -> {  /* nothing */})
                    .create();
            dialog.show();

        });

        openInBrowser = findViewById(R.id.go_to_url_button);
        openInBrowser.setOnClickListener(browser -> {

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleObject.getArticleUrl()));
            startActivity(browserIntent);

        });

    }

    public void startRecipeActivity() {
        Intent recipeIntent = new Intent(this, RecipeFinder.class);
        startActivity(recipeIntent);

    }

    public void startCarChargingActivity() {
        Intent chargingActivity = new Intent(this, CarChargingStation.class);
        startActivity(chargingActivity);
    }

    public void startCurrencyActivity() {
        Intent currencyActivity = new Intent(this, CurrencyConverter.class);
        startActivity(currencyActivity);
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

            case R.id.overflow_help:
                AlertDialog.Builder helpAlertBuilder = new AlertDialog.Builder(NewsDetails.this);
                helpAlertBuilder.setTitle("Help");
                helpAlertBuilder.setMessage(R.string.news_details_help);
                helpAlertBuilder.show();
                break;


        }


        return true;
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
