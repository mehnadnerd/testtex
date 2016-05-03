package com.mehnadnerd.testtex;

import com.mehnadnerd.testtex.data.exam.Exam;
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
        BackendManager.setStage(stage);
        BackendManager.setExam(Exam.createExampleExam());
        Scene scene = new Scene(root, 1000, 600);

        stage.setTitle("TestTeX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(ApplicationStart.class, args);
    }
    //Design Thingies
    /* Have external reference as own class, so no need for metachoices-implement with ChociePlacementRequirements
     *
	 * 
	 * 
	 * 
	 */

}
