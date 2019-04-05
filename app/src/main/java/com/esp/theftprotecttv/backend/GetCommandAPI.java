package com.esp.theftprotecttv.backend;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.esp.theftprotecttv.util.Config;
import com.esp.theftprotecttv.util.Log;
import com.esp.theftprotecttv.util.Pref;

import org.json.JSONObject;

import java.util.HashMap;

public class GetCommandAPI {
    private Context mCaller;
    private HashMap<String, String> mParams = null;
    private Adapter mAdapter = null;
    private ResponseListener responseListener;

    public GetCommandAPI(Context caller, ResponseListener responseListener) {
        this.mCaller = caller;
        this.mParams = new HashMap<String, String>();

        Config.API_GET_COMMAND = Config.HOST + "get_command.php";
        mParams.put("key", Config.API_KEY);
        mParams.put("version", Config.API_VERSION);
        mParams.put("code", Pref.getValue(mCaller, Config.PREF_CODE, ""));
        this.responseListener = responseListener;
    }

    public void execute() {
        this.mAdapter = new Adapter(this.mCaller);
        this.mAdapter.doPost(Config.TAG_GET_COMMAND, Config.API_GET_COMMAND, mParams,
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
                        if (error instanceof NoConnectionError) {
                            if (Pref.getValue(mCaller, Config.PREF_IS_REGISTER, 0) == 1) {
                                Pref.setValue(mCaller, Config.PREF_COMMAND, "LOCK");
                            }
                        } else if (error instanceof TimeoutError) {
                            //
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
                        responseListener.onResponce(Config.TAG_GET_COMMAND,
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

        try {
            jsonObject = new JSONObject(response);
            code = jsonObject.getInt("code");
            if (code == 0) {
                Pref.setValue(mCaller, Config.PREF_COMMAND, jsonObject.getString("command"));
                Pref.setValue(mCaller, Config.PREF_STATUS, jsonObject.getString("status"));
            }
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
                responseListener.onResponce(Config.TAG_GET_COMMAND,
                        Config.API_SUCCESS, code);
            } else if (code > 0) {
                responseListener.onResponce(Config.TAG_GET_COMMAND, Config.API_FAIL, null);
            } else if (code < 0) {
                responseListener.onResponce(Config.TAG_GET_COMMAND, Config.API_FAIL, null);
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
            mAdapter.doCancel(Config.TAG_GET_COMMAND);
    }
}