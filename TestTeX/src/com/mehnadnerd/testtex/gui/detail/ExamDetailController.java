package com.mehnadnerd.testtex.gui.detail;

import com.mehnadnerd.testtex.data.exam.Exam;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mehnadnerd on 2016-04-18.
 */
public class ExamDetailController extends DetailController {
    @FXML
    private CheckBox ExamShowAnswers;
    @FXML
    private TextField ExamDate;
    @FXML
    private TextField ExamClass;
    @FXML
    private TextField ExamTitle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public boolean getExamShowAnswers() {
        return ExamShowAnswers.isSelected();
    }

    public String getExamDate() {
        return ExamDate.getText();
    }

    public String getExamClass() {
        return ExamClass.getText();
    }

    public String getExamTitle() {
        return ExamTitle.getText();
    }

    public Exam writeExam(Exam e) {
        e.setDisplayAnswers(this.getExamShowAnswers());
        e.setExamDate(this.getExamDate());
        e.setClassTitle(this.getExamClass());
        e.setExamTitle(this.getExamTitle());
        return e;
    }

    public void loadExam(Exam e) {
        this.setExamShowAnswers(e.getDisplayAnswers());
        this.setExamDate(e.getExamDate());
        this.setExamClass(e.getClassTitle());
        this.setExamTitle(e.getExamTitle());
    }

    public void setExamShowAnswers(boolean b) {
        ExamShowAnswers.setSelected(b);
    }

    public void setExamDate(String s) {
        ExamDate.setText(s);
    }

    public void setExamClass(String s) {
        ExamClass.setText(s);
    }

    public void setExamTitle(String s) {
        ExamTitle.setText(s);
    }

    @Override
    public void setEnterHandler(EventHandler<ActionEvent> handler) {
        ExamShowAnswers.setOnAction(handler);
        ExamDate.setOnAction(handler);
        ExamClass.setOnAction(handler);
        ExamTitle.setOnAction(handler);
    }
}
