package com.mehnadnerd.testtex.gui;

import com.mehnadnerd.testtex.BackendManager;
import com.mehnadnerd.testtex.FileManager;
import com.mehnadnerd.testtex.data.choice.Choice;
import com.mehnadnerd.testtex.data.choice.RomanChoice;
import com.mehnadnerd.testtex.data.exam.Exam;
import com.mehnadnerd.testtex.data.question.Question;
import com.mehnadnerd.testtex.data.question.RomanQuestion;
import com.mehnadnerd.testtex.gui.detail.ChoiceDetailController;
import com.mehnadnerd.testtex.gui.detail.ExamDetailController;
import com.mehnadnerd.testtex.gui.detail.QuestionDetailController;
import com.mehnadnerd.testtex.gui.detail.RomanChoiceDetailController;
import com.mehnadnerd.testtex.util.TestTeXConstants;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mehnadnerd on 2016-04-17.
 */
public class MainViewController implements Initializable {

    private Stage stage;

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
    public MenuItem deleteButton;
    @FXML
    private MenuItem questionAddButton;
    @FXML
    private MenuItem romanQuestionAddButton;
    @FXML
    private MenuItem choiceAddButton;

    @FXML
    public MenuItem refreshButton;

    @FXML
    private Pane detailPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        treeView.setRoot(BackendManager.getDisplayTree());
        paneListener = new DetailPaneListener();
        treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        treeView.getSelectionModel().selectedItemProperty().addListener(paneListener);


        smartAddButton.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.SHORTCUT_DOWN));
        deleteButton.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.SHORTCUT_DOWN));
        saveButton.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));
        saveAsButton.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHIFT_DOWN, KeyCombination.SHORTCUT_DOWN));
        newButton.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN));
        closeButton.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.SHORTCUT_DOWN));
        openButton.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN));
        exportButton.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN));


        newButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BackendManager.setExam(Exam.createExampleExam());
                forceRefresh();
            }
        });

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File toSave = ((Exam) treeView.getRoot().getValue()).getSaveLoc();
                if (toSave == null) {
                    saveAsButton.getOnAction().handle(null);
                    return;
                }
                FileManager.write(((Exam) treeView.getRoot().getValue()), toSave);

            }
        });

        saveAsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Exam toWrite = ((Exam) treeView.getRoot().getValue());
                FileChooser saveAs = new FileChooser();
                saveAs.setTitle("Save As");
                saveAs.getExtensionFilters().add(new FileChooser.ExtensionFilter("TestTeX Exam", "*.tte"));
                File saveLoc = saveAs.showSaveDialog(stage);
                toWrite.setSaveLoc(saveLoc);
                FileManager.write(toWrite, saveLoc);
            }
        });

        openButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser open = new FileChooser();
                open.setTitle("Open Exam");
                open.getExtensionFilters().add(new FileChooser.ExtensionFilter("TestTeX Exam", "*.tte"));
                BackendManager.setExam(FileManager.read(open.showOpenDialog(stage)));
                stage.setTitle("TestTeX: " + BackendManager.getExam().getExamTitle());
                forceRefresh();
            }
        });

        exportButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File toExport = BackendManager.getExam().getExportLoc();
                if (toExport == null) {
                    FileChooser export = new FileChooser();
                    export.setTitle("Export");
                    export.getExtensionFilters().add(new FileChooser.ExtensionFilter("TeX Exam", "*.tex"));
                    toExport = export.showSaveDialog(stage);
                    BackendManager.getExam().setExportLoc(toExport);
                }
                FileManager.export(BackendManager.getExam(), toExport);
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
                    choiceAddButton.getOnAction().handle(null);
                    /*
                    Choice toAdd = new Choice("Response");

                    ((Question) ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getParent().getValue()).addChoice(toAdd);
                    ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getChildren().add(toAdd.toDisplayFormat());
                    */
                } else if (o.getClass().equals(Choice.class)) {
                    choiceAddButton.getOnAction().handle(null);
                    /*
                    Choice toAdd = new Choice("Response");
                    ((com.mehnadnerd.testtex.data.question.Question) ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getParent().getParent().getValue()).addChoice(toAdd);
                    ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getParent().getChildren().add(toAdd.toDisplayFormat());
                    */
                } else if (o.getClass().equals(RomanChoice.class)) {
                    choiceAddButton.getOnAction().handle(null);
                } else {
                    choiceAddButton.getOnAction().handle(null);
                    System.out.println(o.getClass());
                }
                //forceRefresh();
            }
        });

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Object toDelete = ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getValue();
                System.out.println("Attempting to remove a " + toDelete.getClass().getCanonicalName());

                //remove from data structure

                if (toDelete.getClass().equals(Exam.class)) {
                    //sanity check, doesn't delete root
                    System.err.println("Attempting to delete exam node (Is root node)");
                    return;
                } else if (toDelete.getClass().equals(String.class)) {
                    //sanity check, strings are for labelling things
                    System.err.println("Attempting to delete String node, need to remove parent");
                    return;
                } else if (Question.class.isAssignableFrom(toDelete.getClass())) {
                    //remove from exam
                    ((Exam) treeView.getRoot().getValue()).removeQuestion((Question) toDelete);
                } else if (toDelete.getClass().equals(Choice.class)
                        && ((TreeItem) treeView.getSelectionModel().getSelectedItem())
                        .getParent().getParent().getValue().getClass().equals(Question.class)) {

                    ((Question) ((TreeItem) treeView.getSelectionModel().getSelectedItem())
                            .getParent().getParent().getValue()).removeChoice((Choice) toDelete);

                } else if (toDelete.getClass().equals(Choice.class)
                        && ((TreeItem) treeView.getSelectionModel().getSelectedItem())
                        .getParent().getParent().getValue().getClass().equals(RomanQuestion.class)) {

                    ((RomanQuestion) ((TreeItem) treeView.getSelectionModel().getSelectedItem())
                            .getParent().getParent().getValue()).removeRomanOption((Choice) toDelete);

                } else if (toDelete.getClass().equals(RomanChoice.class)) {
                    ((RomanQuestion) ((TreeItem) treeView.getSelectionModel().getSelectedItem())
                            .getParent().getParent().getValue()).removeChoice((RomanChoice) toDelete);
                }


                //remove from view tree
                ((TreeItem) treeView.getSelectionModel().getSelectedItem()).getParent().getChildren()
                        .remove(treeView.getSelectionModel().getSelectedItem());


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

        choiceAddButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Choice toAdd = new Choice();
                        TreeItem selected = ((TreeItem) treeView.getSelectionModel().getSelectedItem());
                        if (selected.getValue().getClass().equals(Question.class)) {
                            ((Question) selected.getValue()).addChoice(toAdd);
                            ((TreeItem) (selected).getChildren().get(0))
                                    .getChildren().add(toAdd.toDisplayFormat());
                        } else if (selected.getClass().equals(ChoiceContainerTreeItem.class)
                                && selected.getValue().equals(TestTeXConstants.CHOICECONTAINERROMANOPTIONTEXT)) {
                            //roman option container
                            RomanQuestion q = (RomanQuestion) selected.getParent().getValue();
                            q.addRomanOption(toAdd);
                            selected.getChildren().add(toAdd.toDisplayFormat());
                        } else if (selected.getClass().equals(ChoiceContainerTreeItem.class)
                                && selected.getValue().equals(TestTeXConstants.CHOICECONTAINERTREEITEMTEXT)
                                && selected.getParent().getValue().getClass().equals(RomanQuestion.class)) {
                            //Container for RomanChoices under a RomanQuestion
                            RomanQuestion q = (RomanQuestion) selected.getParent().getValue();
                            toAdd = new RomanChoice(q);

                            q.addChoice(toAdd);
                            selected.getChildren().add(toAdd.toDisplayFormat());

                        } else if (selected.getClass().equals(ChoiceContainerTreeItem.class)
                                && selected.getValue().equals(TestTeXConstants.CHOICECONTAINERTREEITEMTEXT)
                                && selected.getParent().getValue().getClass().equals(Question.class)) {
                            //Is a container for Choices under a Question
                            ((Question) (selected).getParent().getValue())
                                    .addChoice(toAdd);
                            (selected).getChildren().add(toAdd.toDisplayFormat());

                        } else if (selected.getValue().getClass().equals(Choice.class)
                                && selected.getParent().getParent().getValue().getClass().equals(Question.class)) {
                            //Choice, so add as sibling
                            ((Question) selected.getParent().getParent().getValue()).addChoice(toAdd);
                            selected.getParent().getChildren().add(toAdd.toDisplayFormat());

                        } else if (selected.getValue().getClass().equals(Choice.class)
                                && selected.getParent().getParent().getValue().getClass().equals(RomanQuestion.class)) {

                            //RomanOption, so add option

                            ((RomanQuestion) selected.getParent().getParent().getValue()).addRomanOption(toAdd);
                            selected.getParent().getChildren().add(toAdd.toDisplayFormat());

                        } else if (selected.getValue().getClass().equals(RomanChoice.class)) {
                            //create the roman choice
                            toAdd = new RomanChoice((RomanQuestion) selected.getParent().getParent().getValue());
                            //add to the roman question as a choice
                            ((RomanQuestion) selected.getParent().getParent().getValue()).addChoice(toAdd);
                            //add to display tree
                            selected.getParent().getChildren().add(toAdd.toDisplayFormat());

                        } else {
                            System.out.println("Failed to add choice to " + selected.getValue().getClass().getCanonicalName());
                            //Fail to add anything
                            //treeView.getRoot().getChildren().add(toAdd.toDisplayFormat());
                        }
                    }
                }

        );

        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
                                      @Override
                                      public void handle(ActionEvent event) {
                                          forceRefresh();
                                      }
                                  }

        );
    }

    private void forceRefresh() {
        treeView.setRoot(BackendManager.getDisplayTree());
        treeView.refresh();
    }

    private class DetailPaneListener implements EventHandler<ActionEvent>, ChangeListener<TreeItem<Object>> {
        private ChoiceDetailController choiceDetail;
        private ExamDetailController examDetail;
        private QuestionDetailController questionDetail;
        private RomanChoiceDetailController romanChoiceDetail;

        private Pane choiceDetailPane;
        private Pane examDetailPane;
        private Pane questionDetailPane;
        private Pane noneDetailPane;
        private Pane romanChoiceDetailPane;

        public DetailPaneListener() {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainViewController.class.getResource("detail/detailChoice.fxml"));
                choiceDetailPane = fxmlLoader.load();
                choiceDetail = fxmlLoader.getController();
                fxmlLoader = new FXMLLoader(MainViewController.class.getResource("detail/detailExam.fxml"));
                examDetailPane = fxmlLoader.load();
                examDetail = fxmlLoader.getController();
                fxmlLoader = new FXMLLoader(MainViewController.class.getResource("detail/detailQuestion.fxml"));
                questionDetailPane = fxmlLoader.load();
                questionDetail = fxmlLoader.getController();
                fxmlLoader = new FXMLLoader(MainViewController.class.getResource("detail/detailRomanChoice.fxml"));
                romanChoiceDetailPane = fxmlLoader.load();
                romanChoiceDetail = fxmlLoader.getController();
                fxmlLoader = new FXMLLoader(MainViewController.class.getResource("detail/detailNone.fxml"));
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
                    c = choiceDetail.writeChoice(c);//n.b. write choice mutates
                    treeView.refresh();
                } else if (oldValue.getValue().getClass().equals(Exam.class)) {
                    Exam e = (Exam) oldValue.getValue();
                    e = examDetail.writeExam(e);//mutates, so assignment is actually unnessecary
                    stage.setTitle("TestTeX: " + e.getExamTitle());
                    // e.setExamTitle(((TextField) examDetailPane.getChildren().get(1)).getText());
                } else if (oldValue.getValue().getClass().equals(Question.class)
                        || oldValue.getValue().getClass().equals(RomanQuestion.class)) {
                    Question q = (Question) oldValue.getValue();
                    q = questionDetail.writeQuestion(q);
                    //q.setQuestionText(((TextField) questionDetailPane.getChildren().get(1)).getText());
                } else if (oldValue.getValue().getClass().equals(RomanChoice.class)) {
                    RomanChoice c = (RomanChoice) oldValue.getValue();
                    c = romanChoiceDetail.writeRomanChoice(c);
                }
            }
            if (newValue != null) {
                //change detailPane for new value
                if (newValue.getValue().getClass().equals(Choice.class)) {
                    detailPane.getChildren().set(0, choiceDetailPane);
                    choiceDetail.loadChoice((Choice) newValue.getValue());
                    //((TextField) choiceDetailPane.getChildren().get(1)).setText(newValue.getValue().toString());
                } else if (newValue.getValue().getClass().equals(Exam.class)) {
                    detailPane.getChildren().set(0, examDetailPane);
                    examDetail.loadExam((Exam) newValue.getValue());
                    //((TextField) examDetailPane.getChildren().get(1)).setText(newValue.getValue().toString());
                } else if (newValue.getValue().getClass().equals(Question.class)
                        || newValue.getValue().getClass().equals(RomanQuestion.class)) {
                    detailPane.getChildren().set(0, questionDetailPane);
                    questionDetail.loadQuestion((Question) newValue.getValue());
                    //((TextField) questionDetailPane.getChildren().get(1)).setText(newValue.getValue().toString());
                } else if (newValue.getValue().getClass().equals(RomanChoice.class)) {
                    detailPane.getChildren().set(0, romanChoiceDetailPane);
                    romanChoiceDetail.loadRomanChoice((RomanChoice) newValue.getValue());
                } else {
                    detailPane.getChildren().set(0, noneDetailPane);
                }
            } else {
                detailPane.getChildren().set(0, noneDetailPane);
            }
            //forceRefresh();


        }

        @Override
        public void handle(ActionEvent event) {

        }
    }

    public void setStage(Stage s) {
        this.stage = s;
    }
}
