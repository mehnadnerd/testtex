package com.mehnadnerd.testtex.data.choice;

import com.mehnadnerd.testtex.data.SaveFormatable;
import com.mehnadnerd.testtex.data.TeXFormatable;

public class Choice implements SaveFormatable, TeXFormatable {
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
		this.isCorrect = isCorrect;
	}
	
	public Choice(String ctext, String explanation, boolean isCorrect) {
		this.ctext=ctext;
		this.explanation=explanation;
		this.isCorrect=isCorrect;
	}
	
	@Override
	public String toTeXFormat() {
		StringBuffer toRet=new StringBuffer();
		toRet.append("\\choice ");
		toRet.append(ctext);
		toRet.append("\n");
		return toRet.toString();
	}
	
	public boolean isCorrect() {
		return isCorrect;
	}

}
