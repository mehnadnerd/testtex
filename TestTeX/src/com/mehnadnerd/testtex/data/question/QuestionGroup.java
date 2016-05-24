package com.mehnadnerd.testtex.data.question;

import com.mehnadnerd.testtex.data.resource.Resource;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mehnadnerd on 2016-05-19.
 */
public class QuestionGroup extends Question {
    protected List<Question> questions = new ArrayList<>();
    protected List<Resource> resources = new ArrayList<>();

    @Override
    public String toTeXFormat() {
        return "";
    }

    @Override
    public String toString() {
        return "Question Group";
    }

    @Override
    public TreeItem toDisplayFormat() {
        TreeItem toRet = new TreeItem(this);

        return toRet;
    }

}
