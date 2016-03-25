package com.mehnadnerd.testtex.data;

public interface TeXFormatable {
	/**
	 * 
	 * @return The object formatted with the LaTeX exam package
	 */
	public String toTeXFormat();
	
	/**
	 * 
	 * @return The object formatted with the LaTeX exam package, with answers included
	 */
	//public String toTeXAnswerFormat();
}
