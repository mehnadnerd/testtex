package com.mehnadnerd.testtex.gui.detail;

import com.mehnadnerd.testtex.data.choice.Choice;
import com.mehnadnerd.testtex.data.choice.RomanChoice;
import com.mehnadnerd.testtex.data.choice.RomanChoiceXref;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by mehnadnerd on 2016-04-18.
 */
public class RomanChoiceDetailController implements Initializable {

    @FXML
    public Pane rootPane;
    @FXML
    public Pane choicePane;

    private List<CheckBox> optionChecks;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public RomanChoice writeRomanChoice(RomanChoice c) {
        List<Choice> possibleRefs = c.getEncaps().getRomanOptions();
        List<RomanChoiceXref> toSet = new ArrayList<>();
        for (int i = 0; i < optionChecks.size(); i++) {
            if (optionChecks.get(i).isSelected()) {
                toSet.add(new RomanChoiceXref(c.getEncaps(), possibleRefs.get(i)));
            }
        }

        c.setRefs(toSet);
        return c;
    }

    public void loadRomanChoice(RomanChoice c) {
        choicePane.getChildren().removeAll(choicePane.getChildren());
        optionChecks = new ArrayList<>();
        //System.out.println(c.getEncaps().getRomanOptions());
        for (Choice r : c.getEncaps().getRomanOptions()) {
            CheckBox toAdd = new CheckBox(r.toString());
            toAdd.setLayoutX(14);
            toAdd.setLayoutY(55 + 30 * optionChecks.size());
            //TODO: Align check boxes
            //checks whether the check box should be selected, by seeing if the romanchoice has that as a ref
            if (c.getRefs().contains(new RomanChoiceXref(c.getEncaps(), r))) {
                toAdd.setSelected(true);
            }
            //System.out.println("Adding Checkbox with name " + toAdd.getText());
            optionChecks.add(toAdd);
        }
        choicePane.getChildren().addAll(optionChecks);
    }
}
