package com.mehnadnerd.testtex.gui;

import javafx.scene.control.TreeItem;

/**
 * Created by mehnadnerd on 2016-04-17.
 */
public class ChoiceContainerTreeItem extends TreeItem<Object> {
    public ChoiceContainerTreeItem(String name) {
        super(name);
    }

    public String getNameString() {
        return super.getValue().toString();
    }
}
