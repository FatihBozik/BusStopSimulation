package com.fatihbozik.busstopsimulation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class StageActivity extends AppCompatActivity {
    private TextView imaginaryClock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stage);
    }

    int[] distanceBetweenStops(int howMany) {
        Random rnd = new Random();
        int[] dizi = new int[howMany];
        for (int i = 0; i < dizi.length; i++) {
            dizi[i] = rnd.nextInt(5) + 1; // [1, 5] arası rastgele bir sayı üret.
            dizi[i] *= 50;
        }
        return dizi;
    }

    private String getCurrentTime(String pattern, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
        return sdf.format(new Date());
    }
}
