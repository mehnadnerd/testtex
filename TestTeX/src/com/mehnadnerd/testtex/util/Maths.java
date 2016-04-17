package com.mehnadnerd.testtex.util;

import java.util.TreeMap;

/**
 * Created by mehnadnerd on 2016-04-16.
 */
public class Maths {
    private final static TreeMap<Integer, String> romanmap = new TreeMap<Integer, String>();

    /**
     * Converts an index number to letter, automatically does offset, 0 -> A, 1 -> B,
     *
     * @param i
     * @return
     */
    public static char numToLetter(int i) {
        return (char) (i + 65);
    }

    /**
     * @param i
     * @return
     */
    public static String numToNumeral(int i) {
        return toRoman(i);
    }


    static {

        romanmap.put(1000, "M");
        romanmap.put(900, "CM");
        romanmap.put(500, "D");
        romanmap.put(400, "CD");
        romanmap.put(100, "C");
        romanmap.put(90, "XC");
        romanmap.put(50, "L");
        romanmap.put(40, "XL");
        romanmap.put(10, "X");
        romanmap.put(9, "IX");
        romanmap.put(5, "V");
        romanmap.put(4, "IV");
        romanmap.put(1, "I");

    }

    public final static String toRoman(int number) {
        int l = romanmap.floorKey(number);
        if (number == l) {
            return romanmap.get(number);
        }
        return romanmap.get(l) + toRoman(number - l);
    }
}
