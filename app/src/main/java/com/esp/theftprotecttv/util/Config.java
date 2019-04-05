package com.esp.theftprotecttv.util;

public class Config {
    /*
     * Development URL WEB URL :
     * http://192.168.1.42/work/tvapp/api/api.php
     */
    public static String TAG = "TheftProtectTv";
    // preference file name
    public static final String PREF_FILE = TAG + "_PREF";
    /*
     * API
     */
    public static String API_KEY = "TVlO@K2016";
    public static String API_VERSION = "v1";
    public static String DEVICE_TYPE = "1";

//    public static String HOST = "http://192.168.1.42/work/tvapp/api/";
//    public static String HOST = "http://tvapp.esprit-apps.com/api/";
//    public static String HOST = "http://theftprotectserver.testplanets.com/api/";
    public static String HOST = "http://app.theft-protect.com/api/";
    /*
     * Error and Warnings
     */
    public static String ERROR_NETWORK = "ERROR_NETWORK";
    public static String ERROR_API = "ERROR_API";
    public static String ERROR_UNKNOWN = "ERROR_UNKNOWN";

    public static int API_SUCCESS = 0;
    public static int API_FAIL = 1;

    // SOCKET TIMEOUT IS SET TO 30 SECONDS
    public static int TIMEOUT_SOCKET = 30000;
    /*
     * Cookie and SESSION
     */
    public static String PREF_SESSION_COOKIE = "sessionid";
    public static String SET_COOKIE_KEY = "Set-Cookie";
    public static String COOKIE_KEY = "Cookie";
    public static String SESSION_COOKIE = "sessionid";

    public static String API_REGISTER = HOST + "/regs.php";
    public static String TAG_REGISTER = "TAG_REGISTER";

    public static String API_GET_COMMAND = HOST + "/get_command.php";
    public static String TAG_GET_COMMAND = "TAG_GET_COMMAND";

    public static String PREF_CODE = "PREF_CODE";
    public static String PREF_COMMAND = "PREF_COMMAND";
    public static String PREF_STATUS = "PREF_STATUS";
    // 1 = Lock , 2 = Unlock
    public static String PREF_IS_LOCK = "PREF_IS_LOCK";
    public static String PREF_IS_REGISTER = "PREF_IS_REGISTER";
    public static String PREF_IS_INTERNET_DIALOG_OPEN = "PREF_IS_INTERNET_DIALOG_OPEN";
    public static String PREF_LOCK_START = "PREF_LOCK_START";
}