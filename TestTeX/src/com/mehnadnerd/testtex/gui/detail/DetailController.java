package com.mehnadnerd.testtex.gui.detail;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

/**
 * Created by mehnadnerd on 2016-05-03.
 */
public abstract class DetailController implements Initializable {
    public abstract void setEnterHandler(EventHandler<ActionEvent> handler);
}
