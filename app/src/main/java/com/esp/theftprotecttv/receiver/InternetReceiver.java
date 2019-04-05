package com.esp.theftprotecttv.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.esp.theftprotecttv.service.LockService;
import com.esp.theftprotecttv.util.Config;
import com.esp.theftprotecttv.util.Pref;
import com.esp.theftprotecttv.util.Utils;

public class InternetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Utils.isOnline(context)) {
            // Do nothing
        } else {
            if (Pref.getValue(context, Config.PREF_CODE, "").equals("")) {
                // Do nothing
            } else {
                Pref.setValue(context, Config.PREF_COMMAND, "LOCK");
                Intent i = new Intent(context, LockService.class);
                context.startService(i);
            }
        }
    }
}