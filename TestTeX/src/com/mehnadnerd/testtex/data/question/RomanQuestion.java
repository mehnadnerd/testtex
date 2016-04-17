package com.mehnadnerd.testtex.data.question;

import com.mehnadnerd.testtex.data.choice.Choice;

import java.util.ArrayList;
import java.util.List;

public class RomanQuestion extends Question {
	private List<Choice> romanchoices = new ArrayList<Choice>();

	public int getRomanChoiceNum(Choice c) {
		return romanchoices.indexOf(c);
	}
	@Override
	public String toTeXFormat() {
		StringBuffer toRet = new StringBuffer();
		toRet.append("\\question \n \\points");
		toRet.append(pointval);
		toRet.append("\n");
		toRet.append(questiontext);
		toRet.append("\n\\begin{enumerate}\n");
		for (Choice c: romanchoices) {
			toRet.append(c.toTeXFormat().replace("\\choice", "\\item"));
		}
		toRet.append("\\end{enumerate}\n");
		//main choice format processing
		for (Choice c : choices) {
			toRet.append(c.toTeXFormat());
		}

		return toRet.toString();
	}
}
