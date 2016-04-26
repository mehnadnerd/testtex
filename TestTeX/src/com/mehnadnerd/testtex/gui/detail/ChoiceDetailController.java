package com.mehnadnerd.testtex.gui.detail;

import com.mehnadnerd.testtex.data.choice.Choice;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mehnadnerd on 2016-04-18.
 */
public class ChoiceDetailController implements Initializable {
    @FXML
    private TextField ChoiceText;
    @FXML
    private TextField ChoiceExplanation;
    @FXML
    private CheckBox ChoiceCorrect;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public String getChoiceText() {
        return ChoiceText.getText();
    }

    public String getChoiceExplanation() {
        return ChoiceExplanation.getText();
    }

    public boolean getChoiceCorrect() {
        return ChoiceCorrect.isSelected();
    }

    public Choice writeChoice(Choice c) {
        c.setChoiceText(this.getChoiceText());
        c.setExplanation(this.getChoiceExplanation());
        c.setCorrect(this.getChoiceCorrect());
        return c;
    }

    public void loadChoice(Choice c) {
        this.setChoiceText(c.getChoiceText());
        this.setChoiceExplanation(c.getExplanation());
        this.setChoiceCorrect(c.getCorrect());
    }

    public void setChoiceText(String s) {
        ChoiceText.setText(s);
    }

    public void setChoiceExplanation(String s) {
        ChoiceExplanation.setText(s);
    }

    public void setChoiceCorrect(boolean b) {
        ChoiceCorrect.setSelected(b);
    }
}
