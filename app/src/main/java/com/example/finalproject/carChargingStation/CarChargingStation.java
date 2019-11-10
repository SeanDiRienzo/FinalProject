package com.example.finalproject.carChargingStation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.finalproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity accepts latitude and longitude input from the user, searches for car charging stations and displays results on the screen
 */
public class CarChargingStation extends AppCompatActivity {
    /**
     * name of current activity
     */
    public static final String ACTIVITY_NAME = "CAR_CHARGING_STATION";
    /**
     * list of car charging stations
     */
    ArrayList<ChargingStationObject> stations = new ArrayList<ChargingStationObject>();
    /**
     * Field from the screen accepts longitude from a user
     */
    private EditText longitude;
    /**
     * List with car charging stations items from the screen
     */
    private ListView theList;
    /**
     * shared preferences instance
     */
    private SharedPreferences sharedPref;
    private ProgressBar progressBar;
    /**
     * Method loads layout, reacts to user's action
     * @param savedInstanceState reference to a Bundle object that is passed into the onCreate method
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_charging_station);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        Button findButton = (Button)findViewById(R.id.searchButton);
        final EditText latitude = findViewById(R.id.latitudeInput);
        longitude = (EditText)findViewById(R.id.longitudeInput);
        theList = (ListView)findViewById(R.id.the_list);
        sharedPref = getSharedPreferences("SharedPreferencesCarChargingStation", MODE_PRIVATE);
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.openchargemap.io/v3/poi/?output=json&countrycode=CA&latitude="
                        + latitude.getText().toString() + "&longitude=" + longitude.getText().toString() + "&maxresults=10";
                DownloadFilesTask downloadFileTask = new DownloadFilesTask();
                downloadFileTask.execute(url);
                longitude.onEditorAction(EditorInfo.IME_ACTION_DONE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("Latitude", latitude.getText().toString());
                editor.putString("Longitude", longitude.getText().toString());
                editor.commit();
            }
        });
        theList.setOnItemClickListener(( parent,  view,  position,  id) ->{
            ChargingStationObject chosenOne = stations.get(position);
            Intent nextPage = new Intent(CarChargingStation.this, StationView.class);
            nextPage.putExtra("itemClicked", chosenOne);
            startActivity(nextPage);
        });
        String latitudeValue = sharedPref.getString("Latitude", "");
        latitude.setText(latitudeValue);
        String longitudeValue = sharedPref.getString("Longitude", "");
        longitude.setText(longitudeValue);
    }

    /**
     * Class connects to the server, reads and process the data
     */
    private class DownloadFilesTask extends AsyncTask<String, Integer, String> {
        /**
         * dialog to show a progression of downloading to the user
         */
        //private ProgressDialog p;

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);

            //Update GUI stuff only:
        }
        /**
         * Method shows a progression of downloading data from the server to the user
         */
  /*      @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(CarChargingStation.this);
            p.setMessage("Please wait...");
            p.setCancelable(false);
            p.show();
        }
     */
       /**
         * Methods connects to the server, retrieves the data about car charging stations
         * @param urls link to the server
         * @return data about car charging stations
         */
        protected String doInBackground(String... urls) {
            HttpURLConnection urlConnection = null;
            String result = "";
            try {
                URL link = new URL(urls[0]);
                urlConnection = (HttpURLConnection) link.openConnection();
                publishProgress(25);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                InputStream in = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                publishProgress(50);
                String line = null;
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                publishProgress(70);
                return result;
            }
                catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return result;
        }
        /**
         * Method gets car charging stations and displays them on the screen as a list of items
         * @param result data about car charging stations retrieved by doInBackground method()
         */
        protected void onPostExecute(String result) {
            stations.clear(); // remove old results
            try {
                JSONArray jsonArray = new JSONArray(result);
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject stationJSON = jsonArray.getJSONObject(i);
                    JSONObject addressJSON = stationJSON.getJSONObject("AddressInfo");

                    ChargingStationObject stationObject = new ChargingStationObject();
                    stationObject.setTitle(addressJSON.getString("Title"));
                    stationObject.setLatitude(addressJSON.getDouble("Latitude"));
                    stationObject.setLongitude(addressJSON.getDouble("Longitude"));
                    stationObject.setPhone(addressJSON.getString("ContactTelephone1"));
                    stations.add(stationObject);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CarChargingStationAdapter adapter = new CarChargingStationAdapter(getApplicationContext(), stations, false);
            theList.setAdapter(adapter);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}

