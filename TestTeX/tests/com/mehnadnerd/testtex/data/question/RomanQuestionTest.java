package com.mehnadnerd.testtex.data.question;

import com.mehnadnerd.testtex.data.choice.Choice;
import com.mehnadnerd.testtex.data.choice.RomanChoice;
import org.junit.Test;

/**
 * Created by mehnadnerd on 2016-04-16.
 */
public class RomanQuestionTest {

    @Test
    public void testToTeXFormat() throws Exception {
        RomanQuestion q = new RomanQuestion();
        q.setQuestiontext("Which of the following is(are) true of Turtwig?");
        q.setPointval(387);
        Choice ci = new Choice("They are the best!");
        Choice cii = new Choice("They are cute.");
        Choice ciii = new Choice("They are stupid.");
        q.addRomanOption(ci);
        q.addRomanOption(cii);
        q.addRomanOption(ciii);
        q.addChoice(new RomanChoice(q, ci));
        q.addChoice(new RomanChoice(q, cii));
        q.addChoice(new RomanChoice(q, ciii));
        q.addChoice(new RomanChoice(q, ci, ciii));
        q.addChoice(new RomanChoice(q, ci, cii, ciii));

        System.out.println(q.toTeXFormat());
    }
}