package com.mehnadnerd.testtex.data.choice;

import com.mehnadnerd.testtex.data.DisplayFormatable;
import com.mehnadnerd.testtex.data.TeXFormatable;
import com.mehnadnerd.testtex.util.TestTeXConstants;
import com.mehnadnerd.testtex.util.TurtleStringModifiy;
import javafx.scene.control.TreeItem;

public class Choice implements DisplayFormatable, TeXFormatable {

    public static final String DEFAULTCHOICETEXT = "Response";
    public static final String DEFAULTCHOICEEXPLANATIONTEXT = "";

    protected String ctext = DEFAULTCHOICETEXT;
    protected String explanation = DEFAULTCHOICEEXPLANATIONTEXT;
    protected boolean isCorrect;

    public Choice() {
        this(DEFAULTCHOICETEXT);
    }

    public Choice(String ctext) {
        this(ctext, DEFAULTCHOICEEXPLANATIONTEXT, false);
    }

    public Choice(String ctext, boolean isCorrect) {
        this(ctext, DEFAULTCHOICEEXPLANATIONTEXT, isCorrect);
    }

    public void setChoiceText(String s) {
        this.ctext = s;
    }

    public String getChoiceText() {
        return ctext;
    }

    public void setExplanation(String s) {
        this.explanation = s;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setCorrect(boolean b) {
        this.isCorrect = b;
    }

    public boolean getCorrect() {
        return isCorrect;
    }

    public Choice(String ctext, String explanation, boolean isCorrect) {
        this.ctext = ctext;
        this.explanation = explanation;
        this.isCorrect = isCorrect;
    }

    @Override
    public String toTeXFormat() {
        StringBuffer toRet = new StringBuffer();
        if (isCorrect) {
            toRet.append("\\CorrectChoice ");
        } else {
            toRet.append("\\choice ");
        }

        toRet.append(ctext);
        toRet.append("\n");
        return toRet.toString();
    }

    @Override
    public TreeItem toDisplayFormat() {
        return new TreeItem<>(this);
    }

    @Override
    public String toString() {
        return TurtleStringModifiy.trim(ctext, TestTeXConstants.maxDisplayChars);
    }
}
