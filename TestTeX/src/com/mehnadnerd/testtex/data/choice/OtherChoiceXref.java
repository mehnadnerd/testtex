package com.mehnadnerd.testtex.data.choice;

import com.mehnadnerd.testtex.data.Xref;
import com.mehnadnerd.testtex.data.question.Question;
import com.mehnadnerd.testtex.util.Maths;

/**
 * Created by mehnadnerd on 2016-04-16.
 */
public class OtherChoiceXref extends Xref {
    private Question encapsulatingQuestion;
    private Choice reference;

    public OtherChoiceXref(Question question, Choice ref) {
        this.encapsulatingQuestion = question;
        this.reference = ref;
    }

    @Override
    public String toTeXFormat() {
        return "" + Maths.numToLetter(encapsulatingQuestion.getChoiceNum(reference));
    }
}
