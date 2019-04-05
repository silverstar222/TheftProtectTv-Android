package com.esp.theftprotecttv.util;

import android.content.Context;
import android.net.ConnectivityManager;

import com.esp.theftprotecttvstage.R;

public class Utils {
    /**
     * Check Connectivity of network.
     */
    public static boolean isOnline(Context context) {
        try {
            if (context == null)
                return false;

            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            if (cm != null)
            {
                if (cm.getActiveNetworkInfo() != null)
                {
                    return cm.getActiveNetworkInfo().isConnected();
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            Log.error("Exception", e);
            return false;
        }
    }

    /**
     * No Internet Connection Alert.
     */
    public static void noInternetConnection(Context context) {
        if (Pref.getValue(context, Config.PREF_IS_INTERNET_DIALOG_OPEN, 0) == 0) {
            Pref.setValue(context, Config.PREF_IS_INTERNET_DIALOG_OPEN, 1);
            AlertDailogView.showAlert(
                    context,
                    context.getResources().getString(
                            R.string.connectionErrorMessage),
                    context.getResources().getString(R.string.ok), true).show();
        }
    }

    public static String getResourceSting(Context context, int resId) {
        return context.getResources().getString(resId);
    }
}