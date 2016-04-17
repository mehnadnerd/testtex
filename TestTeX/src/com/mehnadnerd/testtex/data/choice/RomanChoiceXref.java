package com.mehnadnerd.testtex.data.choice;

import com.mehnadnerd.testtex.data.Xref;
import com.mehnadnerd.testtex.data.question.RomanQuestion;
import com.mehnadnerd.testtex.util.Maths;

/**
 * Created by mehnadnerd on 2016-04-16.
 */
public class RomanChoiceXref extends Xref {
    private RomanQuestion encapsulatingQuestion;
    private Choice reference;

    public RomanChoiceXref(RomanQuestion question, Choice ref) {
        this.encapsulatingQuestion = question;
        this.reference = ref;
    }

    @Override
    public String toTeXFormat() {
        return Maths.numToNumeral(encapsulatingQuestion.getRomanOptionNum(reference) + 1);
    }
}
