package com.example.finalproject.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.finalproject.R;
import com.example.finalproject.carChargingStation.*;
import java.util.ArrayList;
import static com.example.finalproject.carChargingStation.CarChargingStation.ACTIVITY_NAME;


public class NewsArticleAdapter extends BaseAdapter {

    private ArrayList<NewsArticleObject> newsArticleList;

    private Context mContext;

    public NewsArticleAdapter(Context context, ArrayList<NewsArticleObject> newsArticleList) {
        super();
        this.mContext = context;
        this.newsArticleList = newsArticleList;

    }


    @Override
    public int getCount() {
        return newsArticleList.size();
    }

    /**
     * Methods gets a car charging station from a list
     * @param i position of a station in a list
     * @return ChargingStationObject car charging station
     */
    @Override
    public NewsArticleObject getItem(int i) {
        return newsArticleList.get(i);
    }


    @Override
    public long getItemId(int i) {
        return (getItem(i)).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsArticleObject newsRow = this.getItem(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewHolder holder = new ViewHolder();

        convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_news_row, parent, false);
        holder.text = convertView.findViewById(R.id.row_title);

        convertView.setTag(holder);
        holder.text.setText(newsRow.getDescription());
        return convertView;


    }
    private static class ViewHolder {
        TextView text;
    }
}//class
