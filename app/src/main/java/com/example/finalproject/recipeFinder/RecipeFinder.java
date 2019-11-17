/**
 * Student Name: Jason Tomkins
 * Student Number: 040494380
 * Final Project Assignment: RecipeFinder Milestone #1 includes the following:
 * 1 ListView, 1 ProgressBar, 1 Button, 1 EditText, 1 Toast, 1 Snackbar and 1 Custom Dialogue
 * <p>
 * Final Project Assignment: RecipeFinder Milestone 2 includes the following:
 * Id, Title, Image and URL display in ListView
 * AsynckTask Activity
 */

package com.example.finalproject.recipeFinder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.MainActivity;
import com.example.finalproject.R;
import com.example.finalproject.carChargingStation.CarChargingStation;
import com.example.finalproject.currencyConverter.CurrencyConverter;
import com.example.finalproject.news.NewsModule;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashMap;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

public class RecipeFinder extends AppCompatActivity {
    /**
     * @param recipeBarStatus: assign object to XML
     * @param progress: integer assigned to variable to calculate 0 to 100 with 1 second delay
     * @param ACTIVITY_NAME: Enum to declare which activity is running on the Debug Server Output
     * @param recipeList: is the array used to hold values in memory
     * @param pDialog: is for the timed dialogue box
     * @param recipeList: array to store JSON parse
     */
    public static final String ACTIVITY_NAME = "Recipe Finder";
    private String TAG = RecipeFinder.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView recipeListView;
    BaseAdapter myAdapter;
    private Button buttonSearch;
    int progress = 0;
    ProgressBar recipeBar;
    private static String url = "https://www.food2fork.com/api/search?key=45239b72fccf255c80cf01ab76a18a96&q=chicken%20breast&page=2";
    ArrayList<String> recipeList = new ArrayList<>(Arrays.asList());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_finder);
        //recipeList = new ArrayList<>();

        /**
         * @param recipeBarStatus: initialize and reference XML
         * @param theList: initialize and reference XML
         * - OnClickListener added to show toast when row clicked
         */
        buttonSearch = (Button) findViewById(R.id.buttonSearch);
        recipeBar = (ProgressBar) findViewById(R.id.progressBar);


        recipeListView = (ListView) findViewById(R.id.theList);
        recipeListView.setAdapter(myAdapter = new MyListAdapter());
        alertExample();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setProgressValue(progress); // CALLS METHOD for Progress Bar
                new GetRecipes().execute();

                if (buttonSearch != null) {
                    recipeBar.setVisibility(View.VISIBLE);
                }
                System.out.println("1 RecipeList Print:           " + recipeList);
                System.out.println("RecipeListView Print:           " + recipeListView);
                myAdapter.notifyDataSetChanged();
            }
        });
        Toolbar tbar = (Toolbar) findViewById(R.id.recipeToolbar);
        setSupportActionBar(tbar);
    } // END onCreate()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.recipeToolbar);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //what to do when the menu item is selected:
/*
            case R.id.item1:
                Intent goToParking = new Intent(RecipeFinder.this, CarChargingStation.class);
                startActivity(goToParking);
                break;
            case R.id.item2:
                Intent goToCurrency = new Intent(RecipeFinder.this, CurrencyConverter.class);
                startActivity(goToCurrency);
                break;
   */
            case R.id.item3:
                Intent goToNews = new Intent(RecipeFinder.this, NewsModule.class);
                startActivity(goToNews);
                break;
            case R.id.item4:
                Intent goToRecipe = new Intent(RecipeFinder.this, RecipeFinder.class);
                startActivity(goToRecipe);
                break;
        }
        return true;
    } // End onOptionsItemSelected

    public void alertExample() {
        View middle = getLayoutInflater().inflate(R.layout.activity_recipe_alertbox, null);
        EditText editTextTyped = middle.findViewById(R.id.alertBox_text);

        Toast toast = new Toast(getApplicationContext());
        editTextTyped.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("")
                .setPositiveButton("Positive", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        toast.makeText(RecipeFinder.this, editTextTyped.getText(), toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Negative", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // What to do on Cancel
                    }
                }).setView(middle);
        builder.create().show();
    } // End alertExample()

    private class GetRecipes extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RecipeFinder.this);
            pDialog.setMessage("Please wait... Downloading Recipes");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            GetJSONData getJSONData = new GetJSONData();

            // Making a request to url and getting response
            String urlString = getJSONData.establishConnection(url);
            Log.i(TAG, "Response from url: " + urlString);
            if (urlString != null) {
                try {
                    JSONObject jsonObj = new JSONObject(urlString);
                    JSONArray recipeArray = jsonObj.getJSONArray("recipes");
                    // loop through All Recipes
                    for (int i = 0; i < recipeArray.length(); i++) {
                        JSONObject rowItem = recipeArray.getJSONObject(i);

                        String recipeIDString = rowItem.getString("recipe_id");
                        String publisherString = rowItem.getString("publisher");
                        String titleString = rowItem.getString("title");
                        String sourceURLString = rowItem.getString("source_url");
                        String imageRecipeString = rowItem.getString("image_url");

/* ATTRIBUTES NOT USED FOR PARSING
                        String f2f_urlString = rowItem.getString("f2f_url");
                        String socialRankString = rowItem.getString("social_rank");
                        String publisherURLString = rowItem.getString("publisher_url");
*/

                        HashMap<String, String> recipeItems = new HashMap<>();

                        // adding each child node to HashMap key => value
                        recipeItems.put("\n" + "Recipe ID: ", recipeIDString + "\n");
                        recipeItems.put("\n" + "Publisher: ", publisherString + "\n");
                        recipeItems.put("\n" + "Title: ", titleString + "\n");
                        recipeItems.put("\n" + "Source URL: ", sourceURLString + "\n");
                        recipeItems.put("\n" + "Image: ", imageRecipeString + "\n");

                        System.out.println("Print out Recipes: " + recipeItems);
                        //recipeList.add(String.valueOf(recipeItems));
                        recipeList.add(String.valueOf(recipeItems));
                        System.out.println("2 RecipeList Print:           " + recipeList);

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            myAdapter.notifyDataSetChanged();
        } // END onPostExecute

    } // End GetRecipes Class Async


    private class MyListAdapter extends BaseAdapter {

        public int getCount() {
            return recipeList.size();
        } //This function tells how many objects to show

        public String getItem(int position) {
            return recipeList.get(position);
        }  //This returns the string at position p

        public long getItemId(int p) {
            return p;
        } //This returns the database id of the item at position p

        public View getView(int p, View recycled, ViewGroup parent) {
            View thisRow = recycled;

            if (recycled == null)
                thisRow = getLayoutInflater().inflate(R.layout.activity_recipe_row, null);

            TextView titleString = thisRow.findViewById(R.id.txtViewTitle);
            titleString.setText("Recipe Suggestions: " + p + " is" + getItem(p) + "\n");
/*
            TextView sourceURLString = thisRow.findViewById(R.id.txtViewSourceURL  );
            sourceURLString.setText( "Source URL-------: " + p + " is\n " + getItem(p) +"\n");

            TextView recipeIDString = thisRow.findViewById(R.id.txtViewRecipe_id  );
            recipeIDString.setText( "Recipe ID: " + p + " is" + getItem(p) +"\n");

            TextView publisherString = thisRow.findViewById(R.id.txtViewPublisher  );
            publisherString.setText( "Publisher: " + p + " is\n " + getItem(p) +"\n");


            TextView imageRecipeString = thisRow.findViewById(R.id.txtViewImage_url  );
            imageRecipeString.setText( "Image-------: " + p + " is\n " + getItem(p) +"\n");
*/
            return thisRow;
        }

    }

    /**
     * The next method is only for the Progress Update Bar
     *
     * @param progress is an Integer value from 0 to 100 with a 1 second delay
     */
    private void setProgressValue(final int progress) {
        recipeBar.setProgress(progress);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(900);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue(progress + 25);
            }
        });
        thread.start();
    } // END setProgressValue

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME, "onResume()");
    }

    @Override
    protected void onDestroy() {
        Log.e(ACTIVITY_NAME, "onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Log.e(ACTIVITY_NAME, "onStart()");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.e(ACTIVITY_NAME, "onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(ACTIVITY_NAME, "onStop()");
        super.onStop();
    }
}

