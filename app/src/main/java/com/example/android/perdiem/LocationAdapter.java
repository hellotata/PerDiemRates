package com.example.android.perdiem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by richardta on 10/12/16.
 */

public class LocationAdapter extends ArrayAdapter<jObject> {

    public LocationAdapter(Context context, ArrayList<jObject> jObjects) {
        super(context, 0, jObjects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.location_row, parent, false);
        }

        jObject currentJObject = getItem(position);

        TextView cityView = (TextView) listItemView.findViewById(R.id.city);

        cityView.setText(currentJObject.getCity());

        TextView countyView = (TextView) listItemView.findViewById(R.id.county);

        String county = currentJObject.getCounty();

        countyView.setText(county);

        return listItemView;
    }
}
