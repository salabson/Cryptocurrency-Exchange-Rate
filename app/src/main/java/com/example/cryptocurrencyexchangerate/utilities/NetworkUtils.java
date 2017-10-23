package com.example.cryptocurrencyexchangerate.utilities;

import android.net.Uri;
import android.util.Log;

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




}
