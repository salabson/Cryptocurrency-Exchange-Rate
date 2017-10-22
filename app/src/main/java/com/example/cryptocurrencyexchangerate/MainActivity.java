package com.example.cryptocurrencyexchangerate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    Spinner spinner_cryptocurrency;
    Spinner spinner_fiatcurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get reference to button and linearlayout
        Button bn_load_card = (Button)findViewById(R.id.btn_load_card);
        final LinearLayout container = (LinearLayout)findViewById(R.id.bottom_container);

        // poulate spinners
        fillSpinners();


        bn_load_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                final View cardView = layoutInflater.inflate(R.layout.layout_row,null);
                final CardView card_bottom = (CardView)cardView.findViewById(R.id.card_bottom);
                card_bottom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this,ConversionActivity.class);
                        intent.putExtra("top",spinner_cryptocurrency.getSelectedItem().toString());
                        startActivity(intent);
                    }
                });

                container.addView(cardView);
            }
        });

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
}
