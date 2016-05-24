package com.mehnadnerd.testtex.data.resource;

import com.mehnadnerd.testtex.util.TestTeXConstants;
import com.mehnadnerd.testtex.util.TurtleStringModifiy;
import javafx.scene.control.TreeItem;

/**
 * Created by mehnadnerd on 2016-05-20.
 */
public class CodeResource extends Resource {
    private Language language = Language.JAVA;
    private String code = "";

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language l) {
        this.language = l;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String s) {
        this.code = s;
    }

    public enum Language {
        JAVA("Java"), HTML("HTML"), R("R"), C("C"), CPP("C++"), LISP("Lisp");
        public final String listingName;

        Language(String s) {
            listingName = s;
        }
    }

    @Override
    public TreeItem toDisplayFormat() {
        return new TreeItem<>(this);
    }

    @Override
    public String toTeXFormat() {
        StringBuffer toRet = new StringBuffer();
        toRet.append("\\begin{lstlisting}[language=").append(language.listingName).append("]\n");
        toRet.append(code);
        toRet.append("\\end{lstlisting}\n");
        toRet.append("\n");
        return toRet.toString();
    }

    @Override
    public String toString() {
        return "Code|" + language.listingName + "|" + TurtleStringModifiy.trim(code, TestTeXConstants.maxDisplayChars);
    }
}
