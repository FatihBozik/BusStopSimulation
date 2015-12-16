package com.fatihbozik.busstopsimulation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;

public class BusStopActivity extends AppCompatActivity {

    private Intent intentMyService;
    private TextView txtMessage;
    private BroadcastReceiver receiver;
    private int[] distances;
    public ListView busStopsList;
    int sumOfDistance;
    int position;
    HashMap<Integer, Integer> kacDakika;
    HashMap<Integer, String> gececegiDuraklar;
    boolean busStopActivity = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop);

        String saat = getIntent().getStringExtra("saat");
        txtMessage = (TextView) findViewById(R.id.txt_message_bus_stop);
        txtMessage.setText(saat);

        distances = getIntent().getIntArrayExtra("distances");

        intentMyService = new Intent(this, MyService.class);
        intentMyService.putExtra("simulationTime", getIntent().getIntExtra("simulationTime", 0));
        intentMyService.putExtra("saat", getIntent().getStringExtra("saat"));
        intentMyService.putExtra("distances", distances);
        intentMyService.putExtra("maxBusCount", getIntent().getIntExtra("maxBusCount", 1));
        intentMyService.putExtra("busStopActivity", busStopActivity);
        position = getIntent().getIntExtra("position", 0); // hangi durağa tıklandı.
        Bundle extras = getIntent().getExtras();
        intentMyService.putExtras(extras);
        intentMyService.putExtra("position", position);
        startService(intentMyService);

        sumOfDistance = 0;
        for (int i = 0; i < position; i++) sumOfDistance += distances[i];

        // register & define filter for local listener
        IntentFilter mainFilter = new IntentFilter("fatihbozik.action.MYSERVICE");
        receiver = new MyBroadcastReceiver();
        registerReceiver(receiver, mainFilter);

        busStopsList = (ListView) findViewById(R.id.listView);
    }

    // Broadcast Receiver
    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context localContext, Intent callerIntent) {
            String serviceData = callerIntent.getStringExtra("serviceData");
            gececegiDuraklar = (HashMap<Integer, String>) callerIntent.getSerializableExtra("gececegiDuraklar");
            txtMessage.setText(serviceData);

            HashMapAdapter adapter = new HashMapAdapter(BusStopActivity.this, gececegiDuraklar);
            busStopsList.setAdapter(adapter);
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
}
