package com.example.cryptocurrencyexchangerate.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by salabs on 23/10/2017.
 */

public class NetworkUtils {
    // get this class name
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    // create urls details that will use to connect to web API
    final static String CRYPTOCOMPARE_BASE_URL = "https://min-api.cryptocompare.com/data/price";
    final  static String PARAM_FSYM ="fsym";
    final  static String PARAM_TSYMS ="tsyms";

    // create complete url object by combining base url and query parameters
    private static URL buildURL(String cryptoCurrency, String fiatCurrency ) {
        Uri builtUri = Uri.parse(CRYPTOCOMPARE_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_FSYM,cryptoCurrency)
                .appendQueryParameter(PARAM_TSYMS,fiatCurrency)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }

        return url;
    }

// take url and make http request
private static String executeHttpRequest(URL url) throws IOException {
    String response = null;
    // retun null if url is null
    if (url == null) {
        return response;
    }

    // define input stream and connection variable
    InputStream inputStream = null;
    HttpURLConnection urlConnection = null;

    try {
        // connect to internet
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        // read input steam return from the server if the connection is successful
        if (urlConnection.getResponseCode()== 200) {
            inputStream = urlConnection.getInputStream();
            response = readInputStream(inputStream);
            Log.e(LOG_TAG, " Response data." + response);
        }else {
            Log.e(LOG_TAG, "Error response code." + urlConnection.getResponseCode());
        }

    } catch (IOException e) {
        Log.e(LOG_TAG,"Error in retrieving cryptocurrency price data", e);

    }finally {
        // terminate the connection to save resource
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
        // close the input stream
        if (inputStream != null) {
            inputStream.close();
        }
    }
    // return string response
    return response;
}
// this convert input stream to json string
    private static String readInputStream(InputStream inputStream) throws IOException {
        String jsonReponse = null;
        StringBuilder result = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                result.append(line);
                line = bufferedReader.readLine();
            }

        }

        return jsonReponse = result.toString();

    }


}
