package com.example.finalproject.recipeFinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.finalproject.R;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class contains the ListView Adapter and the front-end GUI
 */
public class RecipeFinder extends AppCompatActivity {
    /**
     *ACTIVITY_NAME describes the activity
     * recipeArray is the name of the array to store values
     * initialized myAdapter for BaseAdapter
     */
    public static final String ACTIVITY_NAME = "RECIPE_FINDER";
    ArrayList<String> recipeArray = new ArrayList<>(Arrays.asList("Chicken Soup", "Beef Broccoli", "Tomato Soup"));
    BaseAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_finder);
/**
 * theList: initialize the List
 * Inserted toast Milestone 3
 */

        ListView theList = findViewById(R.id.theList);
        theList.setAdapter(myAdapter = new MyListAdapter());
        /**
         *this onClick listener is for the details of the item, will be converted to fragment later
         */
        theList.setOnItemClickListener((lv, vw, pos, id) -> {
            Toast.makeText(RecipeFinder.this,
                    "You clicked on Row Number:" + pos, Toast.LENGTH_SHORT).show();
        });
/**
 * Inserted SnackBar MileStone 3
 * Inserted Button MileStone 2
 * searchButton: there is an event listener for the button. It also adds another row. This will be converted to pull the JSON script
 */
        Button searchButton = findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(clik ->
        {
            recipeArray.add("Item " + (1 + recipeArray.size()));
            myAdapter.notifyDataSetChanged(); //update yourself
            Snackbar.make(searchButton, "Inserted items from the Array:"+recipeArray, Snackbar.LENGTH_LONG).show();
        });
/**
 * Refresh the screen by pulling the screen up or down
 */
        SwipeRefreshLayout refresher = findViewById(R.id.refresher);
        refresher.setOnRefreshListener(() -> {
            recipeArray.add("Item " + (1 + recipeArray.size()));
            myAdapter.notifyDataSetChanged(); //update yourself
            refresher.setRefreshing(false);  //get rid of spinning wheel;
        });
    }

    /**
     * ListView adapter
     * 4 functions necessary for the ListView
     */
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
        public View getView(int p, View recycled, ViewGroup parent) {
            View thisRow = recycled;
            if (recycled == null)
                thisRow = getLayoutInflater().inflate(R.layout.activity_recipe_row, null);
            TextView itemText = thisRow.findViewById(R.id.idNumberField);
            itemText.setText("Array at:" + p + " is " + getItem(p));
            TextView numberText = thisRow.findViewById(R.id.numberField);
            numberText.setText("Your number is " + p);
            return thisRow;
        }
    }
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
}













