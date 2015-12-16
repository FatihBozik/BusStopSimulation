package com.fatihbozik.busstopsimulation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.util.Random;

public class FirstStageActivity extends AppCompatActivity {
    int busStartTime[];
    int enBuyuk = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_stage);

        final int maxBusCount = getIntent().getIntExtra("maxBusCount", 1);
        final int[] busStartTime = returnBusStartTime(maxBusCount);
        final Bundle extras = new Bundle();
        for(int i = 0; i < maxBusCount; i++) {
            extras.putInt("Otobüs" + (i+1), busStartTime[i]);
        }

        GridView gridview = (GridView) findViewById(R.id.gridview1);
        gridview.setAdapter(new TextAdapter(this, busStartTime, 0));

        Button startButton = (Button) findViewById(R.id.button_next2);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stageActivity = new Intent(FirstStageActivity.this, StageActivity.class);
                stageActivity.putExtra("busStopCount", getIntent().getIntExtra("busStopCount", 5));
                stageActivity.putExtra("maxBusCount", maxBusCount);
                stageActivity.putExtra("enBuyuk", enBuyuk);
                stageActivity.putExtra("busStartTime", busStartTime);
                stageActivity.putExtras(extras);
                startActivity(stageActivity);
            }
        });
    }

    private int[] returnBusStartTime(int maxBusCount) {
        Random rnd = new Random();
        int[] dizi = new int[maxBusCount];
        for(int i = 0; i < maxBusCount; i++) {
            dizi[i] = rnd.nextInt(5);

            if(dizi[i] > enBuyuk) {
                enBuyuk = dizi[i];
            }
        }
        return dizi;
    }
}
