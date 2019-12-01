package com.example.finalproject.news;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.finalproject.R;
import com.squareup.picasso.Picasso;

/**
 * Details Fragment to display details about a selected article
 */
public class DetailFragment extends Fragment {

    /**
     * Boolean parameter to identify if the device is a tablet or not
     */
    private boolean isTablet;
    /**
     * Bundle that is used to pack a serializable object into to pass througrh intent / fragment trasnaction
     */
    private Bundle dataFromActivity;


    /**
     * Button addToFavouritesButton button with onclicklistener to add the article to list of favourites
     */

    private Button addToFavouritesButton;
    /**
     * Button openInBrowser button with onclicklistener to open the article in a web browser
     */
    private Button openInBrowser;

    /**
     * textview articleDescription to display the text description of the article
     */
    private TextView articleDescription;
    /**
     * textview articleTitle to display the text title of the article
     */
    private TextView articleTitle;
    /**
     * imageview articleImage to display the preview image of the article
     */
    private ImageView articleImage;
    /**
     * textview articleUrl to display the text URL of the article
     */
    private TextView articleUrl;
    /**
     * NewsArticleObject articleObject to store the article object passed through fragment transaction
     */
    private NewsArticleObject articleObject;
    /**
     * MyDatabaseOpenHelper dbOpener to manage my sqlite database
     */
    private MyDatabaseOpenHelper dbOpener;

    private TextView titleText;
    private TextView descriptionText;
    private TextView urlText;

    /**
     * check which layout to use
     */
    public void setTablet(boolean tablet) {

        isTablet = tablet;
    }

    /**
     * oncreate method for fragment, initialize dbOpener
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbOpener = new MyDatabaseOpenHelper(DetailFragment.super.getActivity());
    }

    /**
     * oncreateview, perform setup of fragment's ui
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // get arguments from fragment manager
        dataFromActivity = getArguments();
        // create article object from serializable object data
        articleObject = (NewsArticleObject) dataFromActivity.getSerializable("Article");
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.activity_news_details, container, false);
        articleTitle = result.findViewById(R.id.details_title);
        articleDescription = result.findViewById(R.id.details_description);
        articleImage = result.findViewById(R.id.details_image);
        articleUrl = result.findViewById(R.id.url_textview);
        titleText = result.findViewById(R.id.details_title_header);
        descriptionText = result.findViewById(R.id.details_description_header);


        articleTitle.setText(articleObject.getTitle());
        articleDescription.setText(articleObject.getDescription());
        Picasso.get().load(articleObject.getImageUrl()).into(articleImage);
        articleUrl.setText(articleObject.getArticleUrl());
        addToFavouritesButton = result.findViewById(R.id.add_to_favourites_button);

        SQLiteDatabase db = dbOpener.getWritableDatabase();
        addToFavouritesButton.setOnClickListener(fav -> {

            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyDatabaseOpenHelper.COL_TITLE, articleObject.getTitle());
            newRowValues.put(MyDatabaseOpenHelper.COL_DESCRIPTION, articleObject.getDescription());
            newRowValues.put(MyDatabaseOpenHelper.COL_ARTICLEURL, articleObject.getArticleUrl());
            newRowValues.put(MyDatabaseOpenHelper.COL_IMAGEURL, articleObject.getImageUrl());
            long newId = db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailFragment.super.getActivity());
            AlertDialog dialog = builder.setMessage("Added Article to Favourites")
                    .setPositiveButton("OK", (d, w) -> {  /* nothing */})
                    .create();
            dialog.show();

        });

        openInBrowser = result.findViewById(R.id.go_to_url_button);
        openInBrowser.setOnClickListener(browser -> {

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleObject.getArticleUrl()));
            startActivity(browserIntent);

        });

        return result;
    }
}
