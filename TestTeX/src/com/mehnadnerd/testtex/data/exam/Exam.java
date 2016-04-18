package com.mehnadnerd.testtex.data.exam;

import com.mehnadnerd.testtex.data.DisplayFormatable;
import com.mehnadnerd.testtex.data.TeXFormatable;
import com.mehnadnerd.testtex.data.question.Question;
import javafx.scene.control.TreeItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mehnadnerd on 2016-04-16.
 */
public class Exam implements TeXFormatable, DisplayFormatable {
    private List<Question> questions = new ArrayList<Question>();
    private String examTitle = "Turtwig Exam";
    private String classTitle = "Turtwig Class";
    private String testDate = "" + LocalDateTime.now().getYear();
    private boolean displayAnswers;

    public void setExamTitle(String s) {
        this.examTitle = s;
    }

    public void setClassTitle(String s) {
        this.classTitle = s;
    }

    public void setTestDate(String s) {
        this.testDate = s;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public void setDisplayAnswers(boolean b) {
        displayAnswers = b;
    }

    @Override
    public String toTeXFormat() {
        StringBuffer toRet = new StringBuffer();
        toRet.append("\\documentclass[addpoints");
        if (displayAnswers) {
            toRet.append(",answers");
        }
        toRet.append("]{exam}\n");
        toRet.append("\\usepackage{enumerate}\n");
        toRet.append("\\usepackage{listings}");
        toRet.append("\n");
        toRet.append("\\title{" + examTitle + "}\n");
        toRet.append("\\date{" + testDate + "}\n");

        toRet.append("\\pagestyle{headandfoot}\n");
        toRet.append("\\firstpageheader{" + classTitle + "}{\\testtitle}{\\testdate}\n");
        toRet.append("\\firstpageheadrule");
        toRet.append("\\runningheader{" + classTitle + "}{\\testtitle \\,Page\thepage\\ of \\numpages}{\\testdate}\n");
        toRet.append("\\runningheadrule\n");

        toRet.append("\\begin {document}\n");

        toRet.append("\\newcommand{\\testtitle} {" + examTitle + "}\n");
        toRet.append("\\newcommand{\\testdate} {" + testDate + "}\n\n");

        toRet.append("\\begin{questions}\n");
        for (Question q : questions) {
            toRet.append(q.toTeXFormat());
        }
        toRet.append("\\end{questions}\n");
        toRet.append("\\end{document}");

        return toRet.toString();
    }

    @Override
    public TreeItem toDisplayFormat() {
        TreeItem toRet = new TreeItem<>(this);
        for (Question q : questions) {
            toRet.getChildren().add(q.toDisplayFormat());
        }
        return toRet;
    }

    @Override
    public String toString() {
        return this.examTitle;
    }
}
