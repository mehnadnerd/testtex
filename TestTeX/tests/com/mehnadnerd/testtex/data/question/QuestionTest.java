package com.mehnadnerd.testtex.data.question;

import com.mehnadnerd.testtex.data.choice.Choice;
import org.junit.Test;

/**
 * Created by mehnadnerd on 2016-04-16.
 */
public class QuestionTest {

    @Test
    public void testToTeXFormat() throws Exception {
        Question q = new Question();
        q.setQuestiontext("Is Turtwig the best?");
        q.setPointval(387);
        q.addChoice(new Choice("Yes", "Do you need to ask?", true));
        q.addChoice(new Choice("No", "What kind of stupid answer is that?", false));
        System.out.println(q.toTeXFormat());
    }
}