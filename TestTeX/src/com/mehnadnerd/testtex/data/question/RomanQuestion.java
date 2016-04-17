package com.mehnadnerd.testtex.data.question;

import com.mehnadnerd.testtex.data.choice.Choice;

import java.util.ArrayList;
import java.util.List;

public class RomanQuestion extends Question {
	private List<Choice> romanchoices = new ArrayList<>();

	public void addRomanOption(Choice c) {
		romanchoices.add(c);
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
}
