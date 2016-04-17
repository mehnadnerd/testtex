package com.mehnadnerd.testtex.data;

import java.util.List;

/**
 * Created by mehnadnerd on 2016-04-16.
 */
public class Arranger {
    public static List arrange(List toArrange, List<PlacementRequirement> requirements) {
        if (checkArrange(toArrange, requirements)) {
            return toArrange;
        } else {
            System.out.println("Arrangement isn't proper, but this method isn't written");
        }

        return toArrange;
    }

    private static boolean checkArrange(List check, List<PlacementRequirement> requirements) {
        for (int i = 0; i < check.size(); i++) {
            if (!requirements.get(i).isSatisfied(check, i)) {
                return false;
            }
        }
        return true;
    }
}
