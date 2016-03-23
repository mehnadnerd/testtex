package com.mehnadnerd.testtex.data;

import java.util.List;

public class Question implements SaveFormatable, TeXFormatable {
	String qtext;
	List<Choice> choices;
	public Question() {
		
	}
	@Override
	public String toTeXFormat() {
		// TODO Auto-generated method stub
		return null;
	}
}
