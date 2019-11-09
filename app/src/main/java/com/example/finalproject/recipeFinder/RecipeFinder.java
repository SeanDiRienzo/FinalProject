package com.example.finalproject.recipeFinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeFinder extends AppCompatActivity {

    public static final String ACTIVITY_NAME = "RECIPE_FINDER";
    ArrayList<String> recipeArray = new ArrayList<>(Arrays.asList("Item 1", "Item 2", "Item 3"));
    BaseAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_finder);


        //You only need 2 lines in onCreate to actually display data:
        ListView theList = findViewById(R.id.theList);
        theList.setAdapter(myAdapter = new MyListAdapter());
        theList.setOnItemClickListener((lv, vw, pos, id) -> {

            Toast.makeText(RecipeFinder.this,
                    "You clicked on Row Number:" + pos, Toast.LENGTH_SHORT).show();

        });

        Button searchButton = findViewById(R.id.buttonSearch);
        searchButton.setOnClickListener(clik ->
        {
            recipeArray.add("Item " + (1 + recipeArray.size()));
            myAdapter.notifyDataSetChanged(); //update yourself
        });

        SwipeRefreshLayout refresher = findViewById(R.id.refresher);
        refresher.setOnRefreshListener(() -> {

            recipeArray.add("Item " + (1 + recipeArray.size()));
            myAdapter.notifyDataSetChanged(); //update yourself
            refresher.setRefreshing(false);  //get rid of spinning wheel;
        });
    }


    //Need to add 4 functions here:
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
            TextView itemText = thisRow.findViewById(R.id.itemField);
            itemText.setText("Array at:" + p + " is " + getItem(p));
            TextView numberText = thisRow.findViewById(R.id.numberField);
            numberText.setText("Your number is " + p);
            return thisRow;
        }
    }
}













