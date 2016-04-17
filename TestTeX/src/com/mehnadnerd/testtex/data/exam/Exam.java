package com.mehnadnerd.testtex.data.exam;

import com.mehnadnerd.testtex.data.TeXFormatable;
import com.mehnadnerd.testtex.data.question.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mehnadnerd on 2016-04-16.
 */
public class Exam implements TeXFormatable {
    private List<Question> questions = new ArrayList<Question>();
    private String examTitle;
    private String classTitle;
    private String testDate;

    @Override
    public String toTeXFormat() {
        StringBuffer toRet = new StringBuffer();
        toRet.append("\\documentclass[addpoints]{exam}\n");
        toRet.append("\\usepackage{enumerate}\n");
        toRet.append("\\usepackage{listings}");
        toRet.append("\n");
        toRet.append("\\title{" + examTitle + "}\n");
        toRet.append("\\date{" + testDate + "}\n");

        toRet.append(" \\pagestyle{headandfoot}\n");
        toRet.append("\\firstpageheader{" + classTitle + "}{\\testtitle}{\\testdate}\n");
        toRet.append("\\firstpageheadrule");
        toRet.append("\\runningheader{" + classTitle + "}{\\testtitle \\,Page\thepage\\ of \\numpages}{\\testdate}\n");
        toRet.append("\\runningheadrule\n");

        toRet.append("\\begin {document}\n");

        toRet.append("\\newcommand{\\testtitle} {" + examTitle + "}\n");
        toRet.append(" \\newcommand{\\testdate} {" + testDate + "}\n");

        toRet.append("\\begin{questions}\n");
        for (Question q : questions) {
            toRet.append(q.toTeXFormat());
        }
        toRet.append("\\end{questions}\n");
        toRet.append("\\end{document}");

        return toRet.toString();
    }
}
