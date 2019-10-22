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

import static com.example.finalproject.carChargingStation.CarChargingStation.ACTIVITY_NAME;

public class CarChargingStationAdapter extends BaseAdapter {

    private ArrayList<ChargingStationObject> chargingStationList;
    private Context mContext;
    private Boolean fullInfo;


    public CarChargingStationAdapter(Context context, ArrayList<ChargingStationObject> chargingStationList, Boolean fullInfo) {
        super();
        this.mContext = context;
        this.chargingStationList = chargingStationList;
        this.fullInfo = fullInfo;
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
        return (getItem(i)).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View newView = inflater.inflate(R.layout.activity_car_charging_station_row, viewGroup, false);

        ChargingStationObject row = getItem(i);
        TextView rowTitle = (TextView) newView.findViewById(R.id.row_title);

        if(fullInfo) {
            rowTitle.setText(row.getTitle());
        }
        else{
            rowTitle.setText(String.format("Station: %s\n Latitude: %.2f\n Longitude: %.2f\n Phone: %s", row.getTitle(), row.getLatitude(),
                    row.getLongitude(), row.getPhone()));
        }
            return newView;
    }
}
