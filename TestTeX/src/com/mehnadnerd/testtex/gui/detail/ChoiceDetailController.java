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
public class ChoiceDetailController implements Initializable {
    @FXML
    public TextField ChoiceText;
    @FXML
    public Label ChoiceExplanation;
    @FXML
    public Label ChoiceCorrect;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
