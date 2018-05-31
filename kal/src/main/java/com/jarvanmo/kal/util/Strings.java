package com.jarvanmo.kal.util;

/**
 * Created by mo on 17-3-21.
 * Copyright © 2017, cnyanglao, Co,. Ltd. All Rights Reserve
 */

public final class Strings {
    private Strings() {
        // No instances.
    }

    public static boolean isBlank(CharSequence string) {
        return (string == null || string.toString().trim().length() == 0);
    }

    public static String valueOrDefault(String string, String defaultString) {
        return isBlank(string) ? defaultString : string;
    }

    public static String truncateAt(String string, int length) {
        return string.length() > length ? string.substring(0, length) : string;
    }
}
