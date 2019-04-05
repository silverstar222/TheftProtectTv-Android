package com.esp.theftprotecttv.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.esp.theftprotecttv.ui.LockTvActivity;
import com.esp.theftprotecttv.util.Config;
import com.esp.theftprotecttv.util.Pref;

import java.util.Timer;
import java.util.TimerTask;

public class FullLockService extends Service {
    public static Timer timer;

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
         /* API CALL every 3.2 second */
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                lockChecker();
            }
        }, 500, 3200);
    }

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