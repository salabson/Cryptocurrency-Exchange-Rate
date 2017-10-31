package com.example.cryptocurrencyexchangerate;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cryptocurrencyexchangerate.utilities.GeneralUtils;
import com.example.cryptocurrencyexchangerate.utilities.NetworkUtils;

import org.w3c.dom.Text;

public class ConversionActivity extends AppCompatActivity {
    String fiatCode;
    String cryptoCode;
    TextView tv_btc_rate;
    EditText et_coin_amount;
    TextView tv_total_amount;
    TextView tv_main_title;
    TextView tv_sub_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        // retrive the bundles from the intent
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
          return;
        }
         fiatCode = extras.getString("fiat_code");
         cryptoCode = extras.getString("crypto_code");

        // build the main title of the activity
        tv_main_title = (TextView)findViewById(R.id.tv_main_title);
        tv_main_title.setText("Convert " + cryptoCode + " to " + fiatCode);

        // build the main title of the activity
        tv_sub_title = (TextView)findViewById(R.id.tv_sub_title);
        tv_sub_title.setText("Enter amount in " + cryptoCode);

        // edit text for entering amount of bitcoin
        et_coin_amount = (EditText) findViewById(R.id.et_coin_amount);

        // text view that display total value cryptocurrency entered in fiat currency
        tv_total_amount = (TextView) findViewById(R.id.tv_total_amount);
        tv_total_amount.setText(fiatCode);

        // this text view display rate of fiat currency per cryptocurrency
        tv_btc_rate = (TextView) findViewById(R.id.tv_btc_rate);
        tv_btc_rate.setText(cryptoCode);

        // run background network task to retrieve exchange rate information on text change
        et_coin_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String content = charSequence.toString();



            }

            @Override
            public void afterTextChanged(Editable editable) {
                // check for internet availability
                boolean isInternetAvailable = GeneralUtils.isInternetConnected(ConversionActivity.this);
                if (isInternetAvailable) {
                    // if user enter the amount of cryptocurrency then fetch the exchange rate for calculation
                    // otherwise display zero on total amount
                    ExchangeRateAsynTask task = new ExchangeRateAsynTask();
                    if (editable != null && !TextUtils.isEmpty(editable.toString())) {
                        task.execute(cryptoCode, fiatCode);
                    } else {
                        tv_total_amount.setText("0.00");
                        tv_btc_rate.setText("0.00");
                    }

                } else {
                    // display error message if user is not connected to internet
                    Toast.makeText(ConversionActivity.this, "Ooops! You are not connected to internet",Toast.LENGTH_LONG).show();

                }

            }
        });

    }
    private class ExchangeRateAsynTask extends AsyncTask<String, Void, ExchangeRate> {

        @Override
        protected ExchangeRate doInBackground(String... strings) {
            ExchangeRate exchangeRate = null;
            exchangeRate = NetworkUtils.fetchCurrentExchangeRate(strings[0], strings[1]);
            return exchangeRate;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ExchangeRate exchangeRate) {
            super.onPostExecute(exchangeRate);
            if (exchangeRate != null) {

                tv_btc_rate.setText(GeneralUtils.GetNumberFormatForCurrencyCode(exchangeRate.getFiatCurrency(),fiatCode));

                if (!TextUtils.isEmpty(et_coin_amount.getText().toString())) {
                    double crytoAmount = Double.valueOf(et_coin_amount.getText().toString());
                    double totalAmount = crytoAmount * exchangeRate.getFiatCurrency();
                    tv_total_amount.setText(GeneralUtils.GetNumberFormatForCurrencyCode(totalAmount,fiatCode));
                }


            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
