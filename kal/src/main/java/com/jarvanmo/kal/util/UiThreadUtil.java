package com.jarvanmo.kal.util;

import android.os.Handler;
import android.os.Looper;

public class UiThreadUtil {

    public static void runOnUiThread(Runnable runnable){
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    public static void runOnUiThread(Runnable runnable,long delayMillis){
        new Handler(Looper.getMainLooper()).postDelayed(runnable,delayMillis);
    }

}
