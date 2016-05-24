package com.mehnadnerd.testtex.gui;

import com.mehnadnerd.testtex.BackendManager;
import com.mehnadnerd.testtex.FileManager;
import com.mehnadnerd.testtex.data.choice.Choice;
import com.mehnadnerd.testtex.data.choice.RomanChoice;
import com.mehnadnerd.testtex.data.exam.Exam;
import com.mehnadnerd.testtex.data.question.Question;
import com.mehnadnerd.testtex.data.question.RomanQuestion;
import com.mehnadnerd.testtex.data.resource.CodeResource;
import com.mehnadnerd.testtex.data.resource.ImageResource;
import com.mehnadnerd.testtex.data.resource.TextResource;
import com.mehnadnerd.testtex.gui.detail.*;
import com.mehnadnerd.testtex.util.ListManipulator;
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
    public MenuItem randomQuestionButton;
    @FXML
    public MenuItem randomChoiceButton;
    @FXML
    public MenuItem randomAllChoiceButton;

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
                BackendManager.createNewExam();
                System.out.println("Calling force refresh from new");
                forceRefresh();
            }
        });

        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File toSave = BackendManager.getExam().getSaveLoc();
                if (toSave == null) {
                    saveAsButton.getOnAction().handle(null);
                    return;
                }
                FileManager.write(BackendManager.getExam(), toSave);

            }
        });

        saveAsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Exam toWrite = BackendManager.getExam();
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
                    BackendManager.getExam().addQuestion(toAdd);
                    System.out.println("Check for equality in exam: " + (o == BackendManager.getExam()));
                    assert (o == BackendManager.getExam());
                    treeView.getRoot().getChildren().add(toAdd.toDisplayFormat());
                } else if (o.getClass().equals(Question.class)) {
                    Question toAdd = new Question();
                    BackendManager.getExam().addQuestion(toAdd);
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
                    BackendManager.getExam().removeQuestion((Question) toDelete);
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
                BackendManager.getExam().addQuestion(toAdd);
                treeView.getRoot().getChildren().add(toAdd.toDisplayFormat());
            }
        });

        romanQuestionAddButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        RomanQuestion toAdd = new RomanQuestion();
                        BackendManager.getExam().addQuestion(toAdd);
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

        refreshButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println(BackendManager.getExam().toTeXFormat());
                        forceRefresh();
                    }
                }

        );


        //randomise order of questions
        randomQuestionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BackendManager.getExam().setQuestions(
                        ListManipulator.randomise(BackendManager.getExam().getQuestions()));
                forceRefresh();
            }
        });

        randomChoiceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TreeItem selected = ((TreeItem) treeView.getSelectionModel().getSelectedItem());
                if (selected == null) {
                    System.err.println("Attempting to randomise choices, null selected");
                    return;
                }
                if (selected.getValue().getClass().equals(Question.class)) {
                    ((Question) selected.getValue()).setChoices(
                            ListManipulator.randomise(((Question) selected.getValue()).getChoices()));
                } else if (selected.getValue().getClass().equals(RomanQuestion.class)) {
                    ((RomanQuestion) selected.getValue()).setRomanOptions(
                            ListManipulator.randomise(((RomanQuestion) selected.getValue()).getRomanOptions()));
                } else {
                    System.err.println("Attempting to randomise from " + selected.getValue().getClass().getCanonicalName() + ", can't.");
                }
                forceRefresh();
            }
        });

        randomAllChoiceButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (Question q : BackendManager.getExam().getQuestions()) {
                    if (q.getClass().equals(RomanQuestion.class)) {
                        ((RomanQuestion) q).setRomanOptions(
                                ListManipulator.randomise(((RomanQuestion) q).getRomanOptions()));
                    } else {
                        q.setChoices(ListManipulator.randomise(q.getChoices()));
                    }
                }
                forceRefresh();
            }
        });

        this.newButton.getOnAction().handle(null);
        forceRefresh();

    }

    private void forceRefresh() {
        System.out.println("Exams were equal " + (treeView.getRoot().getValue() == BackendManager.getExam()));
        System.out.println("Refreshing, old was: " + treeView.getRoot().getValue().toString());
        treeView.setRoot(BackendManager.getDisplayTree());

        treeView.refresh();
    }

    private class DetailPaneListener implements EventHandler<ActionEvent>, ChangeListener<TreeItem<Object>> {
        private ChoiceDetailController choiceDetail;
        private ExamDetailController examDetail;
        private QuestionDetailController questionDetail;
        private RomanChoiceDetailController romanChoiceDetail;
        private CodeResourceController codeResourceDetail;
        private ImageResourceController imageResourceDetail;
        private TextResourceController textResourceDetail;

        private Pane choiceDetailPane;
        private Pane examDetailPane;
        private Pane questionDetailPane;
        private Pane noneDetailPane;
        private Pane romanChoiceDetailPane;
        private Pane codeResourceDetailPane;
        private Pane imageResourceDetailPane;
        private Pane textResourceDetailPane;

        public DetailPaneListener() {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(MainViewController.class.getResource("detail/detailChoice.fxml"));
                choiceDetailPane = fxmlLoader.load();
                choiceDetail = fxmlLoader.getController();
                choiceDetail.setEnterHandler(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        choiceDetailFromPanel(((Choice) ((TreeItem) treeView.getSelectionModel()
                                .getSelectedItem()).getValue()));
                    }
                });
                fxmlLoader = new FXMLLoader(MainViewController.class.getResource("detail/detailExam.fxml"));
                examDetailPane = fxmlLoader.load();
                examDetail = fxmlLoader.getController();
                examDetail.setEnterHandler(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        examDetailFromPanel(BackendManager.getExam());
                    }
                });
                fxmlLoader = new FXMLLoader(MainViewController.class.getResource("detail/detailQuestion.fxml"));
                questionDetailPane = fxmlLoader.load();
                questionDetail = fxmlLoader.getController();
                questionDetail.setEnterHandler(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        questionDetailFromPanel(((Question) ((TreeItem) treeView.getSelectionModel()
                                .getSelectedItem()).getValue()));
                    }
                });
                fxmlLoader = new FXMLLoader(MainViewController.class.getResource("detail/detailRomanChoice.fxml"));
                romanChoiceDetailPane = fxmlLoader.load();
                romanChoiceDetail = fxmlLoader.getController();
                romanChoiceDetail.setEnterHandler(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        romanChoiceDetailFromPanel(((RomanChoice) ((TreeItem) treeView.getSelectionModel()
                                .getSelectedItem()).getValue()));
                    }
                });
                fxmlLoader = new FXMLLoader(MainViewController.class.getResource("detail/detailNone.fxml"));
                noneDetailPane = fxmlLoader.load();

                fxmlLoader = new FXMLLoader(MainViewController.class.getResource("detail/detailCodeResource.fxml"));
                codeResourceDetailPane = fxmlLoader.load();
                codeResourceDetail = fxmlLoader.getController();

                fxmlLoader = new FXMLLoader(MainViewController.class.getResource("detail/detailImageResource.fxml"));
                imageResourceDetailPane = fxmlLoader.load();
                imageResourceDetail = fxmlLoader.getController();
                imageResourceDetail.setStage(stage);

                fxmlLoader = new FXMLLoader(MainViewController.class.getResource("detail/detailTextResource.fxml"));
                textResourceDetailPane = fxmlLoader.load();
                textResourceDetail = fxmlLoader.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }
            detailPane.getChildren().add(examDetailPane);
            ((TextField) examDetailPane.getChildren().get(1)).setText(BackendManager.getExam().toString());
            treeView.getSelectionModel().select(treeView.getRoot());
        }

        private void examDetailFromPanel(Exam e) {
            e = examDetail.writeExam(e);//mutates, so assignment is actually unnessecary
            if (stage != null) {
                stage.setTitle("TestTeX: " + e.getExamTitle());
            }
            treeView.refresh();
        }

        private void choiceDetailFromPanel(Choice c) {
            c = choiceDetail.writeChoice(c);//mutates, so assignment is actually unnessecary
            treeView.refresh();
        }

        private void questionDetailFromPanel(Question q) {
            q = questionDetail.writeQuestion(q);//mutates, so assignment is actually unnessecary
            treeView.refresh();
        }

        private void romanChoiceDetailFromPanel(RomanChoice r) {
            r = romanChoiceDetail.writeRomanChoice(r);//mutates, so assignment is actually unnessecary
            treeView.refresh();
        }

        private void codeResourceDetailFromPanel(CodeResource r) {
            r = codeResourceDetail.writeCodeResource(r);
            treeView.refresh();
        }

        private void imageResourceDetailFromPanel(ImageResource r) {
            r = imageResourceDetail.writeImageResource(r);
            treeView.refresh();
        }

        private void textResourceDetailFromPanel(TextResource r) {
            r = textResourceDetail.writeTextResource(r);
            treeView.refresh();
        }

        @Override
        public void changed(ObservableValue<? extends TreeItem<Object>> observable, TreeItem<Object> oldValue, TreeItem<Object> newValue) {
            //use old values to record changes
            if (oldValue != null) {
                if (oldValue.getValue().getClass().equals(Choice.class)) {
                    Choice c = ((Choice) oldValue.getValue());
                    choiceDetailFromPanel(c);
                    treeView.refresh();
                } else if (oldValue.getValue().getClass().equals(Exam.class)) {
                    examDetailFromPanel(BackendManager.getExam());
                } else if (oldValue.getValue().getClass().equals(Question.class)
                        || oldValue.getValue().getClass().equals(RomanQuestion.class)) {
                    Question q = (Question) oldValue.getValue();
                    questionDetailFromPanel(q);
                } else if (oldValue.getValue().getClass().equals(RomanChoice.class)) {
                    RomanChoice c = (RomanChoice) oldValue.getValue();
                    romanChoiceDetailFromPanel(c);
                } else if (oldValue.getValue().getClass().equals(CodeResource.class)) {
                    CodeResource c = (CodeResource) oldValue.getValue();
                    codeResourceDetailFromPanel(c);
                } else if (oldValue.getValue().getClass().equals(ImageResource.class)) {
                    ImageResource i = (ImageResource) oldValue.getValue();
                    imageResourceDetailFromPanel(i);
                } else if (oldValue.getValue().getClass().equals(TextResource.class)) {
                    TextResource t = (TextResource) oldValue.getValue();
                    textResourceDetailFromPanel(t);
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
                    examDetail.loadExam(BackendManager.getExam());

                    //((TextField) examDetailPane.getChildren().get(1)).setText(newValue.getValue().toString());
                } else if (newValue.getValue().getClass().equals(Question.class)
                        || newValue.getValue().getClass().equals(RomanQuestion.class)) {
                    detailPane.getChildren().set(0, questionDetailPane);
                    questionDetail.loadQuestion((Question) newValue.getValue());
                    //((TextField) questionDetailPane.getChildren().get(1)).setText(newValue.getValue().toString());
                } else if (newValue.getValue().getClass().equals(RomanChoice.class)) {
                    detailPane.getChildren().set(0, romanChoiceDetailPane);
                    romanChoiceDetail.loadRomanChoice((RomanChoice) newValue.getValue());

                } else if (newValue.getValue().getClass().equals(CodeResource.class)) {
                    detailPane.getChildren().set(0, codeResourceDetailPane);
                    codeResourceDetail.loadCodeResource((CodeResource) newValue.getValue());
                } else if (newValue.getValue().getClass().equals(ImageResource.class)) {
                    detailPane.getChildren().set(0, imageResourceDetailPane);
                    imageResourceDetail.loadImageResource((ImageResource) newValue.getValue());
                } else if (newValue.getValue().getClass().equals(TextResource.class)) {
                    detailPane.getChildren().set(0, textResourceDetailPane);
                    textResourceDetail.loadTextResource((TextResource) newValue.getValue());

                } else {
                    detailPane.getChildren().set(0, noneDetailPane);
                }
            } else {
                detailPane.getChildren().set(0, noneDetailPane);
            }
            treeView.refresh();
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
