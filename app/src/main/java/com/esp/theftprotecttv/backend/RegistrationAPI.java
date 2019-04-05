package com.esp.theftprotecttv.backend;

import android.app.Activity;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.esp.theftprotecttvstage.R;
import com.esp.theftprotecttv.util.AlertDailogView;
import com.esp.theftprotecttv.util.Config;
import com.esp.theftprotecttv.util.Log;
import com.esp.theftprotecttv.util.Pref;

import org.json.JSONObject;

import java.util.HashMap;

public class RegistrationAPI {
    private Context mCaller;
    private HashMap<String, String> mParams = null;
    private Adapter mAdapter = null;
    private ResponseListener responseListener;

    public RegistrationAPI(Context caller, ResponseListener responseListener) {
        this.mCaller = caller;
        this.mParams = new HashMap<String, String>();

        Config.API_REGISTER = Config.HOST + "regs.php";
        mParams.put("key", Config.API_KEY);
        mParams.put("version", Config.API_VERSION);
        this.responseListener = responseListener;
    }

    public void execute() {
        this.mAdapter = new Adapter(this.mCaller);
        this.mAdapter.doPost(Config.TAG_REGISTER, Config.API_REGISTER, mParams,
                new APIResponseListener() {

                    @Override
                    public void onResponse(String response) {
                        mParams = null;
                        // Parse Response and Proceed Further
                        parse(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mParams = null;
                        if (error instanceof TimeoutError
                                || error instanceof NoConnectionError) {
                            if (!((Activity) mCaller).isFinishing()) {
                                if (Pref.getValue(mCaller, Config.PREF_IS_REGISTER, 0) == 0) {
                                    AlertDailogView
                                            .showAlert(
                                                    mCaller,
                                                    mCaller.getResources()
                                                            .getString(
                                                                    R.string.connectionErrorMessage),
                                                    mCaller.getResources()
                                                            .getString(
                                                                    R.string.tryAgain),
                                                    false).show();
                                }
                            }
                        } else if (error instanceof AuthFailureError) {
                            //
                        } else if (error instanceof ServerError) {
                            //
                        } else if (error instanceof NetworkError) {
                            //
                        } else if (error instanceof ParseError) {
                            //
                        }
                        // Inform Caller that the API call is failed
                        responseListener.onResponce(Config.TAG_REGISTER,
                                Config.API_FAIL, null);
                    }
                });
    }

    /*
     * Parse the response and prepare for callback
     */
    private void parse(String response) {
        int code = 0;
        String mesg = "";
        JSONObject jsonObject;
        String getRegCode = "";

        try {
            // Do nothing
            jsonObject = new JSONObject(response);
            Pref.setValue(mCaller, Config.PREF_CODE, jsonObject.getString("code"));
        } catch (Exception e) {
            code = -1;
            mesg = "Exception :: " + this.getClass() + " :: parse() :: "
                    + e.getLocalizedMessage();
            Log.error(this.getClass() + " :: Exception :: ", e);
        } finally {
            response = null;
            /** release variables */
        }

        this.doCallBack(code, mesg);
    }

    /*
     * Send control back to the caller which includes
     *
     * Status: Successful or Failure Message: Its an Object, if required
     */
    private void doCallBack(int code, String mesg) {

        try {
            if (code == 0) {
                responseListener.onResponce(Config.TAG_REGISTER,
                        Config.API_SUCCESS, code);
            } else if (code > 0) {
                responseListener.onResponce(Config.TAG_REGISTER, Config.API_FAIL, null);
            } else if (code < 0) {
                responseListener.onResponce(Config.TAG_REGISTER, Config.API_FAIL, null);
            }
        } catch (Exception e) {
            Log.error(this.getClass() + " :: Exception :: ", e);
        } finally {
            mAdapter = null;
        }
    }

    /*
     * Cancel API Request
     */
    public void doCancel() {
        if (mAdapter != null)
            mAdapter.doCancel(Config.TAG_REGISTER);
    }
}