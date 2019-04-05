package com.esp.theftprotecttv.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

import com.esp.theftprotecttvstage.R;
import com.esp.theftprotecttv.backend.GetCommandAPI;
import com.esp.theftprotecttv.backend.RegistrationAPI;
import com.esp.theftprotecttv.backend.ResponseListener;
import com.esp.theftprotecttv.service.LockService;
import com.esp.theftprotecttv.util.Config;
import com.esp.theftprotecttv.util.Pref;
import com.esp.theftprotecttv.util.Utils;

/**
 * Created by Krunal on 30/08/16.
 */
public class HomeActivity extends Activity {
    private TextView txtCodeGenerate;
    private RegistrationAPI registrationAPI;
    private GetCommandAPI getCommandAPI;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = HomeActivity.this;
        Settings.Global.getInt(context.getContentResolver(), Settings.Global.ADB_ENABLED, 0);
        txtCodeGenerate = (TextView) findViewById(R.id.txtCodeGenerate);

        if (Pref.getValue(context, Config.PREF_CODE, "").equals("")) {
            registrationAPI();
        } else {
            if (Pref.getValue(context, Config.PREF_IS_REGISTER, 0) == 0) {
                txtCodeGenerate.setText(Pref.getValue(context, Config.PREF_CODE, ""));
                Toast.makeText(context, Utils.getResourceSting(context, R.string.notRegistered), Toast.LENGTH_LONG).show();
                getCommandAPI();
            } else {
                Toast.makeText(context, Utils.getResourceSting(context, R.string.notAllowedToRegistered), Toast.LENGTH_LONG).show();
                startLockService();
            }
        }
    }

    ResponseListener responseListener = new ResponseListener() {
        @Override
        public void onResponce(String tag, int result, Object obj) {
            if (result == Config.API_SUCCESS) {
                if (tag.equals(Config.TAG_REGISTER)) {
                    txtCodeGenerate.setText(Pref.getValue(context, Config.PREF_CODE, ""));
                    // getCommandAPI
                    getCommandAPI();
                } else if (tag.equals(Config.TAG_GET_COMMAND)) {
                    // Get Command not found call api calll
                    if (Pref.getValue(context, Config.PREF_STATUS, "").equals("SHOW")) {
                        // Stop Calling and finish
                        Pref.setValue(context, Config.PREF_IS_REGISTER, 1);
                        startLockService();
                    } else {
                        getCommandAPI();
                    }
                }
            } else {
                if (tag.equals(Config.TAG_GET_COMMAND)) {
                    // Get Command not found call api calll
                    if (Pref.getValue(context, Config.PREF_STATUS, "").equals("SHOW")) {
                        // Stop Calling and finish
                        startLockService();
                    } else {
                        getCommandAPI();
                    }
                } else if (tag.equals(Config.TAG_REGISTER)) {
                    registrationAPI();
                }
            }
            registrationAPI = null;
            getCommandAPI = null;
        }

        @Override
        public void onResponce(String tag, int result, Object obj, Object obj2) {

        }

        @Override
        public void onResponce(String tag, int result, Object obj, Object obj2, Object obj3) {

        }
    };

    private void getCommandAPI() {
        if (Utils.isOnline(context)) {
            getCommandAPI = new GetCommandAPI(HomeActivity.this, responseListener);
            getCommandAPI.execute();
        } else {
            Utils.noInternetConnection(context);
        }
    }

    private void registrationAPI() {
        if (Utils.isOnline(this.context)) {
            registrationAPI = new RegistrationAPI(HomeActivity.this, responseListener);
            registrationAPI.execute();
        } else {
            Utils.noInternetConnection(this.context);
        }
    }

    private void startLockService() {
        Intent intent = new Intent(HomeActivity.this, LockService.class);
        startService(intent);
        finish();
    }
}