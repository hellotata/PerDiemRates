package com.example.android.perdiem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.String.valueOf;


public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner stateSpinner = (Spinner) findViewById(R.id.state_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.us_states_entries, R.layout.spinner_state);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        stateSpinner.setAdapter(new NothingSelectedSpinnerAdapter(adapter, R.layout.spinner_state, this));

        final Spinner yearSpinner = (Spinner) findViewById(R.id.year_spinner);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.years_entry, R.layout.spinner_year);

        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);

        yearSpinner.setAdapter(adapter2);

        TextView stateZip = (TextView) findViewById(R.id.state_zip);

        stateZip.setBackgroundColor(Color.parseColor("#e0dfdf"));

        LinearLayout fiscalYear = (LinearLayout) findViewById(R.id.fiscal_year);

        fiscalYear.setBackgroundColor(Color.parseColor("#e0dfdf"));

        LinearLayout mainActivity = (LinearLayout) findViewById(R.id.activity_main);

        mainActivity.setBackgroundColor(Color.parseColor("#f1f1f1"));

        final EditText zipcode = (EditText) findViewById(R.id.zipcode);

        Button findRatesButton = (Button) findViewById(R.id.button);

        findRatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create a new intent to open the {@link LocationsActivity}
                Intent locationsIntent = new Intent(MainActivity.this, LocationActivity.class);

                int yearSelected_spinner_pos = yearSpinner.getSelectedItemPosition();
                String[] yearSizeValues = getResources().getStringArray(R.array.years_values);
                String yearSelected = valueOf(yearSizeValues[yearSelected_spinner_pos]);

                String zipcodeInput = zipcode.getText().toString();

                String stateSelected = null;
                if (stateSpinner != null && stateSpinner.getSelectedItem() != null) {
                    /* subtract 1 because the spinner was populated with a hidden index to track
                     when nothing is selected */
                    int stateSelected_spinner_pos = stateSpinner.getSelectedItemPosition() - 1;
                    String[] stateSize_values = getResources().getStringArray(R.array.us_states_values);
                    stateSelected = valueOf(stateSize_values[stateSelected_spinner_pos]);
                }

                if (zipcodeInput.matches("") && stateSelected == null) {
                    Toast.makeText(MainActivity.this, "Please select a State or enter a ZipCode.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!zipcodeInput.matches("")) {
                    locationsIntent.putExtra("yearSelected", yearSelected);
                    locationsIntent.putExtra("zipcodeInput", zipcodeInput);
                    startActivity(locationsIntent);
                    return;
                } else {
                    locationsIntent.putExtra("yearSelected", yearSelected);
                    locationsIntent.putExtra("stateSelected", stateSelected);
                    startActivity(locationsIntent);
                    return;
                }
            }
        });

    }

}