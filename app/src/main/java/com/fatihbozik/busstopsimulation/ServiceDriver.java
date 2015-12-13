package com.fatihbozik.busstopsimulation;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class ServiceDriver extends AppCompatActivity {

    private Intent intentMyService;
    private ComponentName service;
    private TextView txtMessage;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_driver);

        intentMyService = new Intent(this, MyService.class);
        intentMyService.putExtra("simulationTime", getIntent().getIntExtra("simulationTime", 0));
        service = startService(intentMyService);

        txtMessage = (TextView) findViewById(R.id.txt_message);
        txtMessage.setText("Servis çalıştırıldı..");

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
}

