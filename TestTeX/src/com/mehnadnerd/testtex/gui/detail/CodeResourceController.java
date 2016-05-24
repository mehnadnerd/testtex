package com.mehnadnerd.testtex.gui.detail;

import com.mehnadnerd.testtex.data.resource.CodeResource;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mehnadnerd on 2016-04-18.
 */
public class CodeResourceController extends DetailController {
    @FXML
    public TextField ResourceCode;

    public String getResourceCode() {
        return ResourceCode.getText();
    }

    public void setResourceCode(String s) {
        ResourceCode.setText(s);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public CodeResource writeCodeResource(CodeResource r) {
        r.setCode(getResourceCode());
        return r;
    }

    public void loadCodeResource(CodeResource r) {
        this.setResourceCode(r.getCode());
    }

    @Override
    public void setEnterHandler(EventHandler<ActionEvent> handler) {
        ResourceCode.setOnAction(handler);
    }
}
