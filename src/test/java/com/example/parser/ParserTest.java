package com.example.parser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ParserTest {

	@Autowired
	private Parser parser;
	
	@Test
	void itemHasInstructionIsOKTest() {
		assertTrue(parser.itemHasInstruction("156528"));
	}

	@Test
	void itemHasInstructionIsNotFoundTest() {
		assertFalse(parser.itemHasInstruction("146895011"));
	}

	@Test
	void itemHasInstructionNotExistsTest() {
		assertFalse(parser.itemHasInstruction("0123456789"));
	}
	
}
