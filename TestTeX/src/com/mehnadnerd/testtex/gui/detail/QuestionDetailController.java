package com.mehnadnerd.testtex.gui.detail;

import com.mehnadnerd.testtex.data.question.Question;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mehnadnerd on 2016-04-18.
 */
public class QuestionDetailController implements Initializable {
    @FXML
    public TextField QuestionText;
    @FXML
    public TextField QuestionPoints;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public String getQuestionText() {
        return QuestionText.getText();
    }

    public int getQuestionPoints() {
        int toRet = 1;
        try {
            toRet = Integer.valueOf(QuestionPoints.getText());
        } catch (NumberFormatException e) {
            System.err.println("Failed to parse Question Point value: " + QuestionPoints.getText());
        }
        return toRet;
    }

    public void loadQuestion(Question q) {
        this.setQuestionText(q.getQuestionText());
        this.setQuestionPoints(q.getPointVal());
    }

    public Question writeQuestion(Question q) {
        q.setQuestionText(this.getQuestionText());
        q.setPointVal(this.getQuestionPoints());
        return q;
    }

    public void setQuestionText(String s) {
        QuestionText.setText(s);
    }

    public void setQuestionPoints(int i) {
        QuestionPoints.setText(Integer.toString(i));
    }
}
