package com.mehnadnerd.testtex.gui.detail;

import com.mehnadnerd.testtex.data.resource.ImageResource;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mehnadnerd on 2016-04-18.
 */
public class ImageResourceController extends DetailController {
    @FXML
    public Button SelectFileButton;

    protected File fileToLoad;

    private Stage stage;

    public File getResourceImage() {
        return fileToLoad;
    }

    public void setResourceImage(File f) {
        fileToLoad = f;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SelectFileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser f = new FileChooser();
                f.setTitle("Chppse Image");
                f.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                        new FileChooser.ExtensionFilter("JPEG Image", "*.jpg"), new FileChooser.ExtensionFilter("EPS", "*.eps"));
                setResourceImage(f.showOpenDialog(stage));
            }
        });

    }

    public ImageResource writeImageResource(ImageResource r) {
        r.setImgFile(getResourceImage());
        return r;
    }

    public void loadImageResource(ImageResource r) {
        this.setResourceImage(r.getImgFile());
    }

    @Override
    public void setEnterHandler(EventHandler<ActionEvent> handler) {
        //? What to do
    }

    public void setStage(Stage s) {
        this.stage = s;
    }
}
