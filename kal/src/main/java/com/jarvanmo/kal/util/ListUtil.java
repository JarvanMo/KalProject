package com.jarvanmo.kal.util;

import java.util.List;

/**
 * Created by mo on 17-9-1.
 * 剑气纵横三万里 一剑光寒十九洲
 */

public final class ListUtil {
    private ListUtil(){

    }

    public static  boolean isNullOrEmpty(List toCheck){
        return toCheck == null || toCheck.isEmpty();
    }
}
