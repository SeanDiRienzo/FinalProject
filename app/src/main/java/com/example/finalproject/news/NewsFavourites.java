package com.example.finalproject.news;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.finalproject.R;
import com.example.finalproject.carChargingStation.CarChargingStation;
import com.example.finalproject.currencyConverter.CurrencyConverter;
import com.example.finalproject.recipeFinder.RecipeFinder;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * NewsFavourites activity to display list of saved news articles
 */
public class NewsFavourites extends AppCompatActivity {
    /**
     * arraylist newsArticleFavouriteList to store list of NewsArticleObjects records
     */
    private ArrayList<NewsArticleObject> newsArticleFavouriteList;
    /**
     * listview to display the list of saved newsarticleobjects
     */
    private ListView newsArticleFavouritesListView;
    /**
     * newsarticleadapter to manage the listview of newsarticleobjects
     */
    private NewsArticleAdapter adapter;
    /**
     * sqlitedatabase db to manage database of favourite articles
     */
    private SQLiteDatabase db;
    /**
     * toolbar to provide functionality for switching activities and help
     */
    private Toolbar main_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_favourites);
        main_menu = findViewById(R.id.main_menu_favourites);
        setSupportActionBar(main_menu);
        getSupportActionBar().setTitle(R.string.favourites_header); //string is custom name you want

        newsArticleFavouriteList = new ArrayList<>();
        newsArticleFavouritesListView = findViewById(R.id.favourites_list_view);
        adapter = new NewsArticleAdapter(this, R.layout.news_row, newsArticleFavouriteList);
        newsArticleFavouritesListView.setAdapter(adapter);


        MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper(this);
        db = dbOpener.getWritableDatabase();

        loadFavourites();

        newsArticleFavouritesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /** onclicklistener for user single click of a favourite article within the listview */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsArticleObject item = (NewsArticleObject) parent.getItemAtPosition(position);
                /**
                 * call function to start the details activity
                 * pass in the selected article object
                 * @param item
                 */
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getArticleUrl()));
                startActivity(browserIntent);
            }
        });
        newsArticleFavouritesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            /** onclicklistener for user longpress of a favourite article within the listview - deletes item in list and database */
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {


                AlertDialog.Builder builder = new AlertDialog.Builder(NewsFavourites.this);
                AlertDialog dialog = builder.setTitle("Alert!")
                        .setMessage("Really delete?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            /** onclick function for positive button of alertdialog, confirms deletion of selected record */
                            public void onClick(DialogInterface dialog, int which) {
                                String test = "" + pos;
                                Log.v("position", test);
                                int query = db.delete(MyDatabaseOpenHelper.TABLE_NAME,
                                        MyDatabaseOpenHelper.COL_ID + "=?", new String[]{Long.toString(newsArticleFavouriteList.get(pos).getId())});
                                newsArticleFavouriteList.remove(pos);
                                adapter.notifyDataSetChanged();
                                showSnackbar(main_menu, ("Article Deleted"), LENGTH_SHORT);

                            }
                        })
                        .setNegativeButton("Cancel", (d, w) -> {  /* nothing */})
                        .create();
                dialog.show();


                return true;
            }
        });

    }

    /**
     * inflate toolbar
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * toolbar onclick logic
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

            case R.id.overflow_help:
                AlertDialog.Builder helpAlertBuilder = new AlertDialog.Builder(NewsFavourites.this);
                helpAlertBuilder.setTitle("Help");
                helpAlertBuilder.setMessage(R.string.favourites_help);
                helpAlertBuilder.show();
                break;


        }


        return true;
    }

    /**
     * function to start the recipe activity with intent
     */
    public void startRecipeActivity() {
        Intent recipeIntent = new Intent(this, RecipeFinder.class);
        startActivity(recipeIntent);

    }

    /**
     * function to start the carcharging activity with intent
     */
    public void startCarChargingActivity() {
        Intent chargingActivity = new Intent(this, CarChargingStation.class);
        startActivity(chargingActivity);
    }

    /**
     * function to start the currency activity with intent
     */
    public void startCurrencyActivity() {
        Intent currencyActivity = new Intent(this, CurrencyConverter.class);
        startActivity(currencyActivity);
    }

    /**
     * function to load the list of favourite articles from the locally stored database
     */
    private void loadFavourites() {
        String[] columns = {MyDatabaseOpenHelper.COL_ID, MyDatabaseOpenHelper.COL_TITLE, MyDatabaseOpenHelper.COL_DESCRIPTION,
                MyDatabaseOpenHelper.COL_ARTICLEURL, MyDatabaseOpenHelper.COL_IMAGEURL};
        Cursor results = db.query(false, MyDatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);

        int titleColumnIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_TITLE);
        int descriptionColumnIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_DESCRIPTION);
        int idColumnIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ID);
        int articleUrlColumnIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ARTICLEURL);
        int imageUrlColumnIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_IMAGEURL);
        while (results.moveToNext()) {

            String title = results.getString(titleColumnIndex);
            String description = results.getString(descriptionColumnIndex);
            String articleUrl = results.getString(articleUrlColumnIndex);
            String imageUrl = results.getString(imageUrlColumnIndex);
            long id = results.getLong(idColumnIndex);


            newsArticleFavouriteList.add(new NewsArticleObject(id, title, articleUrl, imageUrl, description));
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * function to expedite the creation and display of a snackbar
     */
    public void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
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
