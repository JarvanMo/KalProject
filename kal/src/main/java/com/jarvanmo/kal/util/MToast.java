package com.jarvanmo.kal.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.IntDef;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.jarvanmo.kal.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by mo on 17-3-24.
 * Copyright © 2017, cnyanglao, Co,. Ltd. All Rights Reserve
 */
public final class MToast {

    public static final int TYPE_INFO = 0;
    public static final int TYPE_SUCCESS = TYPE_INFO + 1;
    public static final int TYPE_WARNING = TYPE_SUCCESS + 1;
    public static final int TYPE_ERROR = TYPE_WARNING + 1;


    private static long lastToastTime = 0L;
    private static CharSequence lastToastString = null;

    private static int lastType = -1;

    @IntDef({TYPE_INFO, TYPE_SUCCESS, TYPE_WARNING, TYPE_ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ToastType {
    }


    private static Toast toast;
    private static Context appContext;
    private static Handler handler;

    public static void init(Context appContext) {
        MToast.appContext = appContext;
        handler = new Handler(Looper.getMainLooper());
    }

    public static void show(@StringRes int strRes, @ToastType int type) {
        show(appContext.getString(strRes), type);
    }

    public static void show(@StringRes int strRes) {
        show(strRes, TYPE_INFO);
    }

    public static void show(CharSequence message) {
        show(message, TYPE_INFO);
    }

    public static synchronized void show(final CharSequence message, @ToastType final int type) {

        if (message == null) {
            return;
        }


        final long currentTime = System.currentTimeMillis();
        if (TextUtils.equals(message, lastToastString) && lastType == type && currentTime - lastToastTime < 500L) {
            return;
        }


//        toast.show();
        handler.post(new Runnable() {
            @Override
            public void run() {
                toast = new Toast(appContext);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(inflateToastLayout(message, type));
                toast.show();
                lastToastTime = currentTime;
                lastToastString = message;
                lastType = type;
            }
        });

    }

    @SuppressLint("InflateParams")
    private static View inflateToastLayout(CharSequence message, int type) {
        LinearLayout layout = new LinearLayout(appContext);
        LayoutInflater inflater = (LayoutInflater) appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_toast_container, null);
        layout.addView(view);

        LinearLayout customToastContainer = (LinearLayout) layout.findViewById(R.id.custom_toast_container);
        ImageView icon = (ImageView) layout.findViewById(R.id.icon);
        TextView text = (TextView) layout.findViewById(R.id.text);

        switch (type) {
            case TYPE_SUCCESS:
                icon.setImageDrawable(ContextCompat.getDrawable(appContext, R.drawable.ic_check_circle_white_24dp));
                customToastContainer.setBackground(ContextCompat.getDrawable(appContext, R.drawable.custom_toast_success_background));
                break;
            case TYPE_WARNING:
                icon.setImageDrawable(ContextCompat.getDrawable(appContext, R.drawable.ic_warning_white_24dp));
                customToastContainer.setBackground(ContextCompat.getDrawable(appContext, R.drawable.custom_toast_warn_background));
                break;
            case TYPE_ERROR:
                icon.setImageDrawable(ContextCompat.getDrawable(appContext, R.drawable.ic_error_white_24dp));
                customToastContainer.setBackground(ContextCompat.getDrawable(appContext, R.drawable.custom_toast_error_background));
                break;
            default:
                icon.setImageDrawable(ContextCompat.getDrawable(appContext, R.drawable.ic_info_white_24dp));
                customToastContainer.setBackground(ContextCompat.getDrawable(appContext, R.drawable.custom_toast_info_background));
                break;
        }

        text.setText(message);

        return layout;
    }
}
