package com.fatihbozik.busstopsimulation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText txtMaxBusCount;
    private EditText txtBusStopCount;
    private int maxBusCount;
    private int busStopCount;

    @Override
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

                    Intent stageIntent = new Intent(MainActivity.this, StageActivity.class);
                    stageIntent.putExtra("maxBusCount", maxBusCount);
                    stageIntent.putExtra("busStopCount", busStopCount);
                    startActivity(stageIntent);
                } else {
                    Toast.makeText(MainActivity.this, R.string.parameter_info, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
