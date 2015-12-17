package com.fatihbozik.busstopsimulation.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MyService extends Service {
    boolean isRunning = true;
    int simulationTime;
    String currentTime;
    int[] distances;
    int maxBusCount;
    int minutes;
    int[] busStartTime;
    int[] list;
    HashMap<Integer, Integer> kacDakika;
    HashMap<Integer, String> gececegiDuraklar;
    boolean control;

    int count;
    int position;

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

        simulationTime = intent.getIntExtra("simulationTime", 0);
        distances = intent.getIntArrayExtra("distances");
        maxBusCount = intent.getIntExtra("maxBusCount", 1);
        position = intent.getIntExtra("position", -1);
        control = intent.getBooleanExtra("busStopActivity", false);
        list = intent.getIntArrayExtra("list");

        if (position == -1) {
            currentTime = getCurrentTime("HH:mm", new Locale("tr"));
        } else {
            currentTime = intent.getStringExtra("saat");
        }

        minutes = convertTimeToMinutes(currentTime);
        kacDakika = new HashMap<>();
        gececegiDuraklar = new HashMap<>();

        busStartTime = new int[maxBusCount];
        Bundle extras = intent.getExtras();
        for (int i = 0; i < maxBusCount; i++) {
            busStartTime[i] = extras.getInt("Otobüs" + (i + 1));
            Log.d("MyService", "BusStartTime:" + busStartTime[i]);
        }

        Thread triggerService = new Thread(new Runnable() {
            public void run() {
                for (int i = 1; (i <= simulationTime) && isRunning; i++) {
                    try {
                        Thread.sleep(1000);
                        currentTime = convertMinutesToTime(++minutes);
                        count++;
                        if (position != -1) {
                            fun(busStartTime, count, position);
                        }
                        if (control) {
                            fun2(busStartTime, count, position);
                        }
                        Intent myResponse = new Intent("fatihbozik.action.MYSERVICE");
                        myResponse.putExtra("serviceData", currentTime);
                        myResponse.putExtra("distances", distances);
                        myResponse.putExtra("kacDakika", kacDakika);
                        myResponse.putExtra("gececegiDuraklar", gececegiDuraklar);

                        sendBroadcast(myResponse);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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

    private void fun(int[] busStartTime, int count, int position) {
        int value = list[position];

        for (int i = 0; i < busStartTime.length; i++) {
            if (busStartTime[i] + value - count >= 0) {
                kacDakika.put(i, busStartTime[i] + value - count);
            } else {
                kacDakika.remove(i);
            }
        }
    }

    private void fun2(int[] busStartTime, int count, int position) {
        Log.d("fatihActivity::", "count - busStartTime[position] : " + (count - busStartTime[position]));

        for (int j = 0; j < list.length; j++) {
            if (count - busStartTime[position] <= list[j]) {
                gececegiDuraklar.put(j, "Durak" + (j + 1));
                Log.d("fatihActivity::", "Durak" + (j + 1) + "ya uğrayacak");
            } else {
                gececegiDuraklar.remove(j);
                Log.d("fatihActivity::", "Durak" + (j + 1) + "ya uğradı...");
            }
        }
    }
}