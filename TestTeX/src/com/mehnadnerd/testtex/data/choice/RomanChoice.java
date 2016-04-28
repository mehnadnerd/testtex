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
            this.refs.add(new RomanChoiceXref(encaps, c));
        }
    }

    public void addRef(Choice ref) {
        refs.add(new RomanChoiceXref(encaps, ref));
    }

    public boolean removeRef(Choice ref) {
        return refs.remove(new RomanChoiceXref(encaps, ref));
    }

    public List<RomanChoiceXref> getRefs() {
        return refs;
    }

    public void setRefs(List<RomanChoiceXref> l) {
        this.refs = l;
    }

    public RomanQuestion getEncaps() {
        return encaps;
    }

    private List<RomanChoiceXref> refs = new ArrayList<>();

    //TODO: Make work for more than 3 options
    @Override
    public String toTeXFormat() {
        StringBuffer toRet = new StringBuffer();
        if (this.getCorrect()) {
            toRet.append("\\CorrectChoice ");
        } else {
            toRet.append("\\choice ");
        }
        switch (refs.size()) {
            case 1:
                toRet.append(refs.get(0).toTeXFormat());
                break;
            case 2:
                toRet.append(refs.get(0).toTeXFormat());
                toRet.append(" and ");
                toRet.append(refs.get(1).toTeXFormat());
                break;
            case 3:
                toRet.append(refs.get(0).toTeXFormat());
                toRet.append(", ");
                toRet.append(refs.get(1).toTeXFormat());
                toRet.append(", and ");
                toRet.append(refs.get(2).toTeXFormat());
        }

        toRet.append(".\n");
        return toRet.toString();
    }

    @Override
    public String toString() {
        StringBuffer toRet = new StringBuffer();
        switch (refs.size()) {
            case 1:
                toRet.append(refs.get(0).toTeXFormat());
                break;
            case 2:
                toRet.append(refs.get(0).toTeXFormat());
                toRet.append(" and ");
                toRet.append(refs.get(1).toTeXFormat());
                break;
            case 3:
                toRet.append(refs.get(0).toTeXFormat());
                toRet.append(", ");
                toRet.append(refs.get(1).toTeXFormat());
                toRet.append(", and ");
                toRet.append(refs.get(2).toTeXFormat());
        }
        return toRet.toString();
    }

    @Override
    public boolean getCorrect() {
        boolean toRet = true;
        //TODO: Make so ones with only one correct choice are still false
        for (RomanChoiceXref r : refs) {
            toRet &= r.isCorrect();
        }
        return toRet;
    }
}
