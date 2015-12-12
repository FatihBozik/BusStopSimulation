package com.fatihbozik.busstopsimulation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText txtMaxBusCount;
    private EditText txtBusStopCount;
    private int maxBusCount;
    private int busStopCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnContinue = (Button) findViewById(R.id.button_next);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMaxBusCount = (EditText) findViewById(R.id.txt_maxBusCount);
                maxBusCount = Integer.parseInt(txtMaxBusCount.getText().toString());

                txtBusStopCount = (EditText) findViewById(R.id.txt_busStopCount);
                busStopCount = Integer.parseInt(txtBusStopCount.getText().toString());

                Intent stopIntent = new Intent(MainActivity.this, StopActivity.class);
                stopIntent.putExtra("maxBusCount", maxBusCount);
                stopIntent.putExtra("busStopCount", busStopCount);
                startActivity(stopIntent);
            }
        });
    }
}
