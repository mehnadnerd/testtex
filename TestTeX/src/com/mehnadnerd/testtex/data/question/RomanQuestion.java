package com.mehnadnerd.testtex.data.question;

import java.util.ArrayList;
import java.util.List;

import com.mehnadnerd.testtex.data.choice.AllOfTheAboveChoice;
import com.mehnadnerd.testtex.data.choice.Choice;
import com.mehnadnerd.testtex.data.choice.MetaChoice;

public class RomanQuestion extends Question {
	private List<Choice> romanchoices = new ArrayList<Choice>();
	@Override
	public String toTeXFormat() {
		StringBuffer toRet = new StringBuffer();
		toRet.append("\\question \n \\points");
		toRet.append(pointval);
		toRet.append("\n");
		toRet.append(qtext);
		toRet.append("\n\\begin{enumerate}\n");
		for (Choice c: romanchoices) {
			toRet.append(c.toTeXFormat().replace("\\choice", "\\item"));
		}
		toRet.append("\\end{enumerate}\n");
		//main choice format processing
		for (Choice c : choices) {
			if (c instanceof MetaChoice) {
				// special processing code
			} else {
				// standard choice formatting
				toRet.append(c.toTeXFormat());
			}
		}
		//secondary choice format processing  (for special cases)
		for (Choice c : choices) {
			if (c instanceof AllOfTheAboveChoice) {
				toRet.append(c.toTeXFormat());
			}
		}
		return toRet.toString();
	}
}
