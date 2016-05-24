package com.mehnadnerd.testtex.gui.detail;

import com.mehnadnerd.testtex.data.resource.TextResource;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mehnadnerd on 2016-04-18.
 */
public class TextResourceController extends DetailController {
    @FXML
    public TextField ResourceText;

    public String getResourceText() {
        return ResourceText.getText();
    }

    public void setResourceText(String s) {
        ResourceText.setText(s);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public TextResource writeTextResource(TextResource r) {
        r.setText(getResourceText());
        return r;
    }

    public void loadTextResource(TextResource r) {
        this.setResourceText(r.getText());
    }

    @Override
    public void setEnterHandler(EventHandler<ActionEvent> handler) {
        ResourceText.setOnAction(handler);
    }
}
