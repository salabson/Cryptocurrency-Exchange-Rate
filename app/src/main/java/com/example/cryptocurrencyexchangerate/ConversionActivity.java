package com.example.cryptocurrencyexchangerate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ConversionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        Bundle top = getIntent().getExtras();
        if (top != null) {
            String topText = top.getString("top");
            TextView result = (TextView)findViewById(R.id.tv_result);
            result.setText(topText);
        }
    }
}
