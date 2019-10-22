package com.example.finalproject.carChargingStation;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

import java.util.ArrayList;

public class CarChargingFavouriteStations extends AppCompatActivity {
    ArrayList<ChargingStationObject> favStations = new ArrayList<ChargingStationObject>();
    public int positionClicked;
    CarChargingStationAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_charging_station_favourites);

        ListView listOfFavourites = (ListView)findViewById(R.id.listOfFavourites);

        MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper(this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();

        //query all the results from the database:
        String [] columns = {MyDatabaseOpenHelper.COL_ID, MyDatabaseOpenHelper.COL_TITLE, MyDatabaseOpenHelper.COL_LATITUDE,
                MyDatabaseOpenHelper.COL_LONGITUDE, MyDatabaseOpenHelper.COL_PHONE};
        Cursor results = db.query(false, MyDatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);

        //find the column indices:
        int titleColumnIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_TITLE);
        int latitudeColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_LATITUDE);
        int idColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ID);
        int longitudeColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_LONGITUDE);
        int phoneColIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_PHONE);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            String title = results.getString(titleColumnIndex);
            double latitude = results.getDouble(latitudeColIndex);
            double longitude = results.getDouble(longitudeColIndex);
            long id = results.getLong(idColIndex);
            String phone = results.getString(phoneColIndex);

            //add the new Contact to the array list:
            favStations.add(new ChargingStationObject(id, title, latitude, longitude, phone));
            adapter = new CarChargingStationAdapter(getApplicationContext(), favStations, false);
            listOfFavourites.setAdapter(adapter);
        }

        listOfFavourites.setOnItemClickListener(( parent,  view,  position,  id) -> {
                    Log.e("you clicked on :", "item " + position);
                    //save the position in case this object gets deleted or updated
                    positionClicked = position;
        });

        Button delete = (Button)findViewById(R.id.deleteButton);
        delete.setOnClickListener(clk -> {
            ChargingStationObject stationToDelete = favStations.get(positionClicked);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            //This is the builder pattern, just call many functions on the same object:
            AlertDialog dialog = builder.setTitle("Alert!")
                    .setMessage("Do you want to delete?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //If you click the "Delete" button
                            favStations.remove(positionClicked);
                            adapter.notifyDataSetChanged();
                            int numDeleted = db.delete(MyDatabaseOpenHelper.TABLE_NAME,
                                    MyDatabaseOpenHelper.COL_ID + "=?", new String[] {Long.toString(stationToDelete.getId())});

                            Log.i("ViewContact", "Deleted " + numDeleted + " rows");

                            //set result to PUSHED_DELETE to show clicked the delete button
                           // setResult(PUSHED_DELETE);
                            //go back to previous page:
                            finish();
                        }
                    })
                    //If you click the "Cancel" button:
                    .setNegativeButton("Cancel", (d,w) -> {  /* nothing */})
                    .create();

            //then show the dialog
            dialog.show();

        });


    }
}
