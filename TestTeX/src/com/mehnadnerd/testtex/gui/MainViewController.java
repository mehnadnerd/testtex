package com.mehnadnerd.testtex.gui;

import com.mehnadnerd.testtex.ApplicationStart;
import com.mehnadnerd.testtex.BackendManager;
import com.mehnadnerd.testtex.data.choice.Choice;
import com.mehnadnerd.testtex.data.choice.RomanChoice;
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
                if (o instanceof Exam) {
                    Question toAdd = new Question();
                    //Add Question both to exam and to view-stops from having to regen tree
                    ((Exam) o).addQuestion(toAdd);
                    treeView.getRoot().getChildren().add(toAdd.toDisplayFormat());
                } else if (o instanceof Question && !(o instanceof RomanQuestion)) {
                    Question toAdd = new Question();
                    ((Exam) treeView.getRoot().getValue()).addQuestion(toAdd);
                    treeView.getRoot().getChildren().add(toAdd.toDisplayFormat());
                } else if (o instanceof String &&
                        ((String) o).equalsIgnoreCase("Choices")) {
                    Choice toAdd = new Choice("Response");

                    ((Question) ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getParent().getValue()).addChoice(toAdd);
                    ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getChildren().add(toAdd.toDisplayFormat());

                } else if (o instanceof Choice) {
                    Choice toAdd = new Choice("Response");
                    ((com.mehnadnerd.testtex.data.question.Question) ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getParent().getParent().getValue()).addChoice(toAdd);
                    ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getParent().getChildren().add(toAdd.toDisplayFormat());
                } else {
                    System.out.println(o.getClass());
                }
                forceRefresh();
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
            ((TextField) examDetailPane.getChildren().get(1)).setText(((Exam) treeView.getRoot().getValue()).toString());
            treeView.getSelectionModel().select(treeView.getRoot());
        }

        @Override
        public void changed(ObservableValue<? extends TreeItem<Object>> observable, TreeItem<Object> oldValue, TreeItem<Object> newValue) {
            //use old values to record changes
            if (oldValue != null) {
                if (oldValue.getValue() instanceof Choice && !(newValue.getValue() instanceof RomanChoice)) {
                    Choice c = ((Choice) oldValue.getValue());
                    c.setChoiceText(((TextField) choiceDetailPane.getChildren().get(1)).getText());
                    treeView.refresh();
                } else if (oldValue.getValue() instanceof Exam) {
                    Exam e = (Exam) oldValue.getValue();
                    e.setExamTitle(((TextField) examDetailPane.getChildren().get(1)).getText());
                } else if (oldValue.getValue() instanceof Question && !(oldValue.getValue() instanceof RomanQuestion)) {
                    Question q = (Question) oldValue.getValue();
                    q.setQuestiontext(((TextField) questionDetailPane.getChildren().get(1)).getText());
                } else {
                }
            }
            if (newValue != null) {
                //change detailPane for new value
                if (newValue.getValue() instanceof Choice && !(newValue.getValue() instanceof RomanChoice)) {
                    detailPane.getChildren().set(0, choiceDetailPane);
                    ((TextField) choiceDetailPane.getChildren().get(1)).setText(((Choice) newValue.getValue()).toString());
                } else if (newValue.getValue() instanceof Exam) {
                    detailPane.getChildren().set(0, examDetailPane);
                    ((TextField) examDetailPane.getChildren().get(1)).setText(((Exam) newValue.getValue()).toString());
                } else if (newValue.getValue() instanceof Question && !(newValue.getValue() instanceof RomanQuestion)) {
                    detailPane.getChildren().set(0, questionDetailPane);
                    ((TextField) questionDetailPane.getChildren().get(1)).setText(((Question) newValue.getValue()).toString());
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
