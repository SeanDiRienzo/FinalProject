package com.example.finalproject.recipeFinder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.content.Context;

import com.example.finalproject.recipeFinder.RecipeObject;

import java.util.ArrayList;

public class RecipeAdapter extends BaseAdapter {
    private ArrayList<RecipeObject> recipeList;
    private Context mContext;

    public RecipeAdapter(Context context, ArrayList<RecipeObject> recipeList) {
        super();
        this.mContext = context;
        this.recipeList = recipeList;
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