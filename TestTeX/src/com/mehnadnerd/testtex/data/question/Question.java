package com.mehnadnerd.testtex.data.question;

import com.mehnadnerd.testtex.data.DisplayFormatable;
import com.mehnadnerd.testtex.data.TeXFormatable;
import com.mehnadnerd.testtex.data.choice.Choice;
import com.mehnadnerd.testtex.gui.ChoiceContainerTreeItem;
import com.mehnadnerd.testtex.util.Strings;
import com.mehnadnerd.testtex.util.TestTeXConstants;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;
import java.util.List;

public class Question implements
        DisplayFormatable, TeXFormatable {
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


    @Override
    public TreeItem toDisplayFormat() {
        TreeItem toRet = new TreeItem<>(this);
        TreeItem choiceHolder = new ChoiceContainerTreeItem("Choices");
        toRet.getChildren().add(choiceHolder);
        for (Choice c : choices) {
            choiceHolder.getChildren().add(c.toDisplayFormat());
        }

        return toRet;
    }

    @Override
    public String toString() {
        return Strings.trim(questiontext, TestTeXConstants.maxDisplayChars);
    }
}