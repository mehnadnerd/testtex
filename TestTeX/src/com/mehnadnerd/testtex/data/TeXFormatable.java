package com.mehnadnerd.testtex.data;

import java.io.Serializable;

public interface TeXFormatable extends Serializable {
	/**
	 * 
	 * @return The object formatted with the LaTeX exam package
	 */
	String toTeXFormat();
	
	/**
	 * 
	 * @return The object formatted with the LaTeX exam package, with answers included
	 */
	//public String toTeXAnswerFormat();
}
