package com.mehnadnerd.testtex.data.choice;

import com.mehnadnerd.testtex.data.question.RomanQuestion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mehnadnerd on 2016-04-16.
 */
public class RomanChoice extends Choice {
    private RomanQuestion encaps;

    public RomanChoice(RomanQuestion encaps) {
        this.encaps = encaps;
    }

    public RomanChoice(RomanQuestion encaps, Choice... refs) {
        this.encaps = encaps;
        for (Choice c : refs) {
            values.add(new RomanChoiceXref(encaps, c));
        }
    }

    public void addRef(Choice ref) {
        values.add(new RomanChoiceXref(encaps, ref));
    }

    private List<RomanChoiceXref> values = new ArrayList<RomanChoiceXref>();

    @Override
    public String toTeXFormat() {
        StringBuffer toRet = new StringBuffer();
        if (isCorrect) {
            toRet.append("\\CorrectChoice ");
        } else {
            toRet.append("\\choice ");
        }
        switch (values.size()) {
            case 1:
                toRet.append(values.get(0).toTeXFormat());
                break;
            case 2:
                toRet.append(values.get(0).toTeXFormat());
                toRet.append(" and ");
                toRet.append(values.get(1).toTeXFormat());
                break;
            case 3:
                toRet.append(values.get(0).toTeXFormat());
                toRet.append(", ");
                toRet.append(values.get(1).toTeXFormat());
                toRet.append(", and ");
                toRet.append(values.get(2).toTeXFormat());
        }

        toRet.append(".\n");
        return toRet.toString();
    }

    @Override
    public String toString() {
        StringBuffer toRet = new StringBuffer();
        switch (values.size()) {
            case 1:
                toRet.append(values.get(0).toTeXFormat());
                break;
            case 2:
                toRet.append(values.get(0).toTeXFormat());
                toRet.append(" and ");
                toRet.append(values.get(1).toTeXFormat());
                break;
            case 3:
                toRet.append(values.get(0).toTeXFormat());
                toRet.append(", ");
                toRet.append(values.get(1).toTeXFormat());
                toRet.append(", and ");
                toRet.append(values.get(2).toTeXFormat());
        }
        return toRet.toString();
    }
}
