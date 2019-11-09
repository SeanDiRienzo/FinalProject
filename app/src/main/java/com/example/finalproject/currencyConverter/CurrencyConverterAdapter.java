package com.example.finalproject.currencyConverter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.finalproject.R;

import java.util.ArrayList;

public class CurrencyConverterAdapter extends BaseAdapter {
    private ArrayList<CurrencyObject> currencyList;
    private Context mContext;

    public CurrencyConverterAdapter(Context context, ArrayList<CurrencyObject> currencyList) {
        super();
        this.mContext = context;
        this.currencyList = currencyList;
    }
    public int getCount() {
        return this.currencyList.size();  } //This function tells how many objects to show

    public CurrencyObject getItem(int position) {
        return this.currencyList.get(position);  }  //This returns the string at position p

    public long getItemId(int p) {
        return p; } //This returns the database id of the item at position p

    public View getView(int p, View recycled, ViewGroup parent)
    {
        View thisRow = recycled;
        CurrencyObject mesg = getItem(p);
        thisRow = ((Activity) this.mContext).getLayoutInflater().inflate(R.layout.currency_row_layout, null);

        TextView txt_currency = thisRow.findViewById(R.id.txt_currency);
        txt_currency.setText(mesg.getName());

        TextView txt_rate = thisRow.findViewById(R.id.txt_rate);
        txt_rate.setText(mesg.getRate());

        return thisRow;
    }
}
