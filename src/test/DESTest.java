package test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import main.DES;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DESTest {

	private DES des;
	
	@BeforeAll
	void setUp() {
		des = new DES();
	}
	
	@Test
	void testStringToBits() {
		// GIVEN a string
		String message = "Hello world!";
		int[] expectedResult = {0,1,0,0,1,0,0,0,0,1,1,0,0,1,0,1,0,1,1,0,1,1,0,
				0,0,1,1,0,1,1,0,0,0,1,1,0,1,1,1,1,0,0,1,0,0,0,0,0,0,1,1,1,0,1,
				1,1,0,1,1,0,1,1,1,1,0,1,1,1,0,0,1,0,0,1,1,0,1,1,0,0,0,1,1,0,0,
				1,0,0,0,0,1,0,0,0,0,1};
		// WHEN the string is converted to bits
		// THEN
		assertArrayEquals(des.stringToBits(message), expectedResult);
	}
}
