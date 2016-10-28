package com.example.android.perdiem;


import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by richardta on 10/12/16.
 */

public class JObjectLoader extends AsyncTaskLoader<List<jObject>> {
    private String mUrl;

    public JObjectLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<jObject> loadInBackground() {
        if(mUrl == null) {
            return null;
        }
        // perform the network request, parse the response, and extract a list of earthquakes
        List<jObject> jObjects = QueryUtils.fetchLocationsData(mUrl);

        return jObjects;
    }
}
