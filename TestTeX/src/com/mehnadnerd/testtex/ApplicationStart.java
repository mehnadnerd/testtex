package com.mehnadnerd.testtex;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ApplicationStart extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gui/main.fxml"));
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
