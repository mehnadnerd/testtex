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
    private List<Question> questions = new ArrayList<>();
    private String examTitle = "Turtwig Exam";
    private String classTitle = "Turtwig Class";
    private String testDate = Integer.toString(LocalDateTime.now().getYear());
    private boolean displayAnswers;

    public void setExamTitle(String s) {
        this.examTitle = s;
    }

    public String getExamTitle() {
        return this.examTitle;
    }

    public void setClassTitle(String s) {
        this.classTitle = s;
    }

    public String getClassTitle() {
        return this.classTitle;
    }

    public void setExamDate(String s) {
        this.testDate = s;
    }

    public String getExamDate() {
        return this.testDate;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public void setDisplayAnswers(boolean b) {
        displayAnswers = b;
    }

    public boolean getDisplayAnswers() {
        return displayAnswers;
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
        toRet.append("\\title{").append(examTitle).append("}\n");
        toRet.append("\\date{").append(testDate).append("}\n");

        toRet.append("\\pagestyle{headandfoot}\n");
        toRet.append("\\firstpageheader{").append(classTitle).append("}{\\testtitle}{\\testdate}\n");
        toRet.append("\\firstpageheadrule");
        toRet.append("\\runningheader{").append(classTitle).append("}{\\testtitle \\,Page \\thepage of \\numpages}{\\testdate}\n");
        toRet.append("\\runningheadrule\n");

        toRet.append("\\begin {document}\n");

        toRet.append("\\newcommand{\\testtitle} {").append(examTitle).append("}\n");
        toRet.append("\\newcommand{\\testdate} {").append(testDate).append("}\n\n");

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
