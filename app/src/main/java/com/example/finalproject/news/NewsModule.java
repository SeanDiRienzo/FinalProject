package com.example.finalproject.news;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.finalproject.R;
import com.example.finalproject.carChargingStation.CarChargingStation;
import com.example.finalproject.currencyConverter.CurrencyConverter;
import com.example.finalproject.recipeFinder.RecipeFinder;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static android.widget.Toast.LENGTH_SHORT;


/**
 * News Activity class that provides the user functionality requested by assignment
 */
public class NewsModule extends AppCompatActivity {

    /**
     * adapter to manage the listview of article objects
     */
    private NewsArticleAdapter adapter;
    /**
     * arraylist to store the list of article objects
     */
    private ArrayList<NewsArticleObject> newsArticleList;
    /**
     * listview to display the list of article objects
     */
    private ListView newsArticleListView;
    /**
     * button to provide user functionality to search for articles
     */
    private Button searchButton;
    /**
     * button to provide user functionality to go to the favourites activity
     */
    private Button favouritesButton;
    /**
     * edittext that allows user to type into to search for articles
     */
    private EditText searchEditText;
    /**
     * string that represents the base url provided for api functionality
     */
    private String NEWS_URL;
    /**
     * progressbar to display progress of articles loading into listview
     */
    private ProgressBar mProgressBar;
    /**
     * toolbar to provide user functionality for switching activities or displaying help
     */
    private Toolbar main_menu;
    /**
     * sharedpreferences object to store the user's text search
     */
    private SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        boolean isTablet = findViewById(R.id.fragmentLocation) != null; //check if the FrameLayout is loaded
        mProgressBar = findViewById(R.id.progress_bar);
        sharedPref = getSharedPreferences("News", MODE_PRIVATE);
        mProgressBar.setVisibility(View.VISIBLE);
        newsArticleList = new ArrayList<>();
        searchEditText = findViewById(R.id.search_editText);
        searchEditText.setText(sharedPref.getString("search", ""));
        searchButton = findViewById(R.id.searchButton);
        favouritesButton = findViewById(R.id.goToFavourites);
        newsArticleListView = findViewById(R.id.articlesListView);

        main_menu = findViewById(R.id.main_menu_news);

        setSupportActionBar(main_menu);

        adapter = new NewsArticleAdapter(this, R.layout.news_row, newsArticleList);
        adapter.setListData(newsArticleList);
        newsArticleListView.setAdapter(adapter);


        /**
         * Function to handle when user clicks "Search"
         */
        favouritesButton.setOnClickListener(favourites -> {
            startFavouritesActivity();
        });
        searchButton.setOnClickListener(search -> {

            /**
             * show snackbar with text from searchbar
             */
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("search", searchEditText.getText().toString());
            editor.commit();

            showSnackbar(main_menu, ("Searching: " + searchEditText.getText().toString()), LENGTH_SHORT);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

            /**
             * close keyboard
             */
            imm.hideSoftInputFromWindow(searchButton.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            NEWS_URL = "https://newsapi.org/v2/everything?q=" + searchEditText.getText().toString() + "&apiKey=fbdf04dd637a47b087b87dd6959ecc5a";


            /**
             * create alert dialog for user after search button pressed
             */
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(NewsModule.this);
            alertBuilder.setTitle("Search : " + searchEditText.getText());
            alertBuilder.setMessage("Clear current view or add new search to current view");
            /**
             * function to run if users hits the 'Add' button
             */
            alertBuilder.setNegativeButton("Add", new DialogInterface.OnClickListener() {
                @Override
                /** onclick for negative button of alert dialog, confirms that the user wants to clear the list of articles and create a new search */
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    new AsyncHttpTask().execute(NEWS_URL);
                }
            });
            /**
             * function to run if users hits the 'clear' button
             */
            alertBuilder.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
                @Override
                /** onclick for po sitive button of alert dialog, confirms that the user wants to append their new search onto the old one */
                public void onClick(DialogInterface dialogInterface, int i) {
                    newsArticleList.clear();
                    adapter.notifyDataSetChanged();
                    new AsyncHttpTask().execute(NEWS_URL);

                }
            });

            /**
             * if this is list is not empty, show alert dialog
             *
             */
            if (newsArticleList.size() > 0) {
                alertBuilder.show();
            } else {
                /**
                 *  list is empty, just query the search, no need for alert dialog
                 */
                new AsyncHttpTask().execute(NEWS_URL);
            }
            /**
             * notify adapter of changes and refresh listview
             */
            adapter.notifyDataSetChanged();


        });

        /**
         * function that handles the user selection of an article item in the listview
         */
        newsArticleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            /** onclick for user single click of item in listview, inflates the details fragment */
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsArticleObject item = (NewsArticleObject) parent.getItemAtPosition(position);
                Bundle dataToPass = new Bundle();
                dataToPass.putSerializable("Article", item);

                if (isTablet) {
                    DetailFragment dFragment = new DetailFragment(); //add a DetailFragment
                    dFragment.setArguments(dataToPass); //pass it a bundle for information
                    dFragment.setTablet(true);  //tell the fragment if it's running on a tablet or not

                    getSupportFragmentManager()

                            .beginTransaction()

                            .replace(R.id.fragmentLocation, dFragment) //Add the fragment in FrameLayout

                            .commit(); //actually load the fragment.
                } else //isPhone
                {
                    Intent nextActivity = new Intent(NewsModule.this, DetailContainer.class);
                    nextActivity.putExtras(dataToPass); //send data to next activity
                    startActivity(nextActivity); //make the transition
                }


            }
        });


    }


    /**
     * shared preferences function to save the last entered search
     */


    @Override
    /** inflate the toolbar */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    /** onclick logic for menu in toolbar */
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
                AlertDialog.Builder helpAlertBuilder = new AlertDialog.Builder(NewsModule.this);
                helpAlertBuilder.setTitle("Help");
                helpAlertBuilder.setMessage(R.string.news_module_help);
                helpAlertBuilder.show();
                break;


        }


        return true;
    }

    /**
     * function to handle newsapi connection on another thread
     */
    public class AsyncHttpTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpsURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);

                urlConnection = (HttpsURLConnection) url.openConnection();


                if (result != null) {

                    String response = streamToString(urlConnection.getInputStream());


                    parseResult(response);


                    return result;


                }
            } catch (MalformedURLException e) {
                e.printStackTrace();


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * side-task completed, closing changes on GUI thread
         * tell my adapter to update and refresh the listview if new articles were found
         */
        @Override
        protected void onPostExecute(String result) {

            if (result != null) {
                adapter.notifyDataSetChanged();
                Toast.makeText(NewsModule.this, "Data Loaded", LENGTH_SHORT).show();
            } else {
                Toast.makeText(NewsModule.this, "Failed to load data!", LENGTH_SHORT).show();
            }

        }

        /**
         * update progress bar with  values
         *
         * @param values
         */
        protected void onProgressUpdate(Integer... values) {

            //Update GUI stuff only (the ProgressBar):
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setProgress(values[0]);
        }

        /**
         * method to parse result received from url connection
         * create new article objects from parsed data and add them to the list
         *
         * @param result
         */
        private void parseResult(String result) {
            try {
                JSONObject response = new JSONObject(result);
                JSONArray posts = response.optJSONArray("articles");
                NewsArticleObject item;
                Float progress;
                for (int i = 0; i < posts.length(); i++) {
                    /**
                     * for every article found
                     * extract desired information
                     * create new article object
                     */
                    JSONObject post = posts.optJSONObject(i);
                    String title = post.optString("title");
                    String image = post.optString("urlToImage");
                    String description = post.optString("description");
                    String url = post.optString("url");
                    item = new NewsArticleObject();
                    item.setTitle(title);
                    item.setImageUrl(image);
                    item.setArticleUrl(url);
                    item.setDescription(description);
                    /**
                     * add new article object to arrayList
                     */
                    newsArticleList.add(item);

                    /**
                     * show progress as a total of articles loaded out of total articles received
                     */
                    progress = ((i + 1) * 100f) / posts.length();
                    Log.d("load percent :", progress.toString());
                    publishProgress(progress.intValue());

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * function to take in the inputstream from urlconnection and build a parseable string
     *
     * @param newsStream
     * @return result
     * @throws IOException
     */
    String streamToString(InputStream newsStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(newsStream));
        String data;
        String result = "";

        while ((data = bufferedReader.readLine()) != null) {
            result += data;
        }

        if (null != newsStream) {
            newsStream.close();
        }


        return result;
    }

    /**
     * function to start the newsfavouritesactivity with intent
     */

    public void startFavouritesActivity() {
        Intent favouritesIntent = new Intent(this, NewsFavourites.class);
        startActivity(favouritesIntent);
    }

    /**
     * function to start the recipe activity with intent
     */
    public void startRecipeActivity() {
        Intent recipeIntent = new Intent(this, RecipeFinder.class);
        startActivity(recipeIntent);

    }

    /**
     * function to start the carchargingactivity with intent
     */
    public void startCarChargingActivity() {
        Intent chargingActivity = new Intent(this, CarChargingStation.class);
        startActivity(chargingActivity);
    }

    public void startCurrencyActivity() {
        Intent currencyActivity = new Intent(this, CurrencyConverter.class);
        startActivity(currencyActivity);
    }

    /**
     * show snackbar
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

