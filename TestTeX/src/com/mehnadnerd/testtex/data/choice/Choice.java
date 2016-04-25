package com.mehnadnerd.testtex.data.choice;

import com.mehnadnerd.testtex.data.DisplayFormatable;
import com.mehnadnerd.testtex.data.TeXFormatable;
import com.mehnadnerd.testtex.util.TestTeXConstants;
import com.mehnadnerd.testtex.util.TurtleStringModifiy;
import javafx.scene.control.TreeItem;

public class Choice implements DisplayFormatable, TeXFormatable {
	protected String ctext;
	protected String explanation;
	protected boolean isCorrect;
	
	public Choice() {
		
	}

	public Choice(String ctext) {
		this(ctext, "", false);
	}

	public Choice(String ctext, boolean isCorrect) {
		this(ctext, "", isCorrect);
	}

	public void setChoiceText(String s) {
		this.ctext = s;
	}

	public void setExplanation(String s) {
		this.explanation = s;
	}

	public void setCorrect(boolean b) {
		this.isCorrect = b;
	}
	
	public Choice(String ctext, String explanation, boolean isCorrect) {
		this.ctext=ctext;
		this.explanation=explanation;
		this.isCorrect=isCorrect;
	}
	
	@Override
	public String toTeXFormat() {
		StringBuffer toRet=new StringBuffer();
		if (isCorrect) {
			toRet.append("\\CorrectChoice ");
		} else {
			toRet.append("\\choice ");
		}

		toRet.append(ctext);
		toRet.append("\n");
		return toRet.toString();
	}
	
	public boolean isCorrect() {
		return isCorrect;
	}

	@Override
	public TreeItem toDisplayFormat() {
		return new TreeItem<>(this);
	}

	@Override
	public String toString() {
		return TurtleStringModifiy.trim(ctext, TestTeXConstants.maxDisplayChars);
	}
}
