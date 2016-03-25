package com.mehnadnerd.testtex.data.choice;

import com.mehnadnerd.testtex.data.SaveFormatable;
import com.mehnadnerd.testtex.data.TeXFormatable;

public class Choice implements SaveFormatable, TeXFormatable {
	protected String ctext;
	protected String explanation;
	protected boolean isCorrect;
	
	public Choice() {
		
	}
	
	public Choice(String ctext, String explanation, boolean isCorrect) {
		this.ctext=ctext;
		this.explanation=explanation;
		this.isCorrect=isCorrect;
	}
	
	@Override
	public String toTeXFormat() {
		StringBuffer toRet=new StringBuffer();
		toRet.append("\\choice\n");
		toRet.append(ctext);
		toRet.append("\n");
		return toRet.toString();
	}
	
	public boolean isCorrect() {
		return isCorrect;
	}

}
