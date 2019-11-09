package com.example.finalproject.currencyConverter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.example.finalproject.carChargingStation.CarChargingStation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CurrencyConverter extends AppCompatActivity {
    public static final String ACTIVITY_NAME = "CURRENCY_CONVERTER";
    //Some url endpoint that you may have
    String myUrl = " https://api.exchangeratesapi.io/latest?base=";
    BaseAdapter myAdapter;
    private ArrayList<CurrencyObject> currencyList = new ArrayList<>();
    JSONObject resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_converter);
        Spinner spinner_currency_in = (Spinner) findViewById(R.id.spinner_currency_in);
        Spinner spinner_currency_out = (Spinner) findViewById(R.id.spinner_currency_out);

        try {
            CurrencyService getRequest = new CurrencyService();

            resp = getRequest.execute(myUrl + "CAD").get();
            ArrayList<String> currencySymbol = new ArrayList<>();
            currencySymbol.add("CAD");
            for (Iterator<String> iter = resp.keys(); iter.hasNext(); ) {
                String key = iter.next();
                currencySymbol.add(key);
                currencyList.add(new CurrencyObject(key, resp.getString(key)));
            }
            initSpinner(spinner_currency_in, currencySymbol);
            initSpinner(spinner_currency_out, currencySymbol);
            spinner_currency_out.setSelection(3);
            EditText edt = findViewById(R.id.input_amount);
            edt.setText("1");
            onConvert(1);

            edt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        onConvert(Integer.parseInt(s.toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button btn_save = findViewById(R.id.btn_save);
        btn_save.setOnClickListener(clik ->
        {
            ListView theList = findViewById(R.id.lv_currency);
            theList.setAdapter(myAdapter = new CurrencyConverterAdapter(this, currencyList));
            myAdapter.notifyDataSetChanged();
        });
    }

    private void onConvert(int amount) throws JSONException {
        Spinner spinner_currency_in = (Spinner) findViewById(R.id.spinner_currency_in);
        Spinner spinner_currency_out = (Spinner) findViewById(R.id.spinner_currency_out);

        double result = convert(amount, spinner_currency_in.getSelectedItem().toString(), spinner_currency_out.getSelectedItem().toString());
        TextView output_amount = findViewById(R.id.output_amount);
        output_amount.setText(String.valueOf(result));
    }
    private double convert(int amount, String from, String to) throws JSONException {
       double result =((double) amount)*resp.getInt(from)/resp.getInt(to);
       return result;
    }
    private void initSpinner(Spinner spinner, ArrayList<String> currencySymbol) {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getStringArray(currencySymbol));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    // Function to convert ArrayList<String> to String[]
    public String[] getStringArray(ArrayList<String> arr) {

        // Convert ArrayList to object array
        Object[] objArr = arr.toArray();

        // convert Object array to String array
        String[] str = Arrays
                .copyOf(objArr, objArr
                                .length,
                        String[].class);

        return str;
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_charging_station: {
                Intent nextPage = new Intent(this, CarChargingStation.class);
                startActivity(nextPage);
                return true;
            }
            case R.id.nav_news: {
                return true;
            }
            case R.id.nav_recipe_finder: {
                return true;
            }
            case R.id.nav_currency: {
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public class CurrencyService extends AsyncTask<String, Void, JSONObject> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;
        ProgressDialog p;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(CurrencyConverter.this);
            p.setMessage("Please wait...It is downloading");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            String stringUrl = params[0];
            JSONObject result;
            String inputLine;
            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);
                //Create a connection
                HttpURLConnection connection = (HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                //Set our result equal to our stringBuilder
                result = new JSONObject(stringBuilder.toString()).getJSONObject("rates");
            } catch (IOException e) {
                e.printStackTrace();
                result = null;
            } catch (JSONException e) {
                e.printStackTrace();
                result = null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            Toast toast = Toast.makeText(getApplicationContext(), "Successful load currency", Toast.LENGTH_SHORT);
            toast.show();
            p.cancel();
        }
    }

}
