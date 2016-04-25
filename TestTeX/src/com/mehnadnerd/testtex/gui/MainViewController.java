package com.mehnadnerd.testtex.gui;

import com.mehnadnerd.testtex.ApplicationStart;
import com.mehnadnerd.testtex.BackendManager;
import com.mehnadnerd.testtex.data.choice.Choice;
import com.mehnadnerd.testtex.data.exam.Exam;
import com.mehnadnerd.testtex.data.question.Question;
import com.mehnadnerd.testtex.data.question.RomanQuestion;
import com.mehnadnerd.testtex.gui.detail.ChoiceDetailController;
import com.mehnadnerd.testtex.gui.detail.ExamDetailController;
import com.mehnadnerd.testtex.gui.detail.QuestionDetailController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mehnadnerd on 2016-04-17.
 */
public class MainViewController implements Initializable {
    private DetailPaneListener paneListener;

    @FXML
    private TreeView treeView;

    @FXML
    private MenuItem saveButton;
    @FXML
    private MenuItem newButton;
    @FXML
    private MenuItem saveAsButton;
    @FXML
    private MenuItem openButton;
    @FXML
    private MenuItem closeButton;
    @FXML
    private MenuItem exportButton;
    @FXML
    private MenuItem aboutButton;

    @FXML
    private MenuItem smartAddButton;
    @FXML
    private MenuItem questionAddButton;
    @FXML
    private MenuItem romanQuestionAddButton;
    @FXML
    private MenuItem choiceAddButton;

    @FXML
    private Pane detailPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        treeView.setRoot(BackendManager.getDisplayTree());
        paneListener = new DetailPaneListener();
        treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        treeView.getSelectionModel().selectedItemProperty().addListener(paneListener);


        saveButton.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));
        saveAsButton.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHIFT_DOWN, KeyCombination.SHORTCUT_DOWN));
        newButton.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN));
        closeButton.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN));
        openButton.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN));
        exportButton.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN));
        smartAddButton.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN));


        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        exportButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BackendManager.export();
            }
        });

        smartAddButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Exam selected->Add question
                //Question Selected->Add question
                //Choices Header Selected->Add choice
                //Choice Selected->Add choice
                //Roman Options Header Selected->Add choice

                Object o = ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getValue();
                System.out.println("Attempting to add from a " + o.getClass().getCanonicalName());
                if (o.getClass().equals(Exam.class)) {
                    Question toAdd = new Question();
                    //Add Question both to exam and to view-stops from having to regen tree
                    ((Exam) o).addQuestion(toAdd);
                    treeView.getRoot().getChildren().add(toAdd.toDisplayFormat());
                } else if (o.getClass().equals(Question.class)) {
                    Question toAdd = new Question();
                    ((Exam) treeView.getRoot().getValue()).addQuestion(toAdd);
                    treeView.getRoot().getChildren().add(toAdd.toDisplayFormat());
                } else if (o.getClass().equals(String.class) &&
                        ((String) o).equalsIgnoreCase("Choices")) {
                    Choice toAdd = new Choice("Response");

                    ((Question) ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getParent().getValue()).addChoice(toAdd);
                    ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getChildren().add(toAdd.toDisplayFormat());

                } else if (o.getClass().equals(Choice.class)) {
                    Choice toAdd = new Choice("Response");
                    ((com.mehnadnerd.testtex.data.question.Question) ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getParent().getParent().getValue()).addChoice(toAdd);
                    ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getParent().getChildren().add(toAdd.toDisplayFormat());
                } else {
                    System.out.println(o.getClass());
                }
                forceRefresh();
            }
        });

        questionAddButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Question toAdd = new Question();
                ((Exam) treeView.getRoot().getValue()).addQuestion(toAdd);
                treeView.getRoot().getChildren().add(toAdd.toDisplayFormat());
            }
        });

        romanQuestionAddButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RomanQuestion toAdd = new RomanQuestion();
                ((Exam) treeView.getRoot().getValue()).addQuestion(toAdd);
                treeView.getRoot().getChildren().add(toAdd.toDisplayFormat());
            }
        });

        choiceAddButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Choice toAdd = new Choice();
                Object selected = ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getValue();
                if (selected.getClass().equals(Question.class)) {
                    ((Question) selected).addChoice(toAdd);
                    ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getChildren().add(toAdd.toDisplayFormat());
                }
                treeView.getRoot().getChildren().add(toAdd.toDisplayFormat());
            }
        });
    }

    private void forceRefresh() {
        treeView.refresh();
    }

    private class DetailPaneListener implements EventHandler<ActionEvent>, ChangeListener<TreeItem<Object>> {
        private ChoiceDetailController choiceDetail;
        private ExamDetailController examDetail;
        private QuestionDetailController questionDetail;

        private Pane choiceDetailPane;
        private Pane examDetailPane;
        private Pane questionDetailPane;
        private Pane noneDetailPane;

        public DetailPaneListener() {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("gui/detail/detailChoice.fxml"));
                choiceDetailPane = fxmlLoader.load();
                choiceDetail = fxmlLoader.getController();
                fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("gui/detail/detailExam.fxml"));
                examDetailPane = fxmlLoader.load();
                examDetail = fxmlLoader.getController();
                fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("gui/detail/detailQuestion.fxml"));
                questionDetailPane = fxmlLoader.load();
                questionDetail = fxmlLoader.load();
                fxmlLoader = new FXMLLoader(ApplicationStart.class.getResource("gui/detail/detailNone.fxml"));
                noneDetailPane = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            detailPane.getChildren().add(examDetailPane);
            ((TextField) examDetailPane.getChildren().get(1)).setText(treeView.getRoot().getValue().toString());
            treeView.getSelectionModel().select(treeView.getRoot());
        }

        @Override
        public void changed(ObservableValue<? extends TreeItem<Object>> observable, TreeItem<Object> oldValue, TreeItem<Object> newValue) {
            //use old values to record changes
            if (oldValue != null) {
                if (oldValue.getValue().getClass().equals(Choice.class)) {
                    Choice c = ((Choice) oldValue.getValue());
                    c.setChoiceText(((TextField) choiceDetailPane.getChildren().get(1)).getText());
                    treeView.refresh();
                } else if (oldValue.getValue().getClass().equals(Exam.class)) {
                    Exam e = (Exam) oldValue.getValue();
                    e.setExamTitle(((TextField) examDetailPane.getChildren().get(1)).getText());
                } else if (oldValue.getValue().getClass().equals(Question.class)) {
                    Question q = (Question) oldValue.getValue();
                    q.setQuestiontext(((TextField) questionDetailPane.getChildren().get(1)).getText());
                } else {
                }
            }
            if (newValue != null) {
                //change detailPane for new value
                if (newValue.getValue().getClass().equals(Choice.class)) {
                    detailPane.getChildren().set(0, choiceDetailPane);
                    ((TextField) choiceDetailPane.getChildren().get(1)).setText(newValue.getValue().toString());
                } else if (newValue.getValue().getClass().equals(Exam.class)) {
                    detailPane.getChildren().set(0, examDetailPane);
                    ((TextField) examDetailPane.getChildren().get(1)).setText(newValue.getValue().toString());
                } else if (newValue.getValue().getClass().equals(Question.class)) {
                    detailPane.getChildren().set(0, questionDetailPane);
                    ((TextField) questionDetailPane.getChildren().get(1)).setText(newValue.getValue().toString());
                } else {
                    detailPane.getChildren().set(0, noneDetailPane);
                }
            } else {
                detailPane.getChildren().set(0, noneDetailPane);
            }
            forceRefresh();


        }

        @Override
        public void handle(ActionEvent event) {

        }
    }


}
