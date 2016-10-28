package com.example.android.perdiem;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by richardta on 10/12/16.
 */

public class LocationActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<jObject>> {
    private static final String BASE_REQUEST_URL = "https://inventory.data.gov/api/action/datastore_search?resource_id=8ea44bc4-22ba-4386-b84c-1494ab28964b";
    private LocationAdapter mAdapter;
    private static final int LOCATION_LOADER_ID = 1;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);

        ListView listView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);

        mAdapter = new LocationAdapter(this, new ArrayList<jObject>());

        listView.setAdapter(mAdapter);

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting()) {

            // get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // initialize the loader. Pass in the int ID constant defined above and pass in null for the
            // bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid because
            // this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(LOCATION_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText("No Internet Connection.");
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Find the current jObject that was clicked on
                jObject currentJObject = mAdapter.getItem(position);

                // Create a new intent to open PerDiemActivity
                Intent perDiemIntent = new Intent(LocationActivity.this, PerDiemActivity.class);
                perDiemIntent.putExtra("currentJSONObject", currentJObject);
                // Send the intent to launch a new activity
                startActivity(perDiemIntent);
            }
        });
    }

    @Override
    public Loader<List<jObject>> onCreateLoader(int i, Bundle bundle) {
        Uri baseUri = Uri.parse(BASE_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        Intent locationsIntent = getIntent();
        String yearSelected = (String) locationsIntent.getSerializableExtra("yearSelected");
        String zipcodeInput = (String) locationsIntent.getSerializableExtra("zipcodeInput");
        String stateSelected = (String) locationsIntent.getSerializableExtra("stateSelected");

        StringBuilder filter = new StringBuilder();
        filter.append("{\"FiscalYear\":\"");
        filter.append(yearSelected);

        if(zipcodeInput != null) {
            filter.append("\",\"Zip\":\"");
            filter.append(zipcodeInput);
        } else {
            filter.append("\",\"State\":\"");
            filter.append(stateSelected);
        }

        filter.append("\"}");

        uriBuilder.appendQueryParameter("filters", filter.toString());

        // create a new loader for the given url
        return new JObjectLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<jObject>> loader, List<jObject> jObjects) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No locations found."
        mEmptyStateTextView.setText("No Locations Found From User Input.");

        // clear the adapter of previous location data
        mAdapter.clear();

        // if there is a valid list of jObjects, then add them to the adapter's data set.
        // this will trigger the ListView to update
        if (jObjects != null && !jObjects.isEmpty()) {
            mAdapter.addAll(jObjects);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<jObject>> loader) {
        // loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
