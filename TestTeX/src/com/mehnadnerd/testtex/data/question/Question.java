package com.mehnadnerd.testtex.data.question;

import java.util.ArrayList;
import java.util.List;

import com.mehnadnerd.testtex.data.SaveFormatable;
import com.mehnadnerd.testtex.data.TeXFormatable;
import com.mehnadnerd.testtex.data.choice.AllOfTheAboveChoice;
import com.mehnadnerd.testtex.data.choice.Choice;
import com.mehnadnerd.testtex.data.choice.MetaChoice;

public class Question implements SaveFormatable, TeXFormatable {
	protected String qtext;
	protected List<Choice> choices = new ArrayList<Choice>();
	protected int pointval;

	public Question() {
	}
	
	public Question(String question, List<Choice> choices) {
		qtext=question;
		this.choices=choices;
	}

	@Override
	public String toTeXFormat() {
		StringBuffer toRet = new StringBuffer();
		toRet.append("\\question \n \\points");
		toRet.append(pointval);
		toRet.append("\n");
		toRet.append(qtext);
		toRet.append("\n");
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