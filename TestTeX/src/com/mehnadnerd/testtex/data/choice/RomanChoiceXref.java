package com.mehnadnerd.testtex.data.choice;

import com.mehnadnerd.testtex.data.Xref;
import com.mehnadnerd.testtex.data.question.RomanQuestion;
import com.mehnadnerd.testtex.util.Maths;
import com.mehnadnerd.testtex.util.TestTeXConstants;
import com.mehnadnerd.testtex.util.TurtleStringModifiy;

/**
 * Immutable class for RomanChoiceXref
 */
public class RomanChoiceXref extends Xref {
    private RomanQuestion encapsulatingQuestion;
    private Choice reference;

    public RomanQuestion getEncapsulatingQuestion() {
        return encapsulatingQuestion;
    }

    public Choice getReference() {
        return reference;
    }


    public RomanChoiceXref(RomanQuestion question, Choice ref) {
        this.encapsulatingQuestion = question;
        this.reference = ref;
    }

    @Override
    public String toTeXFormat() {
        return Maths.numToNumeral(encapsulatingQuestion.getRomanOptionNum(reference) + 1);
    }

    public String toDisplayString() {
        return TurtleStringModifiy.trim(reference.getChoiceText(), TestTeXConstants.maxDisplayChars);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RomanChoiceXref)) {
            return false;
        }
        RomanChoiceXref x = ((RomanChoiceXref) o);
        if (!x.getEncapsulatingQuestion().equals(this.getEncapsulatingQuestion())) {
            return false;
        }
        if (!x.getReference().equals(this.getReference())) {
            return false;
        }

        return true;
    }

    public boolean isCorrect() {
        return reference.getCorrect();
    }
}
