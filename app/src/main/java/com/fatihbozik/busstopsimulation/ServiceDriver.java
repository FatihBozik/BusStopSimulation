package com.fatihbozik.busstopsimulation;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ServiceDriver extends AppCompatActivity {

    private Intent intentMyService;
    private ComponentName service;
    private TextView txtMessage;
    private BroadcastReceiver receiver;
    public ListView busStopsList;
    private int[] busStartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_driver);

        intentMyService = new Intent(this, MyService.class);
        intentMyService.putExtra("simulationTime", getIntent().getIntExtra("simulationTime", 0));
        intentMyService.putExtra("distances", getIntent().getIntArrayExtra("distances"));
        intentMyService.putExtra("maxBusCount", getIntent().getIntExtra("maxBusCount", 1));
        intentMyService.putExtra("list", getIntent().getIntArrayExtra("list"));
        final Bundle extras = getIntent().getExtras();
        intentMyService.putExtras(extras);
        service = startService(intentMyService);

        busStartTime = getIntent().getIntArrayExtra("busStartTime");

        int busStopCount = extras.getInt("busStopCount", 5);
        final String[] busStops = new String[busStopCount];
        for (int i = 0; i < busStopCount; i++) {
            busStops[i] = String.format("%d.Durak", (i + 1));
        }

        txtMessage = (TextView) findViewById(R.id.txt_message);
        txtMessage.setText(getCurrentTime("HH:mm", new Locale("tr")));

        // register & define filter for local listener
        IntentFilter mainFilter = new IntentFilter("fatihbozik.action.MYSERVICE");
        receiver = new MyBroadcastReceiver();
        registerReceiver(receiver, mainFilter);

        busStopsList = (ListView) findViewById(R.id.busStopsListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, busStops);
        busStopsList.setAdapter(adapter);

        busStopsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ServiceDriver.this, BusActivity.class);
                intent.putExtra("simulationTime", getIntent().getIntExtra("simulationTime", 0));
                intent.putExtras(extras);
                intent.putExtra("distances", getIntent().getIntArrayExtra("distances"));
                intent.putExtra("maxBusCount", getIntent().getIntExtra("maxBusCount", 1));
                intent.putExtra("position", position);
                intent.putExtra("saat", txtMessage.getText().toString());
                stopService(intentMyService);
                unregisterReceiver(receiver);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        intentMyService = new Intent(this, MyService.class);
        intentMyService.putExtra("simulationTime", getIntent().getIntExtra("simulationTime", 0));
        intentMyService.putExtra("distances", getIntent().getIntArrayExtra("distances"));
        intentMyService.putExtra("maxBusCount", getIntent().getIntExtra("maxBusCount", 1));
        Bundle extras = getIntent().getExtras();
        intentMyService.putExtras(extras);
        service = startService(intentMyService);

        // register & define filter for local listener
        IntentFilter mainFilter = new IntentFilter("fatihbozik.action.MYSERVICE");
        receiver = new MyBroadcastReceiver();
        registerReceiver(receiver, mainFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            stopService(intentMyService);
            unregisterReceiver(receiver);
        } catch (Exception e) {
            Log.e("ServiceDriver", e.getMessage());
        }
        Log.e("ServiceDriver", "Servis durduruldu.\nBroadcast Receiver kaydı silindi.");
    }

    // Broadcast Receiver
    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context localContext, Intent callerIntent) {
            String serviceData = callerIntent.getStringExtra("serviceData");
            Log.e("ServiceDriver", serviceData);
            txtMessage.setText(serviceData);
        }
    }

    private String getCurrentTime(String pattern, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
        return sdf.format(new Date());
    }
}