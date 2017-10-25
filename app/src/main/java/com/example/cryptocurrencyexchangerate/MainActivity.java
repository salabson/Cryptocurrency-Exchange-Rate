package com.example.cryptocurrencyexchangerate;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cryptocurrencyexchangerate.utilities.GeneralUtils;
import com.example.cryptocurrencyexchangerate.utilities.NetworkUtils;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    // store cryptocurrency indices in spinner
    private static final int BTC_INDEX = 0;
    private static final int ETH_INDEX = 1;
    Button btn_load_card;
    Spinner spinner_cryptocurrency;
    Spinner spinner_fiatcurrency;

    ImageView cryptoImage;
    TextView tv_dispalay_rate;
    TextView tv_crypto_code;
    TextView tv_fiat_code;

    ScrollView bottom_scroll;
    ProgressBar pb_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get reference to button and linearlayout
         btn_load_card = (Button)findViewById(R.id.btn_load_card);
        final LinearLayout container = (LinearLayout)findViewById(R.id.bottom_container);

        // populate spinners with data
        fillSpinners();

        /*when user click this button it creates cryptocurrency exchange rate Card if internet is available
         otherwise it displays error message to the user that internet is not connected*/
        btn_load_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check for internet availability
                boolean isInternetAvailable = GeneralUtils.isInternetConnected(MainActivity.this);
                if (isInternetAvailable) {
                    // inflate the layout row that inserted as a card
                    LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    final View rowView = layoutInflater.inflate(R.layout.layout_row,null);

                    // get reference to Card view in the layout row
                    final CardView card_bottom = (CardView)rowView.findViewById(R.id.card_bottom);

                    // get reference to text view in the layout row
                    tv_dispalay_rate = (TextView)rowView.findViewById(R.id.tv_display_rate);

                    // get reference to progress bar layout row
                    pb_loading = (ProgressBar)rowView.findViewById(R.id.pb_loading);

                    // reference to scroll view that contain the cards
                    bottom_scroll = (ScrollView)findViewById(R.id.bottom_scroll);

                    // reference to two text views that hold currencies code to be passed to conversion activity through intent
                    tv_crypto_code = (TextView)rowView.findViewById(R.id.crypto_code);
                    tv_fiat_code = (TextView)rowView.findViewById(R.id.fiat_code) ;



                    // get reference to remove icon on layout row and set click event to remove card from the screen
                    ImageView remove_card = (ImageView) rowView.findViewById(R.id.img_remove_card);
                    remove_card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((LinearLayout)rowView.getParent()).removeView(rowView);
                        }
                    });




                    // display selected cryptocurrency image on the card
                    cryptoImage = (ImageView)rowView.findViewById(R.id.img_cryptoImage);
                    int cryptoIndex = spinner_cryptocurrency.getSelectedItemPosition();
                    displaySelectedCryptocurrencyImage(cryptoIndex);

                    // set click listener for each card view created by user and wire its click event to display conversion activity
                    card_bottom.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // if network backround is still running or it returns null display error message
                            if (TextUtils.isEmpty(tv_dispalay_rate.getText().toString())) {
                                Toast.makeText(MainActivity.this,"Please wait for data to finish loading",Toast.LENGTH_LONG).show();
                            } else {
                                // start the conversion activity along extra data of crypto and fiat currency of particular exchange rate card
                                Intent intent = new Intent(MainActivity.this,ConversionActivity.class);
                                intent.putExtra("crypto_code",tv_crypto_code.getText().toString());
                                intent.putExtra("fiat_code",tv_fiat_code.getText().toString());
                                startActivity(intent);
                            }

                        }
                    });

                    // add layout row to the main layout
                    container.addView(rowView);

                    // scroll to bottom of layout row to bring the newly created card to focus
                    bottom_scroll.post(new Runnable() {
                        @Override
                        public void run() {
                            bottom_scroll.smoothScrollBy(0, bottom_scroll.getBottom());
                        }
                    });

                    // after preparing Card UI run background network task to display current selected cryptocurrency exchange rate
                    // on the Card
                    ExchangeRateAsyncTask task = new ExchangeRateAsyncTask();
                    task.execute(spinner_cryptocurrency.getSelectedItem().toString(),spinner_fiatcurrency.getSelectedItem().toString());

                } else {
                    Toast.makeText(MainActivity.this, "Ooops! You are not connected to internet",Toast.LENGTH_LONG).show();
                }
                 }

        });



    }

    // this display appropriate cryptocurrency image
    private void displaySelectedCryptocurrencyImage(int cryptoCurrencyIndex) {
        switch (cryptoCurrencyIndex) {
            case BTC_INDEX:
                cryptoImage.setImageResource(R.drawable.ic_btc);
                break;
            case ETH_INDEX:
                cryptoImage.setImageResource(R.drawable.ic_eth);
                break;
            default:
                cryptoImage.setImageResource(R.drawable.ic_btc);
        }
    }

    private void fillSpinners() {
        // get reference to spinners
         spinner_cryptocurrency = (Spinner)findViewById(R.id.spinner_crypto);
         spinner_fiatcurrency = (Spinner)findViewById(R.id.spinner_fiat);

        // Create an ArrayAdapters using the string array and a default spinner layout
        ArrayAdapter<CharSequence> cryptoAdapter = ArrayAdapter.createFromResource(this,R.array.cryptocurrency_array,
                android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> fiatAdapter = ArrayAdapter.createFromResource(this,R.array.fiat_currency_array,
                android.R.layout.simple_spinner_item);
        //set the layout to use when the list of choices appears
        cryptoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fiatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // apply adapters to the spinners
        spinner_cryptocurrency.setAdapter(cryptoAdapter);
        spinner_fiatcurrency.setAdapter(fiatAdapter);
    }

    // this class is reponsible for managing heavy background task
    private class ExchangeRateAsyncTask extends AsyncTask<String, Void, ExchangeRate> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // disable load card button as network background request begins
            btn_load_card.setEnabled(false);
            // display progress bar as network background request begins
            pb_loading.setVisibility(View.VISIBLE);
        }



        @Override
        protected ExchangeRate doInBackground(String... strings) {
            // network data in the background
            ExchangeRate exchangeRate = null;
            if (strings != null) {
                exchangeRate = NetworkUtils.fetchCurrentExchangeRate(strings[0],strings[1]);
            }
            return exchangeRate;
        }


        @Override
        protected void onPostExecute(ExchangeRate exchangeRate) {
            super.onPostExecute(exchangeRate);
            if (exchangeRate == null) {
                tv_dispalay_rate.setText("Data Not Found");
            } else {
                // format current price to specified currency
                String formattedCurrency = GeneralUtils.GetNumberFormatForCurrencyCode(exchangeRate.getFiatCurrency(),
                        spinner_fiatcurrency.getSelectedItem().toString());
                // display it on the card
                tv_dispalay_rate.setTextSize(25);
                tv_dispalay_rate.setText(formattedCurrency);

            }

            // set the text view text property to the selected item of spinner that contain fiat currency
            tv_fiat_code.setText(spinner_fiatcurrency.getSelectedItem().toString());
            // set the text view text property to the selected item of spinner that contain crypto currency
            tv_crypto_code.setText(spinner_cryptocurrency.getSelectedItem().toString());

            // Hide progress bar as network background request completed
            pb_loading.setVisibility(View.INVISIBLE);
            // Enable load card button as network background request completed
            btn_load_card.setEnabled(true);
        }
    }

}
