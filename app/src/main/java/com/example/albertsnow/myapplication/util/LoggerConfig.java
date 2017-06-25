package com.example.albertsnow.myapplication.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by albertsnow on 6/23/17.
 */

public class LoggerConfig {
    public static final boolean ON = true;
    public static final String TAG_PREFIX = "LoggerConfig_";

    public static void i(String tag, String msg) {
        if (ON && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.i(TAG_PREFIX + tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (ON && !TextUtils.isEmpty(tag) && !TextUtils.isEmpty(msg)) {
            Log.e(TAG_PREFIX + tag, msg);
        }
    }

}
