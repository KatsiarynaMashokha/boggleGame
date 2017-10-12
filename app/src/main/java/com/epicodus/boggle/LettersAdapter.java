package com.epicodus.boggle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Guest on 10/11/17.
 */

public class LettersAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mGeneratedLetters;
    private int mCurrentLayout;

    public LettersAdapter(Context mContext, ArrayList<String> mGeneratedLetters, int mCurrentLayout) {
        this.mContext = mContext;
        this.mGeneratedLetters = mGeneratedLetters;
        this.mCurrentLayout = mCurrentLayout;

    }

    @Override
    public int getCount() {
        return mGeneratedLetters.size();
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {
            gridView = inflater.inflate(mCurrentLayout, null);

            TextView letterTextView = gridView.findViewById(R.id.grid_letter);
            letterTextView.setText(mGeneratedLetters.get(position));
        } else {
            gridView = convertView;
        }
        return gridView;
    }
}
