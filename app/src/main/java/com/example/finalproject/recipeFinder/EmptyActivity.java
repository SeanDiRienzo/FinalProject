package com.example.finalproject.recipeFinder;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import com.example.finalproject.R;
import androidx.appcompat.app.AppCompatActivity;

public class EmptyActivity extends AppCompatActivity {
    /**
     * @param ACTIVITY_NAME; Class variable for Debug activity name
     */
    public static final String ACTIVITY_NAME = "EMPTY_ACTIVITY: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_fragment);

        Bundle dataToPass = getIntent().getExtras(); //get the data that was passed from FragmentExample

        ReciperFinder_DetailFragment dFragment = new ReciperFinder_DetailFragment();
        dFragment.setArguments(dataToPass); //pass data to the the fragment
        dFragment.setTablet(false); //tell the Fragment that it's on a phone.
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentLocation, dFragment)
                .addToBackStack("AnyName")
                .commit();

        Button buttonSaveRecipe = (Button) findViewById(R.id.saveRecipeButton);
        buttonSaveRecipe.setOnClickListener(clk -> {
            // Save Recipe
        }); // End Save button
    } // End onCreate()

    @Override
    public void onStart() {
        Log.e(ACTIVITY_NAME, "%%%%%%%%%%% onStart()");
        super.onStart();
        //CALL THE DATABASE HELPER CLASS
        MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper(this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();
        Toast.makeText(this, "Database Opened and Acquired!!", Toast.LENGTH_SHORT).show();

        //QUERY ALL THE RESULTS FROM THE DATABASE:
        String[] columns = {MyDatabaseOpenHelper.COL_ID, MyDatabaseOpenHelper.COL_RECIPE, MyDatabaseOpenHelper.FLAG,};
        //INITIALIZE CURSORS
        Cursor cursor = db.query(false, MyDatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);
    }

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
    protected void onPause() {
        Log.e(ACTIVITY_NAME, "onPause()");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(ACTIVITY_NAME, "onStop()");
        super.onStop();
    }
} // END RecipeFinder.java

