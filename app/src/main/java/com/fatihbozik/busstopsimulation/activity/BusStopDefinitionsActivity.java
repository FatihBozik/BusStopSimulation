package com.fatihbozik.busstopsimulation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.fatihbozik.busstopsimulation.R;
import com.fatihbozik.busstopsimulation.adapter.TextAdapter;

import java.util.Random;

public class BusStopDefinitionsActivity extends AppCompatActivity {
    int busStopsDistances[];
    int remainTimes[];       // Otobüslerin kaç saniye sonra duraklara varacağını tutacak.
    int simulationTime = 0;  // Simulasyon kaç saniye sürecek. Servis o kadar çalıştırılacak.
    int enBuyuk;
    int[] list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop_definitions);
        final int busStopCount = getIntent().getIntExtra("busStopCount", 5);
        list = new int[busStopCount];
        busStopsDistances = distanceBetweenStops(busStopCount - 1);


        final int maxBusCount = getIntent().getIntExtra("maxBusCount", 1);
        final int[] busStartTime = getIntent().getIntArrayExtra("busStartTime");
        enBuyuk = getIntent().getIntExtra("enBuyuk", 0);
        final Bundle extras = getIntent().getExtras();

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new TextAdapter(this, busStopsDistances, 1));

        for (int time : remainTimes) simulationTime += time;

        Button startButton = (Button) findViewById(R.id.simulation_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent driverActivity = new Intent(BusStopDefinitionsActivity.this, ServiceDriver.class);
                driverActivity.putExtra("busStopCount", busStopCount);
                driverActivity.putExtra("maxBusCount", maxBusCount);
                driverActivity.putExtra("simulationTime", simulationTime + enBuyuk + 1);
                driverActivity.putExtra("distances", busStopsDistances);
                driverActivity.putExtra("busStartTime", busStartTime);
                driverActivity.putExtra("list", list);
                driverActivity.putExtras(extras);
                startActivity(driverActivity);
            }
        });
    }

    int[] distanceBetweenStops(int howMany) {
        int[] dizi = generateRandomNumber(howMany, 5);
        remainTimes = new int[dizi.length];

        list[0] = 0;
        for (int i = 1; i <= dizi.length; i++) {
            int sum = 0;
            for (int j = 0; j < i; j++) {
                sum += dizi[j];
            }
            list[i] = sum;
        }

        System.arraycopy(dizi, 0, remainTimes, 0, dizi.length);

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
