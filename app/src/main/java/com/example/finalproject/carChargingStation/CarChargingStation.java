package com.example.finalproject.carChargingStation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.example.finalproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CarChargingStation extends AppCompatActivity {
    public static final String ACTIVITY_NAME = "CAR_CHARGING_STATION";
    ArrayList<ChargingStationObject> stations = new ArrayList<ChargingStationObject>();
    public EditText longitude;
    ListView theList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_charging_station);

        Button findButton = (Button)findViewById(R.id.searchButton);
        final EditText latitude = findViewById(R.id.latitudeInput);
        longitude = findViewById(R.id.longitudeInput);
        theList = (ListView)findViewById(R.id.the_list);

        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://api.openchargemap.io/v3/poi/?output=json&countrycode=CA&latitude="
                        + latitude.getText().toString() + "&longitude=" + longitude.getText().toString() + "&maxresults=10";
                DownloadFilesTask downloadFileTask = new DownloadFilesTask();
                downloadFileTask.execute(url);
                longitude.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });

        theList.setOnItemClickListener(( parent,  view,  position,  id) ->{
       //     setContentView(R.layout.activity_car_charging_station_item_details);
            ChargingStationObject chosenOne = stations.get(position);
            Intent nextPage = new Intent(CarChargingStation.this, StationView.class);
            nextPage.putExtra("itemClicked", chosenOne);
            startActivity(nextPage);
        });

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
    private class DownloadFilesTask extends AsyncTask<String, String, String> {
        private ProgressDialog p;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(CarChargingStation.this);
            p.setMessage("Please wait...");
            p.setCancelable(false);
            p.show();
        }
        protected String doInBackground(String... urls) {
            HttpURLConnection urlConnection = null;
            String result = "";
            try {
                URL link = new URL(urls[0]);
                urlConnection = (HttpURLConnection) link.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();
                while (data != -1) {
                    result += (char) data;
                    data = reader.read();
                }
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

        protected void onPostExecute(String result) {
            p.dismiss();
            try {
                // JSON Parsing of data
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
            CarChargingStationAdapter adapter = new CarChargingStationAdapter(getApplicationContext(), stations, true);
            theList.setAdapter(adapter);

        }
    }
}

