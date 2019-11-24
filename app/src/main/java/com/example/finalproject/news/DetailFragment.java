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


public class DetailFragment extends Fragment {

    private boolean isTablet;
    private Bundle dataFromActivity;
    private long id;
    private String title;
    private String description;
    private Button goToUrlButton;
    private Button addToFavouritesButton;
    private Button openInBrowser;
    private TextView articleDescription;
    private TextView articleTitle;
    private ImageView articleImage;
    private TextView articleUrl;
    private Intent lastIntent;
    private NewsArticleObject articleObject;
    private MyDatabaseOpenHelper dbOpener;


    public void setTablet(boolean tablet) {
        isTablet = tablet;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbOpener = new MyDatabaseOpenHelper(DetailFragment.super.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        articleObject = (NewsArticleObject) bundle.getSerializable("Article");
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.activity_news_details, container, false);
        articleTitle = result.findViewById(R.id.details_title);
        articleDescription = result.findViewById(R.id.details_description);
        articleImage = result.findViewById(R.id.details_image);
        articleUrl = result.findViewById(R.id.url_textview);




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
