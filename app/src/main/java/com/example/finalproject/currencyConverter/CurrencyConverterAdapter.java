package com.example.finalproject.currencyConverter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.finalproject.currencyConverter.*;

import java.util.ArrayList;

public class CurrencyConverterAdapter extends BaseAdapter {
    private ArrayList<CurrencyObject> currencyList;
    private Context mContext;

    public CurrencyConverterAdapter(Context context, ArrayList<CurrencyObject> currencyList) {
        super();
        this.mContext = context;
        this.currencyList = currencyList;
    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
