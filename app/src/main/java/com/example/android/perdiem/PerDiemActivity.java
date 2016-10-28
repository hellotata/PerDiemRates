package com.example.android.perdiem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by richardta on 10/19/16.
 */

public class PerDiemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.per_diem_layout);

        ListView listView = (ListView) findViewById(R.id.per_diem_list);

        Intent perDiemIntent = getIntent();
        jObject jObject = (jObject) perDiemIntent.getSerializableExtra("currentJSONObject");

        ArrayList<String> perDiemRates = new ArrayList<String>();

        perDiemRates.add("$ "+jObject.getMIE());
        perDiemRates.add("$ "+jObject.getJan());
        perDiemRates.add("$ "+jObject.getFeb());
        perDiemRates.add("$ "+jObject.getMar());
        perDiemRates.add("$ "+jObject.getApr());
        perDiemRates.add("$ "+jObject.getMay());
        perDiemRates.add("$ "+jObject.getJun());
        perDiemRates.add("$ "+jObject.getJul());
        perDiemRates.add("$ "+jObject.getAug());
        perDiemRates.add("$ "+jObject.getSep());
        perDiemRates.add("$ "+jObject.getOct());
        perDiemRates.add("$ "+jObject.getNov());
        perDiemRates.add("$ "+jObject.getDec());

        String[] monthMealStrings = getResources().getStringArray(R.array.monthsMeals);

        PerDiemAdapter perDiemAdapter = new PerDiemAdapter(this, perDiemRates, monthMealStrings);

        listView.setAdapter(perDiemAdapter);
    }
}
