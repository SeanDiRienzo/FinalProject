package com.example.finalproject.news;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.R;

import java.util.ArrayList;

import static android.view.View.GONE;


public class NewsArticleAdapter extends BaseAdapter {
    /**
     * ArrayList to store list of NewsArticleObjects
     */
    private ArrayList<NewsArticleObject> newsArticleList;
    /**
     * Parameter to store context in for use in function calls that require context
     */
    private Context mContext;
    /**
     * int layoutResourceId represents the layout id of the view to inflate
     */
    private int layoutResourceId;

    /**
     * constructor for NewsArticleAdapter, sets layoutResourceId, mcontext and populates the arraylist of newsarticleobjects
     */
    public NewsArticleAdapter(Context mContext, int layoutResourceId, ArrayList<NewsArticleObject> newsArticleList) {
        super();
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.newsArticleList = newsArticleList;

    }

    /**
     * Methods gets the count of news article objects
     */
    @Override
    public int getCount() {
        return newsArticleList.size();
    }

    /**
     * Methods gets the article item at index
     */
    @Override
    public NewsArticleObject getItem(int i) {
        return newsArticleList.get(i);
    }

    /**
     * Methods gets the id of the  article item at index
     */
    @Override
    public long getItemId(int i) {
        return (getItem(i)).getId();
    }


    /**
     * manufactures a new view to be placed into the listview as a singular row
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.text = row.findViewById(R.id.row_title);


            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }


        NewsArticleObject item = newsArticleList.get(position);

        if (!TextUtils.isEmpty(Html.fromHtml(item.getTitle()))) {

            holder.text.setText(Html.fromHtml(item.getTitle()));

        } else {

            holder.text.setVisibility(GONE);
        }


        return row;

    }

    /**
     * function to set the arrayList to be used with the NewsArticleAdapter and update the gui accordingly
     */
    public void setListData(ArrayList<NewsArticleObject> mListData) {
        this.newsArticleList = mListData;
        notifyDataSetChanged();
    }

    /**
     * viewholder class to make use of viewholder pattern
     */
    private static class ViewHolder {
        TextView text;
        ImageView imageView;
        TextView descriptionTextView;
    }
}//class
