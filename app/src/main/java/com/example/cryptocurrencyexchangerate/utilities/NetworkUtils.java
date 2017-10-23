package com.example.cryptocurrencyexchangerate.utilities;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by salabs on 23/10/2017.
 */

public class NetworkUtils {
    // create urls details that will use to connect to web API
    final static String CRYPTOCOMPARE_BASE_URL = "https://min-api.cryptocompare.com/data/price";
    final  static String PARAM_FSYM ="fsym";
    final  static String PARAM_TSYM ="tsyms";

    // create complete urls by combining base url and query parameters
    private static URL buildURL(String baseCurrency, String otherCurrency ) {
        Uri builtUri = Uri.parse(CRYPTOCOMPARE_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_FSYM,baseCurrency)
                .appendQueryParameter(PARAM_TSYM,otherCurrency)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
        }

        return url;
    }

}
