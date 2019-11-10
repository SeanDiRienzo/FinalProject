/**
 * Student Name: Jason Tomkins
 * Student Number: 040494380
 * Final Project Assignment: RecipeFinder Milestone #1 includes the following:
 * 1 ListView, 1 ProgressBar, 1 Button, 1 EditText, 1 Toast, 1 Snackbar and 1 Custom Dialogue
 */
package com.example.finalproject.recipeFinder;

import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class RecipeFinder extends AppCompatActivity {
    /**
     * @param recipeBarStatus: assign object to XML
     * @param progress: integer assigned to variable to calculate 0 to 100 with 1 second delay
     * @param ACTIVITY_NAME: Enum to declare which activity is running on the Debug Server Output
     * @param recipeArray: is the array used to hold values in memory
     */
    ProgressBar recipeBarStatus;
    int progress = 0;
    public static final String ACTIVITY_NAME = "RECIPE_FINDER";
    ArrayList<String> recipeArray = new ArrayList<>(Arrays.asList("Chicken Soup", "Beef Broccoli", "Tomato Soup"));
    BaseAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_finder);
    /**
    * @param recipeBarStatus: initialize and reference XML
    * @param theList: initialize and reference XML
    * - OnClickListener added to show toast when row clicked
    */
        ProgressBar recipeBarStatus = findViewById(R.id.progress_bar);
        ListView theList = findViewById(R.id.theList);
        theList.setAdapter(myAdapter = new MyListAdapter());
        theList.setOnItemClickListener((lv, vw, pos, id) -> {
            Toast.makeText(RecipeFinder.this,
                    "You clicked on Row Number:" + pos, Toast.LENGTH_SHORT).show();
        });
        /**
         * @param searchButton: initialize and reference XML
         * - OnClickListener added to add row to ListView + Array + Show Snackbar
         * @param recipeArray: when clicked add item and get array Size.
         * @param myAdapter: refresh/trigger refresh ListView
         */
        Button searchButton = findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clik) {
                //setProgressValue(progress); // CALLS METHOD for Progress Bar
                recipeArray.add("Item " + (1 + recipeArray.size()));
                myAdapter.notifyDataSetChanged(); //update yourself
                Snackbar.make(searchButton, "Inserted Sample Data to Array:" + recipeArray, Snackbar.LENGTH_LONG).show();
            }
        });
        /**
         * Refresh the screen by pulling the screen up or down
         * @param refresher: initialize and reference XML tags that encapsulate ListView
         */
        SwipeRefreshLayout refresher = findViewById(R.id.refresher);
        refresher.setOnRefreshListener(() -> {
            recipeArray.add("Item " + (1 + recipeArray.size()));
            myAdapter.notifyDataSetChanged(); //update yourself
            refresher.setRefreshing(false);  //get rid of spinning wheel;
        });
        /**
         * @param b: New Instance of Builder function initialized
         * - Creates a Custom Dialogue Box when opening the app
         */
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Hello!")
                .setMessage("Welcome to the RecipeFinder using Food2Fork API")
                .setPositiveButton("I'm so excited", (clk, btn) -> { /* do this when clicked */ })
                .setNegativeButton("Just give me recipes!!", (clk, btn) -> { /* do this when clicked */ })
                .create()
                .show();

    } // END onCreate()

    /**
     * The next method is only for the Progress Update Bar
     *
     * @param progress is an Integer value from 0 to 100 with a 1 second delay
     */
    private void setProgressValue(final int progress) {
        recipeBarStatus.setProgress(progress);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setProgressValue(progress + 10);
            }
        });
        thread.start();
    } // END METHOD
    private class MyListAdapter extends BaseAdapter {
        public int getCount() {
            return recipeArray.size();
        } //This function tells how many objects to show
        public String getItem(int position) {
            return recipeArray.get(position);
        }  //This returns the string at position p
        public long getItemId(int p) {
            return p;
        } //This returns the database id of the item at position p
        /**
         * @param p:        object you want to display at row position
         * @param recycled: View variable
         * @param parent:   root
         * @return newView: a new view
         */
        public View getView(int p, View recycled, ViewGroup parent) {
        /**
         * @param recipeArray: to  count the size of the number of objects in the array to inflate
         * @param itemText:    ff
         */
            View thisRow = recycled;
            if (recycled == null)
                thisRow = getLayoutInflater().inflate(R.layout.activity_recipe_row, null);
            TextView itemText = thisRow.findViewById(R.id.idNumberField);
            itemText.setText("ID#:  " + p + " is " + getItem(p));
            TextView numberText = thisRow.findViewById(R.id.numberField);
            numberText.setText("Your number is " + p);
            return thisRow;
        } // End Method
    } // END MyListAdapter Class

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
} // END RecipeFinder Class













