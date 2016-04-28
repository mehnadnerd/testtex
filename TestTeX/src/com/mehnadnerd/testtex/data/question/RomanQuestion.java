package com.mehnadnerd.testtex.data.question;

import com.mehnadnerd.testtex.data.choice.Choice;
import com.mehnadnerd.testtex.gui.ChoiceContainerTreeItem;
import com.mehnadnerd.testtex.util.TestTeXConstants;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;
import java.util.List;

public class RomanQuestion extends Question {
	private List<Choice> romanchoices = new ArrayList<>();

	public void addRomanOption(Choice c) {
		romanchoices.add(c);
	}

	public List<Choice> getRomanOptions() {
		return romanchoices;
	}

	public int getRomanOptionNum(Choice c) {
		return romanchoices.indexOf(c);
	}
	@Override
	public String toTeXFormat() {
		StringBuffer toRet = new StringBuffer();
		toRet.append("\\question[");
		toRet.append(pointval);
		toRet.append("]\n");
		toRet.append(questiontext);
		toRet.append("\n\\begin{enumerate}\n");
		for (Choice c: romanchoices) {
			toRet.append(c.toTeXFormat().replace("\\choice", "\\item"));
		}
		toRet.append("\\end{enumerate}\n\\begin{choices}\n");
		//main choice format processing
		for (Choice c : choices) {
			toRet.append(c.toTeXFormat());
		}
		toRet.append("\\end{choices}\n\n");

		return toRet.toString();
	}

	@Override
	public TreeItem toDisplayFormat() {
		TreeItem toRet = super.toDisplayFormat();
		TreeItem romanOptions = new ChoiceContainerTreeItem(TestTeXConstants.CHOICECONTAINERROMANOPTIONTEXT);
		for (Choice c : romanchoices) {
			romanOptions.getChildren().add(c.toDisplayFormat());
		}
		toRet.getChildren().add(romanOptions);
		return toRet;
	}
}
