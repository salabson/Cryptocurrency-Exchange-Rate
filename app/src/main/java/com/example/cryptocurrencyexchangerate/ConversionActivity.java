package com.example.cryptocurrencyexchangerate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ConversionActivity extends AppCompatActivity {
    String fiatCode;
    String cryptoSelectedItem;
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
        String fiatCode = extras.getString("fiat_code");
        String cryptoCode = extras.getString("cryto_code");

        // build the main title of the activity
        tv_main_title = (TextView)findViewById(R.id.tv_main_title);
        tv_main_title.setText("Convert " + cryptoCode + "to " + fiatCode);

        // build the main title of the activity
        tv_sub_title = (TextView)findViewById(R.id.tv_sub_title);
        tv_main_title.setText("Enter amount in " + cryptoCode);

        et_coin_amount = (EditText) findViewById(R.id.et_coin_amount);

        tv_total_amount = (TextView) findViewById(R.id.tv_total_amount);
        tv_total_amount.setText(fiatCode);
        tv_btc_rate = (TextView) findViewById(R.id.tv_btc_rate);
        tv_btc_rate.setText(cryptoCode);


    }
}
