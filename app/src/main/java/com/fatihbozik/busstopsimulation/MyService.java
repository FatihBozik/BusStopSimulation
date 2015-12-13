package com.fatihbozik.busstopsimulation;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MyService extends Service {
    boolean isRunning = true;
    int simulationTime;
    String currentTime;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("MyService", "MyService::onStartCommand()");

        simulationTime = intent.getIntExtra("simualationTime", 0);

        Thread triggerService = new Thread(new Runnable() {
//            long startingTime = System.currentTimeMillis();
//            long tics = 0;

            public void run() {


                for(int i = 1; i <= simulationTime; i++) {
                    currentTime = getCurrentTime("HH:mm", new Locale("tr"));
                }


//                for (int i = 0; (i < 120) & isRunning; i++) {
//                    try {
//                        tics = System.currentTimeMillis() - startingTime;
//                        Intent myResponse = new Intent("fatihbozik.action.MYSERVICE");
//                        String msg = i + " value: " + tics;
//                        myResponse.putExtra("serviceData", msg);
//                        sendBroadcast(myResponse);
//                        Thread.sleep(1000); //five seconds
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
            }
        });
        triggerService.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
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