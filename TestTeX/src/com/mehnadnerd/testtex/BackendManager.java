package com.mehnadnerd.testtex;

import com.mehnadnerd.testtex.data.choice.Choice;
import com.mehnadnerd.testtex.data.choice.RomanChoice;
import com.mehnadnerd.testtex.data.exam.Exam;
import com.mehnadnerd.testtex.data.question.Question;
import com.mehnadnerd.testtex.data.question.RomanQuestion;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

/**
 * Provides communication and access between gui and backend, horrible static mess
 */
public class BackendManager {

    private static Stage stage;

    public static void setStage(Stage s) {
        stage = s;
    }

    private static Exam exam = new Exam();

    public static void setExam(Exam e) {
        exam = e;
        if (stage != null) {
            stage.setTitle("TestTeX: " + e.getExamTitle());
        }

    }

    public static void createNewExam() {
        exam = new Exam();
    }

    public static Exam getExam() {
        return exam;
    }

    public static TreeItem getDisplayTree() {
        return exam.toDisplayFormat();
    }

    private static Exam createTestExam() {
        Exam e = new Exam();
        e.setExamTitle("Turtwig Exam");
        e.setClassTitle("Turtwig Class");

        Question q1 = new Question();
        q1.setQuestionText("What is Turtwig's id number?");
        q1.setPointVal(5);
        q1.addChoice(new Choice("1"));
        q1.addChoice(new Choice("251"));
        q1.addChoice(new Choice("387", true));
        q1.addChoice(new Choice("495"));
        RomanQuestion q2 = new RomanQuestion();
        q2.setQuestionText("Which of the following is (are) true of Turtwig?");
        q2.setPointVal(387);
        Choice ci = new Choice("They are the best!", true);
        Choice cii = new Choice("They are cute.", true);
        Choice ciii = new Choice("They are stupid.");
        q2.addRomanOption(ci);
        q2.addRomanOption(cii);
        q2.addRomanOption(ciii);
        q2.addChoice(new RomanChoice(q2, ci));
        q2.addChoice(new RomanChoice(q2, cii));
        q2.addChoice(new RomanChoice(q2, ciii));
        q2.addChoice(new RomanChoice(q2, ci, cii));
        q2.addChoice(new RomanChoice(q2, ci, cii, ciii));
        Question q3 = new Question();
        q3.setQuestionText("How much does Turtwig weigh?");
        q3.setPointVal(5);
        q3.addChoice(new Choice("50"));
        q3.addChoice(new Choice("22.5", true));
        q3.addChoice(new Choice("1.06"));
        q3.addChoice(new Choice("13.7"));

        e.addQuestion(q1);
        e.addQuestion(q2);
        e.addQuestion(q3);
        return e;
    }
}
