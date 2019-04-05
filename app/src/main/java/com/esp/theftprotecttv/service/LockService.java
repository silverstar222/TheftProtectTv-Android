package com.esp.theftprotecttv.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.esp.theftprotecttv.backend.GetCommandAPI;
import com.esp.theftprotecttv.backend.ResponseListener;
import com.esp.theftprotecttv.ui.LockTvActivity;
import com.esp.theftprotecttv.util.Config;
import com.esp.theftprotecttv.util.Pref;
import com.esp.theftprotecttv.util.Utils;

import java.util.Timer;
import java.util.TimerTask;

public class LockService extends Service {
    private Timer timer;
    private GetCommandAPI getCommandAPI;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
         /* API CALL every 30 second */
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getCommandAPI();
            }
        }, 5000, 30000);
    }

    private void getCommandAPI() {
        if (Utils.isOnline(this)) {
            getCommandAPI = new GetCommandAPI(this, responseListener);
            getCommandAPI.execute();
        } else {
            Pref.setValue(getApplicationContext(), Config.PREF_COMMAND, "LOCK");
            lockChecker();
        }
    }

    ResponseListener responseListener = new ResponseListener() {
        @Override
        public void onResponce(String tag, int result, Object obj) {
            if (result == Config.API_SUCCESS) {
                if (tag.equals(Config.TAG_GET_COMMAND)) {
                    if (Pref.getValue(getApplicationContext(), Config.PREF_IS_LOCK, 0) == 1 &&
                            Pref.getValue(getApplicationContext(), Config.PREF_COMMAND, "").equals("UNLOCK")) {
                        Pref.setValue(getApplicationContext(), Config.PREF_IS_LOCK, 0);
                        Intent intent = new Intent(getApplicationContext(), LockTvActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        lockChecker();
                    }
                }
            } else {
                lockChecker();
            }
            getCommandAPI = null;
        }

        @Override
        public void onResponce(String tag, int result, Object obj, Object obj2) {

        }

        @Override
        public void onResponce(String tag, int result, Object obj, Object obj2, Object obj3) {

        }
    };

    private void lockChecker() {
        if (Pref.getValue(getApplicationContext(), Config.PREF_LOCK_START, 0) == 0) {
            if (Pref.getValue(getApplicationContext(), Config.PREF_IS_LOCK, 0) == 1 &&
                    Pref.getValue(getApplicationContext(), Config.PREF_COMMAND, "").equals("UNLOCK")) {
                Pref.setValue(getApplicationContext(), Config.PREF_IS_LOCK, 0);
                Intent intent = new Intent(getApplicationContext(), LockTvActivity.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else if (Pref.getValue(getApplicationContext(), Config.PREF_IS_LOCK, 0) == 1 &&
                    Pref.getValue(getApplicationContext(), Config.PREF_COMMAND, "").equals("LOCK")) {
                Pref.setValue(getApplicationContext(), Config.PREF_IS_LOCK, 1);
                Intent intent = new Intent(getApplicationContext(), LockTvActivity.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else if (Pref.getValue(getApplicationContext(), Config.PREF_IS_LOCK, 0) == 0 &&
                    Pref.getValue(getApplicationContext(), Config.PREF_COMMAND, "").equals("LOCK")) {
                Pref.setValue(getApplicationContext(), Config.PREF_IS_LOCK, 1);
                Intent intent = new Intent(getApplicationContext(), LockTvActivity.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }
}