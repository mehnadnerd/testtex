package com.mehnadnerd.testtex.data.question;

import com.mehnadnerd.testtex.data.SaveFormatable;
import com.mehnadnerd.testtex.data.TeXFormatable;
import com.mehnadnerd.testtex.data.choice.Choice;

import java.util.ArrayList;
import java.util.List;

public class Question implements SaveFormatable, TeXFormatable {
    protected String questiontext;
    protected List<Choice> choices = new ArrayList<Choice>();//will be ordered into the the order for printing
    protected int pointval;

    public Question() {
    }

    public Question(String question, List<Choice> choices) {
        questiontext = question;
        this.choices = choices;
    }

    public void addChoice(Choice c) {
        choices.add(c);
    }

    public void removeChoice(Choice c) {
        choices.remove(c);
    }

    public void setQuestiontext(String s) {
        questiontext = s;
    }

    public void setPointval(int i) {
        pointval = i;
    }

    /**
     * Get the number currently assigned to a choice
     *
     * @param choice
     * @return
     */
    public int getChoiceNum(Choice choice) {
        return choices.indexOf(choice);
    }

    @Override
    public String toTeXFormat() {
        StringBuffer toRet = new StringBuffer();
        toRet.append("\\question[");
        toRet.append(pointval);
        toRet.append("]\n");
        toRet.append(questiontext);
        toRet.append("\n\\begin{choices}\n");

        //main choice format processing
        for (Choice c : choices) {
            toRet.append(c.toTeXFormat());
        }
        toRet.append("\\end{choices}\n\n");

        return toRet.toString();
    }
}