package com.mehnadnerd.testtex.util;

/**
 * Created by mehnadnerd on 2016-04-17.
 */
public class Strings {
    public static String trim(String s, int size) {
        if (s.length() < size) {
            return s;
        } else {
            return s.substring(0, size);
        }
    }
}
