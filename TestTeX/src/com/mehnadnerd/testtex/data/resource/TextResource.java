package com.mehnadnerd.testtex.data.resource;

import javafx.scene.control.TreeItem;

/**
 * Created by mehnadnerd on 2016-05-20.
 */
public class TextResource extends Resource {
    private String text = "";

    public void setText(String s) {
        text = s;
    }

    public String getText() {
        return text;
    }

    @Override
    public TreeItem toDisplayFormat() {
        return new TreeItem<>(this);
    }

    @Override
    public String toTeXFormat() {
        return text + "\n";
    }
}
