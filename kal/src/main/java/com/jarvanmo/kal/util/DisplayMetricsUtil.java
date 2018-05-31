package com.jarvanmo.kal.util;

import android.content.Context;

public class DisplayMetricsUtil {
    private DisplayMetricsUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static int getWidth(Context context){
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getHeight(Context context){
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
