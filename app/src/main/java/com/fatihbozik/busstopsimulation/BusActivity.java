package com.fatihbozik.busstopsimulation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

public class BusActivity extends AppCompatActivity {

    private Intent intentMyService;
    private TextView txtMessage;
    private BroadcastReceiver receiver;
    private int[] distances;
    public ListView busStopsList;
    int sumOfDistance;
    int position;
    int[] kacDakika;
    GridView gridViewBus;
    static int count;


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

        gridViewBus = (GridView) findViewById(R.id.gridviewBus);
    }

    // Broadcast Receiver
    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context localContext, Intent callerIntent) {
            String serviceData = callerIntent.getStringExtra("serviceData");
            kacDakika = callerIntent.getIntArrayExtra("kacDakika");
            count = realLength(kacDakika);

            Log.d("BusActivity::Ayraç", "------");
            for(int i = 0; i < kacDakika.length; i++) {
                Log.d("BusActivity::Kacdakika", "kacdakika["+i+"]" + ":" + kacDakika[i] + "");
            }
            Log.d("BusActivity::Count", count + "");
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

    public int realLength(int dizi[]) {
        int count = 0;
        for(int i : dizi) {
            if(i != -99) {
                count++;
            }
        }
        return count;
    }

//    private String getCurrentTime(String pattern, Locale locale) {
//        SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
//        return sdf.format(new Date());
//    }
}
