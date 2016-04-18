package com.mehnadnerd.testtex.gui.detail;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mehnadnerd on 2016-04-18.
 */
public class ExamDetailController implements Initializable {
    @FXML
    public Label ExamShowAnswers;
    @FXML
    public Label ExamDate;
    @FXML
    public Label ExamClass;
    @FXML
    public TextField ExamTitle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
