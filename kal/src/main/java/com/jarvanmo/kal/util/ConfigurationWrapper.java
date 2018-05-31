package com.jarvanmo.kal.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import java.util.Locale;

/**
 * Created by mo on 17-5-22.
 * Copyright © 2017, cnyanglao, Co,. Ltd. All Rights Reserve
 */

public final class ConfigurationWrapper {

    private ConfigurationWrapper() {
    }

    public static Context wrapConfiguration(@NonNull final Context context, @NonNull final Configuration config) {
        return context.createConfigurationContext(config);
    }


    public static Context wrapLocale(@NonNull final Context context, @NonNull final Locale locale) {
        final Resources res = context.getResources();
        final Configuration config = res.getConfiguration();
        config.setLocale(locale);
        return wrapConfiguration(context, config);
    }
}
