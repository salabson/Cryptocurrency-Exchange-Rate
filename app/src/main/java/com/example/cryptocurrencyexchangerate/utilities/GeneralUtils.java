package com.example.cryptocurrencyexchangerate.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.NumberFormat;
import java.util.Currency;

/**
 * Created by salabs on 24/10/2017.
 */

public class GeneralUtils {

    // this method check for internet connectivity and return true or false depending on the state
    public static boolean isInternetConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    // this method convert a number to specified currency format
    public static String GetNumberFormatForCurrencyCode(double numberToFomart, String FiatcurrencyCode) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        Currency currency = Currency.getInstance(FiatcurrencyCode);
        format.setMaximumFractionDigits(currency.getDefaultFractionDigits());
        format.setCurrency(currency);
        return format.format(numberToFomart);
    }

    }
