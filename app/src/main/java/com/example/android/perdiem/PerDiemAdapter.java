package com.example.android.perdiem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by richardta on 10/19/16.
 */

public class PerDiemAdapter extends ArrayAdapter<String> {
    private String[] monthMealStrings;

    public PerDiemAdapter(Context context, ArrayList<String> monthsAndMeal, String[] monthMealStrings) {
        super(context, 0, monthsAndMeal);
        this.monthMealStrings = monthMealStrings;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.per_diem_row, parent, false);
        }

        String currentMonthOrMeal = getItem(position);

        TextView per_diem_month = (TextView) listItemView.findViewById(R.id.per_diem_month);

        per_diem_month.setText(monthMealStrings[position]);

        TextView per_diem_rate = (TextView) listItemView.findViewById(R.id.per_diem_rate);

        per_diem_rate.setText(currentMonthOrMeal);

        return listItemView;
    }
}
