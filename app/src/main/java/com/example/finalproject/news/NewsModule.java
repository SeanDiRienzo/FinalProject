package com.example.finalproject.news;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import static com.google.android.material.snackbar.Snackbar.LENGTH_SHORT;


public class NewsModule extends AppCompatActivity {
    public static final String ACTIVITY_NAME = "NEWS";
    private NewsArticleAdapter adapter;
    private ArrayList<NewsArticleObject> newsArticleList;
    private ListView newsArticleListView;
    private Button searchButton;
    private Button favouritesButton;
    private EditText searchEditText;
    private String NEWS_URL;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        String searchInput;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        newsArticleList = new ArrayList<>();
        searchEditText = findViewById(R.id.search_editText);
        searchButton = findViewById(R.id.searchButton);
        favouritesButton = findViewById(R.id.goToFavourites);
        newsArticleListView = findViewById(R.id.articlesListView);
        adapter = new NewsArticleAdapter(this, R.layout.news_row, newsArticleList);
        adapter.setListData(newsArticleList);
        newsArticleListView.setAdapter(adapter);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showSnackbar(view, ("Searching: " + searchEditText.getText().toString()), LENGTH_SHORT);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchButton.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                NEWS_URL = "https://newsapi.org/v2/everything?q=" + searchEditText.getText().toString() + "&apiKey=fbdf04dd637a47b087b87dd6959ecc5a";



                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(NewsModule.this);
                alertBuilder.setTitle("Search : " + searchEditText.getText());
                alertBuilder.setMessage("Clear current view or add new search to current view");
                alertBuilder.setNegativeButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        new AsyncHttpTask().execute(NEWS_URL);
                    }
                });

                alertBuilder.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapter.clear();
                        new AsyncHttpTask().execute(NEWS_URL);

                    }
                });
                if (newsArticleList.size() > 0) {
                    alertBuilder.show();
                }else {
                    new AsyncHttpTask().execute(NEWS_URL);
                }

                adapter.notifyDataSetChanged();


            }
        });


        newsArticleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsArticleObject item = (NewsArticleObject) parent.getItemAtPosition(position);
                startDetailsactivity(item);
            }
        });


    }

    public void saveSearch(View view) {
        SharedPreferences sharedPref = getSharedPreferences("searchField", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("searchField", searchEditText.getText().toString());
        editor.apply();
    }

    public void displayLastSearch(View view) {
        SharedPreferences sharedPref = getSharedPreferences("searchField", Context.MODE_PRIVATE);
        String searchField = sharedPref.getString("SearchField", "");
        searchEditText.setText(searchField);

    }

    public class AsyncHttpTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpsURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);

                urlConnection = (HttpsURLConnection) url.openConnection();


                if (result != null) {

                    String response = streamToString(urlConnection.getInputStream());


                    parseResult(response);


                    return result;


                }
            } catch (MalformedURLException e) {
                e.printStackTrace();


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            // Download complete. Let us update UI
            if (result != null) {
                adapter.notifyDataSetChanged();

                Toast.makeText(NewsModule.this, "Data Loaded", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(NewsModule.this, "Failed to load data!", Toast.LENGTH_SHORT).show();
            }

        }
    }


    String streamToString(InputStream newsStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(newsStream));
        String data;
        String result = "";

        while ((data = bufferedReader.readLine()) != null) {
            result += data;
        }

        if (null != newsStream) {
            newsStream.close();
        }


        return result;
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("articles");
            NewsArticleObject item;
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                String title = post.optString("title");
                String image = post.optString("urlToImage");
                String description = post.optString("description");
                String url = post.optString("url");
                item = new NewsArticleObject();
                item.setTitle(title);
                item.setImageUrl(image);
                item.setArticleUrl(url);
                item.setDescription(description);

                newsArticleList.add(item);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void startDetailsactivity(NewsArticleObject item) {
        Intent detailsActivity = new Intent(this, NewsDetails.class);
        detailsActivity.putExtra("articleObject", item);

        startActivity(detailsActivity);
    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

