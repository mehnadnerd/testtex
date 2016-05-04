package com.mehnadnerd.testtex;

import com.mehnadnerd.testtex.data.exam.Exam;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

/**
 * Created by mehnadnerd on 2016-05-02.
 */
public class FileManager {
    public static Exam read(File f) {
        try {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (Exam) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean write(Exam exam, File f) {
        try {
            FileOutputStream fos = new FileOutputStream(f, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(exam);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean export(Exam exam, File f) {
        try {
            FileWriter fw = new FileWriter(f);
            fw.write(exam.toTeXFormat());
            System.out.println("Writing to " + f.toString() + "\n" + exam.toTeXFormat());
            fw.close();
            return pdftex(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Compiles the tex document using pdflatex
     *
     * @param f The tex file to compile
     * @return
     */
    public static boolean pdftex(File f) {
        try {
            Process p;
            ProcessBuilder pb = new ProcessBuilder("pdflatex", f.getAbsolutePath(), "--nonstopmode");

            pb.directory(f.getParentFile());
            p = pb.start();
            return true;
        } catch (IOException e) {
            Stage popup = new Stage();
            Button closePopup = new Button("OK");
            closePopup.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    popup.close();
                }
            });
            Label popupLabel = new Label("Compiling to PDF failed. Check your LaTeX installation.");
            Pane popupPane = new FlowPane();
            //set background color of each Pane
            popupPane.setStyle("-fx-padding:10px;");
            //add everything to panes
            popupPane.getChildren().addAll(popupLabel, closePopup);
            Scene popupScene = new Scene(popupPane, 600, 100);
            //make another stage for scene2
            popup.setScene(popupScene);
            //tell stage it is meannt to pop-up (Modal)
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setTitle("pdftex error");
            e.printStackTrace();
            popup.showAndWait();

        }

        return false;
    }
}
