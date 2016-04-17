package com.mehnadnerd.testtex.data;

import java.util.List;

/**
 * Only checks to make sure nothing after is before and vice versa, not for existence, immutable
 *
 * @param <T>
 */
public class PlacementRequirement<T> {
    public PlacementRequirement(List<T> beforeThis, List<T> afterThis) {
        this.beforeThis = beforeThis;
        this.afterThis = afterThis;
    }

    private List<T> beforeThis;
    private List<T> afterThis;

    public boolean isSatisfied(List<T> list, int placeOfThis) {
        List<T> beforePart = list.subList(0, placeOfThis);
        List<T> afterPart = list.subList(placeOfThis + 1, list.size());
        for (T t : beforeThis) {
            if (afterPart.contains(t)) {
                return false;
            }
        }
        for (T t : afterThis) {
            if (beforePart.contains(t)) {
                return false;
            }
        }
        return true;
    }

    public boolean isSatisfied(List<T> list, T objectOfThis) {
        return this.isSatisfied(list, list.indexOf(objectOfThis));
    }
}
