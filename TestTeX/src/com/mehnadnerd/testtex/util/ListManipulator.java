package com.mehnadnerd.testtex.util;

import com.mehnadnerd.testtex.data.DisplayFormatable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Created by mehnadnerd on 2016-05-18.
 */
public class ListManipulator {
    /**
     * Warning: mutates original list
     *
     * @param l
     * @return
     */
    public static List randomise(List l) {
        List toRet = new ArrayList();
        Random r = new Random();
        int length = l.size();
        for (int i = 0; i < length; i++) {
            toRet.add(l.remove(Math.abs(r.nextInt()) % l.size()));
        }
        return toRet;
    }

    /**
     * Warning: mutates original list
     *
     * @param l
     * @return
     */
    public static List<DisplayFormatable> sortAlphabetical(List<DisplayFormatable> l) {
        l.sort(new Comparator<DisplayFormatable>() {
            @Override
            public int compare(DisplayFormatable a, DisplayFormatable b) {
                return a.toString().compareTo(b.toString());
            }
        });

        return l;
    }

}
