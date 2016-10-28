package com.example.android.perdiem;

/**
 * Created by richardta on 10/12/16.
 */

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.android.perdiem.MainActivity.LOG_TAG;

/**
 * Helper methods related to requesting and receiving location data.
 */


public class QueryUtils {

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {

    }

    /**
     * Return a list of {@link jObject} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<jObject> extractFeatureFromJson(String locationJSON) {
        if(TextUtils.isEmpty(locationJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding locations to
        ArrayList<jObject> standardLocation = new ArrayList<>();
        ArrayList<jObject> jObjects = new ArrayList<>();

        try {

              // build up a list of jObject objects with the corresponding data.

            JSONObject baseJsonResponse = new JSONObject(locationJSON);
            JSONObject resultObject = baseJsonResponse.getJSONObject("result");
            JSONArray locationArray = resultObject.getJSONArray("records");

            ArrayList<String> cityFilter = new ArrayList<String>();

            for(int i = 0; i < locationArray.length(); i++) {
                JSONObject currentJSONObject = locationArray.getJSONObject(i);
                String city = currentJSONObject.getString("City");
                if(city.equals("Standard Rate")) {
                    String jan = currentJSONObject.getString("Jan");
                    String feb = currentJSONObject.getString("Feb");
                    String mar = currentJSONObject.getString("Mar");
                    String apr = currentJSONObject.getString("Apr");
                    String may = currentJSONObject.getString("May");
                    String jun = currentJSONObject.getString("Jun");
                    String jul = currentJSONObject.getString("Jul");
                    String aug = currentJSONObject.getString("Aug");
                    String sep = currentJSONObject.getString("Sep");
                    String oct = currentJSONObject.getString("Oct");
                    String nov = currentJSONObject.getString("Nov");
                    String dec = currentJSONObject.getString("Dec");
                    String MIE = currentJSONObject.getString("Meals");
                    standardLocation.add(new jObject(city, "Applies for all locations without specified rates", jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec, MIE));
                    break;
                }
            }

            for(int i = 0; i < locationArray.length(); i++) {
                JSONObject currentJSONObject = locationArray.getJSONObject(i);
                String city = currentJSONObject.getString("City");
                if(cityFilter.contains(city) || city.equals("Standard Rate")) {
                    continue;
                } else {
                    cityFilter.add(city);
                }
                String zipcode = currentJSONObject.getString("Zip");
                String county = currentJSONObject.getString("County");
                String jan = currentJSONObject.getString("Jan");
                String feb = currentJSONObject.getString("Feb");
                String mar = currentJSONObject.getString("Mar");
                String apr = currentJSONObject.getString("Apr");
                String may = currentJSONObject.getString("May");
                String jun = currentJSONObject.getString("Jun");
                String jul = currentJSONObject.getString("Jul");
                String aug = currentJSONObject.getString("Aug");
                String sep = currentJSONObject.getString("Sep");
                String oct = currentJSONObject.getString("Oct");
                String nov = currentJSONObject.getString("Nov");
                String dec = currentJSONObject.getString("Dec");
                String MIE = currentJSONObject.getString("Meals");
                jObjects.add(new jObject(city, county, jan, feb, mar, apr, may, jun, jul, aug, sep, oct, nov, dec, MIE));
            }
            cityFilter.clear();

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the location JSON results", e);
        }

        // Sort the jObject object by its city name
        Collections.sort(jObjects);

        ArrayList<jObject> finalJObjects = new ArrayList<jObject>();

        finalJObjects.addAll(standardLocation);
        finalJObjects.addAll(jObjects);
        // Return the list of Locations
        return finalJObjects;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Query the URL dataset and return a list of {@link jObject} objects.
     */
    public static List<jObject> fetchLocationsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link jObjects}s
        List<jObject> jObjects = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link JObjects}s
        return jObjects;
    }


}
