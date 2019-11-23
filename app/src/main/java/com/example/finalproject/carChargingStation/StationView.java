package com.example.finalproject.carChargingStation;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

import java.util.Locale;

/**
 * Class shows car charging station's details, loads station on a google map
 */
public class StationView extends AppCompatActivity {
    /**
     * Method loads station's details on the screen, loads station on a google map, adds station to the list of favourite stations
     * @param savedInstanceState reference to a Bundle object that is passed into the onCreate method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_charging_station_item_details);
        ChargingStationObject itemClicked = (ChargingStationObject) getIntent().getSerializableExtra("itemClicked");

        TextView title = (TextView) findViewById(R.id.title);
        String stationTitle = itemClicked.getTitle();
        title.setText(stationTitle);

        TextView latitude = (TextView) findViewById(R.id.latitudeResult);
        double latitudeValue = itemClicked.getLatitude();
        latitude.setText(Double.toString(latitudeValue));

        TextView longitude = (TextView) findViewById(R.id.longitudeResult);
        double longitudeValue = itemClicked.getLongitude();
        longitude.setText(Double.toString(longitudeValue));

        TextView phone = (TextView) findViewById(R.id.phoneResult);
        String phoneNumber = itemClicked.getPhone();
        if(phoneNumber.equals("null")){
            phone.setText(R.string.phoneNull);
        }
        else {
            phone.setText(phoneNumber);
        }
        Button loadMapBtn = (Button) findViewById(R.id.loadMap);
        loadMapBtn.setOnClickListener(clk -> {
            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", latitudeValue, longitudeValue);
            Uri intentUri = Uri.parse(uri);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, intentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });
        MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper(this);
        SQLiteDatabase db = dbOpener.getWritableDatabase();
        Button addBtn = (Button) findViewById(R.id.addButton);
        addBtn.setOnClickListener(click -> {
            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyDatabaseOpenHelper.COL_TITLE, stationTitle);
            newRowValues.put(MyDatabaseOpenHelper.COL_LATITUDE, latitudeValue);
            newRowValues.put(MyDatabaseOpenHelper.COL_LONGITUDE, longitudeValue);
            newRowValues.put(MyDatabaseOpenHelper.COL_PHONE, phoneNumber);
            long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog dialog = builder.setMessage("Station was added to the list of favourite stations")
                    .setPositiveButton("OK", (d,w) -> {  /* nothing */})
                    .create();
                    dialog.show();
        });
        Button seeListBtn = (Button)findViewById(R.id.listOfFavouritesButton);
        seeListBtn.setOnClickListener(clk -> {
            Intent nextPage = new Intent(StationView.this, CarChargingFavouriteStations.class);
            startActivity(nextPage);
        });
    }
}
