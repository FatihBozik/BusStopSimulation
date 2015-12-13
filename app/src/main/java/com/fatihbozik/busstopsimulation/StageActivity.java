package com.fatihbozik.busstopsimulation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class StageActivity extends AppCompatActivity {
    private TextView imaginaryClock;
    int busStopsDistances[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);

        int busStopCount = getIntent().getIntExtra("busStopCount", 5);
        busStopsDistances = distanceBetweenStops(busStopCount - 1);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new TextAdapter(this, busStopsDistances));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(StageActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    int[] distanceBetweenStops(int howMany) {
        int[] dizi = generateRandomNumber(howMany, 5);

        for(int i = 0; i < dizi.length; i++) {
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

    private String getCurrentTime(String pattern, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
        return sdf.format(new Date());
    }

    private int convertTimeToMinutes(String time) {
        int hours = Integer.parseInt(time.substring(0, 2));
        int minutes = Integer.parseInt(time.substring(3));

        return (hours * 60) + minutes;
    }

    private String convertMinutesToTime(int minutes) {
        int hours = minutes / 60;
        int minute = minutes % 60;

        return String.format("%02d:%02d", hours, minute);
    }
}
