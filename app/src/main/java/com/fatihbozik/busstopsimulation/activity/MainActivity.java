package com.fatihbozik.busstopsimulation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fatihbozik.busstopsimulation.R;

public class MainActivity extends AppCompatActivity {
    private EditText txtMaxBusCount;
    private EditText txtBusStopCount;
    private int maxBusCount;
    private int busStopCount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNext = (Button) findViewById(R.id.button_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMaxBusCount = (EditText) findViewById(R.id.txt_maxBusCount);
                txtBusStopCount = (EditText) findViewById(R.id.txt_busStopCount);

                if (txtBusStopCount.getText().length() != 0 && txtMaxBusCount.getText().length() != 0) {
                    maxBusCount = Integer.parseInt(txtMaxBusCount.getText().toString());
                    busStopCount = Integer.parseInt(txtBusStopCount.getText().toString());

                    Intent firstStageActivity = new Intent(MainActivity.this, SchedulesActivity.class);
                    firstStageActivity.putExtra("maxBusCount", maxBusCount);
                    firstStageActivity.putExtra("busStopCount", busStopCount);
                    startActivity(firstStageActivity);
                } else {
                    Toast.makeText(MainActivity.this, R.string.parameter_info, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
