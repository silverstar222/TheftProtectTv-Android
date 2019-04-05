package com.esp.theftprotecttv.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.esp.theftprotecttvstage.R;
import com.esp.theftprotecttv.service.FullLockService;
import com.esp.theftprotecttv.util.Config;
import com.esp.theftprotecttv.util.Pref;

/**
 * Created by Krunal on 30/08/16.
 */
public class LockTvActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        setFinishOnTouchOutside(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        if (Pref.getValue(LockTvActivity.this, Config.PREF_COMMAND, "").equals("UNLOCK")) {
            Pref.setValue(LockTvActivity.this, Config.PREF_LOCK_START, 0);
            if (FullLockService.timer != null) {
                FullLockService.timer.cancel();
            }
            finish();
        } else if (Pref.getValue(LockTvActivity.this, Config.PREF_COMMAND, "").equals("LOCK")) {
            Pref.setValue(LockTvActivity.this, Config.PREF_LOCK_START, 1);
            Intent intent = new Intent(LockTvActivity.this, FullLockService.class);
            startService(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Pref.getValue(LockTvActivity.this, Config.PREF_COMMAND, "").equals("LOCK")) {
            return false;
        }
        return true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (Pref.getValue(LockTvActivity.this, Config.PREF_COMMAND, "").equals("LOCK")) {
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Pref.getValue(LockTvActivity.this, Config.PREF_COMMAND, "").equals("LOCK")) {
            Pref.setValue(LockTvActivity.this, Config.PREF_LOCK_START, 1);
        } else if (Pref.getValue(LockTvActivity.this, Config.PREF_COMMAND, "").equals("UNLOCK")) {
            Pref.setValue(LockTvActivity.this, Config.PREF_LOCK_START, 0);
            if (FullLockService.timer != null) {
                FullLockService.timer.cancel();
            }
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Pref.getValue(LockTvActivity.this, Config.PREF_COMMAND, "").equals("LOCK")) {
            Pref.setValue(LockTvActivity.this, Config.PREF_LOCK_START, 0);
            Intent intent = new Intent(LockTvActivity.this, LockTvActivity.class);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (Pref.getValue(LockTvActivity.this, Config.PREF_COMMAND, "").equals("UNLOCK")) {
            Pref.setValue(LockTvActivity.this, Config.PREF_LOCK_START, 0);
            if (FullLockService.timer != null) {
                FullLockService.timer.cancel();
            }
            finish();
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        if (Pref.getValue(LockTvActivity.this, Config.PREF_COMMAND, "").equals("LOCK")) {
            Pref.setValue(LockTvActivity.this, Config.PREF_LOCK_START, 0);
            Intent intent = new Intent(LockTvActivity.this, LockTvActivity.class);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (Pref.getValue(LockTvActivity.this, Config.PREF_COMMAND, "").equals("UNLOCK")) {
            Pref.setValue(LockTvActivity.this, Config.PREF_LOCK_START, 0);
            if (FullLockService.timer != null) {
                FullLockService.timer.cancel();
            }
            finish();
        }
    }
}