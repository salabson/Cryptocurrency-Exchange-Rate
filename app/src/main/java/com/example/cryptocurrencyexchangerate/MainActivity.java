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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    // store cryptocurrency indices in spinner
    private static final int BTC_INDEX = 0;
    private static final int ETH_INDEX = 1;

    Spinner spinner_cryptocurrency;
    Spinner spinner_fiatcurrency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get reference to button and linearlayout
        Button btn_load_card = (Button)findViewById(R.id.btn_load_card);
        final LinearLayout container = (LinearLayout)findViewById(R.id.bottom_container);

        // populate spinners data
        fillSpinners();


        btn_load_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inflate the layout row
                LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                final View rowView = layoutInflater.inflate(R.layout.layout_row,null);

                // get reference to Card view in the layout row
                final CardView card_bottom = (CardView)rowView.findViewById(R.id.card_bottom);



                // get reference to remove icon on layout row and set click event to remove card from the screen
                ImageView remove_card = (ImageView) rowView.findViewById(R.id.img_remove_card);
                remove_card.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((LinearLayout)rowView.getParent()).removeView(rowView);
                    }
                });


                TextView tv_crypto = (TextView)rowView.findViewById(R.id.tv_crypto);
                tv_crypto.setText(spinner_cryptocurrency.getSelectedItem().toString());

                // set click listener for each card view created by user and wire its click event to display conversion activity
                card_bottom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this,ConversionActivity.class);
                        intent.putExtra("top",spinner_cryptocurrency.getSelectedItem().toString());
                        startActivity(intent);
                    }
                });

                container.addView(rowView);
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
