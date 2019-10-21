package com.example.finalproject.carChargingStation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.finalproject.carChargingStation.*;

import java.util.ArrayList;

public class CarChargingStationAdapter extends BaseAdapter {

    private ArrayList<ChargingStationObject> chargingStationList;
    private Context mContext;

    public CarChargingStationAdapter(Context context, ArrayList<ChargingStationObject> chargingStationList) {
        super();
        this.mContext = context;
        this.chargingStationList = chargingStationList;
    }
    @Override
    public int getCount() {
        return chargingStationList.size();
    }

    @Override
    public ChargingStationObject getItem(int i) {
        return chargingStationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View newView = inflater.inflate(R.layout.activity_car_charging_station_row, viewGroup, false);

        ChargingStationObject row = getItem(i);
        TextView rowTitle = (TextView)newView.findViewById(R.id.row_title);

        rowTitle.setText(row.getTitle());
        //return the row:
        return newView;
    }
}
