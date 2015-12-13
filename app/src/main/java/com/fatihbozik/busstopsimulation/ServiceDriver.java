package com.fatihbozik.busstopsimulation;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
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
    private int[] kacDakika;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_driver);

        int busStopCount = getIntent().getIntExtra("busStopCount", 5);
        final String[] busStops = new String[busStopCount];
        for (int i = 0; i < busStopCount; i++) {
            busStops[i] = String.format("%d.Durak", (i + 1));
        }

        intentMyService = new Intent(this, MyService.class);
        intentMyService.putExtra("simulationTime", getIntent().getIntExtra("simulationTime", 0));
        intentMyService.putExtra("distances", getIntent().getIntArrayExtra("distances"));
        intentMyService.putExtra("maxBusCount", getIntent().getIntExtra("maxBusCount", 1));
        Bundle extras = getIntent().getExtras();
        intentMyService.putExtras(extras);
        service = startService(intentMyService);

        txtMessage = (TextView) findViewById(R.id.txt_message);
        txtMessage.setText(getCurrentTime("HH:mm", new Locale("tr")));

        // register & define filter for local listener
        IntentFilter mainFilter = new IntentFilter("fatihbozik.action.MYSERVICE");
        receiver = new MyBroadcastReceiver();
        registerReceiver(receiver, mainFilter);

        ListView busStopsList = (ListView) findViewById(R.id.busStopsListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, busStops);
        busStopsList.setAdapter(adapter);

        busStopsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder diyalogOlusturucu = new AlertDialog.Builder(ServiceDriver.this);
                diyalogOlusturucu.setMessage(busStops[position])
                        .setCancelable(false)
                        .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                diyalogOlusturucu.create().show();
            }
        });
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
            kacDakika = callerIntent.getIntArrayExtra("kacDakika");
            Log.e("ServiceDriver", serviceData);
            txtMessage.setText(serviceData);

            for(int x : kacDakika) {
                if(x != -99) {
                    Log.d("Fatih", (x) + " dakika kaldı : " + serviceData);
                }
            }
        }
    }

    private String getCurrentTime(String pattern, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
        return sdf.format(new Date());
    }
}