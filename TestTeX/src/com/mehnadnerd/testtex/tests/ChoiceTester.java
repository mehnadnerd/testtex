package com.mehnadnerd.testtex.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mehnadnerd.testtex.data.choice.Choice;

public class ChoiceTester {
	@Test
	public void test() {
		Choice c = new Choice("Turtwig","Turtwig is the best",true);
		assertEquals(c.toTeXFormat(),"\\choice\nTurtwig\n");
		assert(c.isCorrect());
	}

}
