package com.fatihbozik.busstopsimulation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.util.Random;

public class StageActivity extends AppCompatActivity {
    //    private TextView imaginaryClock;
    int busStopsDistances[];
    int remainTimes[]; // Otobüslerin kaç saniye sonra duraklara varacağını tutacak.
    int simulationTime = 0;  // Simulasyon kaç saniye sürecek. Servis o kadar çalıştırılacak.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);

        final int busStopCount = getIntent().getIntExtra("busStopCount", 5);
        busStopsDistances = distanceBetweenStops(busStopCount - 1);

        final int maxBusCount = getIntent().getIntExtra("maxBusCount", 1);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new TextAdapter(this, busStopsDistances));

        for (int time : remainTimes) simulationTime += time;

        Button startButton = (Button) findViewById(R.id.simulation_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent driverActivity = new Intent(StageActivity.this, ServiceDriver.class);
                driverActivity.putExtra("busStopCount", busStopCount);
                driverActivity.putExtra("maxBusCount", maxBusCount);
                driverActivity.putExtra("simulationTime", simulationTime);
                driverActivity.putExtra("distances", busStopsDistances);
                startActivity(driverActivity);
            }
        });
    }

    int[] distanceBetweenStops(int howMany) {
        int[] dizi = generateRandomNumber(howMany, 5);
        remainTimes = dizi;

        for (int i = 0; i < dizi.length; i++) {
            dizi[i] *= 50;
        }

        return dizi;
    }

    int[] generateRandomNumber(int times, int number) {
        Random rnd = new Random();
        int[] dizi = new int[times];
        for (int i = 0; i < times; i++) {
            dizi[i] = rnd.nextInt(number) + 1; // [1, n] arası rastgele bir sayı üret.
        }
        return dizi;
    }
}
