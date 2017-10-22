package com.example.cryptocurrencyexchangerate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bn_load = (Button)findViewById(R.id.bn_load);
        final LinearLayout container = (LinearLayout)findViewById(R.id.container);

        bn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                final View cardView = layoutInflater.inflate(R.layout.layout_row,null);
                TextView tv_crypto = (TextView)cardView.findViewById(R.id.tv_crypto);
                tv_crypto.setText("Cryptocurrency");
                container.addView(cardView);
            }
        });

    }
}
