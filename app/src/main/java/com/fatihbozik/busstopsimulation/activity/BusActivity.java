package com.fatihbozik.busstopsimulation.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.fatihbozik.busstopsimulation.R;
import com.fatihbozik.busstopsimulation.adapter.TextAdapter;
import com.fatihbozik.busstopsimulation.service.MyService;

import java.util.HashMap;

public class BusActivity extends AppCompatActivity {

    private Intent intentMyService;
    private TextView txtMessage;
    private BroadcastReceiver receiver;
    private int[] distances;
    public ListView busStopsList;
    int sumOfDistance;
    int position;
    HashMap<Integer, Integer> kacDakika;
    GridView gridViewBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);

        String saat = getIntent().getStringExtra("saat");
        txtMessage = (TextView) findViewById(R.id.txt_message_bus);
        txtMessage.setText(saat);

        distances = getIntent().getIntArrayExtra("distances");

        intentMyService = new Intent(this, MyService.class);
        intentMyService.putExtra("simulationTime", getIntent().getIntExtra("simulationTime", 0));
        intentMyService.putExtra("saat", getIntent().getStringExtra("saat"));
        intentMyService.putExtra("distances", distances);
        intentMyService.putExtra("maxBusCount", getIntent().getIntExtra("maxBusCount", 1));
        position = getIntent().getIntExtra("position", 0); // hangi durağa tıklandı.
        final Bundle extras = getIntent().getExtras();
        intentMyService.putExtras(extras);
        intentMyService.putExtra("position", position);
        startService(intentMyService);

        sumOfDistance = 0;
        for (int i = 0; i < position; i++) sumOfDistance += distances[i];

        // register & define filter for local listener
        IntentFilter mainFilter = new IntentFilter("fatihbozik.action.MYSERVICE");
        receiver = new MyBroadcastReceiver();
        registerReceiver(receiver, mainFilter);

        gridViewBus = (GridView) findViewById(R.id.gridviewBus);
        gridViewBus.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent i = new Intent(BusActivity.this, BusStopActivity.class);
                i.putExtra("simulationTime", getIntent().getIntExtra("simulationTime", 0));
                i.putExtras(extras);
                i.putExtra("distances", getIntent().getIntArrayExtra("distances"));
                i.putExtra("maxBusCount", getIntent().getIntExtra("maxBusCount", 1));
                i.putExtra("position", position);
                i.putExtra("saat", txtMessage.getText().toString());
                stopService(intentMyService);
                unregisterReceiver(receiver);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        intentMyService = new Intent(this, MyService.class);
        intentMyService.putExtra("simulationTime", getIntent().getIntExtra("simulationTime", 0));
        intentMyService.putExtra("saat", getIntent().getStringExtra("saat"));
        intentMyService.putExtra("distances", distances);
        intentMyService.putExtra("maxBusCount", getIntent().getIntExtra("maxBusCount", 1));
        position = getIntent().getIntExtra("position", 0); // hangi durağa tıklandı.
        final Bundle extras = getIntent().getExtras();
        intentMyService.putExtras(extras);
        intentMyService.putExtras(extras);
        intentMyService.putExtra("position", position);
        startService(intentMyService);

        // register & define filter for local listener
        IntentFilter mainFilter = new IntentFilter("fatihbozik.action.MYSERVICE");
        receiver = new MyBroadcastReceiver();
        registerReceiver(receiver, mainFilter);
    }

    // Broadcast Receiver
    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context localContext, Intent callerIntent) {
            String serviceData = callerIntent.getStringExtra("serviceData");
            kacDakika = (HashMap<Integer, Integer>) callerIntent.getSerializableExtra("kacDakika");
            gridViewBus.setAdapter(new TextAdapter(BusActivity.this, kacDakika, 2));
            txtMessage.setText(serviceData);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            stopService(intentMyService);
            unregisterReceiver(receiver);
        } catch (Exception e) {
            Log.e("BusActivity", e.getMessage());
        }
        Log.e("BusActivity", "Servis durduruldu.\nBroadcast Receiver kaydı silindi.");
    }

    //    private String getCurrentTime(String pattern, Locale locale) {
//        SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
//        return sdf.format(new Date());
//    }
}
