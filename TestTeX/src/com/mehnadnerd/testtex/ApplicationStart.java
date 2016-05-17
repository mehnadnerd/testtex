package com.mehnadnerd.testtex;

import com.mehnadnerd.testtex.gui.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationStart extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui/main.fxml"));
        Parent root = fxmlLoader.load();
        MainViewController m = fxmlLoader.getController();
        m.setStage(stage);
        stage.setTitle("TestTeX");
        BackendManager.setStage(stage);
        BackendManager.createNewExam();
        Scene scene = new Scene(root, 1000, 600);


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(ApplicationStart.class, args);
    }

}
