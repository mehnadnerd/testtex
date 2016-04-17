package com.mehnadnerd.testtex.gui;

import com.mehnadnerd.testtex.ApplicationStart;
import com.mehnadnerd.testtex.BackendManager;
import com.mehnadnerd.testtex.data.choice.Choice;
import com.mehnadnerd.testtex.data.exam.Exam;
import com.mehnadnerd.testtex.data.question.Question;
import com.mehnadnerd.testtex.data.question.RomanQuestion;
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
    private MenuItem addButton;

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
        addButton.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN));


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

        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Attempting to add");
                Object o = ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getValue();
                if (o instanceof Question && !(o instanceof RomanQuestion)) {
                    System.out.println("Valid place to add");
                    ((Question) o).addChoice(new Choice("Response"));
                    forceRefresh();
                    System.out.println(((Question) o).toTeXFormat());
                } else {
                    System.out.println(o.getClass());
                }
            }
        });


    }

    private void forceRefresh() {
        treeView.refresh();
        treeView.setRoot(BackendManager.getDisplayTree());
        treeView.refresh();
    }

    private class DetailPaneListener implements EventHandler<ActionEvent>, ChangeListener<TreeItem<Object>> {
        private Pane choiceDetail;
        private Pane examDetail;
        private Pane questionDetail;
        private Pane noneDetail;

        public DetailPaneListener() {
            try {
                choiceDetail = FXMLLoader.load(ApplicationStart.class.getResource("gui/detailChoice.fxml"));
                examDetail = FXMLLoader.load(ApplicationStart.class.getResource("gui/detailExam.fxml"));
                questionDetail = FXMLLoader.load(ApplicationStart.class.getResource("gui/detailQuestion.fxml"));
                noneDetail = FXMLLoader.load(ApplicationStart.class.getResource("gui/detailNone.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            detailPane.getChildren().add(examDetail);
            treeView.getSelectionModel().select(treeView.getRoot());
        }

        @Override
        public void changed(ObservableValue<? extends TreeItem<Object>> observable, TreeItem<Object> oldValue, TreeItem<Object> newValue) {
            //use old values to record changes
            if (oldValue.getValue() instanceof Choice) {

                Choice c = ((Choice) oldValue.getValue());
                //System.out.println("Switching from " + c);
                //System.out.println(choiceDetail.getChildren().get(1));
                //System.out.println(((TextField) choiceDetail.getChildren().get(1)).getText());
                c.setChoiceText(((TextField) choiceDetail.getChildren().get(1)).getText());
                treeView.refresh();
                //System.out.println("Now is " + c);
            } else if (oldValue.getValue() instanceof Exam) {
                detailPane.getChildren().set(0, examDetail);
            } else if (oldValue.getValue() instanceof Question && !(oldValue.getValue() instanceof RomanQuestion)) {
                detailPane.getChildren().set(0, questionDetail);
            } else {
                detailPane.getChildren().set(0, noneDetail);
            }

            //change detailPane for new value
            if (newValue.getValue() instanceof Choice) {
                detailPane.getChildren().set(0, choiceDetail);
                ((TextField) choiceDetail.getChildren().get(1)).setText(((Choice) newValue.getValue()).toString());
            } else if (newValue.getValue() instanceof Exam) {
                detailPane.getChildren().set(0, examDetail);
            } else if (newValue.getValue() instanceof Question && !(newValue.getValue() instanceof RomanQuestion)) {
                detailPane.getChildren().set(0, questionDetail);
            } else {
                detailPane.getChildren().set(0, noneDetail);
            }
        }

        @Override
        public void handle(ActionEvent event) {

        }
    }


}
