package com.example.andr1_group_8;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.ArrayList;
import java.util.List;

/**
 *  Stores data to be used across all activities
 */
public class dataService extends Service {

    public static final String ACTION_PING = dataService.class.getName() + ".PING";
    public static final String ACTION_PONG = dataService.class.getName() + ".PONG";

    private IBinder mBinder = new dataServiceBinder();

    private String user_token;
    private List<News> newsList;

    public dataService() {
    }

    @Override
    public void onCreate() {
        user_token = "invalid";
        newsList = new ArrayList<>();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("dataService", "in StartCommand");
        //LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter(ACTION_PING));

        if ((intent != null) && (intent.getStringExtra("token") != null))
            this.user_token = intent.getStringExtra("token");
        else
            this.user_token = "invalid";

        //return START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v("dataService", "in onBind");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v("dataService", "in onUnbind");
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        Log.v("dataService", "in onRebind");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy () {
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        Log.v("dataService", "onDestroy: ");
        this.user_token = "invalid";
        super.onDestroy();
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public class dataServiceBinder extends Binder {
        dataService getService() {
            return dataService.this;
        }
    }

    public String getUser_token() {
        return user_token;
    }


    /**
     * Set a broadcast receiver to be able to ping the service and check if it's running
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive (Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_PING)) {
                LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getApplicationContext());
                manager.sendBroadcast(new Intent(ACTION_PONG));
            }
        }
    };
}